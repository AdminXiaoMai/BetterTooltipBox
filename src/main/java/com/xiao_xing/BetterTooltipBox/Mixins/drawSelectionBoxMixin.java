package com.xiao_xing.BetterTooltipBox.Mixins;

import static com.xiao_xing.BetterTooltipBox.Config.Enable_SelectionBox;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.AxisAlignedBB;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderGlobal.class)
public class drawSelectionBoxMixin {

    @Redirect(
        method = "drawSelectionBox",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderGlobal;drawOutlinedBoundingBox(Lnet/minecraft/util/AxisAlignedBB;I)V"))
    public void drawOutlinedBoundingBox(AxisAlignedBB box, int color) {
        // 仅在非 NEI 环境使用动态效果
        if (Enable_SelectionBox && !isNEIEnvironment()) {
            float time = (System.currentTimeMillis() % 5000L) / 5000.0f * (float) (2 * Math.PI);
            int newColor = (int) ((Math.sin(time) * 0.5f + 0.5f) * 255) << 16
                | (int) ((Math.cos(time) * 0.5f + 0.5f) * 255) << 8
                | 128;
            RenderGlobal.drawOutlinedBoundingBox(box, newColor);
        } else {
            RenderGlobal.drawOutlinedBoundingBox(box, color);
        }
    }

    private static boolean isNEIEnvironment() {
        try {
            Class.forName("codechicken.nei.LayoutManager");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
