package fuzs.extensibleenums.api.v2;

import dev.architectury.injectables.annotations.ExpectPlatform;
import fuzs.extensibleenums.impl.core.BuiltInEnumFactories;
import net.minecraft.ChatFormatting;
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
 * <p>Similar to Forge's <code>net.minecraftforge.common.IExtensibleEnum</code>, can easily be extended if more enum types are required.
 */
public class CommonAbstractions {

    /**
     * Create a new {@link EnchantmentCategory} enum constant.
     *
     * @param identifier name of enum constant
     * @param canApplyTo which item this type can be applied to
     * @return new enum constant
     */
    @ExpectPlatform
    public static EnchantmentCategory createEnchantmentCategory(ResourceLocation identifier, Predicate<Item> canApplyTo) {
        throw new RuntimeException();
    }

    /**
     * Create a new {@link Rarity} enum constant.
     *
     * @param identifier name of enum constant
     * @param color      chat color for item name
     * @return new enum constant
     */
    @ExpectPlatform
    public static Rarity createRarity(ResourceLocation identifier, ChatFormatting color) {
        throw new RuntimeException();
    }

    /**
     * Create a new {@link MobCategory} enum constant.
     *
     * @param identifier           name of enum constant
     * @param name                 name
     * @param maxInstancesPerChunk entities of this category allowed in a chunk
     * @param isFriendly           used for animals, the respawning cycle runs a lot slower
     * @param isPersistent         can this type despawn again, not really used
     * @param despawnDistance      distance from a player when despawning is possible
     * @return new enum constant
     */
    @ExpectPlatform
    public static MobCategory createMobCategory(ResourceLocation identifier, String name, int maxInstancesPerChunk, boolean isFriendly, boolean isPersistent, int despawnDistance) {
        throw new RuntimeException();
    }

    /**
     * Create a new {@link Raid.RaiderType} enum constant.
     *
     * @param identifier               name of enum constant
     * @param entityType               raider entity type
     * @param spawnsPerWaveBeforeBonus int array with amount of spawns per wave (wave is array index, starting at 1, meaning index 0 is ignored)
     * @return new enum constant
     */
    @ExpectPlatform
    public static Raid.RaiderType createRaiderType(ResourceLocation identifier, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus) {
        throw new RuntimeException();
    }

    /**
     * Create a new {@link net.minecraft.world.entity.monster.SpellcasterIllager.IllagerSpell} enum constant.
     *
     * @param identifier      name of enum constant
     * @param spellColorRed   red portion for spell particle color
     * @param spellColorGreen green portion for spell particle color
     * @param spellColorBlue  blue portion for spell particle color
     * @return new enum constant
     */
    public static SpellcasterIllager.IllagerSpell createIllagerSpell(ResourceLocation identifier, double spellColorRed, double spellColorGreen, double spellColorBlue) {
        return BuiltInEnumFactories.createIllagerSpell(identifier, spellColorRed, spellColorGreen, spellColorBlue);
    }
}
