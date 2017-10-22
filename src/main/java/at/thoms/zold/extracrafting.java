/* package at.thoms.blocks;

import at.thoms.Carpentercraft;
import at.thoms.blocks.blocktypes.blockbasic;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.tileentitys.TileEntityextracrafting;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class extracrafting extends blockbasic implements ITileEntityProvider{

	public extracrafting() {
		super("extracrafting", Material.WOOD);
			this.setCreativeTab(CreativeTabs.DECORATIONS);
			this.setHardness(2.0F);
			//setRegistryName("extracraftingblock");
		
	}

	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityextracrafting();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityextracrafting();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityextracrafting te = (TileEntityextracrafting) world.getTileEntity(pos);
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for(int slot = 0; slot < handler.getSlots(); slot++) {
			ItemStack stack = handler.getStackInSlot(slot);
			InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
		}
	}
	
		@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
		if(!worldIn.isRemote) {
			playerIn.openGui(Carpentercraft.instance, guihandler.extracrafting, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

}
*/
