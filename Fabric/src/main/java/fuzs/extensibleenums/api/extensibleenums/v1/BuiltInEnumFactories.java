package fuzs.extensibleenums.api.extensibleenums.v1;

import com.google.common.collect.ImmutableList;
import fuzs.extensibleenums.impl.extensibleenums.ExtensibleEnchantmentCategory;
import fuzs.extensibleenums.mixin.accessor.IllagerSpellFabricAccessor;
import fuzs.extensibleenums.mixin.accessor.RaiderTypeFabricAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Utility class for creating enum constants and adding them to the enum class.
 * <p>Similar to Forge's <code>net.minecraftforge.common.IExtensibleEnum</code>, can easily be extended if more enum types are required.
 */
public final class BuiltInEnumFactories {

    private BuiltInEnumFactories() {

    }

    /**
     * Create a new {@link EnchantmentCategory} enum constant.
     *
     * @param internalName  name of enum constant
     * @param canApplyTo    which item this type can be applied to
     * @return              new enum constant
     */
    @SuppressWarnings("ConstantConditions")
    public static EnchantmentCategory createEnchantmentCategory(String internalName, Predicate<Item> canApplyTo) {
        Objects.requireNonNull(internalName, "internal name is null");
        Objects.requireNonNull(canApplyTo, "can apply to is null");
        // we can use any enum constant, we just need a concrete implementation of the abstract enum class
        // this needs to be the same class though as the one we add this interface to via mixins
        new EnumAppender<>(EnchantmentCategory.class, EnchantmentCategory.VANISHABLE.getClass(), ImmutableList.of()).addEnumConstant(internalName).applyTo();
        EnchantmentCategory enumConstant = validate(EnchantmentCategory.class, internalName);
        // some post-processing, not setting this will let the default behavior of VANISHABLE run
        // need to cast to object for some reason as compilation fails otherwise under random circumstances
        ((ExtensibleEnchantmentCategory) (Object) enumConstant).extensibleenums$setCanApplyTo(canApplyTo);
        return enumConstant;
    }

    /**
     * Create a new {@link Rarity} enum constant.
     *
     * @param internalName  name of enum constant
     * @param color         chat color for item name
     * @return              new enum constant
     */
    public static Rarity createRarity(String internalName, ChatFormatting color) {
        Objects.requireNonNull(internalName, "internal name is null");
        Objects.requireNonNull(color, "color is null");
        EnumAppender.create(Rarity.class, ChatFormatting.class).addEnumConstant(internalName, color).applyTo();
        return validate(Rarity.class, internalName);
    }

    /**
     * Create a new {@link MobCategory} enum constant.
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
        Objects.requireNonNull(internalName, "internal name is null");
        EnumAppender.create(MobCategory.class, 0, String.class, 0, int.class, 0, boolean.class, 1, boolean.class, 1, int.class, 2, int.class)
                // noDespawnDistance has a default value of 32, but since we do not invoke a constructor the value will be missing and needs to be set manually
                .addEnumConstant(internalName, name, maxInstancesPerChunk, isFriendly, isPersistent, 32, despawnDistance)
                .applyTo();
        return validate(MobCategory.class, internalName);
    }

    /**
     * Create a new {@link Raid.RaiderType} enum constant.
     *
     * @param internalName                  name of enum constant
     * @param entityType                    raider entity type
     * @param spawnsPerWaveBeforeBonus      int array with amount of spawns per wave (wave is array index, starting at 1, meaning index 0 is ignored)
     * @return                              new enum constant
     */
    public static Raid.RaiderType createRaiderType(String internalName, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus) {
        Objects.requireNonNull(internalName, "internal name is null");
        Objects.requireNonNull(entityType, "entity type is null");
        Objects.requireNonNull(spawnsPerWaveBeforeBonus, "spawns per wave before bonus is null");
        EnumAppender.create(Raid.RaiderType.class, EntityType.class, int[].class).addEnumConstant(internalName, entityType, spawnsPerWaveBeforeBonus).applyTo();
        Raid.RaiderType result = validate(Raid.RaiderType.class, internalName);
        // vanilla stores $VALUES, so we update it
        RaiderTypeFabricAccessor.extensibleenums$setValues(Raid.RaiderType.values());
        return result;
    }

    /**
     * Create a new {@link net.minecraft.world.entity.monster.SpellcasterIllager.IllagerSpell} enum constant.
     *
     * @param internalName    name of enum constant
     * @param spellColorRed   red portion for spell particle color
     * @param spellColorGreen green portion for spell particle color
     * @param spellColorBlue  blue portion for spell particle color
     * @return new enum constant
     */
    public static SpellcasterIllager.IllagerSpell createIllagerSpell(String internalName, double spellColorRed, double spellColorGreen, double spellColorBlue) {
        Objects.requireNonNull(internalName, "internal name is null");
        if (Mth.clamp(spellColorRed, 0.0, 1.0) != spellColorRed) throw new IllegalArgumentException("Spell color red out of bounds: " + spellColorRed);
        if (Mth.clamp(spellColorGreen, 0.0, 1.0) != spellColorGreen) throw new IllegalArgumentException("Spell color green out of bounds: " + spellColorGreen);
        if (Mth.clamp(spellColorBlue, 0.0, 1.0) != spellColorBlue) throw new IllegalArgumentException("Spell color blue out of bounds: " + spellColorBlue);
        int id = SpellcasterIllager.IllagerSpell.values().length;
        double[] spellColor = {spellColorRed, spellColorGreen, spellColorBlue};
        EnumAppender.create(SpellcasterIllager.IllagerSpell.class, int.class, double[].class).addEnumConstant(internalName, id, spellColor).applyTo();
        SpellcasterIllager.IllagerSpell result = validate(SpellcasterIllager.IllagerSpell.class, internalName);
        // vanilla uses this IntFunction to get enum values client-side for the particles, our new value is not included in the original values() field, so we need to reset this field
        IllagerSpellFabricAccessor.extensibleenums$setById(ByIdMap.continuous(illagerSpell -> {
            return IllagerSpellFabricAccessor.class.cast(illagerSpell).extensibleenums$getId();
        }, SpellcasterIllager.IllagerSpell.values(), ByIdMap.OutOfBoundsStrategy.ZERO));
        return result;
    }

    private static <T extends Enum<T>> T validate(Class<T> enumClazz, String internalName) {
        for (T enumConstant : enumClazz.getEnumConstants()) {
            if (Objects.equals(enumConstant.name(), internalName)) return enumConstant;
        }
        throw new IllegalStateException("Failed to add %s to %s".formatted(internalName, enumClazz));
    }
}
