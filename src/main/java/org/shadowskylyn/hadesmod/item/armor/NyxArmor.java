package org.shadowskylyn.hadesmod.item.armor;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.shadowskylyn.hadesmod.item.ModItems;

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

    @Override
    public int getEnchantmentValue() {
        return 25;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.DAEDALUS_HAMMER.get());
    }

    @Override
    public String getName() {
        return "hadesmod:nyx";
    }

    @Override
    public float getToughness() {
        return 3.0F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.1F;
    }

}
