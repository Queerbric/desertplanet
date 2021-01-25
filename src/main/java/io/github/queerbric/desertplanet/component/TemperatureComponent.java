package io.github.queerbric.desertplanet.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

public interface TemperatureComponent extends AutoSyncedComponent {
	double getTemperature();

	void setTemperature(double temperature);
}
