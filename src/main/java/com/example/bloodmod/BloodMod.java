package com.example.bloodmod;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(BloodMod.MODID)
public class BloodMod {
    public static final String MODID = "bloodmod";

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // ---- Armas ----
    public static final DeferredItem<Item> ARMA_SANGRE = ITEMS.registerItem(
            "arma_sangre", SangreItem::new, props -> props.stacksTo(1).fireResistant());

    public static final DeferredItem<Item> ENDERALITE_GUILLOTINE = ITEMS.registerItem(
            "enderalite_guillotine", EnderaliteGuillotineItem::new,
            props -> EnderaliteGuillotineItem.configurar(props));

    public static final DeferredItem<Item> VAMPIRIC_CRIMSON = ITEMS.registerItem(
            "vampiric_crimson", VampiricCrimsonItem::new,
            props -> VampiricCrimsonItem.configurar(props));

    // ---- Soul Staff + Soul Skull ----
    public static final DeferredItem<Item> SOUL_STAFF = ITEMS.registerItem(
            "soul_staff", SoulStaffItem::new,
            props -> SoulStaffItem.configurar(props));

    public static final DeferredHolder<EntityType<?>, EntityType<SoulSkullEntity>> SOUL_SKULL =
            ENTITIES.registerEntityType("soul_skull", SoulSkullEntity::new, MobCategory.MISC,
                    builder -> builder.sized(0.5f, 0.5f).clientTrackingRange(10).fireImmune());

    // ---- Chester ----
    public static final DeferredItem<Item> CHESTER_KEY = ITEMS.registerItem(
            "chester_key", ChesterKeyItem::new,
            props -> ChesterKeyItem.configurar(props));

    public static final DeferredHolder<EntityType<?>, EntityType<ChesterEntity>> CHESTER =
            ENTITIES.registerEntityType("chester", ChesterEntity::new, MobCategory.MISC,
                    builder -> builder.sized(0.75f, 0.85f).clientTrackingRange(10));

    // ---- Pestaña propia del modo creativo con todo lo del mod ----
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_PRINCIPAL = CREATIVE_TABS.register(
            "principal", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.bloodmod"))
                    .icon(() -> new ItemStack(ARMA_SANGRE.get()))
                    .displayItems((parametros, salida) -> {
                        salida.accept(ARMA_SANGRE.get());
                        salida.accept(ENDERALITE_GUILLOTINE.get());
                        salida.accept(VAMPIRIC_CRIMSON.get());
                        salida.accept(SOUL_STAFF.get());
                        salida.accept(CHESTER_KEY.get());
                    })
                    .build());

    public BloodMod(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        ENTITIES.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);
    }
}
