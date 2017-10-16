package at.thoms.blocks;

import at.thoms.blocks.blocktypes.blockbasic;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class glasshardened extends blockbasic{

	public glasshardened() {
		super("glasshardened", Material.GLASS);
			this.setCreativeTab(CreativeTabs.DECORATIONS);
			this.setHardness(3.5F);
			this.setLightLevel(33.0F);
			this.setLightOpacity(15);
		
	}

}
