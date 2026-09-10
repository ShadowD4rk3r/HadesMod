package org.shadowskylyn.hadesmod.item.armor.NyxArmor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.item.ModItems;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraft.world.phys.Vec3;

@Mod.EventBusSubscriber(
        modid = HadesMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class NyxArmorEffect {

    private static final int EFFECT_DURATION = 20 * 10;
    private static final int REFRESH_THRESHOLD = 20 * 5;


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

        if (event.phase != TickEvent.Phase.END)
            return;

        Player player = event.player;


        if (!player.level().isNight())
            return;

        int armorPieces = getNyxArmorPieces(player);

        if (!player.level().isNight() || armorPieces < 2) {
            return;
        }

        if (armorPieces < 4) {
            applyHalfSetBuffs(player);
        } else {
            applyFullSetBuffs(player);
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingHurtEvent event) {

        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        // Must be wearing the complete Nyx set
        if (getNyxArmorPieces(player) != 4) {
            return;
        }

        // Nyx's passive powers only function at night
        if (!player.level().isNight()) {
            return;
        }

        LivingEntity target = event.getEntity();

        if (isBehind(player, target)) {
            event.setAmount(event.getAmount() * 1.25F);
        }


    }

    private static boolean isBehind(Player player, net.minecraft.world.entity.LivingEntity target) {

        // Direction the target is looking
        Vec3 targetLook = target.getLookAngle().normalize();

        // Direction from the target toward the player
        Vec3 directionToPlayer = player.position()
                .subtract(target.position())
                .normalize();

        // Dot product tells us how closely the directions line up
        double dot = targetLook.dot(directionToPlayer);

        // Negative means the player is behind the target
        return dot < -0.5;
    }

    public static int getNyxArmorPieces(Player player) {

        int count = 0;

        if (isNyxArmor(player.getItemBySlot(EquipmentSlot.HEAD))) {
            count++;
        }

        if (isNyxArmor(player.getItemBySlot(EquipmentSlot.CHEST))) {
            count++;
        }

        if (isNyxArmor(player.getItemBySlot(EquipmentSlot.LEGS))) {
            count++;
        }

        if (isNyxArmor(player.getItemBySlot(EquipmentSlot.FEET))) {
            count++;
        }

        return count;
    }

    private static boolean isNyxArmor(ItemStack stack) {

        return stack.is(ModItems.NYX_HELMET.get())
                || stack.is(ModItems.NYX_CHESTPLATE.get())
                || stack.is(ModItems.NYX_LEGGINGS.get())
                || stack.is(ModItems.NYX_BOOTS.get());
    }

    private static void applyHalfSetBuffs(Player player) {

        MobEffectInstance nightVision =
                player.getEffect(MobEffects.NIGHT_VISION);

        MobEffectInstance resistance =
                player.getEffect(MobEffects.DAMAGE_RESISTANCE);

        if (nightVision == null ||
                nightVision.getDuration() <= REFRESH_THRESHOLD) {

            player.addEffect(new MobEffectInstance(
                    MobEffects.NIGHT_VISION,
                    EFFECT_DURATION,
                    0,
                    false,
                    false
            ));
        }

        if (resistance == null ||
                resistance.getDuration() <= REFRESH_THRESHOLD) {

            player.addEffect(new MobEffectInstance(
                    MobEffects.DAMAGE_RESISTANCE,
                    EFFECT_DURATION,
                    0,
                    false,
                    false
            ));
        }
    }

    private static void applyFullSetBuffs(Player player) {

        MobEffectInstance nightVision =
                player.getEffect(MobEffects.NIGHT_VISION);

        MobEffectInstance speed =
                player.getEffect(MobEffects.MOVEMENT_SPEED);

        MobEffectInstance resistance =
                player.getEffect(MobEffects.DAMAGE_RESISTANCE);


        if (nightVision == null ||
                nightVision.getDuration() <= REFRESH_THRESHOLD) {

            player.addEffect(new MobEffectInstance(
                    MobEffects.NIGHT_VISION,
                    EFFECT_DURATION,
                    0,
                    false,
                    false
            ));
        }

        if (speed == null ||
                speed.getDuration() <= REFRESH_THRESHOLD) {

            player.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SPEED,
                    EFFECT_DURATION,
                    1,
                    false,
                    false
            ));
        }

        if (resistance == null ||
                resistance.getDuration() <= REFRESH_THRESHOLD) {

            player.addEffect(new MobEffectInstance(
                    MobEffects.DAMAGE_RESISTANCE,
                    EFFECT_DURATION,
                    1,
                    false,
                    false
            ));
        }
    }
}
