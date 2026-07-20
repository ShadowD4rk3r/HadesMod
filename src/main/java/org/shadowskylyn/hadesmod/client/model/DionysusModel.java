package org.shadowskylyn.hadesmod.client.model;

import net.minecraft.resources.ResourceLocation;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.entity.DionysusTraderEntity;
import software.bernie.geckolib.model.GeoModel;

public class DionysusModel extends GeoModel<DionysusTraderEntity> {
    @Override
    public ResourceLocation getModelResource(DionysusTraderEntity entity) {
        return new ResourceLocation(HadesMod.MODID, "geo/dionysustrader.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DionysusTraderEntity entity) {
        return new ResourceLocation(HadesMod.MODID, "textures/entity/dionysus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DionysusTraderEntity entity) {
        return new ResourceLocation(HadesMod.MODID, "animations/dionysustrader.animation.json");
    }
}
