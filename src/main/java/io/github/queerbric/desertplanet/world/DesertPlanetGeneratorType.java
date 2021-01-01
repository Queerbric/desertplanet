package io.github.queerbric.desertplanet.world;

import io.github.queerbric.desertplanet.world.gen.DesertChunkGenerator;

import net.minecraft.client.world.GeneratorType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

public class DesertPlanetGeneratorType extends GeneratorType {
	public DesertPlanetGeneratorType() {
		super("desertplanet");
		GeneratorType.VALUES.add(this);
	}

	@Override
	protected ChunkGenerator getChunkGenerator(Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed) {
		return new DesertChunkGenerator(new FixedBiomeSource(biomeRegistry.get(BiomeKeys.DESERT)), seed);
	}
}
