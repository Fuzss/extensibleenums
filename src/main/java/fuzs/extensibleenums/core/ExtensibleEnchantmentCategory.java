package fuzs.extensibleenums.core;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Predicate;

/**
 * allows {@link net.minecraft.world.item.enchantment.EnchantmentCategory} to be extended, since the enum is abstract and we cannot simply create new enum constants
 */
public interface ExtensibleEnchantmentCategory {
    /**
     * allow for setting custom behavior
     * @param canApplyTo which item this type can be applied to
     */
    void setCanApplyTo(Predicate<Item> canApplyTo);

    /**
     * create a new {@link net.minecraft.world.item.enchantment.EnchantmentCategory} enum constant
     * @param internalName name of enum constant
     * @param canApplyTo which item this type can be applied to
     * @return new enum constant
     */
    static EnchantmentCategory create(String internalName, Predicate<Item> canApplyTo) {
        EnchantmentCategory enchantmentTarget;
        try {
            // we can use any enum constant, we just need a concrete implementation of the abstract enum class
            // this needs to be the same class though as the one we add this interface to via mixins
            enchantmentTarget = UnsafeExtensibleEnum.invokeEnumConstructor(EnchantmentCategory.class, EnchantmentCategory.VANISHABLE.getClass(), internalName);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        // some post-processing, not setting this will let the default behavior of VANISHABLE run
        // need to cast to object for some reason as compilation fails otherwise under random circumstances
        ((ExtensibleEnchantmentCategory) (Object) enchantmentTarget).setCanApplyTo(canApplyTo);
        return enchantmentTarget;
    }
}
