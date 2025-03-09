package fuzs.extensibleenums.api.v2.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.ItemStack;

public class ClientAbstractions {

    /**
     * Create a new {@link RecipeBookCategories} enum constant.
     *
     * @param identifier               name of enum constant
     * @param icons                    item icons for the client recipe book, at most two
     * @return new enum constant
     */
    @ExpectPlatform
    public static RecipeBookCategories createRecipeBookCategory(ResourceLocation identifier, ItemStack... icons) {
        throw new RuntimeException();
    }
}
