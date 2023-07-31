package trou.fantasy_metropolis.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterNamedRenderTypesEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import trou.fantasy_metropolis.FantasyMetropolis;
import trou.fantasy_metropolis.item.ItemSwordWhiter;

import java.util.List;

@Mod.EventBusSubscriber(modid = FantasyMetropolis.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class TooltipHandler {
    private static GuiGraphics guiGraphics = null;

    public static void setGuiGraphics(GuiGraphics value) {
        guiGraphics = value;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void gatherComponents(RenderTooltipEvent.GatherComponents event) {
        if (event.getItemStack().getItem() instanceof ItemSwordWhiter) {
            int range = event.getItemStack().getOrCreateTag().getInt("range");
            event.getTooltipElements().subList(0, 4).clear(); // clear the default tooltip (title, empty, hand, speed)
            event.getTooltipElements().add(Either.left(FormattedText.of(FrameWorker.marqueeTitle(I18n.get("tooltip.whiter_sword.title")))));
            event.getTooltipElements().add(Either.left(FormattedText.of(ChatFormatting.LIGHT_PURPLE + "+ "  + I18n.get("tooltip.skill.hint"))));
            event.getTooltipElements().add(Either.left(FormattedText.of(ChatFormatting.BLUE + "+ "  + I18n.get("tooltip.skill.range") + range)));
            event.getTooltipElements().add(Either.left(FormattedText.of("")));
            event.getTooltipElements().add(Either.left(FormattedText.of(ChatFormatting.BLUE + "+ " + FrameWorker.marqueeDamage(I18n.get("tooltip.attack.damage")) + " " + I18n.get("tooltip.attack.hint"))));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        float baseFrameTime = Minecraft.getInstance().getDeltaFrameTime();
        float speedFactor = 0.2f;
        var result = FrameWorker.increaseTimer(baseFrameTime * speedFactor);
        if (result >= 20) FrameWorker.resetTimer();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRegisterRenderTypes(RegisterNamedRenderTypesEvent event) {
        // not quite sure about these code, especially shader state, they are not functionally working.
        // some ideas are including from breaking apart de code.
        // DE is combine with code chicken core and brandon core, many render functions are not included and pretty complex.
        final RenderStateShard.ShaderStateShard renderStateShard = new RenderStateShard.ShaderStateShard(GameRenderer::getRendertypeItemEntityTranslucentCullShader);
        RenderType baseType = RenderType.create(FantasyMetropolis.MOD_ID + ":whiter_sword", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.TRIANGLES, 256, true, false, RenderType.CompositeState.builder()
                .setShaderState(renderStateShard)
                .setTextureState(new RenderStateShard.TextureStateShard(new ResourceLocation(FantasyMetropolis.MOD_ID, "textures/item/sword_basecolor.png"), false, false))
                .setLightmapState(new RenderStateShard.LightmapStateShard(true))
                .setOverlayState(new RenderStateShard.OverlayStateShard(true))
                .createCompositeState(true));
        event.register("wither_sword", baseType, baseType);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void colorTooltip(RenderTooltipEvent.Color event) {
        if (event.getItemStack().getItem() instanceof ItemSwordWhiter) {
            // make the border and background transparent here so that we can redraw it later.
            event.setBorderStart(0);
            event.setBorderEnd(0);
            event.setBackgroundStart(0);
            event.setBackgroundEnd(0);
        }
    }


    public static void postTooltip(ItemStack stack, PoseStack poseStack, int x, int y, Font font, int width, int height, List<ClientTooltipComponent> components) {
        if (stack.getItem() instanceof ItemSwordWhiter) {
            var characterRender = new CharacterRenderer(guiGraphics);
            var backgroundRender = new BackgroundRenderer(guiGraphics);
            var borderRender = new BorderRenderer(guiGraphics);
            characterRender.drawImage(poseStack, x, y);
            borderRender.drawImage(poseStack, x, y, width, height);
            backgroundRender.drawImage(poseStack, x, y, width, height);
        }
    }
}
