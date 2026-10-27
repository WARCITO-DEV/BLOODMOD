package com.example.bloodmod;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * SOUL SKULL: mascota voladora aliada.
 *  - 5 corazones, 3 de daño por golpe (+ Wither, ver SoulEvents)
 *  - Sigue a su dueño como un allay y se teletransporta si está a más de 10 bloques
 *  - Ataca a lo que ataca el dueño o a lo que lo ataca (como los lobos)
 */
public class SoulSkullEntity extends PathfinderMob {

    /** Soul Skulls vivas de cada jugador (para eliminarlas todas con shift + clic derecho). */
    public static final Map<UUID, List<SoulSkullEntity>> POR_DUENO = new HashMap<>();

    // Estados de animación (el cliente los actualiza en tick())
    public final AnimationState idleAnim = new AnimationState();
    public final AnimationState atkAnim = new AnimationState();
    public final AnimationState followAnim = new AnimationState();

    private UUID duenoId;

    public SoulSkullEntity(EntityType<? extends SoulSkullEntity> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder crearAtributos() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)       // 5 corazones
                .add(Attributes.ATTACK_DAMAGE, 3.0)     // 3 de daño por golpe
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.FLYING_SPEED, 0.3)
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

    // ---------- IA ----------

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true));
        this.goalSelector.addGoal(2, new SeguirDuenoGoal(this));
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    public boolean removeWhenFarAway(double distancia) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            // Animaciones: atk = golpeando, follow = moviéndose, idle = quieta
            boolean atacando = this.isAggressive();
            boolean moviendose = this.getDeltaMovement().horizontalDistanceSqr() > 0.0004
                    || Math.abs(this.getDeltaMovement().y) > 0.02;
            this.atkAnim.animateWhen(atacando, this.tickCount);
            this.followAnim.animateWhen(!atacando && moviendose, this.tickCount);
            this.idleAnim.animateWhen(!atacando && !moviendose, this.tickCount);
            return;
        }

        Player dueno = this.getDueno();
        if (dueno == null) {
            // Sin dueño conectado (por ejemplo tras recargar el mundo): desaparece
            if (this.tickCount > 40) this.discard();
            return;
        }

        // Si se aleja más de 10 bloques, se teletransporta al jugador
        if (this.distanceToSqr(dueno) > 100.0) {
            this.setPos(dueno.getX() + (this.random.nextDouble() - 0.5) * 2.0,
                    dueno.getY() + 1.0,
                    dueno.getZ() + (this.random.nextDouble() - 0.5) * 2.0);
            this.getNavigation().stop();
        }

        // Elegir objetivo como los lobos: lo que ataca al dueño o lo que el dueño ataca
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
    }

    private boolean esObjetivoValido(LivingEntity e, Player dueno) {
        return e != dueno && !(e instanceof SoulSkullEntity);
    }

    // ---------- Objetivo "seguir al dueño" ----------

    private static class SeguirDuenoGoal extends Goal {
        private final SoulSkullEntity skull;

        SeguirDuenoGoal(SoulSkullEntity skull) {
            this.skull = skull;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            Player dueno = skull.getDueno();
            return dueno != null && skull.getTarget() == null && skull.distanceToSqr(dueno) > 9.0;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public void tick() {
            Player dueno = skull.getDueno();
            if (dueno != null) {
                skull.getNavigation().moveTo(dueno.getX(), dueno.getY() + 1.0, dueno.getZ(), 1.2);
            }
        }

        @Override
        public void stop() {
            skull.getNavigation().stop();
        }
    }
}
