package fuzs.extensibleenums.neoforge.impl;

import fuzs.extensibleenums.impl.ExtensibleEnums;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod(ExtensibleEnums.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExtensibleEnumsNeoForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
//        ModConstructor.construct(ExtensibleEnums.MOD_ID, ExtensibleEnums::new);
    }
}
