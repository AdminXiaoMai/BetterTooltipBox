package com.xiao_xing.BetterTooltipBox.Mixins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import com.xiao_xing.BetterTooltipBox.Config;

public class MixinPlugin implements IMixinConfigPlugin {

    public static boolean isLoaderGTNHlib = false;
    public static boolean isNEILoaded = false; // NEI检测标志

    @Override
    public void onLoad(String mixinPackage) {
        // 检测NEI是否存在
        try {
            Class.forName("codechicken.nei.NEIClientConfig");
            isNEILoaded = true;
        } catch (ClassNotFoundException ignored) {
            isNEILoaded = false;
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        // 当NEI存在且开启兼容时，跳过冲突的Mixin
        if (isNEILoaded && Config.Compatible_NEI) {
            return !mixinClassName.contains("ItemTooltipMixin") && !mixinClassName.contains("oldItemTooltipMixin")
                && !mixinClassName.contains("NotItemStackTooltipMixin")
                && !mixinClassName.contains("TooltipMixin");
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        List<String> MixinClass = new ArrayList<>();
        try {
            Class.forName("com.gtnewhorizon.gtnhlib.client.event.RenderTooltipEvent");
            isLoaderGTNHlib = true;
            MixinClass.add("NotItemStackTooltipMixin");
            MixinClass.add("drawSelectionBoxMixin");
            return MixinClass;
        } catch (ClassNotFoundException ignored) {
            try {
                Class<?> c = Class.forName("codechicken.core.asm.Tags");
                String VERSION = c.getField("VERSION")
                    .get(null)
                    .toString();

                // 当NEI存在时跳过冲突Mixin
                if (!isNEILoaded || !Config.Compatible_NEI) {
                    if (compareVersion(VERSION, "1.3.10") != -1) {
                        MixinClass.add("ItemTooltipMixin");
                    } else {
                        MixinClass.add("oldItemTooltipMixin");
                    }
                    MixinClass.add("TooltipMixin");
                }
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException ignored1) {}
            MixinClass.add("drawSelectionBoxMixin");
            return MixinClass;
        }
    }

    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {}

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {}

    public static int compareVersion(String version1, String version2) {
        String[] nums1 = version1.split("\\.");
        String[] nums2 = version2.split("\\.");
        int length = Math.max(nums1.length, nums2.length);

        for (int i = 0; i < length; i++) {
            int v1 = i < nums1.length ? Integer.parseInt(nums1[i]) : 0;
            int v2 = i < nums2.length ? Integer.parseInt(nums2[i]) : 0;
            if (v1 < v2) return -1;
            if (v1 > v2) return 1;
        }
        return 0;
    }
}
