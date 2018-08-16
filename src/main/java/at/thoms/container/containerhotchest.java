package at.thoms.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import at.thoms.tileentitys.TileEntityhotchest;

public class containerhotchest extends Container{
	
	private TileEntityhotchest te;
	
	public containerhotchest(IInventory playerInv, TileEntityhotchest te) {
		this.te = te;
		int index = 9;
		
//		Reference Slot
//		this.addSlotToContainer(new Slot(inventoryIn, index, xPosition, yPosition));
		
//		Tile Entity, Slot 0-26, Slot IDs 0-26

//		this.addSlotToContainer(new Slot(te, 0, 8, 17)); this.addSlotToContainer(new Slot(te, 9, 8, 35)); this.addSlotToContainer(new Slot(te, 18, 8, 53));
//		this.addSlotToContainer(new Slot(te, 1, 26, 17)); this.addSlotToContainer(new Slot(te, 10, 26, 35)); this.addSlotToContainer(new Slot(te, 19, 26, 53));
//		this.addSlotToContainer(new Slot(te, 2, 44, 17)); this.addSlotToContainer(new Slot(te, 11, 44, 35)); this.addSlotToContainer(new Slot(te, 20, 44, 53));
//		this.addSlotToContainer(new Slot(te, 3, 62, 17)); this.addSlotToContainer(new Slot(te, 12, 62, 35)); this.addSlotToContainer(new Slot(te, 21, 62, 53));
//		this.addSlotToContainer(new Slot(te, 4, 80, 17)); this.addSlotToContainer(new Slot(te, 13, 80, 35)); this.addSlotToContainer(new Slot(te, 22, 80, 53));
//		this.addSlotToContainer(new Slot(te, 5, 98, 17)); this.addSlotToContainer(new Slot(te, 14, 98, 35)); this.addSlotToContainer(new Slot(te, 23, 98, 53));
//		this.addSlotToContainer(new Slot(te, 6, 116, 17)); this.addSlotToContainer(new Slot(te, 15, 116, 35)); this.addSlotToContainer(new Slot(te, 24, 116, 53));
//		this.addSlotToContainer(new Slot(te, 7, 134, 17)); this.addSlotToContainer(new Slot(te, 16, 134, 35)); this.addSlotToContainer(new Slot(te, 25, 134, 53));
//		this.addSlotToContainer(new Slot(te, 8, 152, 17)); this.addSlotToContainer(new Slot(te, 17, 152, 35)); this.addSlotToContainer(new Slot(te, 26, 152, 53));

		this.addSlotToContainer(new Slot(te, 0, 8, 17)); 
		this.addSlotToContainer(new Slot(te, 1, 26, 17)); 
		this.addSlotToContainer(new Slot(te, 2, 44, 17)); 
		this.addSlotToContainer(new Slot(te, 3, 62, 17)); 
		this.addSlotToContainer(new Slot(te, 4, 80, 17)); 
		this.addSlotToContainer(new Slot(te, 5, 98, 17)); 
		this.addSlotToContainer(new Slot(te, 6, 116, 17)); 
		this.addSlotToContainer(new Slot(te, 7, 134, 17)); 
		this.addSlotToContainer(new Slot(te, 8, 152, 17)); 
		this.addSlotToContainer(new Slot(te, 9, 8, 35)); 
		this.addSlotToContainer(new Slot(te, 10, 26, 35));
		this.addSlotToContainer(new Slot(te, 11, 44, 35)); 
		this.addSlotToContainer(new Slot(te, 12, 62, 35)); 
		this.addSlotToContainer(new Slot(te, 13, 80, 35)); 
		this.addSlotToContainer(new Slot(te, 14, 98, 35)); 
		this.addSlotToContainer(new Slot(te, 15, 116, 35)); 
		this.addSlotToContainer(new Slot(te, 16, 134, 35)); 
		this.addSlotToContainer(new Slot(te, 17, 152, 35)); 
		this.addSlotToContainer(new Slot(te, 18, 8, 53));
		this.addSlotToContainer(new Slot(te, 19, 26, 53));
		this.addSlotToContainer(new Slot(te, 20, 44, 53));
		this.addSlotToContainer(new Slot(te, 21, 62, 53));
		this.addSlotToContainer(new Slot(te, 22, 80, 53));
		this.addSlotToContainer(new Slot(te, 23, 98, 53));
		this.addSlotToContainer(new Slot(te, 24, 116, 53));
		this.addSlotToContainer(new Slot(te, 25, 134, 53));
		this.addSlotToContainer(new Slot(te, 26, 152, 53));

		
		
	    // Player Inventory, Slot 9-35, Slot IDs 26-53
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(playerInv, index, 8 + x * 18, 84 + y * 18));
	            int counting = x + y * 9 + 26;
	            index++;
	            System.out.println("Player Inventory" + counting);

	        }
	    }

	    // Player Inventory, Slot 0-8, Slot IDs 53-63
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
            int counting = x;
            System.out.println("Player Hotbar" + counting);

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