package fuzs.extensibleenums.forge.impl.client;

import fuzs.extensibleenums.impl.ExtensibleEnums;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = ExtensibleEnums.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ExtensibleEnumsForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
//        ClientModConstructor.construct(ExtensibleEnums.MOD_ID, ExtensibleEnumsClient::new);
    }
}
