package trou.fantasy_metropolis.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import trou.fantasy_metropolis.FantasyMetropolis;

public class BorderRenderer {
    private final GuiGraphics guiGraphics;
    private final ResourceLocation top = new ResourceLocation(FantasyMetropolis.MOD_ID, "textures/tooltip/border/top.png");
    private final ResourceLocation bottom = new ResourceLocation(FantasyMetropolis.MOD_ID, "textures/tooltip/border/bottom.png");
    private final ResourceLocation left = new ResourceLocation(FantasyMetropolis.MOD_ID, "textures/tooltip/border/left.png");
    private final ResourceLocation right = new ResourceLocation(FantasyMetropolis.MOD_ID, "textures/tooltip/border/right.png");
    private final ResourceLocation left_top = new ResourceLocation(FantasyMetropolis.MOD_ID, "textures/tooltip/border/left_top.png");
    private final ResourceLocation right_top = new ResourceLocation(FantasyMetropolis.MOD_ID, "textures/tooltip/border/right_top.png");
    private final ResourceLocation left_bottom = new ResourceLocation(FantasyMetropolis.MOD_ID, "textures/tooltip/border/left_bottom.png");
    private final ResourceLocation right_bottom = new ResourceLocation(FantasyMetropolis.MOD_ID, "textures/tooltip/border/right_bottom.png");
    private final ResourceLocation star = new ResourceLocation(FantasyMetropolis.MOD_ID, "textures/tooltip/border/stars.png");

    public BorderRenderer(GuiGraphics guiGraphics) {
        this.guiGraphics = guiGraphics;
    }


    private void blit(ResourceLocation pAtlasLocation, int pX, int pY, int pWidth, int pHeight) {
        // duration maybe negative, we don't render negative width
        if (pWidth > 0) guiGraphics.blit(pAtlasLocation, pX, pY, 0, 0, pWidth, pHeight, pWidth, pHeight);
    }


    public void drawImage(PoseStack poseStack, int x, int y, int width, int height) {
        poseStack.pushPose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.translate(0, 0, 400); // beneath the text and upon other items
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        // Size: base * 0.07
        var widthOffset = 2;
        width += widthOffset;
        var horizonStart = x - 16;
        var horizonDuration = width - 55;
        var horizonEnd = x - 30 + width;
        var verticalStart = y - 14;
        var verticalDuration = height - 38;
        var verticalEnd = y - 20 + height;
        var horizonCenter = (int) (horizonStart + 0.5 * width);
        blit(left_top, horizonStart, verticalStart, 41, 37);
        blit(top, horizonStart + 41, verticalStart + 7, horizonDuration, 4);
        blit(right_top, horizonEnd, verticalStart, 40, 37);
        blit(left_bottom, horizonStart, verticalEnd, 46, 32);
        blit(left, horizonStart + 9, verticalStart + 37, 1, verticalDuration);
        blit(right, horizonEnd + 30, verticalStart + 37, 1, verticalDuration);
        blit(right_bottom, horizonEnd - 6, verticalEnd, 46, 32);
        blit(bottom, horizonStart + 46, verticalEnd + 25, width - 66, 2);
        blit(star, horizonCenter + 7, verticalStart + 8, 12, 3);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        poseStack.popPose();
    }
}
