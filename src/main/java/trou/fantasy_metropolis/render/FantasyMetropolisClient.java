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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import trou.fantasy_metropolis.FantasyMetropolis;
import trou.fantasy_metropolis.item.ItemSwordWhiter;
import trou.fantasy_metropolis.render.tooltip.*;
import trou.fantasy_metropolis.util.FormattedTextUtil;

import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber(modid = FantasyMetropolis.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class FantasyMetropolisClient {
    private static GuiGraphics guiGraphics = null;

    public static void setGuiGraphics(GuiGraphics value) {
        guiGraphics = value;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void gatherComponents(RenderTooltipEvent.GatherComponents event) {
        if (event.getItemStack().getItem() instanceof ItemSwordWhiter) {
            int range = event.getItemStack().getOrCreateTag().getInt("range");
            boolean firstEmptyElement = false;
            List<Either<FormattedText, TooltipComponent>> waitForRemoval = new LinkedList<>();
            for (var element : event.getTooltipElements()) {
                if (element.left().isEmpty()) continue;
                if (!(element.left().get() instanceof MutableComponent mutableComponent)) continue;
                if (FormattedTextUtil.isEmptyLabel(mutableComponent) && !firstEmptyElement) {
                    firstEmptyElement = true;
                    waitForRemoval.add(element);
                }
                if (FormattedTextUtil.isRemoveTarget(mutableComponent)) waitForRemoval.add(element);
            }

            waitForRemoval.forEach((element) -> event.getTooltipElements().remove(element));

            event.getTooltipElements().add(0, Either.left(FormattedText.of("")));
            event.getTooltipElements().add(1, Either.left(FormattedText.of(ChatFormatting.LIGHT_PURPLE + "+ "  + I18n.get("tooltip.skill.hint"))));
            event.getTooltipElements().add(2, Either.left(FormattedText.of(ChatFormatting.BLUE + "+ "  + I18n.get("tooltip.skill.range") + range)));
            event.getTooltipElements().add(3, Either.left(FormattedText.of("")));
            event.getTooltipElements().add(4, Either.left(FormattedText.of(ChatFormatting.BLUE + "+ " + AnimationWorker.marqueeDamage(I18n.get("tooltip.attack.damage")) + " " + I18n.get("tooltip.attack.hint"))));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        float baseFrameTime = Minecraft.getInstance().getDeltaFrameTime();
        float speedFactor = 0.25f;
        var result = AnimationWorker.increaseTimer(baseFrameTime * speedFactor);
        if (result >= 20) AnimationWorker.resetTimer();
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
            var titleRender = new TitleRenderer(guiGraphics);
            characterRender.drawImage(poseStack, x, y);
            borderRender.drawImage(poseStack, x, y, width, height);
            backgroundRender.drawImage(poseStack, x, y, width, height);
            titleRender.drawImage(poseStack, x, y, width);
        }
    }
}
