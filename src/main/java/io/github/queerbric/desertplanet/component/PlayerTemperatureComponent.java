package io.github.queerbric.desertplanet.component;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

public class PlayerTemperatureComponent implements TemperatureComponent {
	private double temperature = 50;
	public PlayerTemperatureComponent(PlayerEntity player) {

	}

	@Override
	public double getTemperature() {
		return this.temperature;
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
