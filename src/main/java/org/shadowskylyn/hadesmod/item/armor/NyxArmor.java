package org.shadowskylyn.hadesmod.item.armor;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class NyxArmor implements ArmorMaterial {

    private static final int[] DURABILITY = {
            13, //boots
            15, // leggings
            16, //chestplate
            11, //helmet
    };

    private static final int[] DEFENSE = {
            3,
            6,
            8,
            3
    };

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return DURABILITY[type.getSlot().getIndex()] * 37;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return DEFENSE[type.getSlot().getIndex()];
    }

}
