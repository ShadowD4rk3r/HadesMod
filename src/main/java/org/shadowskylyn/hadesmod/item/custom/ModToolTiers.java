package org.shadowskylyn.hadesmod.item.custom;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.item.ModItems;

import java.util.List;

public class ModToolTiers {
    public static final Tier OCEANCORE = TierSortingRegistry.registerTier(
            new ForgeTier(5, 2500, 8f, 5f, 25,
                    Tags.Blocks.NEEDS_NETHERITE_TOOL,
                    () -> Ingredient.of(ModItems.OCEANCORE.get())
            ),
            new ResourceLocation(HadesMod.MODID, "oceancore"),
            List.of(Tiers.NETHERITE),
            List.of()
    );
}
