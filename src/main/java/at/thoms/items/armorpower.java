package at.thoms.items;

import at.thoms.Carpentercraft;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class armorpower extends ItemArmor {

	public armorpower(ItemArmor.ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		
		switch(armorType) {
		case HEAD: setUnlocalizedName("armorpowerHelmet"); setRegistryName("armorpowerHelmet"); break;
		case CHEST: setUnlocalizedName("armorpowerChestplate"); setRegistryName("armorpowerChestplate"); break;
		case LEGS: setUnlocalizedName("armorpowerLeggings"); setRegistryName("armorpowerLeggings"); break;
		case FEET: setUnlocalizedName("armorpowerBoots"); setRegistryName("armorpowerBoots"); break;
		default:
			break;
		}
		
		setCreativeTab(Carpentercraft.Tabbasic);
	}
	
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if(slot == 0 || slot == 1 || slot == 3) {
			return "carpentercraft:textures/models/armor/armorpowerlayer1.png";
		} else if(slot == 2) {
			return "carpentercraft:textures/models/armor/armorpowerlayer2.png";
		} else {
			return null;
		}
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {

		if(itemStack.getItem() == Carpentercraft.armorpowerHelmet) {
		}
		
		else if(itemStack.getItem() == Carpentercraft.armorpowerChestplate) {
			if(world.isRemote) {
				if(Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()) {
					player.motionY += 0.1D;
					// Remove Line to allow Project E Gem Armor like Jetpack
					player.motionY = Math.min(1, player.motionY);
					
					player.motionX *= 1.1D;
					//Remove Line for Sanic Speed
					player.motionX = Math.min(1, player.motionX);
					
					player.motionZ *= 1.1D;
					//Remove Line for Sanic Speed
					player.motionZ = Math.min(1, player.motionX);

					world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, player.posX, player.posY, player.posZ, 0, -0.1D, 0);
				}
			} else {
				player.fallDistance = - (float) player.motionY * 4;
			}
		}
		
		else if(itemStack.getItem() == Carpentercraft.armorpowerLeggings) {
			
		}
		
		else if(itemStack.getItem() == Carpentercraft.armowpowerBoots) {
			if(!world.isRemote) {
				player.fallDistance = 0;
			}
		}
		
		
		
		
	}
		
		
	
	
}
