package at.thoms.oredict;

import at.thoms.Carpentercraft;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class extracraftingrecipes extends extracraftingmanager{
	
	@Override
	public boolean matches(InventoryCrafting craftMatrix, World worldIn) {
		return false;
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
