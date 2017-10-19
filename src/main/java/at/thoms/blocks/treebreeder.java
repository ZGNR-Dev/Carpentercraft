package at.thoms.blocks;

import javax.annotation.Nullable;

import at.thoms.Carpentercraft;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.tileentitys.TileEntitytreebreeder;
import at.thoms.tileentitys.TileEntityultracrafting;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class treebreeder extends blockprimitivemachine<TileEntitytreebreeder> {

	public treebreeder() {
		super("treebreeder", Material.WOOD);
	}
	
@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
 			if (!player.isSneaking()) {
  			} else {
  				player.openGui(Carpentercraft.instance, guihandler.treebreeder, world, pos.getX(), pos.getY(), pos.getZ());
  			}
  		}
  		return true;

	}	
	@Override
	public Class<TileEntitytreebreeder> getTileEntityClass() {
		return TileEntitytreebreeder.class;
	}
	
	@Nullable
	@Override
	public TileEntitytreebreeder createTileEntity(World world, IBlockState state) {
		return new TileEntitytreebreeder();
	}

}