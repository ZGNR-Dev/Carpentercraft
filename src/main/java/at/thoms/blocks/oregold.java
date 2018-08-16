package at.thoms.blocks;

import at.thoms.blocks.blocktypes.blockbasic;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class oregold extends blockbasic{

	public oregold() {
		super("oregold", Material.ROCK);
			this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
			this.setHardness(2.0F);
		
	}

}
