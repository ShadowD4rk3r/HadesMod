package org.shadowskylyn.hadesmod.item.custom;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.block.ModBlocks;
import org.shadowskylyn.hadesmod.item.ModItems;

public class ModCreativeModTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HadesMod.MODID);

    public static final RegistryObject<CreativeModeTab> HADES_MOD = CREATIVE_MODE_TAB.register("hades_tab", () -> CreativeModeTab.builder().icon( () -> new ItemStack( ModItems.OBOL.get()))
            .title(Component.translatable("creativetab.hades_tab"))
            .displayItems((itemDisplayParameters, output) -> {
                // goes in order from top to bottom how they get added probably should do it in aplhabetical order

                //Weapons
                output.accept(ModItems.ETERNAL_SPEAR.get());

                //items and foods below
                output.accept(ModItems.OBOL.get());
                output.accept(ModItems.DRACHMA.get());
                output.accept(ModItems.DAEDALUS_HAMMER.get());
                output.accept(ModItems.AMBROSIA.get());
                output.accept(ModItems.BLOODIRON.get());
                output.accept(ModItems.DIONYSIAN_WINE.get());
                output.accept(ModItems.MOONVINE.get());
                output.accept(ModItems.OCEANCORE.get());
                output.accept(ModItems.PALLASITE.get());
                output.accept(ModItems.REGALIA.get());
                output.accept(ModItems.ROSEVILLE.get());
                output.accept(ModItems.SOLARITE.get());
                output.accept(ModItems.STORMHEART.get());

                //Ores and blocks below
                output.accept(ModBlocks.GOD_ORE.get());

            })
            .build());

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
