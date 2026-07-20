package org.shadowskylyn.hadesmod.shop;

import net.minecraft.world.item.ItemStack;

public class ShopOffer {

    private final ItemStack cost;
    private final ItemStack reward;

    public ShopOffer(ItemStack cost, ItemStack reward) {
        this.cost = cost;
        this.reward = reward;
    }

    public ItemStack getCost() {
        return cost;
    }

    public ItemStack getReward() {
        return reward;
    }
}
