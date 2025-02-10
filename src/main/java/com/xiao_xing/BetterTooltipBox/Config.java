package com.xiao_xing.BetterTooltipBox;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static boolean Enable_BetterTooltipBox = true;
    public static boolean Enable_SelectionBox = false;
    public static boolean Compatible_NEI = true; // 新增NEI兼容开关

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        Enable_BetterTooltipBox = configuration.getBoolean(
            "Enable BetterTooltipBox",
            Configuration.CATEGORY_GENERAL,
            Enable_BetterTooltipBox,
            "Enable BetterTooltipBox");

        Enable_SelectionBox = configuration.getBoolean(
            "Enable SelectionBox",
            Configuration.CATEGORY_GENERAL,
            Enable_SelectionBox,
            "Enable SelectionBox");

        Compatible_NEI = configuration.getBoolean( // 新增配置项
            "Compatible NEI",
            Configuration.CATEGORY_GENERAL,
            Compatible_NEI,
            "Enable compatibility with NotEnoughItems mod");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
