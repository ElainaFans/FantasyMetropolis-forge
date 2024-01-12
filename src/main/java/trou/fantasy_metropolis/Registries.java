package trou.fantasy_metropolis;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import trou.fantasy_metropolis.capability.IContainerCapability;
import trou.fantasy_metropolis.item.ItemSwordWhiter;
import trou.fantasy_metropolis.network.NetworkHandler;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registries {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FantasyMetropolis.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FantasyMetropolis.MOD_ID);
    public static final RegistryObject<Item> ITEM_SWORD_WHITER = ITEMS.register("whiter_sword", ItemSwordWhiter::new);
    public static final RegistryObject<Block> BLOCK_BEDROCK = BLOCKS.register("bedrock", () -> new Block(BlockBehaviour.Properties.of().strength(1.0F, 3600000.0F)));

    public static final Capability<IContainerCapability> FM_CONTAINER = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::registerMessage);
    }
}
