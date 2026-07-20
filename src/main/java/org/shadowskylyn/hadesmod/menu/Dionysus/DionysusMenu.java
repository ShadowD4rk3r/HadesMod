package org.shadowskylyn.hadesmod.menu.Dionysus;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.shadowskylyn.hadesmod.entity.DionysusTraderEntity;
import org.shadowskylyn.hadesmod.menu.ModMenuTypes;

public class DionysusMenu extends AbstractContainerMenu {

    private final DionysusTraderEntity entity;

    public DionysusMenu(int id, Inventory inventory, FriendlyByteBuf buffer) {
        this(
                id,
                inventory,
                (DionysusTraderEntity) inventory.player.level().getEntity(buffer.readInt())
        );
    }

    public DionysusMenu(int id, Inventory inventory, DionysusTraderEntity entity) {
        super(ModMenuTypes.DIONYSUS_MENU.get(), id);
        this.entity = entity;
    }

    public DionysusTraderEntity getEntity() {
        return entity;
    }

    @Override
    public boolean stillValid(Player player) {
        return entity != null &&
                entity.isAlive() &&
                player.distanceTo(entity) <= 8;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}