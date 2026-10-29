package com.example.bloodmod;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.util.GeckoLibUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

/**
 * CHESTER: cofre aliado con patas (animado con GeckoLib).
 *  - 20 de vida, resistencia al empuje, inmune a la caída; los jugadores solo pueden herirlo con hacha
 *  - Inventario de 27 espacios; CLICK derecho lo abre, Shift + CLICK derecho lo sienta
 *  - "Come" (guarda) objetos del suelo hasta 19 bloques y se cura con ellos
 *  - Huye de los monstruos y del fuego; sigue a su dueño; se teletransporta si está a más de 20 bloques
 *  - Si el dueño lleva 3 s quieto, deambula (hasta 19 bloques de él)
 *  - Atado con una cuerda: no se teletransporta ni sigue, pero recoge objetos cercanos
 *  - Sentado sobre una tolva, vacía su inventario en ella
 *  - Al morir suelta su llave (con su contenido), que se recarga durante 2 minutos
 */
public class ChesterEntity extends PathfinderMob implements GeoEntity {

    private static final EntityDataAccessor<Boolean> DATA_SENTADO =
            SynchedEntityData.defineId(ChesterEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_ABIERTO =
            SynchedEntityData.defineId(ChesterEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HUYENDO =
            SynchedEntityData.defineId(ChesterEntity.class, EntityDataSerializers.BOOLEAN);

    // ---- Ajustes ----
    static final byte EVENTO_EXPRESIONES = 61;
    static final byte EVENTO_OLFATO = 62;
    static final double DISTANCIA_TELEPORT_SQR = 20.0 * 20.0; // 20 bloques
    static final double RADIO_OBJETOS = 19.0;                 // ve objetos hasta a 19 bloques
    static final double RADIO_OBJETOS_ATADO = 5.0;
    static final double RADIO_DEAMBULAR = 19.0;
    static final int DUENO_QUIETO_TICKS = 60;                 // 3 s sin moverse -> deambula
    static final float CURA_POR_OBJETO = 2.0f;                // 1 corazón al comer un objeto

    // ---- Animaciones de tu archivo chester.animation.json (sin cambios) ----
    private static final RawAnimation ANIM_IDLE = RawAnimation.begin().thenLoop("estatico_cerrado");
    private static final RawAnimation ANIM_CAMINAR = RawAnimation.begin().thenLoop("walk");
    private static final RawAnimation ANIM_CORRER = RawAnimation.begin().thenLoop("CORRER");
    private static final RawAnimation ANIM_SENTADO = RawAnimation.begin().thenLoop("SENTADO");
    private static final RawAnimation ANIM_ABRIR = RawAnimation.begin().thenPlay("abriendose").thenLoop("estatico_abierto");
    private static final RawAnimation ANIM_ABRIR_SENTADO = RawAnimation.begin().thenPlay("abriendose_sentado");
    private static final RawAnimation ANIM_EXPRESION = RawAnimation.begin().thenPlay("EXPRESIONES");
    private static final RawAnimation ANIM_OLFATO = RawAnimation.begin().thenPlay("OLFATO");
    private static final RawAnimation ANIM_MUERTE = RawAnimation.begin().thenPlayAndHold("MUERTE");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    /** Inventario de Chester (27 espacios). */
    public final SimpleContainer container = new SimpleContainer(27);

    private UUID duenoId;
    private int enfriamientoOlfato;
    private Vec3 ultimaPosDueno;
    int ticksDuenoQuieto;
    private boolean huyendoDeMobs;
    private boolean abiertoPrevio;

    // Solo cliente
    private int ticksExpresiones;
    private int ticksOlfato;
    private RawAnimation animActual = ANIM_IDLE;

    public ChesterEntity(EntityType<? extends ChesterEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder crearAtributos() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.ARMOR, 20.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SENTADO, false);
        builder.define(DATA_ABIERTO, false);
        builder.define(DATA_HUYENDO, false);
    }

    // ---------- GeckoLib ----------

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<ChesterEntity>("chester", 3, this::elegirAnimacion));
    }

    private PlayState elegirAnimacion(AnimationTest<ChesterEntity> test) {
        return test.setAndContinue(this.animActual);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    // ---------- Estado ----------

    public boolean isSentado() { return this.entityData.get(DATA_SENTADO); }
    public void setSentado(boolean v) { this.entityData.set(DATA_SENTADO, v); }
    public boolean isAbierto() { return this.entityData.get(DATA_ABIERTO); }
    public boolean isHuyendo() { return this.entityData.get(DATA_HUYENDO); }
    public void setHuyendo(boolean v) { this.entityData.set(DATA_HUYENDO, v); }

    public void setDueno(Player dueno) { this.duenoId = dueno.getUUID(); }

    public Player getDueno() {
        return duenoId == null ? null : this.level().getPlayerByUUID(duenoId);
    }

    @Override
    public boolean removeWhenFarAway(double distancia) {
        return false;
    }

    // ---------- Sonidos (acepta tanto SoundEvent como Holder<SoundEvent>) ----------

    private static SoundEvent sonido(Object o) {
        if (o instanceof SoundEvent s) return s;
        if (o instanceof Holder<?> h && h.value() instanceof SoundEvent s2) return s2;
        return SoundEvents.ENDERMAN_TELEPORT;
    }

    // Al caminar suena a madera, al ser golpeado a madera y al morir como un shulker (sin @Override a propósito)
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(sonido(SoundEvents.WOOD_STEP), 0.15f, 1.0f);
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return sonido(SoundEvents.WOOD_HIT);
    }

    protected SoundEvent getDeathSound() {
        return sonido(SoundEvents.SHULKER_DEATH);
    }

    // ---------- Guardado en el mundo ----------

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        if (duenoId != null) output.putString("Dueno", duenoId.toString());
        output.putBoolean("Sentado", isSentado());
        NonNullList<ItemStack> lista = NonNullList.withSize(27, ItemStack.EMPTY);
        for (int i = 0; i < 27; i++) lista.set(i, this.container.getItem(i));
        ContainerHelper.saveAllItems(output, lista);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        String id = input.getStringOr("Dueno", "");
        if (!id.isEmpty()) {
            try {
                this.duenoId = UUID.fromString(id);
            } catch (IllegalArgumentException ignored) {
            }
        }
        setSentado(input.getBooleanOr("Sentado", false));
        NonNullList<ItemStack> lista = NonNullList.withSize(27, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, lista);
        for (int i = 0; i < 27; i++) this.container.setItem(i, lista.get(i));
    }

    // ---------- IA ----------

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new HuirGoal(this));
        this.goalSelector.addGoal(1, new BuscarAguaGoal(this));
        this.goalSelector.addGoal(2, new RecogerGoal(this));
        this.goalSelector.addGoal(3, new SeguirGoal(this));
        this.goalSelector.addGoal(4, new DeambularGoal(this));
    }

    private boolean puedeMoverse() {
        return !this.isSentado() && !this.isAbierto();
    }

    /** Huye de los monstruos hostiles (animación CORRER mientras dura). */
    private static class HuirGoal extends AvoidEntityGoal<Monster> {
        private final ChesterEntity chester;

        HuirGoal(ChesterEntity chester) {
            super(chester, Monster.class, 10.0F, 1.0, 1.5);
            this.chester = chester;
        }

        @Override
        public boolean canUse() {
            return chester.puedeMoverse() && super.canUse();
        }

        @Override
        public void start() {
            super.start();
            chester.huyendoDeMobs = true;
        }

        @Override
        public void stop() {
            super.stop();
            chester.huyendoDeMobs = false;
        }
    }

    /** Si se está quemando, busca una fuente de agua cercana para apagarse. */
    private static class BuscarAguaGoal extends Goal {
        private final ChesterEntity chester;
        private BlockPos agua;

        BuscarAguaGoal(ChesterEntity chester) {
            this.chester = chester;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (!chester.isOnFire() || chester.isInWater()) return false;
            this.agua = buscarAgua();
            return this.agua != null;
        }

        @Override
        public boolean canContinueToUse() {
            return chester.isOnFire() && !chester.getNavigation().isDone();
        }

        @Override
        public void start() {
            chester.getNavigation().moveTo(agua.getX() + 0.5, agua.getY(), agua.getZ() + 0.5, 1.5);
        }

        private BlockPos buscarAgua() {
            BlockPos base = chester.blockPosition();
            BlockPos mejor = null;
            double mejorDist = Double.MAX_VALUE;
            for (int dx = -12; dx <= 12; dx++) {
                for (int dz = -12; dz <= 12; dz++) {
                    for (int dy = -3; dy <= 3; dy++) {
                        BlockPos p = base.offset(dx, dy, dz);
                        if (chester.level().getFluidState(p).is(FluidTags.WATER)) {
                            double d = p.distSqr(base);
                            if (d < mejorDist) {
                                mejorDist = d;
                                mejor = p;
                            }
                        }
                    }
                }
            }
            return mejor;
        }
    }

    /** Va a por los objetos del suelo (los "come" y los guarda). */
    private static class RecogerGoal extends Goal {
        private final ChesterEntity chester;
        private ItemEntity objetivo;

        RecogerGoal(ChesterEntity chester) {
            this.chester = chester;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (!chester.puedeMoverse() || chester.isHuyendo() || chester.tickCount % 10 != 0) return false;
            this.objetivo = chester.buscarObjeto();
            return this.objetivo != null;
        }

        @Override
        public boolean canContinueToUse() {
            return objetivo != null && objetivo.isAlive() && chester.puedeMoverse() && !chester.isHuyendo()
                    && chester.container.canAddItem(objetivo.getItem());
        }

        @Override
        public void tick() {
            if (objetivo != null && chester.tickCount % 5 == 0) {
                chester.getNavigation().moveTo(objetivo, 1.0);
            }
        }

        @Override
        public void stop() {
            chester.getNavigation().stop();
            this.objetivo = null;
        }
    }

    /** Sigue al jugador que lo invocó (animación walk). Atado a una cuerda no sigue. */
    private static class SeguirGoal extends Goal {
        private final ChesterEntity chester;

        SeguirGoal(ChesterEntity chester) {
            this.chester = chester;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            Player dueno = chester.getDueno();
            if (dueno == null || !chester.puedeMoverse() || chester.isLeashed()) return false;
            double d = chester.distanceToSqr(dueno);
            // Si el dueño está quieto, Chester puede deambular; solo vuelve si se aleja demasiado
            boolean duenoQuieto = chester.ticksDuenoQuieto >= DUENO_QUIETO_TICKS;
            return duenoQuieto ? d > 19.0 * 19.0 : d > 25.0;
        }

        @Override
        public boolean canContinueToUse() {
            Player dueno = chester.getDueno();
            return dueno != null && chester.puedeMoverse() && !chester.isLeashed()
                    && chester.distanceToSqr(dueno) > 6.25;
        }

        @Override
        public void tick() {
            Player dueno = chester.getDueno();
            if (dueno != null && chester.tickCount % 10 == 0) {
                chester.getNavigation().moveTo(dueno, 1.0);
            }
        }

        @Override
        public void stop() {
            chester.getNavigation().stop();
        }
    }

    /** Con el dueño quieto 3 s, deambula por la zona (hasta 19 bloques de él). */
    private static class DeambularGoal extends Goal {
        private final ChesterEntity chester;

        DeambularGoal(ChesterEntity chester) {
            this.chester = chester;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            Player dueno = chester.getDueno();
            return dueno != null && chester.puedeMoverse() && !chester.isLeashed()
                    && chester.ticksDuenoQuieto >= DUENO_QUIETO_TICKS
                    && chester.getNavigation().isDone() && chester.getRandom().nextInt(40) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return chester.puedeMoverse() && !chester.getNavigation().isDone()
                    && chester.ticksDuenoQuieto >= DUENO_QUIETO_TICKS;
        }

        @Override
        public void start() {
            Player dueno = chester.getDueno();
            if (dueno == null) return;
            double ang = chester.getRandom().nextDouble() * Math.PI * 2.0;
            double r = chester.getRandom().nextDouble() * RADIO_DEAMBULAR;
            chester.getNavigation().moveTo(dueno.getX() + Math.cos(ang) * r, dueno.getY(),
                    dueno.getZ() + Math.sin(ang) * r, 0.7);
        }

        @Override
        public void stop() {
            chester.getNavigation().stop();
        }
    }

    /** Objeto del suelo más cercano que le quepa en el inventario. */
    private ItemEntity buscarObjeto() {
        double radio = this.isLeashed() ? RADIO_OBJETOS_ATADO : RADIO_OBJETOS;
        List<ItemEntity> objetos = this.level().getEntitiesOfClass(ItemEntity.class,
                this.getBoundingBox().inflate(radio, 4.0, radio),
                i -> i.isAlive() && !i.hasPickUpDelay() && this.container.canAddItem(i.getItem()));
        ItemEntity mejor = null;
        double mejorDist = Double.MAX_VALUE;
        for (ItemEntity it : objetos) {
            double d = this.distanceToSqr(it);
            if (d < mejorDist) {
                mejorDist = d;
                mejor = it;
            }
        }
        return mejor;
    }

    // ---------- Daño: solo con hacha ----------

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        Entity atacante = source.getEntity();
        if (atacante instanceof Player jugador && !jugador.getMainHandItem().is(ItemTags.AXES)) {
            return false; // los jugadores solo pueden herirlo con un hacha (de cualquier tipo)
        }
        return super.hurtServer(level, source, amount);
    }

    // ---------- Interacción ----------

    private void avisar(Player player, String texto) {
        if (player instanceof ServerPlayer sp) {
            sp.connection.send(new ClientboundSetActionBarTextPacket(Component.literal(texto)));
            this.level().playSound(null, player.blockPosition(), sonido(SoundEvents.EXPERIENCE_ORB_PICKUP),
                    SoundSource.PLAYERS, 0.6f, 1.0f);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.isDeadOrDying()) return InteractionResult.PASS;
        if (this.level().isClientSide()) return InteractionResult.SUCCESS;

        if (this.duenoId == null) this.duenoId = player.getUUID();
        if (!player.getUUID().equals(this.duenoId)) return InteractionResult.PASS; // solo su dueño

        ItemStack mano = player.getItemInHand(hand);
        if (player.isShiftKeyDown() && mano.is(Items.BUCKET)) {
            // Con una cubeta se guarda de nuevo en su llave: con todo su contenido y sin tiempo de espera
            ItemStack llave = ChesterKeyItem.crearLlaveLista(this.container);
            if (!player.getInventory().add(llave)) {
                this.level().addFreshEntity(new ItemEntity(this.level(), player.getX(), player.getY(), player.getZ(), llave));
            }
            this.container.clearContent();
            this.discard();
            return InteractionResult.SUCCESS;
        }

        if (player.isShiftKeyDown()) {
            setSentado(!isSentado());
            this.getNavigation().stop();
            avisar(player, isSentado() ? "Chester: sentado" : "Chester: te sigue");
        } else {
            player.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> ChestMenu.threeRows(id, inv, this.container),
                    Component.literal("Chester")));
        }
        return InteractionResult.SUCCESS;
    }

    // ---------- Muerte: suelta la llave con todo su contenido ----------

    @Override
    public void die(DamageSource source) {
        if (this.level() instanceof ServerLevel sl && !this.isRemoved()) {
            ItemStack llave = ChesterKeyItem.crearLlaveRecargando(sl, this.container);
            sl.addFreshEntity(new ItemEntity(sl, this.getX(), this.getY(0.5), this.getZ(), llave));
            this.container.clearContent();
        }
        super.die(source);
    }

    // ---------- Lógica principal ----------

    @Override
    public void tick() {
        super.tick();

        if (!(this.level() instanceof ServerLevel sl)) {
            this.tickCliente();
            return;
        }
        if (this.isDeadOrDying()) return;

        this.fallDistance = 0; // inmune a la caída
        if (this.enfriamientoOlfato > 0) this.enfriamientoOlfato--;

        // ¿Hay algún jugador con su inventario abierto?
        boolean abierto = false;
        for (ServerPlayer p : sl.players()) {
            if (p.containerMenu instanceof ChestMenu menu && menu.getContainer() == this.container) {
                abierto = true;
                break;
            }
        }
        if (abierto != this.isAbierto()) this.entityData.set(DATA_ABIERTO, abierto);
        if (abierto != this.abiertoPrevio) {
            this.abiertoPrevio = abierto;
            sl.playSound(null, this.blockPosition(), sonido(abierto ? SoundEvents.SHULKER_OPEN : SoundEvents.SHULKER_CLOSE),
                    SoundSource.NEUTRAL, 1.0f, 1.6f); // como un shulker, pero más agudo
        }
        if (abierto || this.isSentado()) this.getNavigation().stop();

        // Huir: de monstruos o del fuego
        boolean huyendo = this.huyendoDeMobs || this.isOnFire();
        if (huyendo != this.isHuyendo()) this.setHuyendo(huyendo);

        Player dueno = this.getDueno();
        if (dueno != null) {
            // ¿El dueño lleva quieto un rato?
            Vec3 pos = dueno.position();
            if (this.ultimaPosDueno == null || pos.distanceToSqr(this.ultimaPosDueno) > 0.0004) {
                this.ticksDuenoQuieto = 0;
                this.ultimaPosDueno = pos;
            } else {
                this.ticksDuenoQuieto++;
            }

            // Teletransporte si el dueño se aleja más de 20 bloques (no sentado ni atado)
            if (!this.isSentado() && !abierto && !this.isLeashed()
                    && this.distanceToSqr(dueno) > DISTANCIA_TELEPORT_SQR) {
                this.setPos(dueno.getX() + (this.random.nextDouble() - 0.5) * 3.0, dueno.getY(),
                        dueno.getZ() + (this.random.nextDouble() - 0.5) * 3.0);
                this.getNavigation().stop();
            }
        }

        if (this.tickCount % 5 == 0 && !abierto) this.recogerObjetos(sl);
        if (this.isSentado() && this.tickCount % 8 == 0) this.vaciarEnTolva(sl);
    }

    /** Si está encima de un objeto, lo guarda; si no le cabe, huele. */
    private void recogerObjetos(ServerLevel sl) {
        List<ItemEntity> cercanos = sl.getEntitiesOfClass(ItemEntity.class,
                this.getBoundingBox().inflate(0.6, 0.3, 0.6), i -> i.isAlive() && !i.hasPickUpDelay());
        for (ItemEntity it : cercanos) {
            if (!this.container.canAddItem(it.getItem())) {
                if (this.enfriamientoOlfato <= 0) {
                    sl.broadcastEntityEvent(this, EVENTO_OLFATO);
                    sl.sendParticles(ParticleTypes.ANGRY_VILLAGER, this.getX(), this.getY(1.0), this.getZ(), 3, 0.2, 0.1, 0.2, 0.0);
                    this.enfriamientoOlfato = 60;
                }
                continue;
            }
            ItemStack resto = this.container.addItem(it.getItem().copy());
            if (resto.isEmpty()) {
                it.discard();
            } else {
                it.setItem(resto);
            }
            sl.broadcastEntityEvent(this, EVENTO_EXPRESIONES);
            sl.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY(0.8), this.getZ(), 6, 0.3, 0.2, 0.3, 0.0);
            sl.playSound(null, this.blockPosition(), sonido(SoundEvents.GENERIC_EAT), SoundSource.NEUTRAL, 0.6f, 1.0f);
            sl.playSound(null, this.blockPosition(), sonido(SoundEvents.PLAYER_BURP), SoundSource.NEUTRAL, 0.5f, 1.8f);
            this.heal(CURA_POR_OBJETO); // se cura al comer
            return;
        }
    }

    /** Sentado sobre una tolva, le pasa su inventario poco a poco (con notas musicales mientras lo hace). */
    private void vaciarEnTolva(ServerLevel sl) {
        BlockPos[] posiciones = { this.blockPosition(), this.blockPosition().below() };
        for (BlockPos pos : posiciones) {
            if (sl.getBlockEntity(pos) instanceof HopperBlockEntity tolva) {
                for (int i = 0; i < this.container.getContainerSize(); i++) {
                    ItemStack st = this.container.getItem(i);
                    if (!st.isEmpty()) {
                        ItemStack resto = HopperBlockEntity.addItem(null, tolva, st.copy(), Direction.UP);
                        boolean paso = resto.getCount() != st.getCount();
                        this.container.setItem(i, resto);
                        if (paso) {
                            sl.sendParticles(ParticleTypes.NOTE, this.getX(), this.getY(1.1), this.getZ(), 1, 0.2, 0.1, 0.2, 1.0);
                        }
                        break; // un objeto por intervalo
                    }
                }
                return;
            }
        }
    }

    // ---------- Cliente: elegir la animación ----------

    @Override
    public void handleEntityEvent(byte id) {
        if (id == EVENTO_EXPRESIONES) {
            this.ticksExpresiones = 21;
        } else if (id == EVENTO_OLFATO) {
            this.ticksOlfato = 32;
        } else {
            super.handleEntityEvent(id);
        }
    }

    private void tickCliente() {
        double dx = this.getX() - this.xo;
        double dy = this.getY() - this.yo;
        double dz = this.getZ() - this.zo;
        boolean moviendose = dx * dx + dy * dy + dz * dz > 0.0004;

        if (this.ticksExpresiones > 0) this.ticksExpresiones--;
        if (this.ticksOlfato > 0) this.ticksOlfato--;

        boolean abierto = this.isAbierto();
        boolean sentado = this.isSentado();

        if (this.isDeadOrDying()) {
            this.animActual = ANIM_MUERTE;
        } else if (abierto) {
            // abrirse (CLICKDERECHO) y quedarse abierto (IDLE_SOTRAGE) / sentado: abriendose_sentado
            this.animActual = sentado ? ANIM_ABRIR_SENTADO : ANIM_ABRIR;
        } else if (this.ticksOlfato > 0) {
            this.animActual = ANIM_OLFATO;
        } else if (this.ticksExpresiones > 0) {
            this.animActual = ANIM_EXPRESION;
        } else if (this.isHuyendo()) {
            this.animActual = ANIM_CORRER;
        } else if (sentado) {
            this.animActual = ANIM_SENTADO;
        } else if (moviendose) {
            this.animActual = ANIM_CAMINAR;
        } else {
            this.animActual = ANIM_IDLE;
        }
    }
}
