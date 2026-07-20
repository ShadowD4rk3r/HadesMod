package org.shadowskylyn.hadesmod.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import org.shadowskylyn.hadesmod.HadesMod;
import org.shadowskylyn.hadesmod.entity.DionysusTraderEntity;
import org.shadowskylyn.hadesmod.entity.SpearProjectileEntity;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HadesMod.MODID);

    public static final RegistryObject<EntityType<SpearProjectileEntity>> SPEAR_PROJECTILE =
            ENTITY_TYPES.register("spear_projectile",
                    () -> EntityType.Builder.<SpearProjectileEntity>of(
                                    SpearProjectileEntity::new,
                                    MobCategory.MISC
                            )
                            .sized(0.5f, 0.5f)
                            .clientTrackingRange(4)
                            .updateInterval(1)
                            .build(new ResourceLocation(HadesMod.MODID, "spear_projectile").toString())
            );

    public static final RegistryObject<EntityType<DionysusTraderEntity>> DIONYSUS_TRADER =
            ENTITY_TYPES.register("dionysus_trader",
                    () -> EntityType.Builder
                            .of(DionysusTraderEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.95F)
                            .build(new ResourceLocation(HadesMod.MODID, "dionysus_trader").toString()));




}