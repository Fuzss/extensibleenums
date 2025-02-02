package fuzs.extensibleenums.api.v2;

import fuzs.extensibleenums.impl.BuiltInEnumFactoriesImpl;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Rarity;

/**
 * Utility class for creating enum constants and adding them to the enum class.
 */
public interface BuiltInEnumFactories {
    /**
     * the instance
     */
    BuiltInEnumFactories INSTANCE = new BuiltInEnumFactoriesImpl();

    /**
     * Create a new {@link Rarity} enum constant.
     *
     * @param identifier name of enum constant
     * @param color      chat color for item name
     * @return new enum constant
     */
    Rarity createRarity(ResourceLocation identifier, ChatFormatting color);

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
    MobCategory createMobCategory(ResourceLocation identifier, String name, int maxInstancesPerChunk, boolean isFriendly, boolean isPersistent, int despawnDistance);

    /**
     * Create a new {@link Raid.RaiderType} enum constant.
     *
     * @param identifier               name of enum constant
     * @param entityType               raider entity type
     * @param spawnsPerWaveBeforeBonus int array with amount of spawns per wave (wave is array index, starting at 1,
     *                                 meaning index 0 is ignored)
     * @return new enum constant
     */
    Raid.RaiderType createRaiderType(ResourceLocation identifier, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus);

    /**
     * Create a new {@link net.minecraft.world.entity.monster.SpellcasterIllager.IllagerSpell} enum constant.
     *
     * @param identifier      name of enum constant
     * @param spellColorRed   red portion for spell particle color
     * @param spellColorGreen green portion for spell particle color
     * @param spellColorBlue  blue portion for spell particle color
     * @return new enum constant
     */
    SpellcasterIllager.IllagerSpell createIllagerSpell(ResourceLocation identifier, double spellColorRed, double spellColorGreen, double spellColorBlue);
}
