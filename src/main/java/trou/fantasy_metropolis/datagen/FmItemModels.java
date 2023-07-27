package trou.fantasy_metropolis.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import trou.fantasy_metropolis.FantasyMetropolis;
import trou.fantasy_metropolis.Registries;

public class FmItemModels extends ItemModelProvider {

    public FmItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FantasyMetropolis.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(Registries.ITEM_SWORD_WHITER.get());
    }
}
