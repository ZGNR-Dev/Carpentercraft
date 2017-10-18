package at.thoms.blocks;

import at.thoms.blocks.blocktypes.blockbasic;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class woodpinelog extends blockbasic{

	public woodpinelog() {
		super("woodpinelog", Material.WOOD);
			this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
			this.setHardness(1.5F);
		
	}

}
