package org.shadowskylyn.hadesmod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.UUID;

public class SpearProjectileEntity extends Projectile {

    // =========================
    // SYNCED DATA
    // =========================

    private static final EntityDataAccessor<Boolean> STUCK =
            SynchedEntityData.defineId(
                    SpearProjectileEntity.class,
                    EntityDataSerializers.BOOLEAN
            );

    private static final EntityDataAccessor<Integer> TICK_STATE =
            SynchedEntityData.defineId(
                    SpearProjectileEntity.class,
                    EntityDataSerializers.INT
            );

    // =========================
    // STATE
    // =========================

    private LivingEntity stuckTarget;
    private BlockPos stuckPos;

    private float power = 1.0f;
    private UUID ownerUUID;

    // =========================
    // CONSTRUCTOR
    // =========================

    public SpearProjectileEntity(EntityType<? extends Projectile> type, Level level) {
        super(type, level);

        this.noPhysics = false;
        this.setNoGravity(true);
    }

    // =========================
    // ENTITY SYNC
    // =========================

    @Override
    protected void defineSynchedData() {

        this.entityData.define(STUCK, false);
        this.entityData.define(TICK_STATE, 0);
    }

    // =========================
    // OWNER
    // =========================

    public void setOwner(Player player) {
        this.ownerUUID = player.getUUID();
    }

    public Player getOwner() {

        if (ownerUUID == null) {
            return null;
        }

        return level().getPlayerByUUID(ownerUUID);
    }

    // =========================
    // POWER
    // =========================

    public void setPower(float power) {
        this.power = power;
    }

    // =========================
    // MAIN TICK
    // =========================

    @Override
    public void tick() {
        super.tick();

        // =========================
        // STUCK STATE
        // =========================

        if (this.entityData.get(STUCK)) {

            int t = this.entityData.get(TICK_STATE);

            this.entityData.set(TICK_STATE, t + 1);

            // freeze during tether phase
            if (t < 45) {
                this.setDeltaMovement(0, 0, 0);
            }

            // CLIENT VISUALS / never renders fix this
            if (level().isClientSide) {
                renderTether();
            }

            // SERVER PULL LOGIC
            if (!level().isClientSide) {

                if (t >= 20 && t <= 45) {
                    tickPull();
                }

                if (t > 45) {
                    recall();
                }
            }

            // move during recall
            if (t > 45) {
                this.move(MoverType.SELF, this.getDeltaMovement());
            }
            return;
        }

        // =========================
        // COLLISION
        // =========================

        HitResult hit = ProjectileUtil.getHitResultOnMoveVector(
                this,
                this::canHitEntity
        );

        if (hit.getType() != HitResult.Type.MISS) {
            this.onHit(hit);
        }

        // =========================
        // FLIGHT PARTICLES/fix this
        // =========================

        if (level().isClientSide) {

            level().addParticle(
                    ParticleTypes.CRIT,
                    getX(),
                    getY(),
                    getZ(),
                    0,
                    0,
                    0
            );
        }

        // =========================
        // MOVEMENT
        // =========================

        this.move(MoverType.SELF, this.getDeltaMovement());

        this.setDeltaMovement(
                this.getDeltaMovement().scale(0.98)
        );
    }

    // =========================
    // ENTITY FILTER
    // =========================

    @Override
    protected boolean canHitEntity(Entity entity) {

        return entity instanceof LivingEntity
                && entity != getOwner();
    }

    // =========================
    // HIT ROUTING
    // =========================

    @Override
    protected void onHit(HitResult result) {

        if (result instanceof EntityHitResult entityHit) {
            onHitEntity(entityHit);
        }
        else if (result instanceof BlockHitResult blockHit) {
            onHitBlock(blockHit);
        }
    }

    // =========================
    // HIT ENTITY
    // =========================

    @Override
    protected void onHitEntity(EntityHitResult result) {

        Entity entity = result.getEntity();

        Player owner = getOwner();

        if (owner == null) return;

        if (entity instanceof LivingEntity living) {

            this.stuckTarget = living;

            // impact damage
            living.hurt(
                    owner.damageSources().playerAttack(owner),
                    6.0f * power
            );

            // impact knockback
            Vec3 dir = owner.position()
                    .subtract(living.position())
                    .normalize();

            living.setDeltaMovement(dir.scale(1.2));

            living.hurtMarked = true;
        }

        stick();
    }

    // =========================
    // HIT BLOCK
    // =========================

    @Override
    protected void onHitBlock(BlockHitResult result) {

        this.stuckPos = result.getBlockPos();

        stick();
    }

    // =========================
    // STICK STATE
    // =========================

    private void stick() {

        this.setDeltaMovement(0, 0, 0);

        this.setNoGravity(true);

        this.noPhysics = true;

        this.entityData.set(STUCK, true);

        this.entityData.set(TICK_STATE, 0);
    }

    // =========================
    // TETHER PULL
    // =========================

    private void tickPull() {

        Player owner = getOwner();

        if (owner == null) return;

        Vec3 ownerPos = owner.position();

        // =========================
        // PULL ENEMY
        // =========================

        if (stuckTarget != null) {

            Vec3 toOwner = ownerPos.subtract(
                    stuckTarget.position()
            );

            double dist = toOwner.length();

            if (dist > 1.2) {

                Vec3 dir = toOwner.normalize();

                double strength =
                        Math.min(0.35 + (dist * 0.02), 1.2)
                                * power;

                Vec3 newVel = dir.scale(strength);

                stuckTarget.setDeltaMovement(
                        stuckTarget.getDeltaMovement()
                                .scale(0.6)
                                .add(newVel)
                );

                stuckTarget.hurtMarked = true;
            }
        }

        // =========================
        // PULL PLAYER
        // =========================

        if (stuckTarget == null && stuckPos != null) {

            Vec3 target = Vec3.atCenterOf(stuckPos);

            Vec3 toTarget = target.subtract(owner.position());

            double dist = toTarget.length();

            if (dist > 1.2) {

                Vec3 dir = toTarget.normalize();

                double strength =
                        Math.min(0.45 + (dist * 0.03), 1.4)
                                * power;

                Vec3 newVel = dir.scale(strength);

                owner.setDeltaMovement(
                        owner.getDeltaMovement()
                                .scale(0.6)
                                .add(newVel)
                );

                owner.hurtMarked = true;
            }
        }
    }

    // =========================
    // TETHER VISUAL // Doesnt work yet
    // =========================

    private void renderTether() {

        Player owner = getOwner();

        if (owner == null) return;

        Vec3 start = owner.getEyePosition();

        // use spear position directly
        Vec3 end = this.position();

        Vec3 diff = end.subtract(start);

        double length = diff.length();

        int steps = Math.max(10, (int)(length * 5));

        for (int i = 0; i <= steps; i++) {

            double t = i / (double) steps;

            Vec3 pos = start.add(diff.scale(t));
            //Figure why this wont work
            level().addParticle(
                    level().random.nextBoolean()
                            ? ParticleTypes.END_ROD
                            : ParticleTypes.ELECTRIC_SPARK,

                    pos.x,
                    pos.y,
                    pos.z,

                    0,
                    0,
                    0
            );
        }
    }

    // =========================
    // RECALL
    // =========================

    private void recall() {

        Player owner = getOwner();

        if (owner == null) return;

        Vec3 dir = owner.position()
                .subtract(this.position())
                .normalize();

        this.noPhysics = false;

        this.setDeltaMovement(dir.scale(2.0));

        // remove once returned
        if (this.distanceTo(owner) < 1.5f) {
            this.discard();
        }
    }
}