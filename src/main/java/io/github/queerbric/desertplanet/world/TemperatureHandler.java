package io.github.queerbric.desertplanet.world;

import io.github.queerbric.desertplanet.DesertPlanet;
import io.github.queerbric.desertplanet.component.TemperatureComponent;
import io.github.queerbric.desertplanet.util.TemperatureHelper;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class TemperatureHandler {
	public static void tick(ServerWorld world) {
		for (ServerPlayerEntity player : world.getPlayers()) {
			TemperatureComponent component = DesertPlanet.TEMPERATURE.get(player);

			if (findSun(world, player.getBlockPos())) {
				double temperature = component.getTemperature();
				component.setTemperature(TemperatureHelper.increaseFromSunlight(temperature));
			} else {
				double temperature = component.getTemperature();
				component.setTemperature(TemperatureHelper.decreaseFromShade(temperature));
			}
		}
	}

	public static boolean findSun(ServerWorld world, BlockPos start) {
		BlockPos.Mutable mutable = start.mutableCopy();
		int x = start.getX();
		int z = start.getZ();

		for (int y = start.getY() + 1; y <= 256; y++) {
			if (world.getBlockState(mutable.set(x, y, z)).isOpaque()) {
				return false;
			}
		}

		return true;
	}
}
