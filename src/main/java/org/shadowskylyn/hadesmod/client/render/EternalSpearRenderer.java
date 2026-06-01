package org.shadowskylyn.hadesmod.client.render;

import org.shadowskylyn.hadesmod.client.model.EternalSpearModel;
import org.shadowskylyn.hadesmod.item.custom.EternalSpear;

import software.bernie.geckolib.renderer.GeoItemRenderer;

public class EternalSpearRenderer extends GeoItemRenderer<EternalSpear> {

    public EternalSpearRenderer() {
        super(new EternalSpearModel());
    }
}