package com.example.bloodmod;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/** Lógica de las púas teledirigidas de VAMPIRIC CRIMSON. */
@EventBusSubscriber(modid = "bloodmod")
public class PuasEvents {

    static final float DANO_PUA = 6f;
    static final float CURA_PUA = 4f;     // 2 corazones
    static final int VIDA_MAX_TICKS = 100;

    /** Púa -> objetivo que persigue (puede ser null). Solo se usa en el hilo del servidor. */
    public static final Map<AbstractArrow, LivingEntity> PUAS = new HashMap<>();

    @SubscribeEvent
    public static void alTick(ServerTickEvent.Post event) {
        Iterator<Map.Entry<AbstractArrow, LivingEntity>> it = PUAS.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<AbstractArrow, LivingEntity> en = it.next();
            AbstractArrow pua = en.getKey();
            LivingEntity objetivo = en.getValue();

            if (pua.isRemoved()) {
                it.remove();
                continue;
            }
            if (pua.tickCount > VIDA_MAX_TICKS) {
                pua.discard();
                it.remove();
                continue;
            }

            if (objetivo != null && objetivo.isAlive()) {
                Vec3 hacia = objetivo.position().add(0, objetivo.getBbHeight() / 2, 0).subtract(pua.position());
                Vec3 vel = pua.getDeltaMovement();
                double v = Math.max(vel.length(), 1.4);
                Vec3 nueva = vel.scale(0.6).add(hacia.normalize().scale(v * 0.4)).normalize().scale(v);
                pua.setDeltaMovement(nueva);
                pua.hurtMarked = true;
            }

            if (pua.level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.CRIMSON_SPORE, pua.getX(), pua.getY(), pua.getZ(),
                        2, 0.05, 0.05, 0.05, 0.0);
            }
        }
    }

    @SubscribeEvent
    public static void alImpactar(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow pua)) return;
        if (!PUAS.containsKey(pua)) return;
        if (!(pua.level() instanceof ServerLevel sl)) return;

        Entity golpeado = (event.getRayTraceResult() instanceof EntityHitResult h) ? h.getEntity() : null;
        Entity dueno = pua.getOwner();

        event.setCanceled(true);
        if (golpeado != null && golpeado == dueno) return; // atraviesa a su dueño

        if (golpeado instanceof LivingEntity victima && dueno instanceof LivingEntity d) {
            victima.hurtServer(sl, sl.damageSources().arrow(pua, d), DANO_PUA);
            d.heal(CURA_PUA);
            sl.sendParticles(ParticleTypes.HEART, d.getX(), d.getY(0.8), d.getZ(), 6, 0.4, 0.4, 0.4, 0.0);
        }
        pua.discard();
        PUAS.remove(pua);
    }
}
