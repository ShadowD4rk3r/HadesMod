package org.shadowskylyn.hadesmod.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.shadowskylyn.hadesmod.entity.SpearProjectileEntity;
import org.shadowskylyn.hadesmod.registry.ModEntities;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.animatable.GeoItem;
// =========================
// CHARGED SPEAR THROW (NO TRIDENT BEHAVIOR)
// =========================


public class EternalSpear extends Item implements GeoItem{

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public EternalSpear(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {

        if (!(entity instanceof Player player)) return;

        int charge = this.getUseDuration(stack) - timeLeft;
        float power = Math.min(charge / 20f, 1f);

        if (!level.isClientSide) {

            SpearProjectileEntity spear = new SpearProjectileEntity(
                    ModEntities.SPEAR_PROJECTILE.get(),
                    level
            );

            spear.setOwner(player);
            spear.setPower(power);

            Vec3 look = player.getLookAngle();

            spear.setPos(
                    player.getX(),
                    player.getEyeY(),
                    player.getZ()
            );

            spear.setDeltaMovement(
                    look.x * (2.8 * power),
                    look.y * (2.8 * power),
                    look.z * (2.8 * power)
            );

            level.addFreshEntity(spear);
        }
    }

    // disable vanilla trident throw completely
    @Override
    public boolean isFoil(ItemStack stack) {
        return true; // glow always (dash glow can override later visually)
    }
}