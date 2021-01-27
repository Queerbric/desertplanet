package io.github.queerbric.desertplanet.world;

import io.github.queerbric.desertplanet.DesertPlanet;
import io.github.queerbric.desertplanet.component.TemperatureComponent;
import io.github.queerbric.desertplanet.util.TemperatureHelper;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.LightType;

public class TemperatureHandler {
	public static void tick(ServerWorld world) {
		for (ServerPlayerEntity player : world.getPlayers()) {
			TemperatureComponent component = DesertPlanet.TEMPERATURE.get(player);
			int skyLight = world.getLightLevel(LightType.SKY, player.getBlockPos());
			long time = world.getTimeOfDay();
			boolean night = false;
			if (time % 24000 > 12750) {
				night = true;
			}

			// TODO: smoother interpolation
			if (skyLight > 11) {
				double strength = (skyLight - 11) / 4.0;
				double temperature = component.getTemperature();
				if (night) {
					component.setTemperature(TemperatureHelper.decreaseFromNight(temperature, strength));
				} else {
					component.setTemperature(TemperatureHelper.increaseFromSunlight(temperature, strength));
				}

			} else {
				double strength = (12 - skyLight) / 12.0;
				double temperature = component.getTemperature();
				if (night) {
					component.setTemperature(TemperatureHelper.handleShadeInNight(temperature, strength));
				} else {
					component.setTemperature(TemperatureHelper.handleShadeInDay(temperature, strength));
				}
			}
		}
	}
}
