package org.shadowskylyn.hadesmod.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties AMBROSIA = new FoodProperties.Builder().nutrition(9).saturationMod(0.9f)
            .effect( () -> new MobEffectInstance(MobEffects.REGENERATION, 200), 1f )
            .effect( () -> new MobEffectInstance(MobEffects.POISON, 200), 0.3f).build();


    public static final FoodProperties DIONYSIAN_WINE = new FoodProperties.Builder().nutrition(3).saturationMod(0.3f)
            .effect( () -> new MobEffectInstance(MobEffects.CONFUSION, 300, 0), 1f)
            .effect( () -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 250, 0), 1f)
            .alwaysEat()
            .build();
}
