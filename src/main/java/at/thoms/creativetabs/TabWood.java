package at.thoms.creativetabs;

import at.thoms.Carpentercraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TabWood extends CreativeTabs{

	
	public TabWood(){
		super(Carpentercraft.MODID);
		setBackgroundImageName("item_search.png");
	}
	
	@Override
	public Item getTabIconItem() {
		return Carpentercraft.woodTabIcon;
	}
	
	@Override
	public boolean hasSearchBar() {
		return true;
	}
	
	
	
}
