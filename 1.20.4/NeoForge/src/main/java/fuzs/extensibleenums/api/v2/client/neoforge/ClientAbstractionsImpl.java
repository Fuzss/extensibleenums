package fuzs.extensibleenums.api.v2.client.neoforge;

import fuzs.extensibleenums.impl.core.BuiltInEnumFactories;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ClientAbstractionsImpl {

    public static RecipeBookCategories createRecipeBookCategory(ResourceLocation identifier, ItemStack... icons) {
        String internalName = BuiltInEnumFactories.toInternalName(identifier);
        return RecipeBookCategories.create(internalName, icons);
    }
}
