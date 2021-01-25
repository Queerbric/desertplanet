package io.github.queerbric.desertplanet.item;

import java.text.DecimalFormat;
import java.util.List;

import io.github.queerbric.desertplanet.DesertPlanet;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ThermometerItem extends Item {
	private static final DecimalFormat FORMAT = new DecimalFormat("##.#");

	public ThermometerItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!world.isClient()) {
			double temperature = DesertPlanet.TEMPERATURE.get(user).getTemperature();

			user.sendMessage(new TranslatableText("message.thermometer", FORMAT.format(temperature)), true);
		}

		return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText("tooltip.thermometer").formatted(Formatting.GRAY, Formatting.ITALIC));
	}
}
