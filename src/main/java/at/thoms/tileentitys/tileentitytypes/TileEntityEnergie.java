package at.thoms.tileentitys.tileentitytypes;

import cjminecraft.core.energy.EnergyUtils;
import cjminecraft.core.energy.compat.EnergyStorage;
import cjminecraft.core.energy.compat.forge.CustomForgeEnergyStorage;
import cjminecraft.core.util.TileEntityBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Optional;

/**
 * A simple {@link TileEntity} which can hold energy. This uses forge energy. To
 * access the energy storage, use <code>this.storage</code>
 * 
 * @author CJMinecraft
 *
 */
public class TileEntityEnergie extends TileEntityBase {

	protected EnergyStorage storage;
	private Object forgeWrapper;

	/**
	 * Create an energy storage
	 * 
	 * @param capacity
	 *            The capacity of the energy storage
	 */
	public TileEntityEnergie(int capacity) {
		this(capacity, capacity, capacity, 0);
	}

	/**
	 * Create an energy storage
	 * 
	 * @param capacity
	 *            The capacity of the energy storage
	 * @param maxTransfer
	 *            The max receive and max extract of the energy storage
	 */
	public TileEntityEnergie(int capacity, int maxTransfer) {
		this(capacity, maxTransfer, maxTransfer, 0);
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
	public TileEntityEnergie(int capacity, int maxReceive, int maxExtract) {
		this(capacity, maxReceive, maxExtract, 0);
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
	public TileEntityEnergie(long capacity, long maxReceive, long maxExtract, long energy) {
		this.storage = new EnergyStorage(capacity, maxReceive, maxExtract, energy);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.storage.readFromNBT(nbt);
		super.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		this.storage.writeToNBT(nbt);
		return super.writeToNBT(nbt);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY)
			return true;
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			if(this.forgeWrapper == null)
				this.forgeWrapper = new ForgeEnergyWrapper(this.storage);
			return (T) this.forgeWrapper;
		}
		return super.getCapability(capability, facing);
	}
	
	private boolean addedToEnet;
		
	
}
