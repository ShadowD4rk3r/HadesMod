package org.shadowskylyn.hadesmod.client.handler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.client.ModKeybinds;
import org.shadowskylyn.hadesmod.client.render.SpearProjectileRenderer;
import org.shadowskylyn.hadesmod.registry.ModEntities;

@Mod.EventBusSubscriber(
        modid = HadesMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ClientModHandler {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                ModEntities.SPEAR_PROJECTILE.get(),
                SpearProjectileRenderer::new
        );
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event)
    {
        event.register(ModKeybinds.DASH_KEY);
    }
}
