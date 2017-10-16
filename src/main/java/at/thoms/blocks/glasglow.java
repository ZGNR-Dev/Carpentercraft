package at.thoms.blocks;

import at.thoms.blocks.blocktypes.blockbasic;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class glasglow extends blockbasic{

	public glasglow() {
		super("glasglow", Material.GLASS);
			this.setCreativeTab(CreativeTabs.DECORATIONS);
			this.setHardness(0.5F);
			this.setLightLevel(33.0F);
			this.setLightOpacity(15);
		
	}

}
