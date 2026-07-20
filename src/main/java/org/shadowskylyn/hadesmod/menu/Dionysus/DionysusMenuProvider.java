package org.shadowskylyn.hadesmod.menu.Dionysus;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.shadowskylyn.hadesmod.entity.DionysusTraderEntity;

public class DionysusMenuProvider implements MenuProvider
{

    private final DionysusTraderEntity entity;

    public DionysusMenuProvider(DionysusTraderEntity entity) {
        this.entity = entity;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Dionysus");
    }

    @Override
    public AbstractContainerMenu createMenu(int id,
                                            Inventory inventory,
                                            Player player) {

        return new DionysusMenu(id, inventory, entity);
    }

}
