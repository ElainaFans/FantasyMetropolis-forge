package trou.fantasy_metropolis.render;

import net.minecraft.ChatFormatting;

import java.util.Arrays;
import java.util.List;

public class FrameWorker {
    public static float renderTimer = 0;

    private static final List<ChatFormatting> colorCodesTitle = Arrays.asList(
            ChatFormatting.DARK_GRAY,
            ChatFormatting.DARK_PURPLE,
            ChatFormatting.LIGHT_PURPLE,
            ChatFormatting.AQUA,
            ChatFormatting.BLUE,
            ChatFormatting.DARK_AQUA,
            ChatFormatting.DARK_BLUE
    );

    private static final List<ChatFormatting> colorCodesDamage = Arrays.asList(
            ChatFormatting.RED,
            ChatFormatting.GOLD,
            ChatFormatting.YELLOW,
            ChatFormatting.GREEN,
            ChatFormatting.BLUE,
            ChatFormatting.AQUA,
            ChatFormatting.LIGHT_PURPLE
    );

    public static float increaseTimer(float value) {
        return renderTimer += value;
    }

    public static void resetTimer() {
        renderTimer = 0;
    }

    public static String marqueeTitle(String targetString) {
        return marquee(targetString, true, colorCodesTitle);
    }

    public static String marqueeDamage(String targetString) {
        return marquee(targetString, false, colorCodesDamage);
    }

    private static String marquee(String targetString, boolean bold, List<ChatFormatting> colorCodes) {
        var renderTick = (int) renderTimer;
        int colorCodeIndex = renderTick % colorCodes.size();

        char[] charArray = targetString.toCharArray();
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            char currentChar = targetString.charAt(i);

            String colorCode = "" + colorCodes.get((i + colorCodeIndex) % colorCodes.size()) + (bold ? ChatFormatting.BOLD : "");
            string.append(colorCode).append(currentChar);
        }

        return string.toString();
    }
}
