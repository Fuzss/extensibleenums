package fuzs.extensibleenums;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class ExtensibleEnumsFabric implements ModInitializer {
    public static final String MOD_ID = "extensibleenums";
    public static final String MOD_NAME = "Extensible Enums";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        Constructor<Raid.RaiderType> constructor = null;
        Class<?>[] parameterTypes = new Class[]{EntityType.class, int[].class};
        Constructor<?>[] constructors = Raid.RaiderType.class.getDeclaredConstructors();
        main : for (Constructor<?> clazzConstructor : constructors) {
            Class<?>[] constructorParameterTypes = clazzConstructor.getParameterTypes();
            if (parameterTypes.length == constructorParameterTypes.length - 2) {
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (parameterTypes[i] != constructorParameterTypes[i + 2]) {
                        continue main;
                    }
                    constructor = (Constructor<Raid.RaiderType>) clazzConstructor;
                }
            }
        }
        if (constructor != null) {

            Raid.RaiderType raiderType = null;
//            try {
//                constructor.setAccessible(true);
////                raiderType = constructor.newInstance(EntityType.AXOLOTL, new int[]{0, 0, 0, 1, 0, 1, 0, 2});
//            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
            LOGGER.info(raiderType != null ? raiderType.name() : null);

            MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
            Collection<String> namespaces = remapper.getNamespaces();
            String currentRuntimeNamespace = remapper.getCurrentRuntimeNamespace();
            System.out.println("hi");
        }
    }
}
