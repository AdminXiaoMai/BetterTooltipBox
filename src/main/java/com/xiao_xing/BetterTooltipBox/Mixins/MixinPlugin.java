package com.xiao_xing.BetterTooltipBox.Mixins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MixinPlugin implements IMixinConfigPlugin {

    public static boolean isLoaderGTNHlib = false;

    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        List<String> mixinClass = new ArrayList<>();
        boolean neiLoaded = isModLoaded("NotEnoughItems");

        try {
            // GTNH 库处理
            if (isLoaderGTNHlib) {
                mixinClass.add("NotItemStackTooltipMixin");
                mixinClass.add("drawSelectionBoxMixin");
                return mixinClass;
            }

            // NEI 存在时使用专用 Mixin
            if (neiLoaded) {
                mixinClass.add("NEICompatibleTooltipMixin");
            }
            // 原生 Minecraft 处理
            else {
                try {
                    Class<?> c = Class.forName("codechicken.core.asm.Tags");
                    String version = (String) c.getField("VERSION")
                        .get(null);
                    if (compareVersion(version, "1.3.10") != -1) {
                        mixinClass.add("ItemTooltipMixin");
                    } else {
                        mixinClass.add("oldItemTooltipMixin");
                    }
                } catch (Exception ignored) {}
            }

            // 添加通用 Mixin
            mixinClass.add("TooltipMixin");
            mixinClass.add("drawSelectionBoxMixin");
            return mixinClass;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // 检测模组是否加载
    private boolean isModLoaded(String modid) {
        try {
            Class.forName(modid + ".NEIClientConfig");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

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

    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {}

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {}
}
