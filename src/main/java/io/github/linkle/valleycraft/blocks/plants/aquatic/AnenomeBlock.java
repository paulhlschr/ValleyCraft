package io.github.linkle.valleycraft.blocks.plants.aquatic;

import io.github.linkle.valleycraft.blocks.HorizontalWithWaterBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

public class AnenomeBlock extends HorizontalWithWaterBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 13, 14);

    public AnenomeBlock() {
        super(FabricBlockSettings.of(Material.UNDERWATER_PLANT)
                .nonOpaque()
                .breakByHand(false)
                .sounds(BlockSoundGroup.CALCITE)
                .strength(0, 0.1f)
                .ticksRandomly()
                .noCollision());
        setDefaultState();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(25) == 0) {
            int num = 5;

            for (var blockPos : BlockPos.iterate(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
                if (world.getBlockState(blockPos).isOf(this) && --num <= 0) {
                    return;
                }
            }

            var blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

            for (int i = 0; i < 4; ++i) {
                if (world.getBlockState(blockPos).isOf(Blocks.WATER) && state.canPlaceAt(world, blockPos)) {
                    pos = blockPos;
                }

                blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
            }

            if (world.getBlockState(blockPos).isOf(Blocks.WATER) && state.canPlaceAt(world, blockPos)) {
                world.setBlockState(blockPos, state.with(FACING, Direction.fromHorizontal(random.nextInt(4))), Block.NOTIFY_LISTENERS);
            }
        }
    }
    
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        var blockPos = pos.down();
        var blockState = world.getBlockState(blockPos);
        return blockState.isFullCube(world, blockPos);
    }
}