package com.example.bloodmod;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = "bloodmod")
public class SoulEvents {

    static final int WITHER_TICKS = 60; // 3 s (la ficha no indicaba duración)

    @SubscribeEvent
    public static void atributos(EntityAttributeCreationEvent event) {
        event.put(BloodMod.SOUL_SKULL.get(), SoulSkullEntity.crearAtributos().build());
    }

    /** Los golpes de la Soul Skull infligen Wither. */
    @SubscribeEvent
    public static void alDanar(LivingDamageEvent.Post event) {
        if (event.getSource().getDirectEntity() instanceof SoulSkullEntity skull
                && !event.getEntity().level().isClientSide()) {
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.WITHER, WITHER_TICKS, 0), skull);
        }
    }
}
