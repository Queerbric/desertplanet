package io.github.queerbric.desertplanet.mixin;

import io.github.queerbric.desertplanet.DesertPlanet;
import io.github.queerbric.desertplanet.component.TemperatureComponent;
import io.github.queerbric.desertplanet.util.TemperatureHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.world.World;

@Mixin(PotionItem.class)
public class MixinPotionItem {

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
}
