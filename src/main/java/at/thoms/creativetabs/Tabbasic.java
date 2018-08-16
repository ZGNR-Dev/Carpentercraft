package at.thoms.creativetabs;

import at.thoms.Carpentercraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Tabbasic extends CreativeTabs{

	
	public Tabbasic(){
		super(Carpentercraft.MODID);
		setBackgroundImageName("item_search.png");
	}
	
	@Override
	public Item getTabIconItem() {
		return Carpentercraft.goldpickaxesharpened;
	}
	
	@Override
	public boolean hasSearchBar() {
		return true;
	}
	
	
	
}
