package chryseonanddav.tlocc.common.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.particle.ParticleTypes;

public class BrazierV2Block extends Block {
    public static final BooleanProperty HANGING = BooleanProperty.of("hanging");
    private static final VoxelShape GROUND_SHAPE = createCuboidShape(1, 5, 1, 15, 9, 15);
    private static final VoxelShape HANGING_SHAPE = createCuboidShape(1, 5, 1, 15, 9, 15); // Adjusted for hanging

    public BrazierV2Block(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(HANGING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HANGING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction facing = ctx.getSide();
        boolean isHanging = facing == Direction.DOWN; // Hanging if placed on ceiling
        return this.getDefaultState().with(HANGING, isHanging);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(HANGING) ? HANGING_SHAPE : GROUND_SHAPE;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient) {
            entity.damage(world.getDamageSources().inFire(), 1.0F); // Deals 1 heart of fire damage
            entity.setOnFireFor(3); // Sets entity on fire for 3 seconds
            world.playSound(null, pos, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F, 1.0F);
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.isClient) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + (state.get(HANGING) ? 0.9 : 0.8); // Adjust particle height
            double z = pos.getZ() + 0.5;

            // Campfire smoke particles
            if (random.nextFloat() < 0.5F) {
                world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        x, y + 0.5, z,
                        0.0, 0.07, 0.0);
            }

            // Fire crackling sound effect
            if (random.nextFloat() < 0.2F) {
                world.playSound(x, y, z, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.3F, 1.0F, false);
            }
        }
    }
}
