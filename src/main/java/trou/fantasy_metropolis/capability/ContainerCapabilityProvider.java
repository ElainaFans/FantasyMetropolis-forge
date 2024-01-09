package trou.fantasy_metropolis.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;
import trou.fantasy_metropolis.Registries;

import javax.annotation.Nonnull;

public class ContainerCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private IContainerCapability containerCapability;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == Registries.FM_CONTAINER ? LazyOptional.of(this::getOrCreateCapability).cast() : LazyOptional.empty();
    }

    @Nonnull
    IContainerCapability getOrCreateCapability() {
        if (containerCapability == null) {
            this.containerCapability = new ContainerCapability();
        }
        return this.containerCapability;
    }

    @Override
    public CompoundTag serializeNBT() {
        return getOrCreateCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getOrCreateCapability().deserializeNBT(nbt);
    }
}
