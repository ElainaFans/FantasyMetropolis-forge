package trou.fantasy_metropolis.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import trou.fantasy_metropolis.capability.container.SimpleContainer;

public interface IContainerCapability extends INBTSerializable<CompoundTag> {
    SimpleContainer getContainer();
}
