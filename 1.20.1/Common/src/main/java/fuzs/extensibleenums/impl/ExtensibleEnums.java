package fuzs.extensibleenums.impl;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtensibleEnums {
    public static final String MOD_ID = "extensibleenums";
    public static final String MOD_NAME = "Extensible Enums";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
