package fuzs.extensibleenums.impl.core;

import fuzs.extensibleenums.api.v2.core.EnumAppender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

import java.util.Locale;
import java.util.Objects;

public final class BuiltInEnumFactories {

    private BuiltInEnumFactories() {

    }

    public static SpellcasterIllager.IllagerSpell createIllagerSpell(ResourceLocation identifier, double spellColorRed, double spellColorGreen, double spellColorBlue) {
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

    public static AbstractMinecart.Type createMinecartType(ResourceLocation identifier) {
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
