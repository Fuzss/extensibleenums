package fuzs.extensibleenums.fabric.mixin;

import fuzs.extensibleenums.fabric.impl.core.ExtensibleEnchantmentCategory;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

// be careful, class id/order thingys (the $14 part) are different depending on mappings
// on yarn mappings this is $6 instead which also goes for the intermediary (?) name
// this must match the enum value used in ExtensibleEnchantmentCategory
@Mixin(targets = "net.minecraft.world.item.enchantment.EnchantmentCategory$14")
abstract class EnchantmentCategoryFabricMixin implements ExtensibleEnchantmentCategory {
    @Unique
    private Predicate<Item> extensibleenums$canApplyTo;

    @Override
    public void extensibleenums$setCanApplyTo(Predicate<Item> canApplyTo) {
        this.extensibleenums$canApplyTo = canApplyTo;
    }

    @Inject(method = "canEnchant(Lnet/minecraft/world/item/Item;)Z", at = @At("HEAD"), cancellable = true)
    public void canEnchant(Item item, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (this.extensibleenums$canApplyTo != null) {
            callbackInfo.setReturnValue(this.extensibleenums$canApplyTo.test(item));
        }
    }
}
