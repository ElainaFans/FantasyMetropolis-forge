package trou.fantasy_metropolis;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FantasyMetropolis.MOD_ID)
public class FantasyMetropolis {
    public static final String MOD_ID = "fantasy_metropolis";

    public FantasyMetropolis() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Registries.ITEMS.register(modEventBus);
        Registries.BLOCKS.register(modEventBus);
    }
}
