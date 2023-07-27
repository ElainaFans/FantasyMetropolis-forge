package trou.fantasy_metropolis.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import trou.fantasy_metropolis.util.DamageUtil;
import trou.fantasy_metropolis.util.PlayerUtil;

public class ItemSwordWhiter extends SwordItem {
    private static final Properties properties = new Properties().fireResistant().setNoRepair();
    private static final int RANGE_ATTACK = 5;


    public ItemSwordWhiter() {
        super(new TierWhiter(), 0, 9999, properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        stack.getOrCreateTag().putInt("range", 10);
        return stack;
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        super.onCraftedBy(pStack, pLevel, pPlayer);
        pStack.getOrCreateTag().putInt("range", 10);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (!player.level().isClientSide && entity instanceof LivingEntity livingEntity) {
            // Creative need to be handled here because they don't trigger attack event
            if (livingEntity instanceof Player targetPlayer) {
                // Creative who has the sword will not be set dead
                if (targetPlayer.isCreative() && !PlayerUtil.hasSword(targetPlayer)) {
                    DamageUtil.punishPlayer(targetPlayer);
                    targetPlayer.setHealth(0f);
                    return true;
                }
            }
            DamageUtil.killEntityLiving(livingEntity);
            DamageUtil.hurtRange(RANGE_ATTACK, player);
        }
        return true; // We needn't the rest of the interaction
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand.equals(InteractionHand.MAIN_HAND) && player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                player.sendSystemMessage(Component.translatable("whiter_sword.kill_range"));
                int range = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTag().getInt("range");
                DamageUtil.hurtRange(range, player);
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if (!(pEntity instanceof Player player)) return;
        // The first one who has it will be the owner
        CompoundTag tag = pStack.getOrCreateTag();
        if (!tag.contains("owner")) {
            tag.putUUID("owner", pEntity.getUUID());
        } else if (!player.getUUID().equals(tag.getUUID("owner"))) {
            player.getInventory().removeItem(pStack);
            player.drop(pStack, false, false);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        // set attack speed
        if (pEquipmentSlot.equals(EquipmentSlot.MAINHAND)) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", 9996, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return ImmutableMultimap.of();
    }

    private static class TierWhiter implements Tier {
        @Override
        public int getUses() {
            return 0;
        }

        @Override
        public float getSpeed() {
            return 0;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0;
        }

        @Override
        public int getLevel() {
            return 0;
        }

        @Override
        public int getEnchantmentValue() {
            return 0;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }
    }
}
