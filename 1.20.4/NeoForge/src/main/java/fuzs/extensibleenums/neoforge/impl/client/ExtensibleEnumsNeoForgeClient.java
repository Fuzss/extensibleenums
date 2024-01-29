package fuzs.extensibleenums.neoforge.impl.client;

import fuzs.extensibleenums.impl.ExtensibleEnums;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = ExtensibleEnums.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ExtensibleEnumsNeoForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
//        ClientModConstructor.construct(ExtensibleEnums.MOD_ID, ExtensibleEnumsClient::new);
    }
}
