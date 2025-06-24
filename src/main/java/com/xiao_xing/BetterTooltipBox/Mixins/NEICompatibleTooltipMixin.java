package com.xiao_xing.BetterTooltipBox.Mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.xiao_xing.BetterTooltipBox.Util.TooltipHelper;

@Mixin(targets = "codechicken/nei/guihook/GuiContainerManager")
public class NEICompatibleTooltipMixin {

    @Redirect(
        method = "drawPagedTooltip",
        at = @At(value = "INVOKE", target = "Lcodechicken/lib/gui/GuiDraw;drawTooltipBox(IIIIIIII)V"),
        remap = false)
    private void redirectDrawTooltipBox(int x, int y, int w, int h, int bgStart, int bgEnd, int borderStart,
        int borderEnd) {
        TooltipHelper.z = 300;
        TooltipHelper.DrawNEITooltip(x, y, w, h);
    }

    @Redirect(
        method = "drawMultilineTip",
        at = @At(value = "INVOKE", target = "Lcodechicken/lib/gui/GuiDraw;drawTooltipBox(IIII)V"),
        remap = false)
    private void redirectSimpleTooltip(int x, int y, int w, int h) {
        TooltipHelper.z = 300;
        TooltipHelper.DrawNEITooltip(x, y, w, h);
    }
}
