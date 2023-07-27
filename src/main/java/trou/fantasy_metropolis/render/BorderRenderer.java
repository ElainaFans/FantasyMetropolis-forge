package trou.fantasy_metropolis.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

import java.util.List;

public class BorderRenderer {
    public static void drawGradientRect(Matrix4f mat, int zLevel, int left, int top, int right, int bottom, int startColor, int endColor)
    {
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        drawGradientRect(mat, bufferBuilder, left, top, right, bottom, zLevel, startColor, endColor);
        BufferUploader.drawWithShader(bufferBuilder.end());

        RenderSystem.disableBlend();
    }

    public static void drawGradientRect(Matrix4f mat, BufferBuilder bufferBuilder, int left, int top, int right, int bottom, int zLevel, int startColor, int endColor) {
        float startAlpha = (float)(startColor >> 24 & 255) / 255.0F;
        float startRed = (float)(startColor >> 16 & 255) / 255.0F;
        float startGreen = (float)(startColor >> 8 & 255) / 255.0F;
        float startBlue = (float)(startColor & 255) / 255.0F;
        bufferBuilder.vertex(mat, (float)right, (float)top, (float)zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        bufferBuilder.vertex(mat, (float)left, (float)top, (float)zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        bufferBuilder.vertex(mat, (float)left, (float)bottom, (float)zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        bufferBuilder.vertex(mat, (float)right, (float)bottom, (float)zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
    }

    public static void drawBorder(PoseStack poseStack, int x, int y, int width, int height, ItemStack item, List<ClientTooltipComponent> components, Font font, int borderStart, int borderEnd, int bgStart, int bgEnd) {
        poseStack.pushPose();
        Matrix4f matrix = poseStack.last().pose();
        drawGradientRect(matrix, 400, x - 3, y - 3 + 1, x - 3 + 1, y + height + 3 - 1, borderStart, borderEnd);
        drawGradientRect(matrix, 400, x + width + 2, y - 3 + 1, x + width + 3, y + height + 3 - 1, borderStart, borderEnd);
        drawGradientRect(matrix, 400, x - 3, y - 3, x + width + 3, y - 3 + 1, borderStart, borderEnd);
        drawGradientRect(matrix, 400, x - 3, y + height + 2, x + width + 3, y + height + 3, borderStart, borderEnd);
        poseStack.popPose();
    }
}
