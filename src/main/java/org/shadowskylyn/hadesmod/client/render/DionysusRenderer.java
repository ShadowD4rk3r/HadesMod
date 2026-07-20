package org.shadowskylyn.hadesmod.client.render;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.shadowskylyn.hadesmod.entity.DionysusTraderEntity;
import org.shadowskylyn.hadesmod.client.model.DionysusModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DionysusRenderer extends GeoEntityRenderer<DionysusTraderEntity> {
    public DionysusRenderer(EntityRendererProvider.Context context) {
        super(context, new DionysusModel());
    }
}
