package trou.fantasy_metropolis.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import trou.fantasy_metropolis.FantasyMetropolis;

public class NetworkHandler {
    public static SimpleChannel INSTANCE_0;
    public static SimpleChannel INSTANCE_1;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE_0 = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(FantasyMetropolis.MOD_ID, "whiter_sword_range"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
        INSTANCE_1 = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(FantasyMetropolis.MOD_ID, "whiter_sword_container"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
        INSTANCE_0.messageBuilder(PacketRangeUpdate.class, nextID())
                .encoder(PacketRangeUpdate::toBytes)
                .decoder(PacketRangeUpdate::new)
                .consumerMainThread(PacketRangeUpdate::handler)
                .add();
        INSTANCE_1.messageBuilder(PacketContainerUpdate.class, nextID())
                .encoder(PacketContainerUpdate::toBytes)
                .decoder(PacketContainerUpdate::new)
                .consumerMainThread(PacketContainerUpdate::handler)
                .add();
    }
}
