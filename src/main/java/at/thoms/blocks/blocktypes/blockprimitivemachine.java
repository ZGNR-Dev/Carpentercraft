package at.thoms.blocks.blocktypes;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class blockprimitivemachine<te extends TileEntity> extends blockbasic {

	public blockprimitivemachine(String name, Material material) {
		super(name, material);
	}
	
	public abstract Class<te> getTileEntityClass();
	
	public te getTileEntity(IBlockAccess world, BlockPos pos) {
		return (te)world.getTileEntity(pos);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public abstract te createTileEntity(World world, IBlockState state);
	
	

}

