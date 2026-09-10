package org.shadowskylyn.hadesmod.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.item.armor.NyxArmor.NyxArmorAbility;
import org.shadowskylyn.hadesmod.item.armor.NyxArmor.NyxArmorEffect;

@Mod.EventBusSubscriber(
        modid = HadesMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT
)
public class AbilityHud {

    private static final int ABILITY_DURATION = 20 * 8;
    private static final int MAX_COOLDOWN = 20 * 20;

    @SubscribeEvent
    public static void renderHud(RenderGuiEvent.Post event) {

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        // Only show the bar with the full Nyx set
        if (NyxArmorEffect.getNyxArmorPieces(minecraft.player) != 4) {
            return;
        }

        GuiGraphics guiGraphics = event.getGuiGraphics();

        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        int barWidth = 100;
        int barHeight = 8;

        int x = (screenWidth - barWidth) / 2;
        int y = screenHeight - 60;

        // Background
        guiGraphics.fill(
                x,
                y,
                x + barWidth,
                y + barHeight,
                0xFF202020
        );

        float progress;

        if (NyxArmorAbility.isActive()) {

            int remaining = NyxArmorAbility.getAbilityTimer();

            progress = (float) remaining / ABILITY_DURATION;

        } else {

            int cooldown = NyxArmorAbility.getCooldown();

            progress = 1.0F - ((float) cooldown / MAX_COOLDOWN);
        }

        // Keep progress between 0 and 1
        progress = Math.max(0.0F, Math.min(1.0F, progress));

        int filledWidth = (int) (barWidth * progress);

        if (filledWidth > 0) {
            guiGraphics.fill(
                    x,
                    y,
                    x + filledWidth,
                    y + barHeight,
                    0xFFAA66CC
            );
        }

        guiGraphics.renderOutline(
                x,
                y,
                barWidth,
                barHeight,
                0xFFFFFFFF
        );
    }
}
