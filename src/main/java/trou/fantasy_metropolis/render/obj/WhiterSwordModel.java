package trou.fantasy_metropolis.render.obj;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WhiterSwordModel implements BakedModel {
    BakedModel defaultModel;
    BakedModel flatModel;

    public WhiterSwordModel(BakedModel existingModel, BakedModel flatModel) {
        defaultModel = existingModel;
        this.flatModel = flatModel;
    }

    @Override
    public @NotNull BakedModel applyTransform(@NotNull ItemDisplayContext transformType, @NotNull PoseStack poseStack, boolean applyLeftHandTransform) {
        if (transformType == ItemDisplayContext.FIXED || transformType == ItemDisplayContext.GROUND || transformType == ItemDisplayContext.GUI) {
            return flatModel;
        }
        return defaultModel.applyTransform(transformType, poseStack, applyLeftHandTransform);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState pState, @Nullable Direction pDirection, @NotNull RandomSource pRandom) {
        throw new AssertionError("IBakedModel::getQuads should never be called, only IForgeBakedModel::getQuads");
    }

    @Override
    public boolean useAmbientOcclusion() {
        return defaultModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return defaultModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return defaultModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return defaultModel.isCustomRenderer();
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        throw new AssertionError("IBakedModel::getParticleIcon should never be called, only IForgeBakedModel::getParticleIcon");
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return defaultModel.getOverrides();
    }
}
