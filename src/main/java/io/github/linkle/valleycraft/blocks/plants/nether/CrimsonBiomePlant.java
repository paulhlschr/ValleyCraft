package io.github.linkle.valleycraft.blocks.plants.nether;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class CrimsonBiomePlant extends PlantBlock {
    private static final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 12, 13);

    public CrimsonBiomePlant() {
        super(FabricBlockSettings.of(Material.NETHER_SHOOTS)
                .nonOpaque()
                .breakByHand(true)
                .sounds(BlockSoundGroup.NYLIUM)
                .strength(0.4f, 0.2f));
    }

    public CrimsonBiomePlant(Settings settings) {
        super(settings);
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        Block block = floor.getBlock();
        return  block == Blocks.NETHERRACK ||
                block == Blocks.CRIMSON_NYLIUM;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}