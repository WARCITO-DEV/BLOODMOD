package com.example.bloodmod;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = "bloodmod")
public class SoulEvents {

    @SubscribeEvent
    public static void atributos(EntityAttributeCreationEvent event) {
        event.put(BloodMod.SOUL_SKULL.get(), SoulSkullEntity.crearAtributos().build());
        event.put(BloodMod.CHESTER.get(), ChesterEntity.crearAtributos().build());
    }
}
