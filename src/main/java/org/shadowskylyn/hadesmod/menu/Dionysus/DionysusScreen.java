package org.shadowskylyn.hadesmod.menu.Dionysus;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.network.BuyOfferPacket;
import org.shadowskylyn.hadesmod.network.ModPackets;
import org.shadowskylyn.hadesmod.shop.ShopOffer;

public class DionysusScreen extends AbstractContainerScreen<DionysusMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(HadesMod.MODID,
                    "textures/gui/dionysus_gui.png");

    public DionysusScreen(DionysusMenu menu,
                          Inventory inventory,
                          Component title) {

        super(menu, inventory, title);

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics graphics,
                            float partialTick,
                            int mouseX,
                            int mouseY) {

        RenderSystem.setShaderTexture(0, TEXTURE);

        graphics.blit(
                TEXTURE,
                leftPos,
                topPos,
                0,
                0,
                imageWidth,
                imageHeight
        );
    }

    @Override
    public void render(GuiGraphics graphics,
                       int mouseX,
                       int mouseY,
                       float partialTick) {

        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }
    @Override
    protected void init() {
        super.init();

        int y = 40;
        int index  = 0;

        for (ShopOffer offer : menu.getEntity().getOffers()) {

            int offerIndex = index;
            //Wine offer
            addRenderableWidget(
                    Button.builder(
                                    Component.literal(
                                            "Buy "
                                                    + offer.getReward()
                                                    .getHoverName()
                                                    .getString()
                                                    + " ("
                                                    + offer.getCost().getCount()
                                                    + " Obols)"
                                    ),

                                    button -> {

                                        ModPackets.CHANNEL.sendToServer(
                                                new BuyOfferPacket(offerIndex)
                                        );

                                    }

                            )
                            .bounds(
                                    this.leftPos + 20,
                                    this.topPos + y,
                                    140,
                                    20
                            )
                            .build()
            );

            y += 30;
            index++;
        }
    }
}

