package fuzs.extensibleenums.core;

import com.google.common.collect.ImmutableList;
import fuzs.extensibleenums.mixin.accessor.RaiderTypeAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Predicate;

/**
 * we return the created value not from the builder, but instead from the enum itself, so it serves as a final check everything went well
 */
public class EnumFactories {

    /**
     * create a new {@link EnchantmentCategory} enum constant
     *
     * @param internalName  name of enum constant
     * @param canApplyTo    which item this type can be applied to
     * @return              new enum constant
     */
    @SuppressWarnings("ConstantConditions")
    public static EnchantmentCategory createEnchantmentCategory(String internalName, Predicate<Item> canApplyTo) {
        // we can use any enum constant, we just need a concrete implementation of the abstract enum class
        // this needs to be the same class though as the one we add this interface to via mixins
        new EnumAppender<>(EnchantmentCategory.class, EnchantmentCategory.VANISHABLE.getClass(), ImmutableList.of()).addEnumConstant(internalName).apply();
        EnchantmentCategory value = EnchantmentCategory.valueOf(internalName);
        // some post-processing, not setting this will let the default behavior of VANISHABLE run
        // need to cast to object for some reason as compilation fails otherwise under random circumstances
        ((ExtensibleEnchantmentCategory) (Object) value).setCanApplyTo(canApplyTo);
        return value;
    }

    /**
     * create a new {@link Rarity} enum constant
     *
     * @param internalName  name of enum constant
     * @param color         chat color for item name
     * @return              new enum constant
     */
    public static Rarity createRarity(String internalName, ChatFormatting color) {
        EnumAppender.create(Rarity.class, ChatFormatting.class).addEnumConstant(internalName, color).apply();
        return Rarity.valueOf(internalName);
    }

    /**
     * create a new {@link MobCategory} enum constant
     *
     * @param internalName              name of enum constant
     * @param name                      name
     * @param maxInstancesPerChunk      entites of this category allowed in a chunk
     * @param isFriendly                used for animals, the respawning cycle runs a lot slower
     * @param isPersistent              can this type despawn again, not really used
     * @param despawnDistance           distance from a player when despawning is possible
     * @return                          new enum constant
     */
    public static MobCategory createMobCategory(String internalName, String name, int maxInstancesPerChunk, boolean isFriendly, boolean isPersistent, int despawnDistance) {
        EnumAppender.create(MobCategory.class, 0, String.class, 0, int.class, 0, boolean.class, 1, boolean.class, 1, int.class, 2, int.class)
                // noDespawnDistance has a default value of 32, but since we do not invoke a constructor the value will be missing and needs to be set manually
                .addEnumConstant(internalName, name, maxInstancesPerChunk, isFriendly, isPersistent, 32, despawnDistance)
                .apply();
        return MobCategory.valueOf(internalName);
    }

    /**
     * create a new {@link Raid.RaiderType} enum constant
     *
     * @param internalName                  name of enum constant
     * @param entityType                    raider entity type
     * @param spawnsPerWaveBeforeBonus      int array with amount of spawns per wave (wave is array index, starting at 1, meaning index 0 is ignored)
     * @return                              new enum constant
     */
    public static Raid.RaiderType createRaiderType(String internalName, EntityType<?> entityType, int[] spawnsPerWaveBeforeBonus) {
        EnumAppender.create(Raid.RaiderType.class, EntityType.class, int[].class).addEnumConstant(internalName, entityType, spawnsPerWaveBeforeBonus).apply();
        // vanilla stores $VALUES, so we update it
        RaiderTypeAccessor.setValues(Raid.RaiderType.values());
        return Raid.RaiderType.valueOf(internalName);
    }
}
