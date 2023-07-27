package trou.fantasy_metropolis.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;

public class DataGeneration {
    public static void generate(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        generator.addProvider(event.includeClient(), new FmBlockStates(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new FmItemModels(packOutput, event.getExistingFileHelper()));
    }
}
