package com.example.bloodmod;

import java.util.List;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * VAMPIRIC CRIMSON (espada)
 *  - Daño 10, enfriamiento de ataque 0.5 s, durabilidad nivel diamante (1561)
 *  - Al atacar (clic izquierdo): cura 1/2 corazón
 *  - Clic derecho: dispara una púa teledirigida; si impacta, te cura 2 corazones
 */
public class VampiricCrimsonItem extends Item {

    // ---- Ajustes ----
    static final double DANO = 10.0;
    static final double ENFRIAMIENTO_ATAQUE_S = 0.5;
    static final int DURABILIDAD = 1561;
    static final float CURA_GOLPE = 1.0f;            // 1/2 corazón
    static final int ENFRIAMIENTO_PUA_TICKS = 30;    // 1.5 s (no venía en la ficha)
    static final double ALCANCE_TELEDIRIGIDO = 24.0;
    static final double COS_VISION = 0.8;            // cuánto debe estar delante para fijarlo

    public VampiricCrimsonItem(Properties props) {
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
                .component(DataComponents.LORE, new ItemLore(List.of(
                        Component.literal("No es un stand?, nunca supe como se escribia!"))));
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        attacker.heal(CURA_GOLPE);
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

            // Fija el monstruo más cercano que tengas delante
            LivingEntity objetivo = null;
            double mejor = Double.MAX_VALUE;
            for (LivingEntity e : sl.getEntitiesOfClass(LivingEntity.class,
                    player.getBoundingBox().inflate(ALCANCE_TELEDIRIGIDO), x -> x instanceof Monster && x.isAlive())) {
                Vec3 haciaE = e.position().add(0, e.getBbHeight() / 2, 0).subtract(ojos);
                double dist = haciaE.length();
                if (dist < 0.5 || dist > ALCANCE_TELEDIRIGIDO) continue;
                if (haciaE.normalize().dot(mirada) < COS_VISION) continue;
                if (dist < mejor) {
                    mejor = dist;
                    objetivo = e;
                }
            }

            Arrow pua = new Arrow(sl, player, new ItemStack(Items.ARROW), stack);
            pua.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, 1.6f, 0f);
            pua.setNoGravity(true);
            pua.setBaseDamage(0.0);
            pua.pickup = AbstractArrow.Pickup.DISALLOWED;
            sl.addFreshEntity(pua);
            PuasEvents.PUAS.put(pua, objetivo); // sin objetivo = vuela recta

            sl.playSound(null, player.blockPosition(), SoundEvents.ARROW_SHOOT,
                    SoundSource.PLAYERS, 1f, 1.4f);
            player.getCooldowns().addCooldown(stack, ENFRIAMIENTO_PUA_TICKS);
        }
        return InteractionResult.SUCCESS;
    }
}
