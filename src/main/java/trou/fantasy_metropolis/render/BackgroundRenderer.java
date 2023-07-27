package trou.fantasy_metropolis.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import trou.fantasy_metropolis.FantasyMetropolis;

public class BackgroundRenderer {
    private final GuiGraphics guiGraphics;
    private final ResourceLocation border_background = new ResourceLocation(FantasyMetropolis.MOD_ID, "textures/tooltip/border_background.png");

    public BackgroundRenderer(GuiGraphics guiGraphics) {
        this.guiGraphics = guiGraphics;
    }

    public void drawImage(PoseStack poseStack, int x, int y, int width, int height) {
        poseStack.pushPose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.translate(0, 0, 400); // beneath the text and upon other items
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        int BG_WIDTH = (int) (width * 1.5); // rescale the width
        int BG_HEIGHT = (int) (height * 1.3); // rescale the height
        guiGraphics.blit(border_background, x - 22, y - 10, 0, 0, BG_WIDTH, BG_HEIGHT, BG_WIDTH, BG_HEIGHT);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        poseStack.popPose();
    }
}
