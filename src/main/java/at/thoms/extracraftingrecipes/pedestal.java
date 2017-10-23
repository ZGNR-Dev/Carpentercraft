package at.thoms.extracraftingrecipes;

import at.thoms.Carpentercraft;
import at.thoms.oredict.extracraftingmanager;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class pedestal extends extracraftingmanager{
	
	@Override
	public boolean matches(InventoryCrafting craftMatrix, World worldIn) {
		if(craftMatrix.getSizeInventory() != 16){ return false;}
		boolean matches = false;
		for(int i = 0; 1 < craftMatrix.getSizeInventory(); i++){
			matches = craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(i).getItem() == Carpentercraft.ingotsteel;
			
		if(!matches){ break; }
		}
		return matches;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return new ItemStack(Carpentercraft.pedestal, 1);
	}

	@Override
	public int getRecipeSize() {
		return 16;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(Carpentercraft.pedestal, 1);
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return null;
	}

}
