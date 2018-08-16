package at.thoms.container;

import at.thoms.blocks.chestcompressed;
import at.thoms.tileentitys.TileEntitychestcompressed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class containerchestcompressed extends Container {

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = null;
		Slot slot = inventorySlots.get(index);
	
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
	
			int containerSlots = inventorySlots.size() - player.inventory.mainInventory.length;
	
			if (index < containerSlots) {
				if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
				return null;
			}
	
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
	
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
	
			slot.onPickupFromSlot(player, itemstack1);
		}
	
		return itemstack;
	}
 
	public containerchestcompressed(InventoryPlayer playerInv, final TileEntitychestcompressed chestcompressed){
		IItemHandler inventory = chestcompressed.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		

		/* for(int u = 0; u < 6; u++){
			for(int o = 0; 0 < 9; o++){
				System.out.println("Es passiert etwas" + u + 0 * 9);
				addSlotToContainer(new SlotItemHandler(inventory, u + o * 9 + 36, 8 + u * 18, 18 + o * 18){
					@Override
					public void onSlotChanged() {
						chestcompressed.markDirty();
			}
					
		});
				
						if(u == 6){
				break;
			}
	
			} */
			
	
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
	
		for (int k = 0; k < 9; k++) {
			addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
		}
		
		addSlotToContainer(new SlotItemHandler(inventory, 37, 8, 18){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 38, 26, 18){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 39, 44, 18){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 40, 62, 18){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 41, 80, 18){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 42, 98, 18){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 43, 116, 18){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 44, 134, 18){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 45, 152, 18){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});

		addSlotToContainer(new SlotItemHandler(inventory, 37, 8, 36){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 38, 26, 36){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 39, 44, 36){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 40, 62, 36){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 41, 80, 36){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 42, 98, 36){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 43, 116, 36){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 44, 134, 36){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 45, 152, 36){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});

		addSlotToContainer(new SlotItemHandler(inventory, 46, 8, 54){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 47, 26, 54){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 48, 44, 54){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 49, 62, 54){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 50, 80, 54){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 51, 98, 54){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 52, 116, 54){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 53, 134, 54){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 53, 152, 54){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		
		addSlotToContainer(new SlotItemHandler(inventory, 54, 8, 72){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 55, 26, 72){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 56, 44, 72){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 57, 62, 72){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 58, 80, 72){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 59, 98, 72){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 60, 116, 72){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 61, 134, 72){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 62, 152, 72){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});

		addSlotToContainer(new SlotItemHandler(inventory, 63, 8, 90){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 64, 26, 90){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 65, 44, 90){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 66, 62, 90){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 67, 80, 90){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 68, 98, 90){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 69, 116, 90){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 70, 134, 90){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 71, 152, 90){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});

		addSlotToContainer(new SlotItemHandler(inventory, 72, 8, 108){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 73, 26, 108){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 74, 44, 108){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 75, 62, 108){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 76, 80, 108){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 77, 98, 108){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 78, 116, 108){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 79, 134, 108){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});
		addSlotToContainer(new SlotItemHandler(inventory, 80, 152, 108){
			@Override
			public void onSlotChanged() {
				chestcompressed.markDirty();
			}
			
		});

	}
}





/*
package at.thoms.container;

import at.thoms.tileentitys.TileEntitychestcompressed;
import at.thoms.utils.slotChestCompressed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class containerchestcompressed extends Container {

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = null;
		Slot slot = inventorySlots.get(index);
	
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
	
			int containerSlots = inventorySlots.size() - player.inventory.mainInventory.length;
	
			if (index < containerSlots) {
				if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
				return null;
			}
	
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
	
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
	
			slot.onPickupFromSlot(player, itemstack1);
		}
	
		return itemstack;
	}

	public containerchestcompressed(InventoryPlayer playerInv, final TileEntitychestcompressed chestcompressed){
//	public containerchestcompressed(InventoryPlayer playerInv, final TileEntitychestcompressed chestcompressed) {
		IItemHandler inventory = chestcompressed.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		
	
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
			}
		}
	
		for (int k = 0; k < 9; ++k) {
			addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 192));
		}
		
		for (int u = 0; u < 6; ++u) {
			for(int h = 0; h < 9; ++h){
				addSlotToContainer(new SlotItemHandler(inventory, u + h * 9 + 9, 8 + u * 18, 18 + h * 18){
						@Override
						public void onSlotChanged() {
							chestcompressed.markDirty();
						}

			});
		}

	}

	}
} */