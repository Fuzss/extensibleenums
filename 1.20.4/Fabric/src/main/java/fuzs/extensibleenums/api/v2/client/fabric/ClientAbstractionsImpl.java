package fuzs.extensibleenums.api.v2.client.fabric;

import com.google.common.collect.ImmutableList;
import fuzs.extensibleenums.api.v2.core.EnumAppender;
import fuzs.extensibleenums.impl.core.BuiltInEnumFactories;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public class ClientAbstractionsImpl {

    public static RecipeBookCategories createRecipeBookCategory(ResourceLocation identifier, ItemStack... icons) {
        String internalName = BuiltInEnumFactories.toInternalName(identifier);
        EnumAppender.create(RecipeBookCategories.class, List.class).addEnumConstant(internalName, ImmutableList.copyOf(icons)).applyTo();
        return BuiltInEnumFactories.testEnumValueAddition(RecipeBookCategories.class, internalName);
    }
}
