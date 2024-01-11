package trou.fantasy_metropolis.network;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import trou.fantasy_metropolis.util.CapabilityUtil;

import java.util.UUID;
import java.util.function.Supplier;

public class PacketContainerUpdate {
    private final UUID sourceUUID;
    private final CompoundTag compoundTag;

    public PacketContainerUpdate(FriendlyByteBuf buffer) {
        sourceUUID = buffer.readUUID();
        compoundTag = buffer.readNbt();
    }

    public PacketContainerUpdate(UUID sourceUUID, CompoundTag compoundTag) {
        this.sourceUUID = sourceUUID;
        this.compoundTag = compoundTag;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(sourceUUID);
        buf.writeNbt(compoundTag);
    }
    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var clientLevel = Minecraft.getInstance().level;
            if (clientLevel != null) {
                var clientPlayer = clientLevel.getPlayerByUUID(sourceUUID);
                if (clientPlayer != null) {
                    CapabilityUtil.applyCapability(compoundTag, clientPlayer);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
