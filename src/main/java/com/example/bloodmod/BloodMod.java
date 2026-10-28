package com.example.bloodmod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(BloodMod.MODID)
public class BloodMod {
    public static final String MODID = "bloodmod";

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(MODID);

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

    public BloodMod(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        ENTITIES.register(modEventBus);
        modEventBus.addListener(BloodMod::addCreative);
    }

    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ARMA_SANGRE);
            event.accept(ENDERALITE_GUILLOTINE);
            event.accept(VAMPIRIC_CRIMSON);
            event.accept(SOUL_STAFF);
            event.accept(CHESTER_KEY);
        }
    }
}
