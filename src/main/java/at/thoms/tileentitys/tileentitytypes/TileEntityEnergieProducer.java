package at.thoms.tileentitys.tileentitytypes;

import cjminecraft.core.energy.EnergyUtils;
import cjminecraft.core.energy.compat.TileEntityEnergy;
import cjminecraft.core.energy.EnergyUnits;
import cjminecraft.core.energy.EnergyUtils;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Optional;

/**
 * A {@link TileEntity} which acts as an energy storage. The {@link TileEntity}
 * uses forge energy and to access the storage use <code>this.storage</code>.
 * This is a an energy storage which will generate energy and transfer this to
 * its surroundings
 * 
 * @author CJMinecraft
 *
 */
public class TileEntityEnergieProducer extends TileEntityEnergie implements IEnergyProvider {

	private Object teslaWrapper;

	/**
	 * Create an energy storage
	 * 
	 * @param capacity
	 *            The capacity of the energy storage
	 */
	public TileEntityEnergieProducer(long capacity) {
		super(capacity, capacity, capacity, 0);
	}

	/**
	 * Create an energy storage
	 * 
	 * @param capacity
	 *            The capacity of the energy storage
	 * @param maxTransfer
	 *            The max receive and max extract of the energy storage
	 */
	public TileEntityEnergieProducer(long capacity, long maxTransfer) {
		super(capacity, maxTransfer, maxTransfer, 0);
	}

	/**
	 * Create an energy storage
	 * 
	 * @param capacity
	 *            The capacity of the energy storage
	 * @param maxReceive
	 *            The maximum amount of energy which can be received
	 * @param maxExtract
	 *            The maximum amount of energy which can be extracted
	 */
	public TileEntityEnergieProducer(long capacity, long maxReceive, long maxExtract) {
		super(capacity, maxReceive, maxExtract, 0);
	}

	/**
	 * Create an energy storage
	 * 
	 * @param capacity
	 *            The capacity of the energy storage
	 * @param maxReceive
	 *            The maximum amount of energy which can be received
	 * @param maxExtract
	 *            The maximum amount of energy which can be extracted
	 * @param energy
	 *            The energy inside of the energy storage
	 */
	public TileEntityEnergieProducer(long capacity, long maxReceive, long maxExtract, long energy) {
		super(capacity, maxReceive, maxExtract, energy);
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return (int) this.storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return (int) this.storage.getMaxEnergyStored();
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		return (int) this.storage.extractEnergy(maxExtract, simulate);
	}

	/* @Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (EnergyUtils.TESLA_LOADED
				&& (capability == EnergyUtils.TESLA_PRODUCER || capability == EnergyUtils.TESLA_HOLDER))
			return true;
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (EnergyUtils.TESLA_LOADED
				&& (capability == EnergyUtils.TESLA_PRODUCER || capability == EnergyUtils.TESLA_HOLDER)) {
			if (this.teslaWrapper == null)
				this.teslaWrapper = new TeslaWrapper(this.storage);
			return (T) this.teslaWrapper;
		}
		return super.getCapability(capability, facing);
	} */ // TODO All


}
