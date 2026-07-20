package org.shadowskylyn.hadesmod.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class InventoryHelper {


    public static boolean hasItem(
            Player player,
            Item item,
            int amount
    ) {

        int count = 0;

        for(ItemStack stack : player.getInventory().items) {

            if(stack.is(item)) {
                count += stack.getCount();
            }

        }

        return count >= amount;
    }

    public static void removeItem(
            Player player,
            Item item,
            int amount
    ) {

        int remaining = amount;


        for(ItemStack stack : player.getInventory().items) {

            if(stack.is(item)) {

                int removed =
                        Math.min(
                                stack.getCount(),
                                remaining
                        );

                stack.shrink(removed);

                remaining -= removed;

                if(remaining <= 0)
                    break;
            }
        }
    }
}