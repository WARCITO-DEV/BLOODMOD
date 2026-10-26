package com.example.bloodmod;

import java.util.List;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * ENDERALITE GUILLOTINE (hacha)
 *  - Daño 12, enfriamiento de ataque 1 s, durabilidad nivel diamante (1561)
 *  - Al atacar: Wither I durante 2 s
 *  - Clic derecho: atrae en cono (6 bloques) a toda entidad viva y la aturde 2 s
 */
public class EnderaliteGuillotineItem extends Item {

    // ---- Ajustes ----
    static final double DANO = 12.0;
    static final double ENFRIAMIENTO_ATAQUE_S = 1.0;   // segundos entre golpes completos
    static final int DURABILIDAD = 1561;
    static final double RANGO = 6.0;
    static final double COS_CONO = Math.cos(Math.toRadians(40)); // semiángulo del cono: 40°
    static final int ATURDIMIENTO_TICKS = 40;           // 2 s
    static final int ENFRIAMIENTO_HABILIDAD_TICKS = 100; // 5 s (no venía en la ficha)
    static final int WITHER_TICKS = 40;                 // 2 s

    public EnderaliteGuillotineItem(Properties props) {
        super(props);
    }

    public static Item.Properties configurar(Item.Properties p) {
        ItemAttributeModifiers attrs = ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, DANO - 1.0, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, (1.0 / ENFRIAMIENTO_ATAQUE_S) - 4.0,
                                AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
        return p.durability(DURABILIDAD)
                .enchantable(10)
                .component(DataComponents.ATTRIBUTE_MODIFIERS, attrs)
                .component(DataComponents.LORE, new ItemLore(List.of(Component.literal("TENGO ENERGIA!!!!!"))));
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addEffect(new MobEffectInstance(MobEffects.WITHER, WITHER_TICKS, 0), attacker);
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(stack)) {
            return InteractionResult.PASS;
        }

        if (level instanceof ServerLevel sl) {
            Vec3 ojos = player.getEyePosition();
            Vec3 mirada = player.getLookAngle();

            for (LivingEntity e : sl.getEntitiesOfClass(LivingEntity.class,
                    player.getBoundingBox().inflate(RANGO), x -> x != player && x.isAlive())) {
                Vec3 haciaE = e.position().add(0, e.getBbHeight() / 2, 0).subtract(ojos);
                double dist = haciaE.length();
                if (dist > RANGO || dist < 0.01) continue;
                if (haciaE.normalize().dot(mirada) < COS_CONO) continue; // fuera del cono

                // Atraer hacia el jugador
                Vec3 haciaJugador = player.position().subtract(e.position());
                double fuerza = Math.min(1.8, 0.5 + dist * 0.2);
                e.setDeltaMovement(haciaJugador.normalize().scale(fuerza).add(0, 0.2, 0));
                e.hurtMarked = true;

                // Aturdir: inmóvil e inofensivo durante 2 s
                e.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, ATURDIMIENTO_TICKS, 7), player);
                e.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, ATURDIMIENTO_TICKS, 9), player);
            }

            // Partículas en forma de cono
            for (int d = 1; d <= (int) RANGO; d++) {
                Vec3 p = ojos.add(mirada.scale(d));
                sl.sendParticles(ParticleTypes.PORTAL, p.x, p.y, p.z, 8, d * 0.25, d * 0.25, d * 0.25, 0.15);
            }
            sl.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                    SoundSource.PLAYERS, 1f, 0.5f);
            player.getCooldowns().addCooldown(stack, ENFRIAMIENTO_HABILIDAD_TICKS);
        }
        return InteractionResult.SUCCESS;
    }
}
