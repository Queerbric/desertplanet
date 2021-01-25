package io.github.queerbric.desertplanet.item;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class PhotoItem extends Item {
	public PhotoItem(Settings settings) {
		super(settings);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText("tooltip.photo.1").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
		tooltip.add(new TranslatableText("tooltip.photo.2").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
		tooltip.add(new TranslatableText("tooltip.reduces_stress").formatted(Formatting.GOLD));
	}
}
