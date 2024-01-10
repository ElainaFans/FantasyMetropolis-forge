package trou.fantasy_metropolis;

import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import trou.fantasy_metropolis.capability.ContainerCapabilityProvider;
import trou.fantasy_metropolis.capability.IContainerCapability;
import trou.fantasy_metropolis.item.ItemSwordWhiter;
import trou.fantasy_metropolis.network.NetworkHandler;
import trou.fantasy_metropolis.network.PacketSwordWhiter;
import trou.fantasy_metropolis.util.DamageUtil;
import trou.fantasy_metropolis.util.PlayerUtil;

import java.util.Set;

@Mod.EventBusSubscriber
public class EventHandler {

    public static Set<Class<? extends Entity>> antiEntity = Sets.newHashSet();

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.isShiftKeyDown() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ItemSwordWhiter) {
            NetworkHandler.INSTANCE.sendToServer(new PacketSwordWhiter((int) event.getScrollDelta()));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerHarvest(PlayerEvent.HarvestCheck event) {
        var player = event.getEntity();
        var block = event.getTargetBlock().getBlock();
        if (PlayerUtil.hasSword(player)) event.setCanHarvest(true);
        else if (block.equals(Registries.BLOCK_BEDROCK.get()) && !PlayerUtil.hasSword(player)) {
            event.setCanHarvest(false);
        }
    }

    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        // black magic to dig bedrock
        if (event.getLevel().isClientSide) return;
        var player = event.getEntity();
        var itemStack = event.getEntity().getItemInHand(InteractionHand.MAIN_HAND);
        var block = player.level().getBlockState(event.getPos()).getBlock();
        // replace the real bedrock with our fake bedrock
        if (block.equals(Registries.BLOCK_BEDROCK.get()) && !PlayerUtil.hasSword(player)) {
            player.level().setBlock(event.getPos(), Blocks.BEDROCK.defaultBlockState(), 3);
        } else if (block.equals(Blocks.BEDROCK) && itemStack.getItem() instanceof DiggerItem && PlayerUtil.hasSword(player)) {
            player.level().setBlock(event.getPos(), Registries.BLOCK_BEDROCK.get().defaultBlockState(), 3);
        }
    }

    @SubscribeEvent
    public static void onPlayerAttacked(LivingAttackEvent event) {
        if (event.getEntity().level().isClientSide) return;
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player player) {
            Entity attacker = event.getSource().getEntity();
            if (PlayerUtil.hasSword(player)) {
                var damageSource = event.getEntity().level().damageSources().magic();
                // reflect the damage to the attacker
                if (attacker != null) attacker.hurt(damageSource, Float.MAX_VALUE);
                // player will get no damage
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (event.getEntity() instanceof FakePlayer) return;
        LivingEntity livingEntity = event.getEntity();
        // if we trigger hurt manually, we need to identify whether the player can be damaged
        if (livingEntity instanceof Player) {
            if (PlayerUtil.hasSword(((Player) livingEntity))) {
                event.setCanceled(true);
                return;
            }
        }
        // arrow damage enhanced if source player owned the sword
        if (event.getSource().isIndirect()) {
            var attacker = event.getSource().getEntity();
            var receiver = event.getEntity();
            if (attacker instanceof Player playerAttacker) {
                if (PlayerUtil.hasSword(playerAttacker)) {
                    DamageUtil.killEntityLiving(receiver);
                }
            }
        }
    }

    // black magic included in order to prohibit reviving of some living
    @SubscribeEvent
    public void onEntityItemJoinWorld(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        for (Class<? extends Entity> clazz : antiEntity) {
            if (clazz.isInstance(entity)) {
                event.setCanceled(true);
                return;
            }
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilityEvent(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof Player) {
            event.addCapability(new ResourceLocation(FantasyMetropolis.MOD_ID, "container"), new ContainerCapabilityProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        LazyOptional<IContainerCapability> oldContainerCap = event.getOriginal().getCapability(Registries.FM_CONTAINER);
        LazyOptional<IContainerCapability> newContainerCap = event.getEntity().getCapability(Registries.FM_CONTAINER);
        if (oldContainerCap.isPresent() && newContainerCap.isPresent()) {
            newContainerCap.ifPresent((newCap) -> {
                oldContainerCap.ifPresent((oldCap) -> {
                    newCap.deserializeNBT(oldCap.serializeNBT());
                    event.getEntity().invalidateCaps();
                });
            });
        }
    }
}
