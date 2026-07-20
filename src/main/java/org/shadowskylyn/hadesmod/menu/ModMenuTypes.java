package org.shadowskylyn.hadesmod.menu;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.menu.Dionysus.DionysusMenu;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, HadesMod.MODID);

    public static final RegistryObject<MenuType<DionysusMenu>> DIONYSUS_MENU =
            MENUS.register("dionysus_menu",
                    () -> IForgeMenuType.create(DionysusMenu::new));
}