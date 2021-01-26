package io.github.queerbric.desertplanet.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(CauldronBlock.class)
public abstract class MixinCauldronBlock extends Block {
	@Shadow public abstract void setLevel(World world, BlockPos pos, BlockState state, int level);

	public MixinCauldronBlock(Settings settings) {
		super(settings);
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return true;
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		int level = state.get(CauldronBlock.LEVEL);
		if (level == 3) {
			return;
		}

		if (world.getBlockState(pos.up()).isIn(BlockTags.LEAVES)) {
			world.breakBlock(pos.up(), false);
			setLevel(world, pos, state, level + 1);
		}
	}
}
