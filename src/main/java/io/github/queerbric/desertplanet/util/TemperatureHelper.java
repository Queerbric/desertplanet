package io.github.queerbric.desertplanet.util;

public class TemperatureHelper {
	public static double decreaseFromWaterBottle(double temperature) {
		// Desmos: \frac{x}{5}-9\left\{0\le x\le100\right\}
		// Reduces temperature of player proportional to their body temperature, if it's above 45.
		double reduction = (temperature / 5.0) - 9.0;
		return temperature - Math.max(0, reduction);
	}

	public static double increaseFromSunlight(double temperature, double strength) {
		// Desmos: -\frac{x}{30}+\frac{10}{3}\left\{0\le x\le100\right\}
		// Temperature gained by sunlight slowly diminishes over time to give the players some more leeway with heat exhaustion
		double increase = -(temperature / 30.0) + (10 / 3.0);
		return temperature + increase * strength;
	}

	public static double handleShadeInDay(double temperature, double strength) {
		// Desmos: -\frac{x}{20}+3\left\{0\le x\le100\right\}
		// Reduces temperature if above 60, increases if below 60.
		double delta = -(temperature / 20.0) + 3.0;
		return temperature + delta * strength;
	}

	public static double decreaseFromNight(double temperature, double strength) {
		// Desmos: \frac{x}{20}\left\{0\le x\le100\right\}
		// Higher temperatures decrease faster
		double reduction = temperature / 20.0;
		return temperature - reduction * strength;
	}

	public static double handleShadeInNight(double temperature, double strength) {
		// Desmos: -\frac{x}{15}+2.5\left\{0\le x\le100\right\}
		// Reduces temperature if above 37.5, increases if below 37.5.
		double delta = -(temperature / 15.0) + 2.5;
		return temperature + delta * strength;
	}
}
