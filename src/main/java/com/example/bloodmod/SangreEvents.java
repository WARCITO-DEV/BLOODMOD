package com.example.bloodmod;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

/**
 * Las flechas del Arco de Sangre llevan un tag "sangre_aoe_<carga 0-100>".
 * Al impactar (en un mob o en un bloque) explotan en área: más carga = más radio y más daño.
 */
@EventBusSubscriber(modid = "bloodmod")
public class SangreEvents {

    @SubscribeEvent
    public static void alImpactar(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow flecha)) return;
        if (!(flecha.level() instanceof ServerLevel sl)) return;

        int porcentaje = -1;
        for (String tag : flecha.getTags()) {
            if (tag.startsWith(SangreItem.TAG_AOE)) {
                porcentaje = Integer.parseInt(tag.substring(SangreItem.TAG_AOE.length()));
            }
        }
        if (porcentaje < 0) return; // no es una flecha de sangre

        event.setCanceled(true); // el daño lo hace el área, no el impacto directo

        float carga = porcentaje / 100f;
        double radio = 1.5 + carga * 3.5;      // 1.5 .. 5 bloques
        float dano = 4f + carga * 8f;          // 4 .. 12 de daño
        Vec3 pos = event.getRayTraceResult().getLocation();
        Entity dueno = flecha.getOwner();
        DamageSource fuente = sl.damageSources().arrow(flecha, dueno);

        AABB area = new AABB(pos, pos).inflate(radio);
        for (LivingEntity e : sl.getEntitiesOfClass(LivingEntity.class, area,
                x -> x != dueno && x.isAlive() && !(x instanceof Player))) {
            e.hurtServer(sl, fuente, dano);
        }

        sl.sendParticles(ParticleTypes.EXPLOSION, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
        sl.sendParticles(ParticleTypes.CRIMSON_SPORE, pos.x, pos.y, pos.z,
                (int) (20 + carga * 40), radio / 2, 0.4, radio / 2, 0.03);
        sl.playSound(null, flecha.blockPosition(), SoundEvents.ARROW_HIT,
                SoundSource.PLAYERS, 1f, 0.5f);

        flecha.discard();
    }
}
