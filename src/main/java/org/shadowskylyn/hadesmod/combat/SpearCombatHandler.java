package org.shadowskylyn.hadesmod.combat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.item.custom.EternalSpear;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = HadesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpearCombatHandler {

    // =========================
    // GLOBAL STATE (DASH SYSTEM)
    // =========================

    public static int dashTicks = 0;          // visual + combat window
    public static float dashDamageBonus = 0f; // scaling hook

    // =========================
    // TICK SYSTEM
    // =========================

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.side.isServer()) return;

        if (dashTicks > 0) {
            dashTicks--;

            // decay bonus smoothly
            dashDamageBonus *= 0.90f;
        } else {
            dashDamageBonus = 0f;
        }
    }

    // =========================
    // MELEE SPEAR (RAYCAST)
    // =========================

    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {

        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();

        if (!(stack.getItem() instanceof EternalSpear)) return;

        event.setCanceled(true);

        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle();

        double range = 5.0;

        AABB box = player.getBoundingBox()
                .expandTowards(look.scale(range))
                .inflate(1.0);

        List<LivingEntity> targets = new ArrayList<>();

        for (Entity e : player.level().getEntities(player, box)) {

            if (e instanceof LivingEntity living && e.isPickable()) {

                Vec3 toEntity = e.position().subtract(start);

                if (toEntity.dot(look) > 0.25) {
                    targets.add(living);
                }
            }
        }

        if (targets.isEmpty()) return;

        targets.sort((a, b) ->
                Double.compare(a.distanceToSqr(player), b.distanceToSqr(player))
        );

        float baseDamage = 11.0f;

        float damage = baseDamage + dashDamageBonus;

        Vec3 knockDir = look.normalize();

        int index = 0;

        for (LivingEntity target : targets) {

            float finalDamage = damage * (1.0f - index * 0.2f);

            if (finalDamage <= 0) break;

            target.hurt(
                    player.damageSources().playerAttack(player),
                    finalDamage
            );

            // knockback
            target.push(knockDir.x * 1.2, 0.2, knockDir.z * 1.2);

            // hit stop feel
            target.setDeltaMovement(0, 0, 0);
            target.invulnerableTime = 2;

            index++;
        }

        stack.hurtAndBreak(1, player, p ->
                p.broadcastBreakEvent(player.getUsedItemHand())
        );
    }
}