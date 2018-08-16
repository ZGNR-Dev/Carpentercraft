package at.thoms.blocks;

import at.thoms.blocks.blocktypes.blockbasic;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class glasshardened extends blockbasic{

	public glasshardened() {
		super("glasshardened", Material.GLASS);
			this.setCreativeTab(CreativeTabs.DECORATIONS);
			this.setHardness(3.5F);
			this.setLightLevel(33.0F);
			this.setLightOpacity(15);
		
	}
	
	/**
	 * Say the texture is transparent
	 */
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
}
