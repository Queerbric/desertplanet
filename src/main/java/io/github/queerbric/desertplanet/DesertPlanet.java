package io.github.queerbric.desertplanet;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import io.github.queerbric.desertplanet.component.TemperatureComponent;
import io.github.queerbric.desertplanet.item.DesertPlanetItems;
import io.github.queerbric.desertplanet.world.DesertPlanetGeneratorType;
import io.github.queerbric.desertplanet.world.gen.DesertChunkGenerator;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class DesertPlanet implements ModInitializer {
	public static final ComponentKey<TemperatureComponent> TEMPERATURE = ComponentRegistry.getOrCreate(id("temperature"), TemperatureComponent.class);
	private static DesertPlanetGeneratorType generatorType;
	@Override
	public void onInitialize() {
		DesertPlanetItems.init();

		Registry.register(Registry.CHUNK_GENERATOR, id("desertplanet"), DesertChunkGenerator.CODEC);

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			generatorType = new DesertPlanetGeneratorType();
		}
	}

	public static Identifier id(String path) {
		return new Identifier("desertplanet", path);
	}
}
