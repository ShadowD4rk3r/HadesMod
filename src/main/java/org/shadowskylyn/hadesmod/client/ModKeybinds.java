package org.shadowskylyn.hadesmod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.shadowskylyn.hadesmod.HadesMod;

public final class ModKeybinds {

        private static final String CATEGORY =  "key.categories." + HadesMod.MODID;

        public static final ModKeybinds INSTANCE = new ModKeybinds();

        private ModKeybinds() {}

        public static final KeyMapping DASH_KEY = new KeyMapping(
                "key." + HadesMod.MODID + ".dash_key",
                KeyConflictContext.IN_GAME,
                InputConstants.getKey(InputConstants.KEY_X, -1),
                CATEGORY
                );

}
