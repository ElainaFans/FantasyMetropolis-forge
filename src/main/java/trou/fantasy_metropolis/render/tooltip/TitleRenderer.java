package trou.fantasy_metropolis.render.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import trou.fantasy_metropolis.FantasyMetropolis;

public class TitleRenderer {
    private final GuiGraphics guiGraphics;

    public TitleRenderer(GuiGraphics guiGraphics) {
        this.guiGraphics = guiGraphics;
    }

    public void drawImage(PoseStack poseStack, int x, int y, int width) {
        poseStack.pushPose();
        poseStack.translate(0, 0, 450);
        RenderSystem.enableBlend();
        var currentImage = AnimationWorker.marqueeGif(29);
        guiGraphics.blit(currentImage, x - 24 + width / 2, y - 27, 0, 0, 46, 60, 46, 60);
        RenderSystem.disableBlend();
        poseStack.popPose();
    }
}
