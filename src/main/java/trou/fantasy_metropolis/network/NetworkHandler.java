package trou.fantasy_metropolis.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import trou.fantasy_metropolis.FantasyMetropolis;

public class NetworkHandler {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(FantasyMetropolis.MOD_ID, "whiter_sword_range"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
        INSTANCE.messageBuilder(PacketSwordWhiter.class, nextID())
                .encoder(PacketSwordWhiter::toBytes)
                .decoder(PacketSwordWhiter::new)
                .consumerMainThread(PacketSwordWhiter::handler)
                .add();
    }
}
