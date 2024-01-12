package trou.fantasy_metropolis.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import trou.fantasy_metropolis.Registries;
import trou.fantasy_metropolis.capability.IContainerCapability;
import trou.fantasy_metropolis.capability.container.SimpleContainer;
import trou.fantasy_metropolis.capability.container.WhiterSlot;

@Mixin(InventoryMenu.class)
public abstract class InventoryMenuMixin extends AbstractContainerMenu {
    protected InventoryMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }
    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/world/entity/player/Inventory;ZLnet/minecraft/world/entity/player/Player;)V")
    private void init(Inventory playerInventory, boolean active, Player owner, CallbackInfo ci) {
        LazyOptional<IContainerCapability> lazyContainerCap = owner.getCapability(Registries.FM_CONTAINER);
        lazyContainerCap.ifPresent((containerCap) -> {
            SimpleContainer container = containerCap.getContainer();
            this.addSlot(new WhiterSlot(container, 0, 77, 8));
        });
    }
}