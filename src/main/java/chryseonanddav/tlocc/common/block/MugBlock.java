package chryseonanddav.tlocc.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class MugBlock extends Block {
    private static final VoxelShape SHAPE = createCuboidShape(0, 0, 0, 10, 7, 10);
    public static final BooleanProperty FILLED = BooleanProperty.of("filled");
    private static final String LIQUID_KEY = "Liquid";

    public MugBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(FILLED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FILLED);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getStackInHand(hand);

        if (!state.get(FILLED)) {
            String liquid = null;

            if (heldItem.isOf(Items.WATER_BUCKET)) {
                liquid = "water";
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.PLAYERS, 1.0F, 1.0F);
                player.setStackInHand(hand, new ItemStack(Items.BUCKET));
            } else if (heldItem.isOf(Items.MILK_BUCKET)) {
                liquid = "milk";
                world.playSound(null, pos, SoundEvents.ENTITY_COW_MILK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                player.setStackInHand(hand, new ItemStack(Items.BUCKET));
            } else if (heldItem.isOf(Items.HONEY_BOTTLE)) {
                liquid = "honey";
                world.playSound(null, pos, SoundEvents.ITEM_HONEY_BOTTLE_DRINK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                heldItem.decrement(1);
            } else if (heldItem.isOf(Items.POTION)) {
                liquid = "potion:" + Registries.POTION.getId(PotionUtil.getPotion(heldItem)).toString();
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS, 1.0F, 1.0F);
                heldItem.decrement(1);
            }

            if (liquid != null) {
                BlockState newState = state.with(FILLED, true);
                world.setBlockState(pos, newState);
                setLiquid(world, pos, liquid);
                return ActionResult.SUCCESS;
            }
        } else {
            if (!world.isClient) {
                String liquid = getLiquid(world, pos);

                if ("water".equals(liquid)) {
                    player.playSound(SoundEvents.ENTITY_GENERIC_DRINK, 1.0F, 1.0F);
                } else if ("milk".equals(liquid)) {
                    if (player instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity) player).clearStatusEffects();
                    }
                    player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                } else if ("honey".equals(liquid)) {
                    player.removeStatusEffect(StatusEffects.POISON);
                    player.playSound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK, 1.0F, 1.0F);
                } else if (liquid.startsWith("potion:")) {
                    String potionId = liquid.substring(7);
                    Potion potion = Registries.POTION.get(new Identifier(potionId));

                    if (potion != null) {
                        for (StatusEffectInstance effect : potion.getEffects()) {
                            player.addStatusEffect(new StatusEffectInstance(effect));
                        }
                    }
                    player.playSound(SoundEvents.ENTITY_GENERIC_DRINK, 1.0F, 1.0F);
                }

                world.setBlockState(pos, state.with(FILLED, false));
                clearLiquid(world, pos);
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    private void setLiquid(World world, BlockPos pos, String liquid) {
        NbtCompound nbt = new NbtCompound();
        nbt.putString(LIQUID_KEY, liquid);
        world.getBlockEntity(pos).readNbt(nbt);
    }

    private String getLiquid(World world, BlockPos pos) {
        NbtCompound nbt = world.getBlockEntity(pos).createNbt();
        return (nbt != null) ? nbt.getString(LIQUID_KEY) : null;
    }

    private void clearLiquid(World world, BlockPos pos) {
        NbtCompound nbt = new NbtCompound();
        nbt.remove(LIQUID_KEY);
        world.getBlockEntity(pos).readNbt(nbt);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
