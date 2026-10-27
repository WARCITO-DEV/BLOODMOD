package com.example.bloodmod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.Level;

/**
 * SOUL STAFF (bastón)
 *  - Clic derecho: invoca una Soul Skull (enfriamiento 3 s)
 *  - Shift + clic derecho: elimina todas las Soul Skull que invocaste
 *  - Mientras tengas Soul Skulls activas, el bastón cambia a la textura de hueso con calavera de wither
 */
public class SoulStaffItem extends Item {

    static final int ENFRIAMIENTO_TICKS = 60; // 3 s
    static final int MAX_SOUL_SKULLS = 10;    // tope por jugador (la ficha no lo indicaba)

    private static final Identifier MODELO_NORMAL = Identifier.fromNamespaceAndPath("bloodmod", "soul_staff");
    private static final Identifier MODELO_ACTIVO = Identifier.fromNamespaceAndPath("bloodmod", "soul_staff_active");

    public SoulStaffItem(Properties props) {
        super(props);
    }

    public static Item.Properties configurar(Item.Properties p) {
        return p.stacksTo(1).component(DataComponents.LORE, new ItemLore(List.of(
                Component.literal("Clic derecho: invoca una Soul Skull"),
                Component.literal("Shift + clic derecho: desaparece todas tus Soul Skull"),
                Component.literal("Infligen Wither").withStyle(ChatFormatting.YELLOW),
                Component.literal("Hacen 4 de daño por golpe").withStyle(ChatFormatting.YELLOW),
                Component.literal("No es una bosa de frijoles pero esta bien, ES DE BUENA MARCA!!!"))));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level instanceof ServerLevel sl) {
            if (player.isShiftKeyDown()) {
                // Eliminar todas las Soul Skull de este jugador
                List<SoulSkullEntity> lista = SoulSkullEntity.POR_DUENO.get(player.getUUID());
                if (lista != null) {
                    for (SoulSkullEntity s : new ArrayList<>(lista)) {
                        if (!s.isRemoved()) {
                            sl.sendParticles(ParticleTypes.SOUL, s.getX(), s.getY(0.5), s.getZ(), 8, 0.2, 0.2, 0.2, 0.02);
                            s.discard();
                        }
                    }
                    lista.clear();
                }
                sl.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                        SoundSource.PLAYERS, 1f, 1f);
                return InteractionResult.SUCCESS;
            }

            if (player.getCooldowns().isOnCooldown(stack)) {
                return InteractionResult.PASS;
            }
            if (SoulSkullEntity.contar(player.getUUID()) >= MAX_SOUL_SKULLS) {
                return InteractionResult.PASS;
            }

            SoulSkullEntity skull = new SoulSkullEntity(BloodMod.SOUL_SKULL.get(), sl);
            skull.setPos(player.getX(), player.getY() + 1.2, player.getZ());
            skull.setDueno(player);
            sl.addFreshEntity(skull);

            sl.sendParticles(ParticleTypes.SOUL, skull.getX(), skull.getY(0.5), skull.getZ(), 10, 0.3, 0.3, 0.3, 0.03);
            sl.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                    SoundSource.PLAYERS, 1f, 1.4f);
            player.getCooldowns().addCooldown(stack, ENFRIAMIENTO_TICKS);
        }
        return InteractionResult.SUCCESS;
    }

    // Textura temporal: hueso con calavera de wither mientras haya Soul Skulls activas
    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, EquipmentSlot slot) {
        Identifier deseado = SoulSkullEntity.contar(entity.getUUID()) > 0 ? MODELO_ACTIVO : MODELO_NORMAL;
        if (!deseado.equals(stack.get(DataComponents.ITEM_MODEL))) {
            stack.set(DataComponents.ITEM_MODEL, deseado);
        }
    }
}
