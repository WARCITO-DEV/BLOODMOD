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
     * Los monstruos persiguen a Chester solo cuando no tienen ningún otro objetivo,
     * y lo ignoran por completo mientras está sentado.
     */
    @SubscribeEvent
    public static void alTickMonstruo(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Monster m)) return;
        if (!(m.level() instanceof ServerLevel sl)) return;
        if (m.tickCount % 20 != 0) return;

        if (m.getTarget() instanceof ChesterEntity objetivoActual) {
            if (objetivoActual.isSentado()) m.setTarget(null); // sentado: lo ignoran
            return;
        }
        if (m.getTarget() != null) return;

        List<ChesterEntity> chesters = sl.getEntitiesOfClass(ChesterEntity.class, m.getBoundingBox().inflate(16.0));
        ChesterEntity mejor = null;
        double mejorDist = Double.MAX_VALUE;
        for (ChesterEntity c : chesters) {
            if (!c.isAlive() || c.isSentado() || !m.hasLineOfSight(c)) continue;
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
