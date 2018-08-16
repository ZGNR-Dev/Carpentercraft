package at.thoms.tileentitys;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

import cjminecraft.core.energy.compat.forge.CustomForgeEnergyStorage;
import cjminecraft.core.energy.EnergyUnits;
import cjminecraft.core.energy.EnergyUtils;

public class TileEntityenergieStorage extends TileEntity implements ITickable{

	private ItemStackHandler handler;
	private CustomForgeEnergyStorage energieStorage;
	
	public TileEntityenergieStorage() {
		this.handler = new ItemStackHandler(2);
		this.energieStorage = new CustomForgeEnergyStorage(133769420, 100000);
	}
	
	@Override
	public void tick() {
		if(this.worldObj != null) {
			if(!this.worldObj.isRemote) {
				this.energieStorage.extractEnergyInternal((int) EnergyUtils.giveEnergyAllFaces(this.worldObj, this.pos, 1000, EnergyUnits.FORGE_ENERGY, false, true), false);
				this.energieStorage.receiveEnergyInternal((int) EnergyUtils.takeEnergyAllFaces(this.worldObj, this.pos, 1000, EnergyUnits.FORGE_ENERGY, false, true), false);
				this.energieStorage.extractEnergyInternal((int) EnergyUtils.giveEnergy(this.handler.getStackInSlot(0), 1000, EnergyUnits.FORGE_ENERGY , false, null), false);
				this.energieStorage.receiveEnergyInternal((int) EnergyUtils.takeEnergy(this.handler.getStackInSlot(1), 1000, EnergyUnits.FORGE_ENERGY, false, null), false);
			}
		}
	}
	
	
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", handler.serializeNBT());
		this.energieStorage.writeToNBT(compound);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		handler.deserializeNBT(compound.getCompoundTag("inventory"));
		this.energieStorage.readFromNBT(compound);
		super.readFromNBT(compound);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) this.handler;
		if(capability == CapabilityEnergy.ENERGY)
			return (T) this.energieStorage;
			return super.getCapability(capability, facing);
		//return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)handler : super.getCapability(capability, facing);
	}

}