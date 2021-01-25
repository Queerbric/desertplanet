package io.github.queerbric.desertplanet.item;

import io.github.queerbric.desertplanet.DesertPlanet;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class DesertPlanetItems {
	public static Item PHOTO;
	public static Item TEMPERATURE;

	public static void init() {
		PHOTO = Registry.register(Registry.ITEM, DesertPlanet.id("photo"), new PhotoItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.EPIC)));
		TEMPERATURE = Registry.register(Registry.ITEM, DesertPlanet.id("thermometer"), new ThermometerItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1)));
	}
}
