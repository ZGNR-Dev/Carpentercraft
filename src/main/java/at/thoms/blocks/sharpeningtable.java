package at.thoms.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLMessage.OpenGui;
import at.thoms.Carpentercraft;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.tileentitys.TileEntitysharpeningtable;

public class sharpeningtable extends blockprimitivemachine<TileEntitysharpeningtable> implements ITileEntityProvider{

	public sharpeningtable(){
		super("sharpeningtable", Material.ROCK);
		this.setHardness(2.0f);
		this.setResistance(6.0f);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setHarvestLevel("pickaxe", 0);
		
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			player.openGui(Carpentercraft.instance, guihandler.sharpeningtable, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	public boolean isOpaqueCube() {
		return false;
	}
	
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntitysharpeningtable tehc = (TileEntitysharpeningtable)world.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(world, pos, tehc);
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack) {
		if(stack.hasDisplayName()) {
			((TileEntitysharpeningtable)world.getTileEntity(pos)).setCustomName(stack.getDisplayName());
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitysharpeningtable();
	}

	@Override
	public Class<TileEntitysharpeningtable> getTileEntityClass() {
		return TileEntitysharpeningtable.class;
	}

	@Override
	public TileEntitysharpeningtable createTileEntity(World world, IBlockState state) {
		return new TileEntitysharpeningtable();
	}
}