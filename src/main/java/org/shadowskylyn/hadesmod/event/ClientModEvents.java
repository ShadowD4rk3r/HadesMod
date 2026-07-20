package org.shadowskylyn.hadesmod.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.client.render.DionysusRenderer;
import org.shadowskylyn.hadesmod.menu.Dionysus.DionysusScreen;
import org.shadowskylyn.hadesmod.menu.ModMenuTypes;
import org.shadowskylyn.hadesmod.registry.ModEntities;

@Mod.EventBusSubscriber(modid = HadesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                ModEntities.DIONYSUS_TRADER.get(),
                DionysusRenderer::new
        );
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(
                    ModMenuTypes.DIONYSUS_MENU.get(),
                    DionysusScreen::new
            );
        });
    }
}
