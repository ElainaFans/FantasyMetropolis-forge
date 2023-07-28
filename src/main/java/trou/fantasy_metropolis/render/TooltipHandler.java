package trou.fantasy_metropolis.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
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
    private static int borderStart = 0;
    private static int borderEnd = 0;
    private static int bgStart = 0;
    private static int bgEnd = 0;

    public static GuiGraphics guiGraphics = null;
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void gatherComponents(RenderTooltipEvent.GatherComponents event) {
        if (event.getItemStack().getItem() instanceof ItemSwordWhiter) {
            int range = event.getItemStack().getOrCreateTag().getInt("range");
            event.getTooltipElements().clear();
            event.getTooltipElements().add(Either.left(FormattedText.of(FrameWorker.marquee(I18n.get("tooltip.whiter_sword.title")))));
            event.getTooltipElements().add(Either.left(FormattedText.of("")));
            event.getTooltipElements().add(Either.left(FormattedText.of(ChatFormatting.LIGHT_PURPLE + "+ " + ChatFormatting.BOLD + I18n.get("tip.whiter"))));
            event.getTooltipElements().add(Either.left(FormattedText.of(ChatFormatting.BLUE + "+ " + ChatFormatting.BOLD + I18n.get("tip.range") + range)));
            event.getTooltipElements().add(Either.left(FormattedText.of("")));
            event.getTooltipElements().add(Either.left(FormattedText.of(ChatFormatting.DARK_RED + "+ " + FrameWorker.marquee("INFINITE ") + I18n.get("tip.damage"))));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void colorTooltip(RenderTooltipEvent.Color event) {
        if (event.getItemStack().getItem() instanceof ItemSwordWhiter) {
            // make the border and background transparent here so that we can redraw it later.
            event.setBorderStart(0);
            event.setBorderEnd(0);
            event.setBackgroundStart(0);
            event.setBackgroundEnd(0);
            // save the relevant position in order to redraw in the post stage
            borderStart = event.getBorderStart();
            borderEnd = event.getBorderEnd();
            bgStart = event.getBackgroundStart();
            bgEnd = event.getBackgroundEnd();
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        float baseFrameTime = Minecraft.getInstance().getDeltaFrameTime();
        float speedFactor = 0.1f;
        var result = FrameWorker.increaseTimer(baseFrameTime * speedFactor);
        if (result >= 20) FrameWorker.resetTimer();
    }



    public static void postTooltip(ItemStack stack, PoseStack poseStack, int x, int y, Font font, int width, int height, List<ClientTooltipComponent> components) {
        if (stack.getItem() instanceof ItemSwordWhiter) {
            BorderRenderer.drawBorder(poseStack, x, y ,width, height, stack, components, font, borderStart, borderEnd, bgStart, bgEnd);
            var characterRender = new CharacterRenderer(guiGraphics);
            characterRender.drawImage(poseStack, x, y);
            var backgroundRender = new BackgroundRenderer(guiGraphics);
            backgroundRender.drawImage(poseStack, x, y, width, height);
        }
    }
}
