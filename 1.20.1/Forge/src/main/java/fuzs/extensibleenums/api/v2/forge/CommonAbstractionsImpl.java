package fuzs.extensibleenums.api.v2.forge;

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
        return EnchantmentCategory.create(internalName, canApplyTo);
    }

    public static Rarity createRarity(ResourceLocation identifier, ChatFormatting color) {
        Objects.requireNonNull(color, "color is null");
        String internalName = BuiltInEnumFactories.toInternalName(identifier);
        return Rarity.create(internalName, color);
    }

    public static MobCategory createMobCategory(ResourceLocation identifier, String name, int maxInstancesPerChunk, boolean isFriendly, boolean isPersistent, int despawnDistance) {
        String internalName = BuiltInEnumFactories.toInternalName(identifier);
        return MobCategory.create(internalName, name, maxInstancesPerChunk, isFriendly, isPersistent, despawnDistance);
    }

    public static Raid.RaiderType createRaiderType(ResourceLocation identifier, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus) {
        Objects.requireNonNull(entityType, "entity type is null");
        Objects.requireNonNull(spawnsPerWaveBeforeBonus, "spawns per wave before bonus is null");
        String internalName = BuiltInEnumFactories.toInternalName(identifier);
        return Raid.RaiderType.create(internalName, entityType, spawnsPerWaveBeforeBonus);
    }
}
