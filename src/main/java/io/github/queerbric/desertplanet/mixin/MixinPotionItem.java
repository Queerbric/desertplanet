package io.github.queerbric.desertplanet.mixin;

import java.util.List;

import io.github.queerbric.desertplanet.DesertPlanet;
import io.github.queerbric.desertplanet.component.TemperatureComponent;
import io.github.queerbric.desertplanet.util.TemperatureHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Mixin(PotionItem.class)
public class MixinPotionItem extends Item {
	public MixinPotionItem(Settings settings) {
		super(settings);
	}

	@Inject(method = "finishUsing", at = @At("HEAD"))
	public void handleWater(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		if (!world.isClient() && PotionUtil.getPotion(stack) == Potions.WATER && user instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) user;
			TemperatureComponent temp = DesertPlanet.TEMPERATURE.get(player);

			double temperature = temp.getTemperature();
			temp.setTemperature(TemperatureHelper.reduceFromWaterBottle(temperature));

			System.out.println(temp.getTemperature());
		}
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		if (PotionUtil.getPotion(stack) == Potions.WATER) {
			tooltip.add(new TranslatableText("tooltip.reduces_temp").formatted(Formatting.GOLD));
		} else {
			// We put this in the else statement to prevent water bottles from having "No Effects" in the tooltip
			PotionUtil.buildTooltip(stack, tooltip, 1.0F);
		}
	}
}
