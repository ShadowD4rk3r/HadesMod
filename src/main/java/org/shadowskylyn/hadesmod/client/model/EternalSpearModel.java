package org.shadowskylyn.hadesmod.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import org.shadowskylyn.hadesmod.item.custom.EternalSpear;

public class EternalSpearModel extends GeoModel<EternalSpear>
{
    private final ResourceLocation model =
            new ResourceLocation("hadesmod", "geo/spear.geo.json");

    private final ResourceLocation texture =
            new ResourceLocation("hadesmod", "textures/item/spear.png");

    private final ResourceLocation animation =
            new ResourceLocation("hadesmod", "animations/spear.animation.json");

    @Override
    public ResourceLocation getModelResource(EternalSpear animatable)
    {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(EternalSpear animatable)
    {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(EternalSpear animatable)
    {
        return animation;
    }
}