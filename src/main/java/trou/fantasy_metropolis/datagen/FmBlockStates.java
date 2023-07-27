package trou.fantasy_metropolis.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import trou.fantasy_metropolis.FantasyMetropolis;
import trou.fantasy_metropolis.Registries;

import java.util.Objects;

public class FmBlockStates extends BlockStateProvider {

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    public FmBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FantasyMetropolis.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        var blockBedrock = Registries.BLOCK_BEDROCK.get();
        var name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(blockBedrock)).getPath();

        simpleBlock(Registries.BLOCK_BEDROCK.get(), models().cubeAll(name, mcLoc("block/bedrock")));
    }
}
