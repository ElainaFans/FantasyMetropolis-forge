package trou.fantasy_metropolis.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.loaders.ObjModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import trou.fantasy_metropolis.FantasyMetropolis;

public class FmItemModels extends ItemModelProvider {

    public FmItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FantasyMetropolis.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        getBuilder("whiter_sword")
                .transforms()
                .transform(ItemDisplayContext.GUI).scale(0.05f).rotation(180,0,0).translation(0, 4.0f, 0).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).scale(0.05f).rotation(180,0,0).translation(0, 10f, 0).end()
                .end().customLoader(ObjModelBuilder::begin)
                .modelLocation(modLoc("textures/item/sword.obj"))
                .overrideMaterialLibrary(modLoc("textures/item/sword.mtl"))
                .flipV(true);
    }
}
