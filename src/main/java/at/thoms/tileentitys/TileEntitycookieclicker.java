package at.thoms.tileentitys;

import javax.swing.JOptionPane;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntitycookieclicker extends TileEntity {

	private int count;
	private int isalreadyp = 0;
	private int isalreadyn = 0;

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("count", count);
		compound.setInteger("isalreadyp", isalreadyp);
		compound.setInteger("isalreadyn", isalreadyn);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		count = compound.getInteger("count");
		isalreadyp = compound.getInteger("isalreadyp");
		isalreadyn = compound.getInteger("isalreadyn");
		super.readFromNBT(compound);
	}
	
	public int getCount() {
		return count;
	}
	
	public int getIsAlreadyP(){
		return isalreadyp;
	}
	
	public int getIsAlreadyN(){
		return isalreadyn;
	}
	
	public void incrementCount() {
		count++;
		markDirty();
	}
	
	public void decrementCount() {
		count--;
		markDirty();
	}
	
	public void surprise() {
		if(count >= 1337 && isalreadyp < 1){
			JOptionPane.showMessageDialog(null, "You have found the positive surprise");
			isalreadyp++;
			markDirty();
		}
	}

	public void urbad(){
		if(count >= -1337 && isalreadyn < 1){
			JOptionPane.showMessageDialog(null, "You have found the negative surprise. U r bad");
			isalreadyn++;
			markDirty();
		}
	}
}