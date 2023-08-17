package trou.fantasy_metropolis.render.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;

public class SeparatorRenderer {
    public static void drawGradientRectHorizontal(Matrix4f mat, int zLevel, int left, int top, int right, int bottom, int startColor, int endColor)
    {
        float startAlpha = (float)(startColor >> 24 & 255) / 255.0F;
        float startRed   = (float)(startColor >> 16 & 255) / 255.0F;
        float startGreen = (float)(startColor >>  8 & 255) / 255.0F;
        float startBlue  = (float)(startColor       & 255) / 255.0F;
        float endAlpha   = (float)(endColor   >> 24 & 255) / 255.0F;
        float endRed     = (float)(endColor   >> 16 & 255) / 255.0F;
        float endGreen   = (float)(endColor   >>  8 & 255) / 255.0F;
        float endBlue    = (float)(endColor         & 255) / 255.0F;

        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buffer.vertex(mat, right,    top, zLevel).color(  endRed,   endGreen,   endBlue,   endAlpha).endVertex();
        buffer.vertex(mat,  left,    top, zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.vertex(mat,  left, bottom, zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.vertex(mat, right, bottom, zLevel).color(  endRed,   endGreen,   endBlue,   endAlpha).endVertex();
        tesselator.end();

        RenderSystem.disableBlend();
    }

    public static void drawSeparator(PoseStack poseStack, int x, int y, int width, int color) {
        poseStack.pushPose();
        Matrix4f matrix = poseStack.last().pose();
        drawGradientRectHorizontal(matrix, 500, x, y, x + width / 2, y + 1, color & 0xFFFFFF, color);
        drawGradientRectHorizontal(matrix, 500, x + width / 2, y, x + width, y + 1, color, color & 0xFFFFFF);
        poseStack.popPose();
    }
}
