package trou.fantasy_metropolis.render;

import net.minecraft.ChatFormatting;

import java.util.Arrays;
import java.util.List;

public class FrameWorker {
    public static float renderTimer = 0;
    private static final List<ChatFormatting> colorCodes = Arrays.asList(
            ChatFormatting.GREEN,
            ChatFormatting.BLUE,
            ChatFormatting.RED,
            ChatFormatting.YELLOW,
            ChatFormatting.LIGHT_PURPLE
    );

    public static float increaseTimer(float value) {
        return renderTimer += value;
    }

    public static void resetTimer() {
        renderTimer = 0;
    }

    public static String marquee(String targetString) {
        var renderTick = (int) renderTimer;
        int colorCodeIndex = renderTick % colorCodes.size();

        char[] charArray = targetString.toCharArray();
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            char currentChar = targetString.charAt(i);
            String colorCode = "" + colorCodes.get((i + colorCodeIndex) % colorCodes.size());
            string.append(colorCode).append(currentChar);
        }

        return string.toString();
    }
}
