package fuzs.extensibleenums.impl.client;

import com.google.common.collect.ImmutableList;
import fuzs.extensibleenums.api.v2.client.ClientBuiltInEnumFactories;
import fuzs.extensibleenums.api.v2.core.EnumAppender;
import fuzs.extensibleenums.impl.BuiltInEnumFactoriesImpl;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class ClientBuiltInEnumFactoriesImpl implements ClientBuiltInEnumFactories {

    @Override
    public RecipeBookCategories createRecipeBookCategory(ResourceLocation identifier, ItemStack... icons) {
        String internalName = BuiltInEnumFactoriesImpl.toInternalName(identifier);
        EnumAppender.create(RecipeBookCategories.class, List.class).addEnumConstant(internalName, ImmutableList.copyOf(icons)).applyTo();
        return BuiltInEnumFactoriesImpl.testEnumValueAddition(RecipeBookCategories.class, internalName);
    }
}
