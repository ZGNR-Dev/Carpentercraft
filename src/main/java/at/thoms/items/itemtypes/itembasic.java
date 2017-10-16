package at.thoms.items.itemtypes;

import net.minecraft.item.Item;

public class itembasic extends Item{
	
	public itembasic(String name) {
		setUnlocalizedName(name);

	}
		
	public String getSimpleName() {
		return getUnlocalizedName().substring(5);
	}
}
