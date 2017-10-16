package at.thoms.blocks;

import at.thoms.blocks.blocktypes.blockbasic;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class glassglowraw extends blockbasic{

	public glassglowraw() {
		super("glassglowraw", Material.GLASS);
			this.setCreativeTab(CreativeTabs.DECORATIONS);
			this.setHardness(2.0F);
			this.setLightLevel(15.0F);
		
	}

}
