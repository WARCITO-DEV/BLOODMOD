package com.example.bloodmod;

import java.util.List;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = "bloodmod")
public class ChesterEvents {

    /**
     * Los monstruos solo se fijan en Chester cuando no tienen ningún otro objetivo:
     * se comprueba una vez por segundo y solo si el monstruo no tiene objetivo.
     */
    @SubscribeEvent
    public static void alTickMonstruo(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Monster m)) return;
        if (!(m.level() instanceof ServerLevel sl)) return;
        if (m.tickCount % 20 != 0 || m.getTarget() != null) return;

        List<ChesterEntity> chesters = sl.getEntitiesOfClass(ChesterEntity.class, m.getBoundingBox().inflate(16.0));
        ChesterEntity mejor = null;
        double mejorDist = Double.MAX_VALUE;
        for (ChesterEntity c : chesters) {
            if (!c.isAlive() || !m.hasLineOfSight(c)) continue;
            double d = m.distanceToSqr(c);
            if (d < mejorDist) {
                mejorDist = d;
                mejor = c;
            }
        }
        if (mejor != null) {
            m.setTarget(mejor);
        }
    }
}
