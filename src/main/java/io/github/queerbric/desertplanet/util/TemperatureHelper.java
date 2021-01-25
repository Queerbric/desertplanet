package io.github.queerbric.desertplanet.util;

public class TemperatureHelper {
	public static double reduceFromWaterBottle(double temperature) {
		// Desmos: \frac{x}{15}-3\left\{0\le x\le100\right\}
		// Reduces temperature of player proportional to their body temperature, if it's above 45.
		double reduction = (temperature / 15.0) - 3.0;
		return temperature - Math.max(0, reduction);
	}
}
