package io.github.queerbric.desertplanet.util;

public class TemperatureHelper {
	public static double decreaseFromWaterBottle(double temperature) {
		// Desmos: \frac{x}{15}-3\left\{0\le x\le100\right\}
		// Reduces temperature of player proportional to their body temperature, if it's above 45.
		double reduction = (temperature / 15.0) - 3.0;
		return temperature - Math.max(0, reduction);
	}

	public static double increaseFromSunlight(double temperature) {
		// Desmos: -\frac{x}{30}+\frac{10}{3}\left\{0\le x\le100\right\}
		// Temperature gained by sunlight slowly diminishes over time to give the players some more leeway with heat exhaustion
		double increase = -(temperature / 30.0) + (10 / 3.0);
		return temperature + increase;
	}

	public static double decreaseFromShade(double temperature) {
		// Desmos: \frac{x}{25}-2.5\left\{0\le x\le100\right\}
		// Decreases temperature from shade, down to 62.5.
		double reduction = (temperature / 25.0) - 2.5;
		return temperature - Math.max(0, reduction);
	}
}
