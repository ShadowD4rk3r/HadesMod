package org.shadowskylyn.hadesmod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.shadowskylyn.hadesmod.HadesMod;

public class ModPackets {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL =
            NetworkRegistry.newSimpleChannel(
                    new ResourceLocation(HadesMod.MODID, "main"),
                    () -> PROTOCOL_VERSION,
                    PROTOCOL_VERSION::equals,
                    PROTOCOL_VERSION::equals
            );


    private static int id = 0;


    public static void register() {

        CHANNEL.registerMessage(
                id++,
                BuyOfferPacket.class,
                BuyOfferPacket::encode,
                BuyOfferPacket::decode,
                BuyOfferPacket::handle
        );

    }
}