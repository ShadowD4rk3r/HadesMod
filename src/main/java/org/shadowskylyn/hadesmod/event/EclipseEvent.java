package org.shadowskylyn.hadesmod.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.core.jmx.Server;
import org.shadowskylyn.hadesmod.world.WorldEventManager;

public class EclipseEvent {

    private static boolean active = false;
    private static int ticksRemaining = 0;
    private static float eclipseProgress = 0.0F;

    public static void start (ServerLevel level)
    {
        if (active)
            return;

        active = true;
        WorldEventManager.startEvent(WorldEvent.ECLIPSE);


        //Full minecraft day (subject to change depending on what's needed)
        ticksRemaining = 24000;

        MutableComponent text = Component.literal("The ")
                .append(Component.literal("Olympian's")
                        .withStyle(style ->
                                style.withColor(ChatFormatting.GOLD)
                                .withObfuscated(true)))
                .append(Component.literal(" haven't taken kindly to those words."));

        level.getServer().getPlayerList().broadcastSystemMessage(
                text,
                false
        );
    }

    public static void tick(ServerLevel level)
    {
        if (!active)
            return;

        ticksRemaining--;

        eclipseProgress = 1.0F - ((float) ticksRemaining / 24000F);

        if(ticksRemaining <= 0)
            end(level);
    }

    public static void end(ServerLevel level)
    {
        active = false;

        if (WorldEventManager.isEvent(WorldEvent.ECLIPSE))
        {
            active = false;
            ticksRemaining = 0;
            eclipseProgress = 0.0F;

            WorldEventManager.endEvent();
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {

        if (!EclipseEvent.isActive())
            return;

        for (ServerLevel level : event.getServer().getAllLevels()) {
            EclipseEvent.tick(level);
        }
    }

    public static boolean isActive() {
        return active;
    }

    public static float getProgress() {
        return eclipseProgress;
    }

    public static int getTicksRemaining() {
        return ticksRemaining;
    }
}
