package fuzs.extensibleenums.api.v2.client;

import fuzs.extensibleenums.impl.client.ClientBuiltInEnumFactoriesImpl;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * Utility class for creating enum constants and adding them to the enum class.
 */
public interface ClientBuiltInEnumFactories {
    /**
     * the instance
     */
    ClientBuiltInEnumFactories INSTANCE = new ClientBuiltInEnumFactoriesImpl();

    /**
     * Create a new {@link RecipeBookCategories} enum constant.
     *
     * @param identifier               name of enum constant
     * @param icons                    item icons for the client recipe book, at most two
     * @return new enum constant
     */
    RecipeBookCategories createRecipeBookCategory(ResourceLocation identifier, ItemStack... icons);
}
