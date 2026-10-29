package com.example.bloodmod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * SOUL SKULL: mascota voladora aliada (invocada con el Soul Staff).
 *  - 2 corazones, 7 de daño por golpe (+ Wither), 1.5 golpes por segundo
 *  - Inmune a la caída, al Wither y al fuego
 *  - Vuela igual que un vex y ataca como un vex (embiste al objetivo)
 *  - Sigue a su dueño; si está a más de 30 bloques se teletransporta
 *  - Si el dueño lleva más de 7 s quieto, desciende al suelo a "descansar"
 *  - Si el dueño muere, todas sus Soul Skulls mueren
 */
public class SoulSkullEntity extends PathfinderMob {

    // ---- Ajustes ----
    static final float DANO = 7f;
    static final int ENFRIAMIENTO_ATAQUE_TICKS = 13;  // 20 / 13 = ~1.5 golpes por segundo
    static final int WITHER_TICKS = 60;               // 3 s (la ficha no indica duración)
    static final int QUIETO_TICKS = 140;              // 7 s sin moverse -> descansa
    static final double DISTANCIA_TELEPORT_SQR = 900.0; // 30 bloques
    static final int SONIDO_IDLE_TICKS = 100;           // ghast idle cada 5 s

    /** Soul Skulls vivas de cada jugador (para eliminarlas todas con shift + CLICK derecho). */
    public static final Map<UUID, List<SoulSkullEntity>> POR_DUENO = new HashMap<>();

    // Estados de animación (el cliente los actualiza en tick())
    public final AnimationState idleAnim = new AnimationState();
    public final AnimationState atkAnim = new AnimationState();
    public final AnimationState followAnim = new AnimationState();

    private UUID duenoId;
    private Vec3 ultimaPosDueno;
    private int ticksQuieto;
    private int enfriamientoAtaque;
    private int ticksAtacando;

    public SoulSkullEntity(EntityType<? extends SoulSkullEntity> type, Level level) {
        super(type, level);
        this.moveControl = new MovimientoVex(this);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder crearAtributos() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0)        // 2 corazones
                .add(Attributes.ATTACK_DAMAGE, 7.0)     // 7 de daño por golpe
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    // ---------- Dueño ----------

    public void setDueno(Player dueno) {
        this.duenoId = dueno.getUUID();
        POR_DUENO.computeIfAbsent(this.duenoId, k -> new ArrayList<>()).add(this);
    }

    public Player getDueno() {
        return duenoId == null ? null : this.level().getPlayerByUUID(duenoId);
    }

    /** Cuántas Soul Skulls vivas tiene ese jugador. */
    public static int contar(UUID dueno) {
        List<SoulSkullEntity> lista = POR_DUENO.get(dueno);
        if (lista == null) return 0;
        lista.removeIf(SoulSkullEntity::isRemoved);
        return lista.size();
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

    // ---------- Lógica principal ----------

    @Override
    public void tick() {
        super.tick();

        if (!(this.level() instanceof ServerLevel sl)) {
            // Cliente: animaciones
            //  atk = acaba de dar un golpe, follow = moviéndose (incluye ir hacia un enemigo), idle = quieta
            boolean atacando = this.isAggressive();
            double dx = this.getX() - this.xo;
            double dy = this.getY() - this.yo;
            double dz = this.getZ() - this.zo;
            boolean moviendose = dx * dx + dy * dy + dz * dz > 0.0004;
            this.atkAnim.animateWhen(atacando, this.tickCount);
            this.followAnim.animateWhen(!atacando && moviendose, this.tickCount);
            this.idleAnim.animateWhen(!atacando && !moviendose, this.tickCount);
            return;
        }

        // ---- Servidor ----
        Player dueno = this.getDueno();
        if (dueno == null || !dueno.isAlive()) {
            // Sin dueño o dueño muerto: la Soul Skull muere también
            sl.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY(0.5), this.getZ(), 8, 0.2, 0.2, 0.2, 0.02);
            this.discard();
            return;
        }

        // Inmunidades: caída, Wither y fuego (el fuego/lava se anulan además con fireImmune() en el registro)
        this.fallDistance = 0;
        this.removeEffect(MobEffects.WITHER);
        this.clearFire();

        if (this.enfriamientoAtaque > 0) this.enfriamientoAtaque--;
        if (this.ticksAtacando > 0) {
            this.ticksAtacando--;
            if (this.ticksAtacando == 0) this.setAggressive(false);
        }

        // Teletransporte si se aleja más de 10 bloques
        if (this.distanceToSqr(dueno) > DISTANCIA_TELEPORT_SQR) {
            this.setPos(dueno.getX() + (this.random.nextDouble() - 0.5) * 2.0,
                    dueno.getY() + 1.0,
                    dueno.getZ() + (this.random.nextDouble() - 0.5) * 2.0);
            this.setDeltaMovement(Vec3.ZERO);
        }

        // ¿El jugador lleva quieto más de 3 s?
        Vec3 pos = dueno.position();
        if (this.ultimaPosDueno == null || pos.distanceToSqr(this.ultimaPosDueno) > 0.0004) {
            this.ticksQuieto = 0;
            this.ultimaPosDueno = pos;
        } else {
            this.ticksQuieto++;
        }

        // Elegir objetivo (como los vex): lo que ataca al dueño o lo que el dueño ataca
        if (this.tickCount % 5 == 0) {
            LivingEntity actual = this.getTarget();
            if (actual == null || !actual.isAlive() || !esObjetivoValido(actual, dueno)) {
                this.setTarget(null);
                LivingEntity candidato = dueno.getLastHurtByMob();
                if (candidato != null && candidato.isAlive() && esObjetivoValido(candidato, dueno)
                        && dueno.tickCount - dueno.getLastHurtByMobTimestamp() < 100) {
                    this.setTarget(candidato);
                } else {
                    candidato = dueno.getLastHurtMob();
                    if (candidato != null && candidato.isAlive() && esObjetivoValido(candidato, dueno)
                            && dueno.tickCount - dueno.getLastHurtMobTimestamp() < 100) {
                        this.setTarget(candidato);
                    }
                }
            }
        }

        LivingEntity objetivo = this.getTarget();
        if (objetivo != null && objetivo.isAlive()) {
            // Embestir al objetivo (se usa "follow" mientras se acerca y "atk" solo al golpear)
            this.getMoveControl().setWantedPosition(objetivo.getX(), objetivo.getY(0.5), objetivo.getZ(), 1.0);
            if (this.enfriamientoAtaque == 0
                    && this.getBoundingBox().inflate(0.2).intersects(objetivo.getBoundingBox())) {
                this.atacar(sl, objetivo);
            }
        } else if (this.ticksQuieto >= QUIETO_TICKS) {
            // Descansar en el suelo junto al dueño (animación idle al quedarse quieta)
            double ang = ((this.getUUID().hashCode() & 0xFF) / 255.0) * Math.PI * 2.0;
            this.getMoveControl().setWantedPosition(
                    dueno.getX() + Math.cos(ang) * 1.5, dueno.getY(), dueno.getZ() + Math.sin(ang) * 1.5, 0.6);
        } else if (this.distanceToSqr(dueno) > 16.0) {
            // Seguir al dueño
            this.getMoveControl().setWantedPosition(dueno.getX(), dueno.getY() + 1.0, dueno.getZ(), 1.0);
        }

        // Pocas partículas de alma al moverse
        double mx = this.getX() - this.xo;
        double my = this.getY() - this.yo;
        double mz = this.getZ() - this.zo;
        if (this.tickCount % 6 == 0 && mx * mx + my * my + mz * mz > 0.0004) {
            sl.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY(0.5), this.getZ(), 1, 0.1, 0.1, 0.1, 0.0);
        }

        // Sonido idle: ghast, más grave, cada 5 segundos
        if (this.tickCount % SONIDO_IDLE_TICKS == 0) {
            sl.playSound(null, this.blockPosition(), sonido(SoundEvents.GHAST_AMBIENT),
                    SoundSource.NEUTRAL, 1.0f, 0.5f);
        }
    }

    private boolean esObjetivoValido(LivingEntity e, Player dueno) {
        return e != dueno && !(e instanceof SoulSkullEntity) && this.distanceToSqr(e) < 32.0 * 32.0;
    }

    private void atacar(ServerLevel sl, LivingEntity objetivo) {
        this.enfriamientoAtaque = ENFRIAMIENTO_ATAQUE_TICKS;
        objetivo.hurtServer(sl, sl.damageSources().mobAttack(this), DANO);
        objetivo.addEffect(new MobEffectInstance(MobEffects.WITHER, WITHER_TICKS, 0), this);
        this.ticksAtacando = ENFRIAMIENTO_ATAQUE_TICKS + 2;
        this.setAggressive(true); // se sincroniza con el cliente -> animación atk
        sl.playSound(null, this.blockPosition(), sonido(SoundEvents.PHANTOM_BITE),
                SoundSource.HOSTILE, 1.0f, 1.0f);
    }

    // ---------- Movimiento idéntico al del vex ----------

    private static class MovimientoVex extends MoveControl {
        MovimientoVex(SoulSkullEntity skull) {
            super(skull);
        }

        @Override
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 hacia = new Vec3(this.wantedX - this.mob.getX(), this.wantedY - this.mob.getY(),
                        this.wantedZ - this.mob.getZ());
                double dist = hacia.length();
                if (dist < this.mob.getBoundingBox().getSize()) {
                    this.operation = MoveControl.Operation.WAIT;
                    this.mob.setDeltaMovement(this.mob.getDeltaMovement().scale(0.5));
                } else {
                    this.mob.setDeltaMovement(this.mob.getDeltaMovement()
                            .add(hacia.scale(this.speedModifier * 0.05 / dist)));
                    if (this.mob.getTarget() == null) {
                        Vec3 v = this.mob.getDeltaMovement();
                        this.mob.setYRot(-((float) Mth.atan2(v.x, v.z)) * (180.0F / (float) Math.PI));
                    } else {
                        double dx = this.mob.getTarget().getX() - this.mob.getX();
                        double dz = this.mob.getTarget().getZ() - this.mob.getZ();
                        this.mob.setYRot(-((float) Mth.atan2(dx, dz)) * (180.0F / (float) Math.PI));
                    }
                    this.mob.yBodyRot = this.mob.getYRot();
                }
            }
        }
    }
}
