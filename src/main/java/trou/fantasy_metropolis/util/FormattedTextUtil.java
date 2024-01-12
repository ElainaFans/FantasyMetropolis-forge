package trou.fantasy_metropolis.util;

import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;

public class FormattedTextUtil {
    private static final String[] KEYWORDS = {
            "item.fantasy_metropolis.whiter_sword",
            "item.modifiers.mainhand",
            "attribute.modifier.equals.0"
    };
    public static boolean isEmptyLabel(MutableComponent formattedText) {
        var siblingsCount = formattedText.getSiblings().size();
        var contentEmpty = formattedText.getContents().equals(ComponentContents.EMPTY);
        return siblingsCount == 0 && contentEmpty;
    }

    public static boolean isRemoveTarget(MutableComponent formattedText) {
        return labelContains(formattedText, KEYWORDS);
    }

    private static boolean labelContains(MutableComponent formattedText, String[] keywords) {
        var realLabel = formattedText.toString();
        boolean realContains = false;
        for (String keyword : keywords) {
            if (realLabel.contains(keyword)) {
                realContains = true;
                break;
            }
        }
        return realContains;
    }
}
