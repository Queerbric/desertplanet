package io.github.queerbric.desertplanet.world.feature;

import io.github.queerbric.desertplanet.DesertPlanet;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class DesertPlanetFeatures {
	public static Feature<DefaultFeatureConfig> ORE_AREA;

	public static void init() {
		ORE_AREA = Registry.register(Registry.FEATURE, DesertPlanet.id("ore_area"), new OreAreaFeature(DefaultFeatureConfig.CODEC));
	}
}
