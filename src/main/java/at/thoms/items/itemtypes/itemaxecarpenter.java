package at.thoms.items.itemtypes;

import net.minecraft.item.Item;

public class itemaxecarpenter extends Item {
	
	public itemaxecarpenter (String name) {
		setUnlocalizedName(name);
	}

	public String getSimpleName() {
		return getUnlocalizedName().substring(5);
	}

}