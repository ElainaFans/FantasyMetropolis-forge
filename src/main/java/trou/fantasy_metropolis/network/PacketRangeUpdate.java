package trou.fantasy_metropolis.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketRangeUpdate {
    private final int scroll;

    public PacketRangeUpdate(FriendlyByteBuf buffer) {
        scroll = buffer.readInt();
    }

    public PacketRangeUpdate(int scroll) {
        this.scroll = scroll;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(scroll);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            if (sender != null) {
                ItemStack stack = sender.getItemInHand(InteractionHand.MAIN_HAND);
                CompoundTag tag = stack.getOrCreateTag();
                int value = tag.getInt("range") + scroll;
                tag.putInt("range", Math.max(value, 0));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
