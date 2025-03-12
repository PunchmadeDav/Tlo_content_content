package chryseonanddav.tlocc.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class BrazierBlock extends CampfireBlock {
    public static final VoxelShape SHAPE = createCuboidShape(1, 0, 1, 15, 9, 15);
    public static final BooleanProperty HANGING = BooleanProperty.of("hanging");

    public BrazierBlock(Settings settings) {
        super(false, 1, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HANGING, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HANGING);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN) {
            return state.with(HANGING, true);
        }
        return state;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.damage(world.getDamageSources().inFire(), 1.0F);
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5,
                0.0, 0.07, 0.0);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CampfireBlockEntity(pos, state);
    }
}
