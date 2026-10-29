package com.example.bloodmod;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Arma de hemomancia con 3 modos. Shift + CLICK derecho para cambiar:
 *   0 = Katana  (larga, RÁPIDA, recupera 1/2 corazón por golpe)
 *   1 = Guadaña (daño en área; al matar suelta garras que atraen a los hostiles)
 *   2 = Arco    (mantén CLICK derecho para cargar: drena vida, más carga =
 *                más flechas + más daño en área)
 */
public class SangreItem extends Item {

    public static final int KATANA = 0;
    public static final int GUADANA = 1;
    public static final int ARCO = 2;
    public static final int TOTAL_MODOS = 3;

    // ---- Ajustes (cámbialos a tu gusto) ----
    static final float CURA_KATANA = 1.0f;       // 1 HP = 1/2 corazón
    static final double VELOCIDAD_KATANA = -1.6; // base 4.0 -> 2.4 golpes/seg (rápida)
    static final double ALCANCE_KATANA = 2.5;

    static final double RADIO_GARRAS = 10.0;     // a qué distancia atraen las garras
    static final float DANO_GARRAS = 2.0f;

    static final int MAX_CARGA = 60;             // ticks para carga completa (3 s)
    static final int MIN_CARGA = 8;              // carga mínima para disparar
    static final int INTERVALO_DRENAJE = 10;     // cada cuántos ticks cargando pierdes 1 HP
    // => carga completa = 6 HP (3 corazones) de coste

    public SangreItem(Properties props) {
        super(props);
    }

    // ---------- Modo guardado en el propio ItemStack ----------

    public static int getMode(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getIntOr("mode", 0);
    }

    private static void applyMode(ItemStack stack, int mode) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt("mode", mode));

        ItemAttributeModifiers.Builder b = ItemAttributeModifiers.builder();
        String nombre;
        String modelo;
        switch (mode) {
            case KATANA -> {
                nombre = "§4Katana de Sangre";
                modelo = "katana_sangre";
                b.add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 6, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND);
                b.add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, VELOCIDAD_KATANA, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND);
                b.add(Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(Identifier.fromNamespaceAndPath("bloodmod", "katana_reach"),
                                ALCANCE_KATANA, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND);
            }
            case GUADANA -> {
                nombre = "§cGuadaña Carmesí";
                modelo = "guadana_carmesi";
                b.add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 9, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND);
                b.add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, -3.0, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND);
            }
            default -> {
                nombre = "§5Arco de Sangre";
                modelo = "arco_sangre";
                b.add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 2, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND);
                b.add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.4, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND);
            }
        }
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, b.build());
        stack.set(DataComponents.CUSTOM_NAME, Component.literal(nombre));
        // Cambia la textura según el modo (assets/bloodmod/items/<modelo>.json)
        stack.set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("bloodmod", modelo));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, EquipmentSlot slot) {
        if (!stack.has(DataComponents.CUSTOM_DATA)) {
            applyMode(stack, KATANA);
        }
    }

    // ---------- CLICK derecho: cambiar de modo / empezar a cargar el arco ----------

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                applyMode(stack, (getMode(stack) + 1) % TOTAL_MODOS);
                level.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                        SoundSource.PLAYERS, 0.8f, 0.6f);
            }
            return InteractionResult.SUCCESS;
        }

        if (getMode(stack) == ARCO && player.getHealth() > 4f
                && !player.getCooldowns().isOnCooldown(stack)) {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    // ---------- Arco: carga con coste de vida ----------

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return getMode(stack) == ARCO ? 72000 : 0;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return getMode(stack) == ARCO ? ItemUseAnimation.BOW : ItemUseAnimation.NONE;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remaining) {
        if (!(level instanceof ServerLevel sl) || !(entity instanceof Player player)) return;

        int cargado = getUseDuration(stack, entity) - remaining;
        if (cargado > 0 && cargado <= MAX_CARGA && cargado % INTERVALO_DRENAJE == 0) {
            if (player.getHealth() > 3f) {
                player.setHealth(player.getHealth() - 1f); // 1 HP por intervalo
                sl.sendParticles(ParticleTypes.CRIMSON_SPORE,
                        player.getX(), player.getY(0.6), player.getZ(), 6, 0.3, 0.3, 0.3, 0.02);
            } else {
                player.stopUsingItem(); // nunca te mata: corta la carga
            }
        }
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int remaining) {
        if (!(level instanceof ServerLevel sl) || !(entity instanceof Player player)) return false;

        int cargado = Math.min(getUseDuration(stack, entity) - remaining, MAX_CARGA);
        if (cargado < MIN_CARGA) return false;

        float carga = cargado / (float) MAX_CARGA;      // 0..1
        int flechas = 1 + Math.round(carga * 4);         // 1..5 flechas
        for (int i = 0; i < flechas; i++) {
            float desvio = (i - (flechas - 1) / 2f) * 7f; // abanico de 7° entre flechas
            Arrow flecha = new Arrow(sl, player, new ItemStack(Items.ARROW), stack);
            flecha.shootFromRotation(player, player.getXRot(), player.getYRot() + desvio,
                    0f, 2.5f + carga, 1.0f);
            flecha.setBaseDamage(0.0); // el daño real es el de área (SangreEvents)
            flecha.pickup = AbstractArrow.Pickup.DISALLOWED;
            SangreEvents.CARGAS.put(flecha, Math.round(carga * 100));
            sl.addFreshEntity(flecha);
        }
        sl.playSound(null, player.blockPosition(), SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS, 1f, 0.6f);
        player.getCooldowns().addCooldown(stack, 15);
        return true;
    }

    // ---------- Katana y guadaña: efectos al golpear ----------

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int mode = getMode(stack);

        if (mode == KATANA) {
            attacker.heal(CURA_KATANA); // 1/2 corazón por golpe
            if (attacker.level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.DAMAGE_INDICATOR,
                        target.getX(), target.getY(0.5), target.getZ(), 4, 0.2, 0.2, 0.2, 0.1);
            }
        }

        if (mode == GUADANA && attacker.level() instanceof ServerLevel sl) {
            DamageSource fuente = attacker instanceof Player p
                    ? sl.damageSources().playerAttack(p)
                    : sl.damageSources().mobAttack(attacker);

            // Daño en área alrededor del objetivo
            AABB area = target.getBoundingBox().inflate(3.0, 1.0, 3.0);
            for (LivingEntity e : sl.getEntitiesOfClass(LivingEntity.class, area,
                    x -> x != attacker && x != target && x.isAlive() && !(x instanceof Player))) {
                e.hurtServer(sl, fuente, 6f);
            }
            sl.sendParticles(ParticleTypes.SWEEP_ATTACK,
                    target.getX(), target.getY(0.5), target.getZ(), 3, 1.0, 0.1, 1.0, 0);

            // Si el golpe mató al objetivo -> garras que atraen a los hostiles
            if (target.isDeadOrDying()) {
                soltarGarras(sl, attacker, fuente, target.position().add(0, target.getBbHeight() / 2, 0));
            }
        }
    }

    /** Garras de sangre: dañan un poco y arrastran a los hostiles hacia donde murió la víctima. */
    private void soltarGarras(ServerLevel sl, LivingEntity atacante, DamageSource fuente, Vec3 origen) {
        AABB zona = new AABB(origen, origen).inflate(RADIO_GARRAS);
        for (Monster m : sl.getEntitiesOfClass(Monster.class, zona, x -> x.isAlive() && x != atacante)) {
            Vec3 haciaOrigen = origen.subtract(m.position());
            double dist = haciaOrigen.length();

            // línea de partículas (la "garra")
            int pasos = Math.max(1, (int) (dist * 3));
            for (int i = 0; i <= pasos; i++) {
                Vec3 p = m.position().add(0, m.getBbHeight() / 2, 0)
                        .add(haciaOrigen.scale((double) i / pasos));
                sl.sendParticles(ParticleTypes.CRIMSON_SPORE, p.x, p.y, p.z, 2, 0.03, 0.03, 0.03, 0);
            }
            sl.sendParticles(ParticleTypes.SWEEP_ATTACK, m.getX(), m.getY(0.5), m.getZ(), 1, 0, 0, 0, 0);

            m.hurtServer(sl, fuente, DANO_GARRAS);  // primero daño...
            if (dist > 1.5) {                        // ...y luego el tirón (para que el knockback no lo anule)
                double fuerza = Math.min(1.6, 0.4 + dist * 0.15);
                m.setDeltaMovement(haciaOrigen.normalize().scale(fuerza).add(0, 0.25, 0));
                m.hurtMarked = true;
            }
        }
    }
}
