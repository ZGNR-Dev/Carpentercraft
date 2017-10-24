package at.thoms.extracraftingrecipes;

import at.thoms.Carpentercraft;
import at.thoms.oredict.extracraftingmanager;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class recipeultracrafting extends extracraftingmanager{
	
	@Override
	public boolean matches(InventoryCrafting craftMatrix, World worldIn) {
		if(craftMatrix.getSizeInventory() != 16){return false;}
		else{
		boolean matches = false;
		for(int i = 0; 1 < craftMatrix.getSizeInventory(); i++) {
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(1).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(2).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(3).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(4).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(5).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(6).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(7).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(8).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(9).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(10).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(11).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(12).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(13).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(14).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(15).getItem() == Carpentercraft.ingotsteel;
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(16).getItem() == Carpentercraft.ingotsteel;
			
			
			}
		}
		return false;
		
		
		
		
		 /* if(craftMatrix.getSizeInventory() != 16){ return false;}
		boolean matches = false;
		for(int i = 0; 1 < craftMatrix.getSizeInventory(); i++){
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(i).getItem() == Carpentercraft.ingotsteel;
			
		if(!matches){ break; }
		}
		return matches; */
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return new ItemStack(Carpentercraft.ultracrafting, 1);
	}

	@Override
	public int getRecipeSize() {
		return 16;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return null;
	}


}
