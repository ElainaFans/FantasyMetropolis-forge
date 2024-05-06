package trou.fantasy_metropolis.render.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import trou.fantasy_metropolis.FantasyMetropolis;

public class CharacterRenderer {
    private final GuiGraphics guiGraphics;
    private final ResourceLocation left_character = new ResourceLocation(FantasyMetropolis.MOD_ID, "textures/tooltip/character_left.png");

    public CharacterRenderer(GuiGraphics guiGraphics) {
        this.guiGraphics = guiGraphics;
    }

    public void drawImage(PoseStack poseStack, int x, int y) {
        poseStack.pushPose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.translate(0, 0, 410.0); // always on the top layer
        int CHARACTER_WIDTH = 58;
        int CHARACTER_HEIGHT = 80;
        guiGraphics.blit(left_character, x - 68, y - 16, 0, 0, CHARACTER_WIDTH, CHARACTER_HEIGHT, CHARACTER_WIDTH, CHARACTER_HEIGHT);
        poseStack.popPose();
    }
}
