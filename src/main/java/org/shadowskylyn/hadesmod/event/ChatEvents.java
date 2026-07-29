package org.shadowskylyn.hadesmod.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.world.WorldEventManager;

@Mod.EventBusSubscriber(modid = HadesMod.MODID)
public class ChatEvents {

    @SubscribeEvent
    public static void onChat(ServerChatEvent event) {

        ServerPlayer player = event.getPlayer();
        ServerLevel level = player.serverLevel();;


        String message = event.getMessage().getString().toLowerCase();
        System.out.println("It got the message:");

        if (message.contains("apollo") && message.contains("artemis")) {
            System.out.println("Eclipse recongized");
            WorldEventManager.startEvent(WorldEvent.ECLIPSE);
            EclipseEvent.start(player.serverLevel());
        }
        else if (message.contains("end"))
        {
            System.out.println("Eclipse ending");
            EclipseEvent.end(player.serverLevel());
        }
    }

}
