package at.thoms.container;

import at.thoms.tileentitys.TileEntityextracrafting;
import at.thoms.tileentitys.TileEntitytreebreeder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class containertreebreeder extends Container {
	

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	public containertreebreeder(InventoryPlayer playerInv, final TileEntitytreebreeder pedestal) {
		IItemHandler inventory = pedestal.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		addSlotToContainer(new SlotFurnaceFuel(playerInv, 3, 56, 52));
		addSlotToContainer(new SlotItemHandler(inventory, 0, 46, 16));
		addSlotToContainer(new SlotItemHandler(inventory, 1, 63, 16) {
			
			@Override
			public void onSlotChanged() {
				pedestal.markDirty();
			}
		});
	
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
	
		for (int k = 0; k < 9; k++) {
			addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
		}
	}

}