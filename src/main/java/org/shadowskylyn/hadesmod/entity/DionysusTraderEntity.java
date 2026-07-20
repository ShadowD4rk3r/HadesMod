package org.shadowskylyn.hadesmod.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.shadowskylyn.hadesmod.item.ModItems;
import org.shadowskylyn.hadesmod.shop.ShopOffer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.shadowskylyn.hadesmod.menu.Dionysus.DionysusMenuProvider;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;

public class DionysusTraderEntity extends PathfinderMob implements GeoEntity {


    private final List<ShopOffer> offers = new ArrayList<>();
    private final List<String> dialogue = new ArrayList<>();

    private boolean talking = false;

    public DionysusTraderEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);

        createOffers();
        createDialogue();
    }


    //GECKOLIB STUFF DONT GET RID OF

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // We'll add animations later
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    //Attributes
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.00)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    private void createOffers()
    {
        offers.add(new ShopOffer(
                new ItemStack(ModItems.OBOL.get(), 25),
                new ItemStack(ModItems.DIONYSIAN_WINE.get())
        ));

        offers.add(new ShopOffer(
                new ItemStack(ModItems.OBOL.get(), 50),
                new ItemStack(ModItems.NECTAR.get())
        ));
    }
    private void createDialogue() {
        dialogue.add("Ah, there you are.");
        dialogue.add("Care for a drink?");
        dialogue.add("Even the Underworld deserves celebration.");
    }

    public List<ShopOffer> getOffers() {
        return offers;
    }

    public List<String> getDialogue() { return dialogue; }

    public String getGodName() { return "Dionysus"; }

    public String getTitle() {return "God of Wine"; }



    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(0,
                new FloatGoal(this));


        this.goalSelector.addGoal(1,
                new RandomStrollGoal(this, 0.8) {

                    @Override
                    public boolean canUse() {

                        return !talking
                                && super.canUse();
                    }
                });


        this.goalSelector.addGoal(2,
                new LookAtPlayerGoal(
                        this,
                        Player.class,
                        8
                ));


        this.goalSelector.addGoal(3,
                new RandomLookAroundGoal(this));
    }


    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        if (!level().isClientSide && player instanceof ServerPlayer serverPlayer) {

            talking = true;

            this.getNavigation().stop();

            this.getLookControl().setLookAt(
                    player.getX(),
                    player.getEyeY(),
                    player.getZ()
            );

            NetworkHooks.openScreen(
                    serverPlayer,
                    new DionysusMenuProvider(this),
                    buffer -> buffer.writeInt(getId())
            );
        }

        return InteractionResult.sidedSuccess(level().isClientSide);
    }
}
