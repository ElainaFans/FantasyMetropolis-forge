package trou.fantasy_metropolis.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import trou.fantasy_metropolis.FantasyMetropolis;

public class NetworkHandler {
    public static SimpleChannel CHANNEL_RANGE;
    public static SimpleChannel CHANNEL_CONTAINER;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        CHANNEL_RANGE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(FantasyMetropolis.MOD_ID, "whiter_sword_range"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
        CHANNEL_CONTAINER = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(FantasyMetropolis.MOD_ID, "whiter_sword_container"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
        CHANNEL_RANGE.messageBuilder(PacketRangeUpdate.class, nextID())
                .encoder(PacketRangeUpdate::toBytes)
                .decoder(PacketRangeUpdate::new)
                .consumerMainThread(PacketRangeUpdate::handler)
                .add();
        CHANNEL_CONTAINER.messageBuilder(PacketContainerUpdate.class, nextID())
                .encoder(PacketContainerUpdate::toBytes)
                .decoder(PacketContainerUpdate::new)
                .consumerMainThread(PacketContainerUpdate::handler)
                .add();
    }
}
