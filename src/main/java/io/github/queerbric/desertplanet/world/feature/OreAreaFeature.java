package io.github.queerbric.desertplanet.world.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class OreAreaFeature extends Feature<DefaultFeatureConfig> {
	private static final WeightedList<BlockState> STATES = new WeightedList<BlockState>()
			.add(Blocks.IRON_ORE.getDefaultState(), 1);

	public OreAreaFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
		BlockState oreState = STATES.pickRandom(random);

		int oreCount = 20 + random.nextInt(30);

		int topY = 0;
		for (int i = 0; i < oreCount; i++) {
			int x = random.nextInt(16) - random.nextInt(16) + pos.getX();
			int z = random.nextInt(16) - random.nextInt(16) + pos.getZ();
			int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z) - 1;

			// Update max possible height
			topY = Math.max(y, topY);

			world.setBlockState(new BlockPos(x, y, z), oreState, 3);
		}

		for (int y = topY; y > 1; y--) {
			double rawCount = countAt(y);
			double count = MathHelper.lerp(random.nextDouble(), rawCount * 0.5, rawCount * 1.5);

			for (int i = 0; i < count; i++) {
				int x = random.nextInt(16) - random.nextInt(16) + pos.getX();
				int z = random.nextInt(16) - random.nextInt(16) + pos.getZ();

				BlockPos local = new BlockPos(x, y, z);

				if (world.getBlockState(local).isOpaque()) {
					world.setBlockState(local, oreState, 3);
				}
			}
		}

		return true;
	}

	// Desmos: \frac{500}{x+15}
	private static double countAt(int y) {
		return 500.0 / (y + 15);
	}
}
