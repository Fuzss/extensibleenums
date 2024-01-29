package fuzs.extensibleenums.forge.impl;

import fuzs.extensibleenums.impl.ExtensibleEnums;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(ExtensibleEnums.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExtensibleEnumsForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
//        ModConstructor.construct(ExtensibleEnums.MOD_ID, ExtensibleEnums::new);
    }
}
