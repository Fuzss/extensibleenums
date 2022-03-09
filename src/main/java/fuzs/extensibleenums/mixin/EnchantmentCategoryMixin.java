package fuzs.extensibleenums.mixin;

import fuzs.extensibleenums.core.ExtensibleEnchantmentCategory;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

// be careful, class id/order thingys (the $6 part) are different from Forge
@Mixin(targets = "net.minecraft.world.item.enchantment.EnchantmentCategory$14")
public abstract class EnchantmentCategoryMixin implements ExtensibleEnchantmentCategory {
    @Unique
    private Predicate<Item> canApplyTo;

    @Override
    public void setCanApplyTo(Predicate<Item> canApplyTo) {
        this.canApplyTo = canApplyTo;
    }

    @Inject(method = "canEnchant(Lnet/minecraft/world/item/Item;)Z", at = @At("HEAD"), cancellable = true)
    public void canEnchant$head(Item item, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (this.canApplyTo != null) {
            callbackInfo.setReturnValue(this.canApplyTo.test(item));
        }
    }
}
