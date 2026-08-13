package org.shadowskylyn.hadesmod.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.shadowskylyn.hadesmod.HadesMod;

public class ModDimensions {

    public static final ResourceKey<Level> LOOM_OF_FATES =
            ResourceKey.create(
                    Registries.DIMENSION,
                    new ResourceLocation("hadesmod", "loom_of_fates")
            );
}
