package fuzs.extensibleenums.api.v2.client.forge;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.Locale;
import java.util.Objects;

@ApiStatus.Internal
public class ClientAbstractionsImpl {

    public static RecipeBookCategories createRecipeBookCategory(ResourceLocation identifier, ItemStack... icons) {
        Objects.requireNonNull(identifier, "identifier is null");
        String internalName = identifier.toDebugFileName().toUpperCase(Locale.ROOT);
        return RecipeBookCategories.create(internalName, icons);
    }
}
