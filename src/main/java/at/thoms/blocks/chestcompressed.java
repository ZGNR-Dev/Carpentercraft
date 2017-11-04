package at.thoms.blocks;

import javax.annotation.Nullable;

import at.thoms.Carpentercraft;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.tileentitys.TileEntitychestcompressed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class chestcompressed extends blockprimitivemachine<TileEntitychestcompressed> {

	public chestcompressed() {
		super("chestcompressed", Material.WOOD);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntitychestcompressed tile = getTileEntity(world, pos);
			if (!player.isSneaking()) {
				player.openGui(Carpentercraft.instance, guihandler.chestcompressed, world, pos.getX(), pos.getY(), pos.getZ());
				} else {
			}
		}
		return true;
	}	
	
	
	@Override
	public Class<TileEntitychestcompressed> getTileEntityClass() {
		return TileEntitychestcompressed.class;
	}
	
	@Nullable
	@Override
	public TileEntitychestcompressed createTileEntity(World world, IBlockState state) {
		return new TileEntitychestcompressed();
	}

}






