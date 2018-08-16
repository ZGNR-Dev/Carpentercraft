package at.thoms.container;

import at.thoms.slots.Slotenergiegeneratorinput;
import at.thoms.tileentitys.TileEntityenergiegenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * The container for the furnace generator
 * 
 * @author CJMinecraft
 *
 */
public class containerenergiegenerator extends Container {

	/**
	 * The inventories
	 */

	private TileEntityenergiegenerator te;
	private IItemHandler handler;

	/**
	 * Initialise the container for the furnace generator
	 * 
	 * @param playerInv
	 *            The player's inventory
	 * @param te
	 *            The {@link TileEntityFurnaceGenerator} with the inventory
	 */
	public containerenergiegenerator(IInventory playerInv, TileEntityenergiegenerator te) {
		this.te = te;
		this.handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		this.addSlotToContainer(new Slotenergiegeneratorinput(handler, 0, 81, 35));

		// The player's inventory slots
		int xPos = 8; // The x position of the top left player inventory slot on
						// our texture
		int yPos = 84; // The y position of the top left player inventory slot
						// on our texture

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

	/**
	 * Allow for SHIFT click transfers
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = null;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot < this.handler.getSlots()) {
				// From the energy cell inventory to the player's inventory
				if (!this.mergeItemStack(current, this.handler.getSlots(), handler.getSlots() + 36, true))
					return null;
			} else {
				// From the player's inventory to the block breaker's inventory
				if (!this.mergeItemStack(current, 0, this.handler.getSlots(), false))
					return null;
			}

			if (current.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (current.stackSize == previous.stackSize)
				return null;
			slot.onPickupFromSlot(playerIn, current);
		}
		return previous;
	}

	/**
	 * Say we can interact with the player
	 */
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}