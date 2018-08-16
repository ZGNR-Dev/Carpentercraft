package at.thoms.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import at.thoms.tileentitys.TileEntityjustchest;

public class containerjustchest extends Container{
	
	private TileEntityjustchest te;
	
	public containerjustchest(IInventory playerInv, TileEntityjustchest te) {
		this.te = te;
		
//		Reference Slot
//		this.addSlotToContainer(new Slot(inventoryIn, index, xPosition, yPosition));
		
		//Tile Entity, Slot 0-8, Slot IDs 0-8
		for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 3; ++x) {
	            this.addSlotToContainer(new Slot(te, x + y * 3, 62 + x * 18, 17 + y * 18));
	        }
	    }

	    // Player Inventory, Slot 9-35, Slot IDs 9-35
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
	        }
	    }

	    // Player Inventory, Slot 0-8, Slot IDs 36-44
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
	    }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.te.isUseableByPlayer(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int fromSlot) {
		ItemStack previous = null;
		Slot slot = (Slot)this.inventorySlots.get(fromSlot);
		
		if(slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();
		
			if(fromSlot < 9) {
				if(!this.mergeItemStack(current, 9, 45, true))
					return null;
			} else {
				if(!this.mergeItemStack(current, 0, 9, false))
					return null;
			}
		
			if(current.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();
			
			if(current.stackSize == previous.stackSize)
				return null;
			slot.onPickupFromSlot(player, current);
		}
		return previous;
	}
}