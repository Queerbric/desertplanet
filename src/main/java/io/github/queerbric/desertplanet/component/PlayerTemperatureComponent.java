package io.github.queerbric.desertplanet.component;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.MathHelper;

public class PlayerTemperatureComponent implements TemperatureComponent {
	private double temperature = 50;
	public PlayerTemperatureComponent(PlayerEntity player) {

	}

	@Override
	public double getTemperature() {
		return this.temperature;
	}

	@Override
	public void setTemperature(double temperature) {
		this.temperature = MathHelper.clamp(temperature, 0, 100);
	}

	@Override
	public void readFromNbt(CompoundTag tag) {
		this.temperature = tag.getDouble("temp");
	}

	@Override
	public void writeToNbt(CompoundTag tag) {
		tag.putDouble("temp", this.temperature);
	}
}
