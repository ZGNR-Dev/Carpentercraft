package at.thoms.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import at.thoms.Carpentercraft;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.tileentitys.TileEntityblockcreator;

import javax.annotation.Nullable;

public class blockcreator extends blockprimitivemachine<TileEntityblockcreator> {

	public blockcreator() {
		super("blockcreator", Material.ROCK);
		this.setCreativeTab(Carpentercraft.Tabbasic);
		this.setHardness(4.0F);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
				player.openGui(Carpentercraft.instance, guihandler.blockcreator, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}	
	
	
	@Override
	public Class<TileEntityblockcreator> getTileEntityClass() {
		return TileEntityblockcreator.class;
	}
	
	@Nullable
	@Override
	public TileEntityblockcreator createTileEntity(World world, IBlockState state) {
		return new TileEntityblockcreator();
	}

	
	
}