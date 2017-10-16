package at.thoms.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import at.thoms.tileentitys.TileEntitycookieclicker;
import at.thoms.tileentitys.TileEntityultracrafting;

import javax.annotation.Nullable;

public class cookieclicker extends blockprimitivemachine<TileEntitycookieclicker> {

	public cookieclicker() {
		super("cookieclicker", Material.ROCK);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setHardness(2.0F);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntitycookieclicker tile = getTileEntity(world, pos);
			if (side == EnumFacing.DOWN) {
				tile.decrementCount();
			} else if (side == EnumFacing.UP) {
				tile.incrementCount();
			}
			player.addChatMessage(new TextComponentString("Count: " + tile.getCount()));
		}
		return true;
	}
	
	@Override
	public Class<TileEntitycookieclicker> getTileEntityClass() {
		return TileEntitycookieclicker.class;
	}
	
	@Nullable
	@Override
	public TileEntitycookieclicker createTileEntity(World world, IBlockState state) {
		return new TileEntitycookieclicker();
	}

}