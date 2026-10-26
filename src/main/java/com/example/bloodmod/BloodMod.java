package com.example.bloodmod;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(BloodMod.MODID)
public class BloodMod {
    public static final String MODID = "bloodmod";

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> ARMA_SANGRE = ITEMS.registerItem(
            "arma_sangre", SangreItem::new, props -> props.stacksTo(1).fireResistant());

    public static final DeferredItem<Item> ENDERALITE_GUILLOTINE = ITEMS.registerItem(
            "enderalite_guillotine", EnderaliteGuillotineItem::new,
            props -> EnderaliteGuillotineItem.configurar(props));

    public static final DeferredItem<Item> VAMPIRIC_CRIMSON = ITEMS.registerItem(
            "vampiric_crimson", VampiricCrimsonItem::new,
            props -> VampiricCrimsonItem.configurar(props));

    public BloodMod(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        modEventBus.addListener(BloodMod::addCreative);
    }

    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ARMA_SANGRE);
            event.accept(ENDERALITE_GUILLOTINE);
            event.accept(VAMPIRIC_CRIMSON);
        }
    }
}
