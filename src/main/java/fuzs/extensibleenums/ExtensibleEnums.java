package fuzs.extensibleenums;

import fuzs.extensibleenums.core.ExtensibleEnchantmentCategory;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtensibleEnums implements ModInitializer {
    public static final String MOD_ID = "extensibleenums";
    public static final String MOD_NAME = "Extensible Enums";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final EnchantmentCategory DAMAGEABLE = ExtensibleEnchantmentCategory.create("DAMAGEABLE", item -> item instanceof ShearsItem || item instanceof FlintAndSteelItem || item instanceof FishingRodItem);

    @Override
    public void onInitialize() {
        EnchantmentCategory damageable = DAMAGEABLE;
        System.out.println(damageable);
        System.out.println((Object) damageable instanceof ExtensibleEnchantmentCategory);
        System.out.println((Object) EnchantmentCategory.VANISHABLE instanceof ExtensibleEnchantmentCategory);
    }
}
