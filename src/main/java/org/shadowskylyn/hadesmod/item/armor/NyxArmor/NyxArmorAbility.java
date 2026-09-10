package org.shadowskylyn.hadesmod.item.armor.NyxArmor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class NyxArmorAbility {
    private static final int ABILITY_DURATION = 20 * 8;
    private static final int COOLDOWN = 20 * 20;

    private static int abilityTimer = 0;
    private static int cooldown = 0;

    private static boolean active = false;

    public static void tick() {

        if (cooldown > 0) {
            cooldown--;
        }

        if (!active) {
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) {
            active = false;
            abilityTimer = 0;
            return;
        }

        // Ability ends if the player removes the full Nyx set
        if (NyxArmorEffect.getNyxArmorPieces(player) != 4) {
            deactivate(player);
            return;
        }

        // Ability ends when night ends
        long time = player.level().getDayTime() % 24000;

        if (time < 12000 || time >= 23000) {
            deactivate(player);
            return;
        }

        clearMobTargets(player);

        abilityTimer--;

        if (abilityTimer <= 0) {
            deactivate(player);
        }
    }

    public static void activate() {

        if (cooldown > 0 || active) {
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) {
            return;
        }

        // Full Nyx set required
        if (NyxArmorEffect.getNyxArmorPieces(player) != 4) {
            player.displayClientMessage(
                    Component.literal("Nyx's Embrace requires the full Nyx armor set."),
                    true
            );
            return;
        }
        long time = player.level().getDayTime() % 24000;

        if (time < 12000 || time >= 23000) {
            player.displayClientMessage(
                    Component.literal("Nyx's Embrace can only be used at night."),
                    true
            );
            return;
        }

        active = true;
        abilityTimer = ABILITY_DURATION;

        // Apply invisibility
        player.addEffect(new MobEffectInstance(
                MobEffects.INVISIBILITY,
                ABILITY_DURATION,
                0,
                false,
                false
        ));

        // Make nearby mobs forget the player
        clearMobTargets(player);
    }

    public static void deactivate() {

        LocalPlayer player = Minecraft.getInstance().player;

        active = false;
        abilityTimer = 0;
        cooldown = COOLDOWN;

        if (player != null) {
            player.removeEffect(MobEffects.INVISIBILITY);
        }
    }

    private static void deactivate(LocalPlayer player) {

        active = false;
        abilityTimer = 0;
        cooldown = COOLDOWN;

        player.removeEffect(MobEffects.INVISIBILITY);
    }

    private static void clearMobTargets(LocalPlayer player) {

        for (LivingEntity entity : player.level().getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(32)
        )) {

            if (entity instanceof Mob mob) {

                if (mob.getTarget() == player) {
                    mob.setTarget(null);
                }
            }
        }
    }

    public static void onPlayerAttack() {

        if (!active) {
            return;
        }

        deactivate();
    }

    public static boolean isActive() {
        return active;
    }

    public static int getAbilityTimer() {
        return abilityTimer;
    }


    public static int getCooldown() {
        return cooldown;
    }
}
