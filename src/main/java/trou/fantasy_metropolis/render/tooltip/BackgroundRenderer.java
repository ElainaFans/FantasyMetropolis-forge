package trou.fantasy_metropolis.render.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import trou.fantasy_metropolis.FantasyMetropolis;

public class BackgroundRenderer {
    private final GuiGraphics guiGraphics;
    private final ResourceLocation border_background = new ResourceLocation(FantasyMetropolis.MOD_ID, "textures/tooltip/border/background.png");

    public BackgroundRenderer(GuiGraphics guiGraphics) {
        this.guiGraphics = guiGraphics;
    }

    public void drawImage(PoseStack poseStack, int x, int y, int width, int height) {
        poseStack.pushPose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.translate(0, 0, 399); // beneath the text and upon other items (under the border)
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        int BG_WIDTH = width + 8;
        int BG_HEIGHT = height + 6;
        guiGraphics.blit(border_background, x - 6, y - 3, 0, 0, BG_WIDTH, BG_HEIGHT, BG_WIDTH, BG_HEIGHT);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        poseStack.popPose();
    }
}
