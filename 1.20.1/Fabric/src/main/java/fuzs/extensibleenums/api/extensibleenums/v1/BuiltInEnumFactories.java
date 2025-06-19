package fuzs.extensibleenums.api.extensibleenums.v1;

import fuzs.extensibleenums.api.v2.CommonAbstractions;
import fuzs.extensibleenums.impl.ExtensibleEnums;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Predicate;

/**
 * Utility class for creating enum constants and adding them to the enum class.
 * <p>Similar to Forge's <code>net.minecraftforge.common.IExtensibleEnum</code>, can easily be extended if more enum
 * types are required.
 */
public final class BuiltInEnumFactories {

    private BuiltInEnumFactories() {

    }

    /**
     * Create a new {@link EnchantmentCategory} enum constant.
     *
     * @param internalName name of enum constant
     * @param canApplyTo   which item this type can be applied to
     * @return new enum constant
     */
    @SuppressWarnings("ConstantConditions")
    public static EnchantmentCategory createEnchantmentCategory(String internalName, Predicate<Item> canApplyTo) {
        return CommonAbstractions.createEnchantmentCategory(id(internalName), canApplyTo);
    }

    /**
     * Create a new {@link Rarity} enum constant.
     *
     * @param internalName name of enum constant
     * @param color        chat color for item name
     * @return new enum constant
     */
    public static Rarity createRarity(String internalName, ChatFormatting color) {
        return CommonAbstractions.createRarity(id(internalName), color);
    }

    /**
     * Create a new {@link MobCategory} enum constant.
     *
     * @param internalName         name of enum constant
     * @param name                 name
     * @param maxInstancesPerChunk entites of this category allowed in a chunk
     * @param isFriendly           used for animals, the respawning cycle runs a lot slower
     * @param isPersistent         can this type despawn again, not really used
     * @param despawnDistance      distance from a player when despawning is possible
     * @return new enum constant
     */
    public static MobCategory createMobCategory(String internalName, String name, int maxInstancesPerChunk, boolean isFriendly, boolean isPersistent, int despawnDistance) {
        return CommonAbstractions.createMobCategory(id(internalName),
                name,
                maxInstancesPerChunk,
                isFriendly,
                isPersistent,
                despawnDistance);
    }

    /**
     * Create a new {@link Raid.RaiderType} enum constant.
     *
     * @param internalName             name of enum constant
     * @param entityType               raider entity type
     * @param spawnsPerWaveBeforeBonus int array with amount of spawns per wave (wave is array index, starting at 1,
     *                                 meaning index 0 is ignored)
     * @return new enum constant
     */
    public static Raid.RaiderType createRaiderType(String internalName, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus) {
        return CommonAbstractions.createRaiderType(id(internalName), entityType, spawnsPerWaveBeforeBonus);
    }

    /**
     * Create a new {@link SpellcasterIllager.IllagerSpell} enum constant.
     *
     * @param internalName    name of enum constant
     * @param spellColorRed   red portion for spell particle color
     * @param spellColorGreen green portion for spell particle color
     * @param spellColorBlue  blue portion for spell particle color
     * @return new enum constant
     */
    public static SpellcasterIllager.IllagerSpell createIllagerSpell(String internalName, double spellColorRed, double spellColorGreen, double spellColorBlue) {
        return CommonAbstractions.createIllagerSpell(id(internalName), spellColorRed, spellColorGreen, spellColorBlue);
    }

    private static ResourceLocation id(String path) {
        return ExtensibleEnums.id(Util.sanitizeName(path, ResourceLocation::validPathChar));
    }
}
