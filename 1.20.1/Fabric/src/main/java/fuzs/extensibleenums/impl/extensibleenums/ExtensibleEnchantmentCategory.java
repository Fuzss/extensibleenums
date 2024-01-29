package fuzs.extensibleenums.impl.extensibleenums;

import net.minecraft.world.item.Item;

import java.util.function.Predicate;

/**
 * allows {@link net.minecraft.world.item.enchantment.EnchantmentCategory} to be extended, since the enum is abstract, and we cannot simply create new enum constants
 */
public interface ExtensibleEnchantmentCategory {

    /**
     * allow for setting custom behavior
     * @param canApplyTo which item this type can be applied to
     */
    void extensibleenums$setCanApplyTo(Predicate<Item> canApplyTo);
}
