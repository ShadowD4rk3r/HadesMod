package org.shadowskylyn.hadesmod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.shadowskylyn.hadesmod.entity.DionysusTraderEntity;
import org.shadowskylyn.hadesmod.shop.ShopOffer;
import org.shadowskylyn.hadesmod.util.InventoryHelper;

import java.util.function.Supplier;

public class BuyOfferPacket {


    private final int offerIndex;


    public BuyOfferPacket(int offerIndex) {
        this.offerIndex = offerIndex;
    }


    public BuyOfferPacket(FriendlyByteBuf buffer) {
        this.offerIndex = buffer.readInt();
    }


    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(offerIndex);
    }


    public static BuyOfferPacket decode(FriendlyByteBuf buffer) {
        return new BuyOfferPacket(buffer);
    }


    public static void handle(
            BuyOfferPacket packet,
            Supplier<NetworkEvent.Context> context
    ) {

        context.get().enqueueWork(() -> {

            ServerPlayer player =
                    context.get().getSender();

            if(player == null)
                return;

            DionysusTraderEntity trader = null;

            for(var entity :
                    player.level()
                            .getEntitiesOfClass(
                                    DionysusTraderEntity.class,
                                    player.getBoundingBox().inflate(8)
                            )) {

                trader = entity;
                break;
            }

            if(trader == null)
                return;

            ShopOffer offer = trader.getOffers().get(packet.offerIndex);

            ItemStack cost = offer.getCost();

            // CHECK MONEY

            boolean canBuy =
                    InventoryHelper.hasItem(
                            player,
                            cost.getItem(),
                            cost.getCount()
                    );

            if(!canBuy) {

                player.sendSystemMessage(
                        Component.literal(
                                "You don't have enough Obols."
                        )
                );

                return;
            }

            // REMOVE MONEY

            InventoryHelper.removeItem(
                    player,
                    cost.getItem(),
                    cost.getCount()
            );

            // GIVE ITEM

            player.getInventory()
                    .add(offer.getReward().copy());


            player.sendSystemMessage(
                    Component.literal(
                            "Purchased "
                                    + offer.getReward()
                                    .getHoverName()
                                    .getString()
                    )
            );

        });


        context.get()
                .setPacketHandled(true);
    }
}