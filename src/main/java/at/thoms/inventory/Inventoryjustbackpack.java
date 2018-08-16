package at.thoms.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class Inventoryjustbackpack extends InventoryBasic{

	private final ItemStack stack;
	
//	String title, boolean customName, int slotCount
	public Inventoryjustbackpack(ItemStack stack) {
		super("backpackzero", false, 4);
		this.stack = stack;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		readFromNBT();
		super.openInventory(player);
	}
	
	@Override
	public void closeInventory(EntityPlayer player) {
		writeToNBT();
		super.closeInventory(player);
	}
	
	@Override
	public void markDirty() {
		writeToNBT();
		super.markDirty();
	}
	
	public void writeToNBT(){
		NBTTagCompound tag = stack.getTagCompound();
		if(tag == null){
			stack.setTagCompound(tag = new NBTTagCompound());
			
		}
		
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < getSizeInventory(); i++){
			ItemStack stack = getStackInSlot(i);
			
			if(stack != null){
				NBTTagCompound slotTag = new NBTTagCompound();
				slotTag.setInteger("slotIndex", i);
				stack.writeToNBT(slotTag);
				list.appendTag(slotTag);
			}
		}
		
		tag.setTag("inventory", list);
		
	}
	
	public void readFromNBT(){
		NBTTagCompound tag = stack.getTagCompound();
		
		if(tag == null){
			writeToNBT();
			return ;
		}
		
		NBTTagList list = tag.getTagList("inventory", Constants.NBT.TAG_COMPOUND);
	
		
		if(list == null){
			writeToNBT();
			return ;
		}
		
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound slotTag = list.getCompoundTagAt(i);
			int index = slotTag.getInteger("slotInteger");
			ItemStack stack = ItemStack.loadItemStackFromNBT(slotTag);
			setInventorySlotContents(index, stack);
		}
	}
	
	
	
	
	
}
