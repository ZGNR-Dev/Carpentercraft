/* package at.thoms.zold;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class extracraftingmanager implements IRecipe{
	public Item itemIn;
	public int amount;
	public int size;

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return null;
	}

	@Override
	public int getRecipeSize() {
		return size;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(itemIn, amount);
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return null;
	}

}
 */