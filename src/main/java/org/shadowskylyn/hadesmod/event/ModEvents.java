package org.shadowskylyn.hadesmod.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.entity.DionysusTraderEntity;
import org.shadowskylyn.hadesmod.registry.ModEntities;

import javax.swing.text.html.parser.Entity;

@Mod.EventBusSubscriber(modid = HadesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents
{
    @SubscribeEvent
    public static void registerAttributues(EntityAttributeCreationEvent event)
    {
        event.put(
                ModEntities.DIONYSUS_TRADER.get(),
                DionysusTraderEntity.createAttributes().build()
        );
    }
}
