package trou.fantasy_metropolis.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import trou.fantasy_metropolis.item.ItemSwordWhiter;

public class PlayerUtil {
    public static boolean hasSword(Player player) {
        for(ItemStack itemStack : player.getInventory().items) {
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemSwordWhiter) {
                return true;
            }
        }
        return false;
    }
}
