package com.example.bloodmod;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.core.NonNullList;

/**
 * CHESTER: cofre aliado con patas.
 *  - 20 de vida, 5 de armadura; los jugadores solo pueden herirlo con un hacha
 *  - Inventario de 27 espacios (como un cofre); clic derecho lo abre
 *  - Shift + clic derecho: sentarse / levantarse
 *  - Recoge objetos del suelo; si no le caben, huele (animación OLFATO)
 *  - Huye de los monstruos hostiles; sigue al jugador que lo invocó
 *  - Sentado sobre una tolva, vacía su inventario en ella
 *  - Al morir suelta su llave (con todo lo que guardaba), que se recarga durante 5 minutos
 */
public class ChesterEntity extends PathfinderMob {

    private static final EntityDataAccessor<Boolean> DATA_SENTADO =
            SynchedEntityData.defineId(ChesterEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_ABIERTO =
            SynchedEntityData.defineId(ChesterEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HUYENDO =
            SynchedEntityData.defineId(ChesterEntity.class, EntityDataSerializers.BOOLEAN);

    // Eventos de animación (se envían del servidor al cliente)
    static final byte EVENTO_EXPRESIONES = 61;
    static final byte EVENTO_OLFATO = 62;

    static final double DISTANCIA_TELEPORT_SQR = 24.0 * 24.0;

    // Estados de animación (los actualiza el cliente en tick())
    public final AnimationState walkAnim = new AnimationState();
    public final AnimationState correrAnim = new AnimationState();
    public final AnimationState expresionesAnim = new AnimationState();
    public final AnimationState abriendoseAnim = new AnimationState();
    public final AnimationState abriendoseSentadoAnim = new AnimationState();
    public final AnimationState cerrandoseAnim = new AnimationState();
    public final AnimationState cerrandoseSentadoAnim = new AnimationState();
    public final AnimationState cerradoAnim = new AnimationState();
    public final AnimationState abiertoAnim = new AnimationState();
    public final AnimationState sentadoAnim = new AnimationState();
    public final AnimationState olfatoAnim = new AnimationState();
    public final AnimationState muerteAnim = new AnimationState();

    /** Inventario de Chester (27 espacios). */
    public final SimpleContainer container = new SimpleContainer(27);

    private UUID duenoId;
    private int enfriamientoOlfato;

    // Solo cliente
    private boolean abiertoPrevio;
    private int ticksTransicion;
    private int ticksExpresiones;
    private int ticksOlfato;

    public ChesterEntity(EntityType<? extends ChesterEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder crearAtributos() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.ARMOR, 5.0)
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
        this.goalSelector.addGoal(2, new SeguirGoal(this));
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
            return !chester.isSentado() && !chester.isAbierto() && super.canUse();
        }

        @Override
        public void start() {
            super.start();
            chester.setHuyendo(true);
        }

        @Override
        public void stop() {
            super.stop();
            chester.setHuyendo(false);
        }
    }

    /** Sigue al jugador que lo invocó (animación walk). */
    private static class SeguirGoal extends Goal {
        private final ChesterEntity chester;

        SeguirGoal(ChesterEntity chester) {
            this.chester = chester;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            Player dueno = chester.getDueno();
            return dueno != null && !chester.isSentado() && !chester.isAbierto()
                    && chester.distanceToSqr(dueno) > 25.0;
        }

        @Override
        public boolean canContinueToUse() {
            Player dueno = chester.getDueno();
            return dueno != null && !chester.isSentado() && !chester.isAbierto()
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

    // ---------- Daño: solo con hacha ----------

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        Entity atacante = source.getEntity();
        if (atacante instanceof Player jugador && !jugador.getMainHandItem().is(ItemTags.AXES)) {
            return false; // los jugadores solo pueden herirlo con un hacha
        }
        return super.hurtServer(level, source, amount);
    }

    // ---------- Interacción ----------

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.isDeadOrDying()) return InteractionResult.PASS;
        if (this.level().isClientSide()) return InteractionResult.SUCCESS;

        if (this.duenoId == null) this.duenoId = player.getUUID();
        if (!player.getUUID().equals(this.duenoId)) return InteractionResult.PASS; // solo su dueño

        if (player.isShiftKeyDown()) {
            setSentado(!isSentado());
            this.getNavigation().stop();
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
        if (abierto || this.isSentado()) this.getNavigation().stop();

        // Teletransporte si el dueño está muy lejos
        Player dueno = this.getDueno();
        if (dueno != null && !this.isSentado() && !abierto && this.distanceToSqr(dueno) > DISTANCIA_TELEPORT_SQR) {
            this.setPos(dueno.getX() + (this.random.nextDouble() - 0.5) * 3.0, dueno.getY(),
                    dueno.getZ() + (this.random.nextDouble() - 0.5) * 3.0);
            this.getNavigation().stop();
        }

        if (this.tickCount % 5 == 0 && !abierto) this.recogerObjetos(sl);
        if (this.isSentado() && this.tickCount % 8 == 0) this.vaciarEnTolva(sl);
    }

    private void recogerObjetos(ServerLevel sl) {
        List<ItemEntity> objetos = sl.getEntitiesOfClass(ItemEntity.class,
                this.getBoundingBox().inflate(8.0, 2.0, 8.0), i -> i.isAlive() && !i.hasPickUpDelay());

        ItemEntity objetivo = null;
        double mejor = Double.MAX_VALUE;
        for (ItemEntity it : objetos) {
            if (!this.container.canAddItem(it.getItem())) {
                // No le cabe: huele para indicarlo
                if (this.distanceToSqr(it) < 4.0 && this.enfriamientoOlfato <= 0) {
                    sl.broadcastEntityEvent(this, EVENTO_OLFATO);
                    this.enfriamientoOlfato = 60;
                }
                continue;
            }
            double d = this.distanceToSqr(it);
            if (d < mejor) {
                mejor = d;
                objetivo = it;
            }
        }
        if (objetivo == null) return;

        if (this.getBoundingBox().inflate(0.6, 0.3, 0.6).intersects(objetivo.getBoundingBox())) {
            ItemStack resto = this.container.addItem(objetivo.getItem().copy());
            if (resto.isEmpty()) {
                objetivo.discard();
            } else {
                objetivo.setItem(resto);
            }
            sl.broadcastEntityEvent(this, EVENTO_EXPRESIONES);
        } else if (!this.isSentado() && !this.isHuyendo()) {
            this.getNavigation().moveTo(objetivo, 1.0);
        }
    }

    /** Sentado sobre una tolva, le pasa su inventario poco a poco. */
    private void vaciarEnTolva(ServerLevel sl) {
        BlockPos[] posiciones = { this.blockPosition(), this.blockPosition().below() };
        for (BlockPos pos : posiciones) {
            if (sl.getBlockEntity(pos) instanceof HopperBlockEntity tolva) {
                for (int i = 0; i < this.container.getContainerSize(); i++) {
                    ItemStack st = this.container.getItem(i);
                    if (!st.isEmpty()) {
                        ItemStack resto = HopperBlockEntity.addItem(null, tolva, st.copy(), Direction.UP);
                        this.container.setItem(i, resto);
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

        boolean abierto = this.isAbierto();
        boolean sentado = this.isSentado();
        boolean huyendo = this.isHuyendo();

        if (abierto != this.abiertoPrevio) {
            this.ticksTransicion = 10; // la animación de abrir/cerrar dura ~0.46 s
            this.abiertoPrevio = abierto;
        }
        if (this.ticksTransicion > 0) this.ticksTransicion--;
        if (this.ticksExpresiones > 0) this.ticksExpresiones--;
        if (this.ticksOlfato > 0) this.ticksOlfato--;

        // 0 walk, 1 correr, 2 expresiones, 3 abriendose, 4 abriendose_sentado, 5 cerrandose,
        // 6 cerrandose_sentado, 7 cerrado, 8 abierto, 9 sentado, 10 olfato, 11 muerte
        int a;
        if (this.isDeadOrDying()) {
            a = 11;
        } else if (abierto) {
            a = (this.ticksTransicion > 0 || sentado) ? (sentado ? 4 : 3) : 8;
        } else if (this.ticksTransicion > 0) {
            a = sentado ? 6 : 5;
        } else if (this.ticksOlfato > 0) {
            a = 10;
        } else if (this.ticksExpresiones > 0) {
            a = 2;
        } else if (huyendo) {
            a = 1;
        } else if (sentado) {
            a = 9;
        } else if (moviendose) {
            a = 0;
        } else {
            a = 7;
        }

        walkAnim.animateWhen(a == 0, this.tickCount);
        correrAnim.animateWhen(a == 1, this.tickCount);
        expresionesAnim.animateWhen(a == 2, this.tickCount);
        abriendoseAnim.animateWhen(a == 3, this.tickCount);
        abriendoseSentadoAnim.animateWhen(a == 4, this.tickCount);
        cerrandoseAnim.animateWhen(a == 5, this.tickCount);
        cerrandoseSentadoAnim.animateWhen(a == 6, this.tickCount);
        cerradoAnim.animateWhen(a == 7, this.tickCount);
        abiertoAnim.animateWhen(a == 8, this.tickCount);
        sentadoAnim.animateWhen(a == 9, this.tickCount);
        olfatoAnim.animateWhen(a == 10, this.tickCount);
        muerteAnim.animateWhen(a == 11, this.tickCount);
    }
}
