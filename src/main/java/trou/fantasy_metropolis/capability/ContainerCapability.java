package trou.fantasy_metropolis.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class ContainerCapability implements IContainerCapability{
    SimpleContainer container;
    @Override
    public SimpleContainer getContainer() {
        if (container == null) {
            container = new SimpleContainer(1);
        }
        return container;
    }

    @Override
    public CompoundTag serializeNBT() {
        return getContainer().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getContainer().deserializeNBT(nbt);
    }
}
