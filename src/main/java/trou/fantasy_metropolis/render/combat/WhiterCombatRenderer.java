package trou.fantasy_metropolis.render.combat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import trou.fantasy_metropolis.Registries;
import trou.fantasy_metropolis.capability.IContainerCapability;
import trou.fantasy_metropolis.capability.SimpleContainer;
import trou.fantasy_metropolis.item.ItemSwordWhiter;

public class WhiterCombatRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public WhiterCombatRenderer(PlayerRenderer playerRenderer) {
        super(playerRenderer);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, @NotNull AbstractClientPlayer livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (Minecraft.getInstance().player != null) {
            LazyOptional<IContainerCapability> lazyContainerCap = Minecraft.getInstance().player.getCapability(Registries.FM_CONTAINER);
            lazyContainerCap.ifPresent((containerCap) -> {
                SimpleContainer container = containerCap.getContainer();
                var item = container.getItem(0);
                if (item.getItem() instanceof ItemSwordWhiter) {
                    poseStack.pushPose();
                    poseStack.translate(0, 0.35, 0.25);
                    poseStack.rotateAround(Axis.YP.rotationDegrees(180F), 0F, 1F, 0F);
                    Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntity.level(), 0);
                    poseStack.popPose();
                }
            });
        }
    }
}
