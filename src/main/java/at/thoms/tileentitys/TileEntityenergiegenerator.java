package at.thoms.tileentitys;

import at.thoms.blocks.energiegenerator;
import at.thoms.blocks.blocktypes.blockmachine;
import at.thoms.tileentitys.tileentitytypes.TileEntityEnergieProducer;
import at.thoms.utils.capabilities.Worker;
import at.thoms.utils.ModCapabilities;
import cjminecraft.core.energy.EnergyUnits;
import cjminecraft.core.energy.EnergyUtils;
import cjminecraft.core.energy.compat.TileEntityEnergyProducer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * A {@link TileEntity} which generates power
 * 
 * @author CJMinecraft
 *
 */
public class TileEntityenergiegenerator extends TileEntityEnergieProducer implements ITickable {

	private ItemStackHandler handler;
	private Worker worker;

	/**
	 * Initialsie a furnace generator (which is typically called when loading
	 * the world)
	 */
	public TileEntityenergiegenerator() {
		super(100000, 0, 1000);
//		if (this.worldObj != null)
//			this.type = this.worldObj.getBlockState(this.pos).getValue(BlockMachine.TYPE);
		this.handler = new ItemStackHandler(1);
		this.worker = new Worker(1, () -> {
			// Do work
			if (this.worker.getMaxWork() != 1)
				if (this.storage.getMaxEnergyStored()
						- this.storage.getEnergyStored() > 40)
					this.storage.receiveEnergyInternal(40, false);
		}, () -> {
			// Work done
			this.worker.setMaxCooldown(1);
			if (this.handler.getStackInSlot(0).getItem() == Items.BUCKET)
				return;
			ItemStack fuel = this.handler.extractItem(0, 1, false);
			if (fuel != null)
				if (this.storage.getMaxEnergyStored()
						- this.storage.getEnergyStored() > 40)
					this.worker.setMaxCooldown(TileEntityFurnace.getItemBurnTime(fuel));
			if (fuel.getItem() == Items.LAVA_BUCKET)
				this.handler.setStackInSlot(0, new ItemStack(Items.BUCKET));
		});
	}


	/**
	 * Update (i.e. generate the energy and burn the fuel)
	 */
	@Override
	public void update() {
		if (this.worldObj != null) {
			if (!this.worldObj.isRemote) {
				int extract = 1000;
				this.storage.extractEnergyInternal((int) EnergyUtils.giveEnergyAllFaces(this.worldObj, this.pos, extract,
						EnergyUnits.FORGE_ENERGY, false), false);
				if (this.storage.getMaxEnergyStored()
						- this.storage.getEnergyStored() > 40) {
					this.worker.doWork();
					this.markDirty();
					this.worldObj.setBlockState(this.pos,
							this.worldObj.getBlockState(this.pos).withProperty(energiegenerator.ACTIVATED, true), 2);
				} else {
					this.worldObj.setBlockState(this.pos,
							this.worldObj.getBlockState(this.pos).withProperty(energiegenerator.ACTIVATED, false), 2);
				}
				if (this.worker.getMaxWork() == 1)
					this.worldObj.setBlockState(this.pos,
							this.worldObj.getBlockState(this.pos).withProperty(energiegenerator.ACTIVATED, false), 2);
			}
		}
	}

	/**
	 * Allow for chip upgrading
	 */
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	/**
	 * Read data from nbt
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.handler.deserializeNBT(nbt.getCompoundTag("Inventory"));
		this.worker.deserializeNBT(nbt.getCompoundTag("Worker"));
		super.readFromNBT(nbt);
	}

	/**
	 * Write data to nbt
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setTag("Inventory", this.handler.serializeNBT());
		nbt.setTag("Worker", this.worker.serializeNBT());
		return super.writeToNBT(nbt);
	}

	/**
	 * Say that we have an inventory and a worker
	 */
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				|| capability == ModCapabilities.CAPABILITY_WORKER)
			return true;
		return super.hasCapability(capability, facing);
	}

	/**
	 * Get the inventory and the worker
	 */
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) this.handler;
		if (capability == ModCapabilities.CAPABILITY_WORKER)
			return (T) this.worker;
		return super.getCapability(capability, facing);
	}

}