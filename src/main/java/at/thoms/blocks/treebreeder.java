package at.thoms.blocks;

import javax.annotation.Nullable;

import at.thoms.Carpentercraft;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.tileentitys.TileEntitytreebreeder;
import at.thoms.utils.ModBlocks;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class treebreeder extends blockprimitivemachine<TileEntitytreebreeder> {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool BURNING = PropertyBool.create("burning");
	
	
	public treebreeder() {
		super("treebreeder", Material.WOOD);
		this.setUnlocalizedName("treebreeder");
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntitytreebreeder tile = getTileEntity(world, pos);
			if (!player.isSneaking()) {
				player.openGui(Carpentercraft.instance, guihandler.treebreeder, world, pos.getX(), pos.getY(), pos.getZ());
				} else {
					player.addChatMessage(new TextComponentString("Sneak 100"));
			}
		}
		return true;
	}	
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, BURNING });
	}

	/**
	 * Returns the correct meta for the block I recommend also saving the
	 * EnumFacing to the meta but I haven't
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
//		ChipTypes type = (ChipTypes) state.getValue(TYPE);
		EnumFacing facing = (EnumFacing) state.getValue(FACING);
		int meta = EnumFacing.values().length + facing.ordinal();
		return meta;
	}

	/**
	 * Gets the block state from the meta
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
//		ChipTypes type = ChipTypes.values()[(int) (meta / EnumFacing.values().length) % ChipTypes.values().length];
		EnumFacing facing = EnumFacing.values()[meta % EnumFacing.values().length];
		return this.getDefaultState().withProperty(FACING, facing);
	}


	
	
	
	public static void setState(boolean active, World worldIn, BlockPos pos) 
	{
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(active) worldIn.setBlockState(pos, ModBlocks.treebreeder.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, true), 3);
		else worldIn.setBlockState(pos, ModBlocks.treebreeder.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, false), 3);
		
		if(tileentity != null) 
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
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






