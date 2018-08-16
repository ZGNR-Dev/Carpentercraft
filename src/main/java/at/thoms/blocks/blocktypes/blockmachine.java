package at.thoms.blocks.blocktypes;

import java.util.ArrayList;
import java.util.List;

import at.thoms.Carpentercraft;
import at.thoms.interfaces.IMetaBlockName;
import cjminecraft.core.energy.EnergyUtils;
import cjminecraft.core.energy.compat.forge.CustomForgeEnergyStorage;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class blockmachine<te extends TileEntity> extends blockbasic implements IMetaBlockName, ITileEntityProvider {

	protected int energy = 0;
	protected int capacity = 1000000;
	protected int maxReceive = 1000, maxExtract = 1000;


	public blockmachine(String name, Material material) {
		super(name, Material.IRON); // The blocks material
		this.setHardness(3);
		this.setResistance(20);
		this.isBlockContainer = true;
	}
	
	public abstract Class<te> getTileEntityClass();

	public blockmachine setEnergy(int energy, int capacity, int maxReceive, int maxExtract) {
		this.energy = energy;
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		return this;
	}


	/**
	 * Default method to create the tile entity. You probably want to override
	 * this
	 */
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return null;
	}

	/**
	 * Default method to create the tile entity. You probably want to override
	 * this
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}

	/* @Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> drops = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(new ItemBlockMachine(this));
		TileEntity te = world.getTileEntity(pos);
		if (te != null && EnergyUtils.hasSupport(te, null)) {
			stack = new ItemStack(new ItemBlockMachine(this), 1, world.getBlockState(pos).getValue(TYPE).getID(),
					te.getUpdateTag());
			stack.setTagCompound(te.getUpdateTag());
		}
		drops.add(stack);
		return drops;
	} */

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te,
			ItemStack stack) {
		super.harvestBlock(world, player, pos, state, te, stack);
		world.setBlockToAir(pos);
		world.removeTileEntity(pos);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		if (willHarvest)
			return true;
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		TileEntity te = world.getTileEntity(pos);
		if (te != null && EnergyUtils.hasSupport(te, null) && stack.hasTagCompound()) {
			if (EnergyUtils.getEnergySupport(te, null).getContainer(te, null) instanceof CustomForgeEnergyStorage) {
				CustomForgeEnergyStorage storage = (CustomForgeEnergyStorage) EnergyUtils.getEnergySupport(te, null)
						.getContainer(te, null);
				storage.readFromNBT(stack.getTagCompound());
				storage.setMaxReceive(maxReceive);
				storage.setMaxExtract(maxExtract);
				world.notifyBlockUpdate(pos, state, state, 2);
			}
		}
	}

}