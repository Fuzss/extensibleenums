package fuzs.extensibleenums.impl;

import fuzs.extensibleenums.api.v2.core.EnumAppender;
import fuzs.extensibleenums.api.v2.BuiltInEnumFactories;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Rarity;

import java.util.Locale;
import java.util.Objects;

public final class BuiltInEnumFactoriesImpl implements BuiltInEnumFactories {

    @Override
    public Rarity createRarity(ResourceLocation identifier, ChatFormatting color) {
        Objects.requireNonNull(color, "color is null");
        String internalName = BuiltInEnumFactoriesImpl.toInternalName(identifier);
        EnumAppender.create(Rarity.class, ChatFormatting.class).addEnumConstant(internalName, color).applyTo();
        return BuiltInEnumFactoriesImpl.testEnumValueAddition(Rarity.class, internalName);
    }

    @Override
    public MobCategory createMobCategory(ResourceLocation identifier, String name, int maxInstancesPerChunk, boolean isFriendly, boolean isPersistent, int despawnDistance) {
        String internalName = BuiltInEnumFactoriesImpl.toInternalName(identifier);
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
        return BuiltInEnumFactoriesImpl.testEnumValueAddition(MobCategory.class, internalName);
    }

    @Override
    public Raid.RaiderType createRaiderType(ResourceLocation identifier, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus) {
        Objects.requireNonNull(entityType, "entity type is null");
        Objects.requireNonNull(spawnsPerWaveBeforeBonus, "spawns per wave before bonus is null");
        String internalName = BuiltInEnumFactoriesImpl.toInternalName(identifier);
        EnumAppender.create(Raid.RaiderType.class, EntityType.class, int[].class).addEnumConstant(internalName,
                entityType,
                spawnsPerWaveBeforeBonus
        ).applyTo();
        Raid.RaiderType result = BuiltInEnumFactoriesImpl.testEnumValueAddition(Raid.RaiderType.class, internalName);
        // vanilla stores $VALUES, so we update it
        Raid.RaiderType.VALUES = Raid.RaiderType.values();
        return result;
    }

    @Override
    public SpellcasterIllager.IllagerSpell createIllagerSpell(ResourceLocation identifier, double spellColorRed, double spellColorGreen, double spellColorBlue) {
        testSpellColor(spellColorRed, "red");
        testSpellColor(spellColorGreen, "green");
        testSpellColor(spellColorBlue, "blue");
        int id = SpellcasterIllager.IllagerSpell.values().length;
        double[] spellColor = {spellColorRed, spellColorGreen, spellColorBlue};
        String internalName = toInternalName(identifier);
        EnumAppender.create(SpellcasterIllager.IllagerSpell.class, int.class, double[].class).addEnumConstant(internalName, id, spellColor).applyTo();
        SpellcasterIllager.IllagerSpell result = testEnumValueAddition(SpellcasterIllager.IllagerSpell.class, internalName);
        // vanilla uses this IntFunction to get enum values client-side for the particles, our new value is not included in the original values() field, so we need to reset this field
        SpellcasterIllager.IllagerSpell.BY_ID = ByIdMap.continuous((SpellcasterIllager.IllagerSpell illagerSpell) -> {
            return illagerSpell.id;
        }, SpellcasterIllager.IllagerSpell.values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        return result;
    }

    private static void testSpellColor(double spellColor, String color) {
        if (spellColor < 0.0 || spellColor > 1.0) {
            throw new IllegalArgumentException("Spell color %s out of bounds: %s".formatted(color, spellColor));
        }
    }

    @Override
    public AbstractMinecart.Type createMinecartType(ResourceLocation identifier) {
        String internalName = toInternalName(identifier);
        EnumAppender.create(AbstractMinecart.Type.class).addEnumConstant(internalName).applyTo();
        return testEnumValueAddition(AbstractMinecart.Type.class, internalName);
    }

    public static <T extends Enum<T>> T testEnumValueAddition(Class<T> enumClazz, String internalName) {
        for (T enumConstant : enumClazz.getEnumConstants()) {
            if (Objects.equals(enumConstant.name(), internalName)) return enumConstant;
        }
        throw new IllegalStateException("Failed to add %s to %s".formatted(internalName, enumClazz));
    }

    public static String toInternalName(ResourceLocation resourceLocation) {
        Objects.requireNonNull(resourceLocation, "resource location is null");
        return resourceLocation.toDebugFileName().toUpperCase(Locale.ROOT);
    }
}
