package fuzs.extensibleenums;

import fuzs.extensibleenums.core.EnumAppender;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ExtensibleEnumsFabric implements ModInitializer {
    public static final String MOD_ID = "extensibleenums";
    public static final String MOD_NAME = "Extensible Enums";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        boolean anEnum = Raid.RaiderType.class.isEnum();
        Raid.RaiderType[] enumConstants = Raid.RaiderType.class.getEnumConstants();
        EnumAppender<Raid.RaiderType> raiderTypeEnumAppender = EnumAppender.create(Raid.RaiderType.class, 0, EntityType.class, 0, int[].class);
        Raid.RaiderType basher = raiderTypeEnumAppender.add("BASHER", EntityType.ALLAY, new int[]{0, 0, 0, 0, 3, 0, 0, 1});
        Raid.RaiderType[] enumConstants2 = Raid.RaiderType.class.getEnumConstants();
        LOGGER.info("{}", basher);
        Raid.RaiderType[] values = Raid.RaiderType.values();
        LOGGER.info("{}", Arrays.toString(values));

        EnumBuster<Raid.RaiderType> buster = new EnumBuster<>(Raid.RaiderType.class);
        Raid.RaiderType buster1;
        try {
            buster1 = buster.make("BUSTER");
            buster.addByValue(buster1);
            LOGGER.info("{}", buster1);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        Raid.RaiderType[] enumConstants4 = Raid.RaiderType.class.getEnumConstants();
        Raid.RaiderType[] values4 = Raid.RaiderType.values();
        LOGGER.info("{}", Arrays.toString(values4));
    }
}
