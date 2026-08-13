package org.shadowskylyn.hadesmod.event;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.world.ModDimensions;

@Mod.EventBusSubscriber(modid = HadesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TeleportHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

        // Only run once per tick
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (!(event.player instanceof ServerPlayer player)) {
            return;
        }

        // Only execute on the server
        if (!(player.level() instanceof ServerLevel currentLevel)) {
            return;
        }

        // Only activate in The End
        if (currentLevel.dimension() != Level.END) {
            return;
        }

        // Wait until the player has actually fallen into the void
        if (player.getY() > -10) {
            return;
        }

        MinecraftServer server = currentLevel.getServer();

        if (server == null) {
            return;
        }

        ServerLevel loomLevel = server.getLevel(ModDimensions.LOOM_OF_FATES);

        if (loomLevel == null) {
            System.out.println("Could not find Loom of Fates dimension!");
            return;
        }

        BlockPos destination = new BlockPos(0, 0, 0);

        player.teleportTo(
                loomLevel,
                0,
                0,
                0,
                player.getYRot(),
                player.getXRot()
        );

        StructureTemplate template  = server.getStructureManager()
                .getOrCreate(new ResourceLocation(HadesMod.MODID, "loom_of_fate"));

        template.placeInWorld(
                loomLevel,
                destination,
                destination,
                new StructurePlaceSettings(),
                loomLevel.getRandom(),
                2
        );
    }
}
