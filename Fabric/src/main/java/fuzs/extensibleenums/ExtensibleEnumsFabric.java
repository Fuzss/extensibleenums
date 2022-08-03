package fuzs.extensibleenums;

import fuzs.extensibleenums.core.EnumFactories;
import net.fabricmc.api.ModInitializer;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtensibleEnumsFabric implements ModInitializer {
    public static final String MOD_ID = "extensibleenums";
    public static final String MOD_NAME = "Extensible Enums";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        EnchantmentCategory wateringCan = EnumFactories.createEnchantmentCategory("WATERING_CAN", item -> item instanceof SpyglassItem);
        EnchantmentCategory[] values = EnchantmentCategory.values();
        MobCategory mobCategory = EnumFactories.createMobCategory("ALLAY", "allay", 32, true, true, 128);
        MobCategory[] values1 = MobCategory.values();
        Rarity godly = EnumFactories.createRarity("GODLY", ChatFormatting.GREEN);
        Rarity[] values2 = Rarity.values();
        Raid.RaiderType creeper = EnumFactories.createRaiderType("CREEPER", EntityType.CREEPER, new int[]{0, 0, 2, 0, 1, 4, 2, 5});
        Raid.RaiderType[] values3 = Raid.RaiderType.values();
        System.out.println("hi");
    }
}
