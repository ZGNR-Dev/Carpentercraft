package at.thoms.blocks;

import at.thoms.blocks.blocktypes.blockbasic;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class sandglow extends blockbasic{

	public sandglow() {
		super("sandglow", Material.SAND);
			this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
			this.setHardness(1.0F);
			this.setLightLevel(11.0F);
		
	}

}
