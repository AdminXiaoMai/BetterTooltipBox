package com.xiao_xing.BetterTooltipBox;

import com.xiao_xing.BetterTooltipBox.Mixins.MixinPlugin;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        if (MixinPlugin.isLoaderGTNHlib && !Config.Compatible_NEI) {
            try {
                Class.forName("com.xiao_xing.BetterTooltipBox.client.event.renderTooltipEvent")
                    .getConstructor()
                    .newInstance();
            } catch (Exception ignored) {}
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
