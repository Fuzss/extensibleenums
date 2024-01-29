package fuzs.extensibleenums.api.v2.neoforge;

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

import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;

@ApiStatus.Internal
public class CommonAbstractionsImpl {

    public static EnchantmentCategory createEnchantmentCategory(ResourceLocation identifier, Predicate<Item> canApplyTo) {
        Objects.requireNonNull(identifier, "identifier is null");
        Objects.requireNonNull(canApplyTo, "can apply to is null");
        String internalName = identifier.toDebugFileName().toUpperCase(Locale.ROOT);
        return EnchantmentCategory.create(internalName, canApplyTo);
    }

    public static Rarity createRarity(ResourceLocation identifier, ChatFormatting color) {
        Objects.requireNonNull(identifier, "identifier is null");
        Objects.requireNonNull(color, "color is null");
        String internalName = identifier.toDebugFileName().toUpperCase(Locale.ROOT);
        return Rarity.create(internalName, color);
    }

    public static MobCategory createMobCategory(ResourceLocation identifier, String name, int maxInstancesPerChunk, boolean isFriendly, boolean isPersistent, int despawnDistance) {
        Objects.requireNonNull(identifier, "identifier is null");
        String internalName = identifier.toDebugFileName().toUpperCase(Locale.ROOT);
        return MobCategory.create(internalName, name, maxInstancesPerChunk, isFriendly, isPersistent, despawnDistance);
    }

    public static Raid.RaiderType createRaiderType(ResourceLocation identifier, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus) {
        Objects.requireNonNull(identifier, "identifier is null");
        Objects.requireNonNull(entityType, "entity type is null");
        Objects.requireNonNull(spawnsPerWaveBeforeBonus, "spawns per wave before bonus is null");
        String internalName = identifier.toDebugFileName().toUpperCase(Locale.ROOT);
        return Raid.RaiderType.create(internalName, entityType, spawnsPerWaveBeforeBonus);
    }
}
