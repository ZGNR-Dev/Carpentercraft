package at.thoms.blocks;

import at.thoms.blocks.blocktypes.blockbasic;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class orefakegold extends blockbasic{

	public orefakegold() {
		super("orefakegold", Material.ROCK);
			this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
			this.setHardness(2.0F);
		
	}

}
