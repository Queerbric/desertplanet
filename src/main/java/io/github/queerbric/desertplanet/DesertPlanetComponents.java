package io.github.queerbric.desertplanet;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import io.github.queerbric.desertplanet.component.PlayerTemperatureComponent;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;

public class DesertPlanetComponents implements EntityComponentInitializer {
	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(DesertPlanet.TEMPERATURE, PlayerTemperatureComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
	}
}
