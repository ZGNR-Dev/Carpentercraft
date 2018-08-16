package at.thoms.container;

import java.util.List;

import at.thoms.slots.Slotscrapconverterinput;
import at.thoms.slots.Slotscrapconverteroutput;
import at.thoms.tileentitys.TileEntityscrapconverter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class containerscrapconverter extends Container {
  protected TileEntityscrapconverter tileEntity;
  private int burnTime;
  private int cookTime;
  
  public containerscrapconverter(InventoryPlayer inventoryPlayer, TileEntityscrapconverter tileEntity)
  {
    this.tileEntity = tileEntity;
    
    Slotscrapconverterinput input = new Slotscrapconverterinput(tileEntity, 0, 46, 17);
    SlotFurnaceFuel fuel = new SlotFurnaceFuel(tileEntity, 1, 46, 53);
    
    Slotscrapconverteroutput output = new Slotscrapconverteroutput(tileEntity, 2, 121, 35);
    
    addSlotToContainer(input);
    addSlotToContainer(fuel);
    addSlotToContainer(output);
    
    bindPlayerInventory(inventoryPlayer);
  }
  
  @Override
  public boolean canInteractWith(EntityPlayer player)
  {
    return this.tileEntity.isUsableByPlayer(player);
  }
  
  protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
  {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 9; j++) {
        addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
      }
    }
    for (int i = 0; i < 9; i++) {
      addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
    }
  }
  
  @Override
  public ItemStack transferStackInSlot(EntityPlayer player, int slot)
  {
    ItemStack stack = null;
    Slot slotObject = (Slot)this.inventorySlots.get(slot);
    if ((slotObject != null) && (slotObject.getHasStack()))
    {
      ItemStack stackInSlot = slotObject.getStack();
      stack = stackInSlot.copy();
      if (slot < 3)
      {
        if (!mergeItemStack(stackInSlot, 3, 39, true)) {
          return null;
        }
      }
      else if (!mergeItemStack(stackInSlot, 0, 2, false)) {
        return null;
      }
      if (stackInSlot.stackSize == 0) {
        slotObject.putStack(null);
      } else {
        slotObject.onSlotChanged();
      }
      if (stackInSlot.stackSize == stack.stackSize) {
        return null;
      }
      slotObject.onPickupFromSlot(player, stackInSlot);
    }
    return stack;
  }
  
  @Override
  protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean negativeDirection)
  {
    if (stack == null)
    {
      new RuntimeException("stack==null").printStackTrace();
      return false;
    }
    if (stack.getItem() == null)
    {
      new RuntimeException("stack.getItem()==null").printStackTrace();
      return false;
    }
    boolean flag1 = false;
    int k = startIndex;
    if (negativeDirection) {
      k = endIndex - 1;
    }
    if (stack.isStackable()) {
      while ((stack.stackSize > 0) && (((!negativeDirection) && (k < endIndex)) || ((negativeDirection) && (k >= startIndex))))
      {
        Slot slot = (Slot)this.inventorySlots.get(k);
        ItemStack itemstack1 = slot.getStack();
        int stackLimit = Math.min(slot.getItemStackLimit(stack), stack.getMaxStackSize());
        if ((itemstack1 != null) && (itemstack1.getItem() == stack.getItem()) && ((!stack.getHasSubtypes()) || (stack.getMetadata() == itemstack1.getMetadata())) && (ItemStack.areItemStackTagsEqual(stack, itemstack1)))
        {
          int l = itemstack1.stackSize + stack.stackSize;
          if (l <= stackLimit)
          {
            stack.stackSize = 0;
            itemstack1.stackSize = l;
            slot.onSlotChanged();
            flag1 = true;
          }
          else if (itemstack1.stackSize < stackLimit)
          {
            stack.stackSize -= stackLimit - itemstack1.stackSize;
            itemstack1.stackSize = stackLimit;
            slot.onSlotChanged();
            flag1 = true;
          }
        }
        if (negativeDirection) {
          k--;
        } else {
          k++;
        }
      }
    }
    if (stack.stackSize > 0)
    {
      if (negativeDirection) {
        k = endIndex - 1;
      } else {
        k = startIndex;
      }
      while (((!negativeDirection) && (k < endIndex)) || ((negativeDirection) && (k >= startIndex)))
      {
        Slot slot = (Slot)this.inventorySlots.get(k);
        ItemStack itemstack1 = slot.getStack();
        if ((itemstack1 == null) && (slot.isItemValid(stack)))
        {
          int stackLimit = Math.min(slot.getItemStackLimit(stack), this.tileEntity.getInventoryStackLimit());
          
          ItemStack itemStack2 = stack.copy();
          if (itemStack2.stackSize > stackLimit) {
            itemStack2.stackSize = stackLimit;
          }
          slot.putStack(itemStack2);
          slot.onSlotChanged();
          stack.stackSize -= itemStack2.stackSize;
          if (stack.stackSize == 0)
          {
            flag1 = true;
            break;
          }
        }
        if (negativeDirection) {
          k--;
        } else {
          k++;
        }
      }
    }
    return flag1;
  }
  
  @Override
  public void detectAndSendChanges()
  {
    super.detectAndSendChanges();
    for (int i = 0; i < this.listeners.size(); i++)
    {
      IContainerListener icrafting = (IContainerListener)this.listeners.get(i);
      if (this.burnTime != this.tileEntity.getBurnTime()) {
        icrafting.sendProgressBarUpdate(this, 0, this.tileEntity.getBurnTime());
      }
      if (this.cookTime != this.tileEntity.getCookTime()) {
        icrafting.sendProgressBarUpdate(this, 2, this.tileEntity.getCookTime());
      }
    }
    this.burnTime = this.tileEntity.getBurnTime();
    
    this.cookTime = this.tileEntity.getCookTime();
  }
  
//  @SideOnly(Side.CLIENT)
//  public void updateProgressBar(int id, int data)
//  {
//    this.tileEntity.updateProgressBar(id, data);
//  }
  
  public static int calcRedstoneFromTileEntityFoundry(TileEntityscrapconverter inv)
  {
    if (inv == null) {
      return 0;
    }
    float f = Math.min(inv.getContentSize(), 16.0F) / 16.0F;
    for (int j = 0; j < inv.getSizeInventory(); j++)
    {
      ItemStack itemstack = inv.getStackInSlot(j);
      if (itemstack != null) {
        f += itemstack.stackSize / Math.min(inv.getInventoryStackLimit(), itemstack.getMaxStackSize());
      }
    }
    f /= (inv.getSizeInventory() + 1);
    
    return MathHelper.ceiling_float_int(f * 15.0F);
  }
}
