package trou.fantasy_metropolis.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;
import trou.fantasy_metropolis.Registries;
import trou.fantasy_metropolis.capability.IContainerCapability;
import trou.fantasy_metropolis.network.NetworkHandler;
import trou.fantasy_metropolis.network.PacketContainerUpdate;

public class CapabilityUtil {
    public static void copyCapability(Player source, Player target) {
        source.reviveCaps();
        LazyOptional<IContainerCapability> oldContainerCap = source.getCapability(Registries.FM_CONTAINER);
        LazyOptional<IContainerCapability> newContainerCap = target.getCapability(Registries.FM_CONTAINER);
        if (oldContainerCap.resolve().isPresent() && newContainerCap.resolve().isPresent()) {
            var newCap = newContainerCap.resolve().get();
            var oldCap = oldContainerCap.resolve().get();
            newCap.deserializeNBT(oldCap.serializeNBT());
            target.invalidateCaps();
        }
    }

    public static void applyCapability(CompoundTag compoundTag, Player target) {
        LazyOptional<IContainerCapability> newContainerCap = target.getCapability(Registries.FM_CONTAINER);
        if (newContainerCap.resolve().isPresent()) {
            var newCap = newContainerCap.resolve().get();
            newCap.deserializeNBT(compoundTag);
        }
    }

    public static void tryNotifyPlayers(Player source) {
        LazyOptional<IContainerCapability> capability = source.getCapability(Registries.FM_CONTAINER);
        if (capability.resolve().isPresent()) {
            var cap = capability.resolve().get();
            if (cap.getContainer().isDirty()) {
                NetworkHandler.INSTANCE_1.send(PacketDistributor.ALL.noArg(), new PacketContainerUpdate(source.getUUID(), cap.serializeNBT()));
                cap.getContainer().cancelDirty();
            }
        }
    }

    public static void notifyPlayers(Player source) {
        LazyOptional<IContainerCapability> capability = source.getCapability(Registries.FM_CONTAINER);
        if (capability.resolve().isPresent()) {
            var cap = capability.resolve().get();
            NetworkHandler.INSTANCE_1.send(PacketDistributor.ALL.noArg(), new PacketContainerUpdate(source.getUUID(), cap.serializeNBT()));
            cap.getContainer().cancelDirty();
        }
    }
}
