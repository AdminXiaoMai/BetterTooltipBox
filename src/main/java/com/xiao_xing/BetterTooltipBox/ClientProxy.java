package com.xiao_xing.BetterTooltipBox;

import com.xiao_xing.BetterTooltipBox.Mixins.MixinPlugin;

import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        // GTNH 处理
        if (MixinPlugin.isLoaderGTNHlib) {
            try {
                Class.forName("com.xiao_xing.BetterTooltipBox.client.event.renderTooltipEvent")
                    .getConstructor()
                    .newInstance();
            } catch (Exception ignored) {}
        }
    }
}
