package org.shadowskylyn.hadesmod.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.client.ModKeybinds;


@Mod.EventBusSubscriber( modid = HadesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeHandler {
    private static int dashCooldown = 0;
    private static final int MAX_DASHES = 2;
    private static int dashesLeft = MAX_DASHES;
    private static final int DASH_COOLDOWN_TICKS = 10;
    private static int dashMessageTicks = 0;
    private static boolean showDashMessage = false;

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event)
    {
        if (showDashMessage)
        {
            LocalPlayer player = Minecraft.getInstance().player;

            if (player != null)
            {
                player.playSound(SoundEvents.CAMEL_DASH, 0.5F, 1.5F);
            }

            dashMessageTicks--;

            if (dashMessageTicks <= 0)
            {
                showDashMessage = false;
            }
        }

        if (event.phase != TickEvent.Phase.END) return;

        if (dashCooldown > 0)
        {
            dashCooldown--;
        }

        //recharge the dash when you use your last one
        if (dashCooldown <= 0 && dashesLeft  == 0)
        {
            dashesLeft = MAX_DASHES;

            showDashMessage = true;
            dashMessageTicks = 5;
        }

        if (ModKeybinds.DASH_KEY.consumeClick() && dashesLeft != 0 && dashCooldown <= 0)
        {
            dashesLeft--;

            doDash();

            if (dashesLeft == 0)
            {
                dashCooldown = DASH_COOLDOWN_TICKS;
            }
        }
    }

    private static void doDash() {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) return;

        Vec3 look = player.getLookAngle();

        double dashStrength = 1.5;

        player.setDeltaMovement(
                look.x * dashStrength,
                player.getDeltaMovement().y,
                look.z * dashStrength
        );

        player.hasImpulse = true;
        player.hurtMarked = true;
    }
}
