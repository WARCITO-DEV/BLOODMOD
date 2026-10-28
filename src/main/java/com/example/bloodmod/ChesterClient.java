package com.example.bloodmod;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/** Registro del modelo y el renderer de Chester (solo en el cliente). */
@EventBusSubscriber(modid = "bloodmod", value = Dist.CLIENT)
public class ChesterClient {

    @SubscribeEvent
    public static void registrarCapas(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ChesterModel.LAYER, ChesterModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registrarRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BloodMod.CHESTER.get(), ChesterRenderer::new);
    }
}
