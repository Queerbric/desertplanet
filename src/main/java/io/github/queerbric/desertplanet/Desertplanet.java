package io.github.queerbric.desertplanet;

import io.github.queerbric.desertplanet.world.DesertPlanetGeneratorType;
import io.github.queerbric.desertplanet.world.gen.DesertChunkGenerator;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class Desertplanet implements ModInitializer {
	private static DesertPlanetGeneratorType generatorType;
	@Override
	public void onInitialize() {
		Registry.register(Registry.CHUNK_GENERATOR, new Identifier("desertplanet", "desertplanet"), DesertChunkGenerator.CODEC);

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			generatorType = new DesertPlanetGeneratorType();
		}
	}
}
