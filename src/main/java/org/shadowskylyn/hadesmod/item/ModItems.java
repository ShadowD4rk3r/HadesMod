package org.shadowskylyn.hadesmod.item;

import net.minecraft.world.item.Rarity;
import org.shadowskylyn.hadesmod.HadesMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems
{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HadesMod.MODID);

    public static final RegistryObject<Item> DAEDALUS_HAMMER = ITEMS.register("daedalus_hammer",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> OBOL = ITEMS.register("obol",
            () -> new Item(new Item.Properties()));

    //God Ore's and Dionysus

    //Aphrodite's Ore
    public static final RegistryObject<Item> ROSEVILLE = ITEMS.register("roseville" ,
            () -> new Item(new Item.Properties().fireResistant()));
    //Athena's Ore
    public static final RegistryObject<Item> PALLASITE = ITEMS.register("pallasite",
            () -> new Item(new Item.Properties().fireResistant()));
    //Artemis' Ore
    public static final RegistryObject<Item> MOONVINE = ITEMS.register("moonvine",
            () -> new Item(new Item.Properties().fireResistant()));
    //Ares' Ore
    public static final RegistryObject<Item> BLOODIRON = ITEMS.register("bloodiron",
            () -> new Item(new Item.Properties().fireResistant()));
    //Apollo's Ore
    public static final RegistryObject<Item> SOLARITE = ITEMS.register("solarite",
            () -> new Item(new Item.Properties().fireResistant()));
    //Dionysus' Wine
    public static final RegistryObject<Item> DIONYSIAN_WINE = ITEMS.register("dionysian_wine",
            () -> new Item(new Item.Properties().food(ModFoods.DIONYSIAN_WINE).stacksTo(16).fireResistant()));
    //Hera's Ore
    public static final RegistryObject<Item> REGALIA = ITEMS.register("regalia",
            () -> new Item(new Item.Properties().fireResistant()));
    //Poseidon's Ore
    public static final RegistryObject<Item> OCEANCORE = ITEMS.register("oceancore",
            () -> new Item(new Item.Properties().fireResistant()));
    //Zeus' Ore
    public static final RegistryObject<Item> STORMHEART = ITEMS.register("stormheart",
            () -> new Item(new Item.Properties().fireResistant()));


    //custom food item, refer to ModFoods to create food
    public static final RegistryObject<Item> AMBROSIA = ITEMS.register("ambrosia",
            () -> new Item(new Item.Properties().food(ModFoods.AMBROSIA).stacksTo(16)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
