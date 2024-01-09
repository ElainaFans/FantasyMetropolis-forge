package trou.fantasy_metropolis.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IContainerCapability extends INBTSerializable<CompoundTag> {
    SimpleContainer getContainer();
}
