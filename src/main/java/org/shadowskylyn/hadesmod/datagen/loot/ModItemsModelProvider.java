package org.shadowskylyn.hadesmod.datagen.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.item.ModItems;

public class ModItemsModelProvider extends ItemModelProvider {
    public ModItemsModelProvider(PackOutput output, ExistingFileHelper existingFileHelper)
    {
        super(output, HadesMod.MODID, existingFileHelper);
    }


    @Override
    protected void registerModels()
    {
        //simpleItem(ModItems.OBOL);
        handheldItem(ModItems.ETERNAL_SPEAR);
    }

    //Normal Flat Inventory Items, (Ores, and things of that nature)
    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
               mcLoc("item/generated")).texture("layer0",
                modLoc("item/" + item.getId().getPath()));
    }

    //Handheld View of Items, (Weapons and drinks)
    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                mcLoc("item/handheld")).texture("layer0",
                modLoc("item/" + item.getId().getPath()));
    }
    //Used for when a block needs an inventory Icon (Ore blocks and special blocks)
    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                mcLoc("item/generated")).texture("layer0",
                modLoc("item/" + item.getId().getPath()));
    }
}
