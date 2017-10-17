package at.thoms.items;

import at.thoms.items.itemtypes.itembasic;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class pelletcoal extends itembasic implements IFuelHandler{

	public pelletcoal() {
		super("pelletcoal");
		setCreativeTab(CreativeTabs.MATERIALS);
		setRegistryName("pelletcoal");	
	}

	@Override
	public int getBurnTime(ItemStack fuel) {
		return 100;
	}

}
