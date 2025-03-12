package chryseonanddav.tlocc.common.entity.remains;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.VehicleInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PlayerRemainsEntity extends LivingEntity implements VehicleInventory {

    private static final int MAX_SIZE = 27 * 2; // Maximum inventory size
    private DefaultedList<ItemStack> inventory;

    public PlayerRemainsEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.setInvulnerable(true);
        this.inventory = DefaultedList.ofSize(MAX_SIZE, ItemStack.EMPTY);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 100f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 0f);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Inventories.readNbt(nbt, this.getInventory());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        Inventories.writeNbt(nbt, this.getInventory());
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source.getAttacker();
            if (player.isSneaking()) {
                if (getWorld().isClient) return true;

                this.dropInventory();
                this.discard();
                return true;
            }
        }
        return false;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getStackInHand(hand);

        if (heldItem.getItem() instanceof BowItem ||
                heldItem.getItem() instanceof ShieldItem ||
                heldItem.getItem() instanceof TridentItem ||
                heldItem.getItem() instanceof FishingRodItem ||
                heldItem.getItem() instanceof CrossbowItem
        ) return ActionResult.PASS;

        if (getWorld().isClient) {
            return ActionResult.PASS;
        }
        player.openHandledScreen(this);
        return ActionResult.SUCCESS;
    }
    @Override
    protected void dropInventory() {
        for (int i = 0; i < inventory.size(); i++) {
            if (!inventory.get(i).isEmpty()) {
                ItemEntity itemEntity = new ItemEntity(getWorld(), getX(), getY(), getZ(), inventory.get(i));
                getWorld().spawnEntity(itemEntity);
            }
        }
    }

    public void addInventoryStack(ItemStack stack) {
        if (stack == null || stack.isEmpty() || stack.getItem().equals(Items.AIR)) return;

        for (int i = 0; i < MAX_SIZE; i++) {
            if (inventory.get(i).isEmpty()) {
                setStack(i, stack);
                break;
            }
        }
    }

    @Override
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public void resetInventory() {
        this.inventory = DefaultedList.ofSize(MAX_SIZE, ItemStack.EMPTY);
    }

    @Override
    public int size() {
        return MAX_SIZE;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return GenericContainerScreenHandler.createGeneric9x6(syncId, inv, this);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return DefaultedList.ofSize(4, ItemStack.EMPTY);
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    //region// No clue //
    @Override
    public @Nullable Identifier getLootTableId() {
        return null;
    }

    @Override
    public void setLootTableId(@Nullable Identifier lootTableId) {
    }

    @Override
    public long getLootTableSeed() {
        return 0;
    }

    @Override
    public void setLootTableSeed(long lootTableSeed) {
    }

    @Override
    public void markDirty() {
    }
    //endregion
}
