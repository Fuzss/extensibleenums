package fuzs.extensibleenums.api.v2.fabric;

import fuzs.extensibleenums.api.v2.core.EnumAppender;
import fuzs.extensibleenums.fabric.impl.core.ExtensibleEnchantmentCategory;
import fuzs.extensibleenums.impl.core.BuiltInEnumFactories;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.ApiStatus;

import java.util.Objects;
import java.util.function.Predicate;

@ApiStatus.Internal
public class CommonAbstractionsImpl {

    public static EnchantmentCategory createEnchantmentCategory(ResourceLocation identifier, Predicate<Item> canApplyTo) {
        Objects.requireNonNull(canApplyTo, "can apply to is null");
        String internalName = BuiltInEnumFactories.toInternalName(identifier);
        // we can use any enum constant, we just need a concrete implementation of the abstract enum class
        // this needs to be the same class though as the one we add this interface to via mixins
        new EnumAppender<>(EnchantmentCategory.class, EnchantmentCategory.VANISHABLE.getClass()).addEnumConstant(
                internalName).applyTo();
        EnchantmentCategory enumConstant = BuiltInEnumFactories.testEnumValueAddition(EnchantmentCategory.class,
                internalName
        );
        // some post-processing, not setting this will let the default behavior of VANISHABLE run
        // need to cast to object for some reason as compilation fails otherwise under random circumstances
        ExtensibleEnchantmentCategory.class.cast(enumConstant).extensibleenums$setCanApplyTo(canApplyTo);
        return enumConstant;
    }

    public static Rarity createRarity(ResourceLocation identifier, ChatFormatting color) {
        Objects.requireNonNull(color, "color is null");
        String internalName = BuiltInEnumFactories.toInternalName(identifier);
        EnumAppender.create(Rarity.class, ChatFormatting.class).addEnumConstant(internalName, color).applyTo();
        return BuiltInEnumFactories.testEnumValueAddition(Rarity.class, internalName);
    }

    public static MobCategory createMobCategory(ResourceLocation identifier, String name, int maxInstancesPerChunk, boolean isFriendly, boolean isPersistent, int despawnDistance) {
        String internalName = BuiltInEnumFactories.toInternalName(identifier);
        EnumAppender.create(MobCategory.class,
                        0,
                        String.class,
                        0,
                        int.class,
                        0,
                        boolean.class,
                        1,
                        boolean.class,
                        1,
                        int.class,
                        2,
                        int.class
                )
                // noDespawnDistance has a default value of 32, but since we do not invoke a constructor the value will be missing and needs to be set manually
                .addEnumConstant(internalName,
                        name,
                        maxInstancesPerChunk,
                        isFriendly,
                        isPersistent,
                        32,
                        despawnDistance
                ).applyTo();
        return BuiltInEnumFactories.testEnumValueAddition(MobCategory.class, internalName);
    }

    public static Raid.RaiderType createRaiderType(ResourceLocation identifier, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus) {
        Objects.requireNonNull(entityType, "entity type is null");
        Objects.requireNonNull(spawnsPerWaveBeforeBonus, "spawns per wave before bonus is null");
        String internalName = BuiltInEnumFactories.toInternalName(identifier);
        EnumAppender.create(Raid.RaiderType.class, EntityType.class, int[].class).addEnumConstant(internalName,
                entityType,
                spawnsPerWaveBeforeBonus
        ).applyTo();
        Raid.RaiderType result = BuiltInEnumFactories.testEnumValueAddition(Raid.RaiderType.class, internalName);
        // vanilla stores $VALUES, so we update it
        Raid.RaiderType.VALUES = Raid.RaiderType.values();
        return result;
    }
}
