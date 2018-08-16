package at.thoms.container;

import at.thoms.recipes.Recipestreebreeder;
import at.thoms.slots.Slotbeefuel;
import at.thoms.slots.SlottreebreederOutput;
import at.thoms.tileentitys.TileEntitytreebreeder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class containertreebreeder extends Container
{
	private final TileEntitytreebreeder tileentity;
	private int cookTime, totalCookTime, burnTime, currentBurnTime;
	
	public containertreebreeder(InventoryPlayer player, TileEntitytreebreeder tileentity) 
	{
		this.tileentity = tileentity;
		
		this.addSlotToContainer(new Slot(tileentity, 0, 26, 11));
		this.addSlotToContainer(new Slot(tileentity, 1, 26, 59));
		this.addSlotToContainer(new Slotbeefuel(tileentity, 2, 7, 35));
		this.addSlotToContainer(new SlottreebreederOutput(player.player, tileentity, 3, 81, 36));
		
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
			}
		}
		
		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public void addListener(IContainerListener listener) 
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileentity);
	}
	
	@Override
	public void detectAndSendChanges() 
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); ++i) 
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			
//			if(this.cookTime != this.tileentity.getField(2)) listener.sendWindowProperty(this, 2, this.tileentity.getField(2));
//			if(this.burnTime != this.tileentity.getField(0)) listener.sendWindowProperty(this, 0, this.tileentity.getField(0));
//			if(this.currentBurnTime != this.tileentity.getField(1)) listener.sendWindowProperty(this, 1, this.tileentity.getField(1));
//			if(this.totalCookTime != this.tileentity.getField(3)) listener.sendWindowProperty(this, 3, this.tileentity.getField(3));
		}
		
		this.cookTime = this.tileentity.getField(2);
		this.burnTime = this.tileentity.getField(0);
		this.currentBurnTime = this.tileentity.getField(1);
		this.totalCookTime = this.tileentity.getField(3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) 
	{
		this.tileentity.setField(id, data);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) 
	{
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) 
	{
		ItemStack stack = null;
		Slot slot = (Slot)this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) 
		{
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if(index == 3) 
			{
				if(!this.mergeItemStack(stack1, 4, 40, true)) return null;
				slot.onSlotChange(stack1, stack);
			}
			else if(index != 2 && index != 1 && index != 0) 
			{		
				Slot slot1 = (Slot)this.inventorySlots.get(index + 1);
				
//				if(!Recipestreebreeder.getInstance().getSinteringResult(stack1, slot1.getStack()).isEmpty())
				if(!Recipestreebreeder.getInstance().getSinteringResult(stack1, slot1.getStack()).equals(null))
				{
					if(!this.mergeItemStack(stack1, 0, 2, false)) 
					{
						return null;
					}
					else if(TileEntitytreebreeder.isItemBee(stack1))
					{
						if(!this.mergeItemStack(stack1, 2, 3, false)) return null;
					}
					else if(TileEntitytreebreeder.isItemBee(stack1))
					{
						if(!this.mergeItemStack(stack1, 2, 3, false)) return null;
					}
					else if(TileEntitytreebreeder.isItemBee(stack1))
					{
						if(!this.mergeItemStack(stack1, 2, 3, false)) return null;
					}
					else if(index >= 4 && index < 31)
					{
						if(!this.mergeItemStack(stack1, 31, 40, false)) return null;
					}
					else if(index >= 31 && index < 40 && !this.mergeItemStack(stack1, 4, 31, false))
					{
						return null;
					}
				}
			} 
			else if(!this.mergeItemStack(stack1, 4, 40, false)) 
			{
				return null;
			}

			if(stack1 != null)
			{
				slot.onSlotChanged();

			}
			if(stack1.stackSize == stack.stackSize) return null;
			slot.onPickupFromSlot(playerIn, stack1);
		}
		return stack;
	}
}