package io.github.queerbric.desertplanet.world.gen;

import java.util.Random;
import java.util.stream.IntStream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.queerbric.desertplanet.world.feature.DesertPlanetFeatures;
import io.github.queerbric.desertplanet.world.noise.OpenSimplexNoise;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.FeatureConfig;

public class DesertChunkGenerator extends ChunkGenerator {
	public static final Codec<DesertChunkGenerator> CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
					BiomeSource.CODEC.fieldOf("biome_source").forGetter(generator -> generator.biomeSource),
					Codec.LONG.fieldOf("seed").stable().forGetter(generator -> generator.seed))
				.apply(instance, instance.stable(DesertChunkGenerator::new)));
	private static final BlockState STONE = Blocks.STONE.getDefaultState();
	private static final BlockState WATER = Blocks.WATER.getDefaultState();

	private final OctaveSimplexNoiseSampler surfaceDepthNoise;
	private final OpenSimplexNoise craterSampler;
	private final OpenSimplexNoise duneSampler;
	private final OpenSimplexNoise duneHeightSampler;
	private final OpenSimplexNoise warpX;
	private final OpenSimplexNoise warpZ;

	private final BiomeSource biomeSource;
	private final long seed;

	public DesertChunkGenerator(BiomeSource biomeSource, long seed) {
		super(biomeSource, new StructuresConfig(true));
		this.biomeSource = biomeSource;
		this.seed = seed;

		ChunkRandom random = new ChunkRandom(seed);

		this.surfaceDepthNoise = new OctaveSimplexNoiseSampler(random, IntStream.rangeClosed(-3, 0));
		this.duneSampler = new OpenSimplexNoise(random.nextLong());
		this.duneHeightSampler = new OpenSimplexNoise(random.nextLong());
		this.craterSampler = new OpenSimplexNoise(random.nextLong());
		this.warpX = new OpenSimplexNoise(random.nextLong());
		this.warpZ = new OpenSimplexNoise(random.nextLong());
	}

	@Override
	protected Codec<? extends ChunkGenerator> getCodec() {
		return CODEC;
	}

	@Override
	public ChunkGenerator withSeed(long seed) {
		return new DesertChunkGenerator(biomeSource.withSeed(seed), seed);
	}

	@Override
	public void buildSurface(ChunkRegion region, Chunk chunk) {
		ChunkPos chunkPos = chunk.getPos();
		int chunkX = chunkPos.x;
		int chunkZ = chunkPos.z;
		ChunkRandom chunkRandom = new ChunkRandom();
		chunkRandom.setTerrainSeed(chunkX, chunkZ);
		int startX = chunkPos.getStartX();
		int startZ = chunkPos.getStartZ();
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for(int x = 0; x < 16; ++x) {
			for(int z = 0; z < 16; ++z) {
				chunk.setBlockState(mutable.set(startX + x, 0, startZ + z), Blocks.BEDROCK.getDefaultState(), false);

				int localX = startX + x;
				int localZ = startZ + z;
				int height = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, x, z);
				for (int y = height; y > 0; y--) {
					BlockState state = Blocks.STONE.getDefaultState();

					if (y >= 60) {
						state = Blocks.SAND.getDefaultState();
					} else if (y >= 50) {
						state = Blocks.SANDSTONE.getDefaultState();
					}

					region.setBlockState(mutable.set(localX, y, localZ), state, 3);
				}
			}
		}
	}

	@Override
	public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		ChunkPos pos = chunk.getPos();
		int startX = pos.getStartX();
		int startZ = pos.getStartZ();

		for (int x = startX; x < startX + 16; x++) {
		    for (int z = startZ; z < startZ + 16; z++) {
		    	int height = (int) (70 + getHeight(x, z));

				for (int y = 0; y < height; y++) {
					chunk.setBlockState(mutable.set(x, y, z), Blocks.STONE.getDefaultState(), false);
				}
		    }
		}
	}

	private double getHeight(int x, int z) {
		double localX = x / 75.0;
		double localZ = z / 75.0;

		double radiusSq = localX * localX + localZ * localZ;
		double noise = this.craterSampler.eval(x / 20.0, z / 20.0) * 0.25;

		radiusSq += noise;

		if (radiusSq <= 1) {
			return (1.75 * (radiusSq * radiusSq) - 0.75) * 12;
		} else {
			double craterDropOff = 12.0 / (radiusSq * radiusSq);

			double duneHeight = (this.duneHeightSampler.eval(x / 50.0, z / 50.0) + 1) / 2;
			double dune = (1 - Math.abs(this.duneSampler.eval((x + this.warpX.eval(x / 15.0, z / 15.0)) / 60.0, (z + this.warpZ.eval(x / 15.0, z / 15.0)) / 60.0))) * (6 + duneHeight * 14) - 3;
			// TODO: sign based falloff so one side is gentler than the other simulating wind formations

			return Math.max(craterDropOff, dune);
		}
	}

	@Override
	public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {

	}

	@Override
	public void generateFeatures(ChunkRegion world, StructureAccessor accessor) {
		ChunkRandom random = new ChunkRandom();
		ChunkPos pos = new ChunkPos(world.getCenterChunkX(), world.getCenterChunkZ());
		random.setPopulationSeed(world.getSeed(), pos.x, pos.z);

		if (random.nextInt(64) == 0) {
			if (Math.abs(pos.x) > 8 || Math.abs(pos.z) > 8) {
				int x = random.nextInt(16) + pos.x * 16;
				int z = random.nextInt(16) + pos.z * 16;
				int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
				DesertPlanetFeatures.ORE_AREA.generate(world, this, random, new BlockPos(x, y, z), FeatureConfig.DEFAULT);
			}
		}
	}

	@Override
	public void setStructureStarts(DynamicRegistryManager dynamicRegistryManager, StructureAccessor structureAccessor, Chunk chunk, StructureManager structureManager, long worldSeed) {
		// No structures
		// TODO: this breaks strongholds
	}

	// TODO: implement these once we have structures

	@Override
	public int getHeight(int x, int z, Heightmap.Type heightmapType) {
		return 0;
	}

	@Override
	public BlockView getColumnSample(int x, int z) {
		return null;
	}
}
