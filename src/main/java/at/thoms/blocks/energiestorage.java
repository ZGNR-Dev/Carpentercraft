package at.thoms.blocks;

import at.thoms.Carpentercraft;
import at.thoms.blocks.blocktypes.blockmachine;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.tileentitys.TileEntityenergieStorage;
import cjminecraft.core.CJCore;
import cjminecraft.core.energy.EnergyUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * The block for the energy cell
 * 
 * @author CJMinecraft
 *
 */
public class energiestorage extends blockmachine {


	/**
	 * Initialise the energy cell block
	 * 
	 * @param unlocalizedName
	 *            The unlocalized name of the energy cell
	 */
	public energiestorage(String unlocalizedName) {
		super("energiestorage", Material.ROCK);
		this.useNeighborBrightness = true;
		
		// Set the energy which is used. See BlockMachine
		setEnergy(0, 1000000, 1000, 1000);
	}

	/**
	 * For {@link TileEntity} creation
	 */

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityenergieStorage();
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityenergieStorage();
	}

	/**
	 * Drop the contents of the energy cell when broken
	 */
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityenergieStorage te = (TileEntityenergieStorage) world.getTileEntity(pos);
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (int slot = 0; slot < handler.getSlots(); slot++) {
			ItemStack stack = handler.getStackInSlot(slot);
			InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
		}
		super.breakBlock(world, pos, state);
	}

	/**
	 * Open the gui when right clicked
	 */
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
		if (!worldIn.isRemote)
			playerIn.openGui(Carpentercraft.instance, guihandler.energiestorage, worldIn, pos.getX(), pos.getY(),
					pos.getZ());
		return true;
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

	@Override
	public String getSpecialName(ItemStack stack) {
		return null;
	}

	@Override
	public Class<TileEntityenergieStorage> getTileEntityClass() {
		return TileEntityenergieStorage.class;
	}

}