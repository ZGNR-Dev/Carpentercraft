package at.thoms.container;

import at.thoms.slots.SlotEnergyItem;
import at.thoms.tileentitys.TileEntityenergieStorage;
import cjminecraft.core.energy.EnergyUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class containerenergiestorage extends Container {

	private TileEntityenergieStorage te;
	private IItemHandler handler;

	public containerenergiestorage(IInventory playerInv, TileEntityenergieStorage te) {
		this.te = te;
		this.handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		//Our tile entity slots
		this.addSlotToContainer(new SlotEnergyItem(handler, 0, 44, 35));
		this.addSlotToContainer(new SlotEnergyItem(handler, 1, 116, 35));
		
		// The player's inventory slots
		int xPos = 8; // The x position of the top left player inventory slot on our texture
		int yPos = 84; // The y position of the top left player inventory slot on our texture

		// Player slots
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}

		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, xPos + x * 18, yPos + 58));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
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

}