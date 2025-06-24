package com.xiao_xing.BetterTooltipBox;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static boolean Enable_BetterTooltipBox = true;
    public static boolean Enable_SelectionBox = false;
    public static boolean NEI_Integration = true;
    public static boolean NEI_CompactMode = false;

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

        NEI_Integration = configuration.getBoolean(
            "NEI Integration",
            "Compatibility",
            NEI_Integration,
            "Enable BetterTooltipBox in NEI interfaces");

        NEI_CompactMode = configuration
            .getBoolean("NEI Compact Mode", "Compatibility", NEI_CompactMode, "Use compact tooltip style in NEI");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
