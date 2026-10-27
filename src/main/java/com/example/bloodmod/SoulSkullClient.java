package com.example.bloodmod;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/** Registro del modelo y el renderer (solo en el cliente). */
@EventBusSubscriber(modid = "bloodmod", value = Dist.CLIENT)
public class SoulSkullClient {

    @SubscribeEvent
    public static void registrarCapas(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.add(SoulSkullModel.LAYER, SoulSkullModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registrarRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BloodMod.SOUL_SKULL.get(), SoulSkullRenderer::new);
    }
}
