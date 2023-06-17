package fuzs.extensibleenums.mixin.accessor;

import net.minecraft.world.entity.monster.SpellcasterIllager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.IntFunction;

@Mixin(SpellcasterIllager.IllagerSpell.class)
public interface IllagerSpellFabricAccessor {

    @Accessor("BY_ID")
    @Mutable
    static void extensibleenums$setById(IntFunction<SpellcasterIllager.IllagerSpell> byId) {
        throw new RuntimeException();
    }

    @Accessor("id")
    int extensibleenums$getId();
}
