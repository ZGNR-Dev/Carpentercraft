package at.thoms.items;

import at.thoms.items.itemtypes.itembasic;
import at.thoms.utils.IBeePower;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class bee extends itembasic implements IBeePower{

	public bee() {
		super("bee");
			setCreativeTab(CreativeTabs.MISC);
			setRegistryName("bee");
	}

	@Override
	public int getBeeBurnTime(ItemStack fuel) {
		return 800;
	}
	
}
