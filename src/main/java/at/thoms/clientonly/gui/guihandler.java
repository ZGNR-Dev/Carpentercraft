package at.thoms.clientonly.gui;

import at.thoms.Carpentercraft;
import at.thoms.container.*;
import at.thoms.tileentitys.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class guihandler implements IGuiHandler{
	
  /* GUI IDs */
	
	public static final int extracrafting = 1;
	public static final int ultracrafting = 2;
	public static final int treebreeder = 3;
	public static final int pedestal = 4;
	

	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
		case extracrafting:
			return new containerextracrafting(player.inventory, (TileEntityextracrafting) world.getTileEntity(new BlockPos(x, y, z)));
		case ultracrafting:
			return new containerultracrafting(player.inventory, (TileEntityultracrafting)world.getTileEntity(new BlockPos(x, y, z)));
		case treebreeder:
			return new containertreebreeder(player.inventory, (TileEntitytreebreeder)world.getTileEntity(new BlockPos(x, y, z)));
		case pedestal:
			return new containerpedestal(player.inventory, (TileEntitypedestal)world.getTileEntity(new BlockPos(x, y, z)));
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID){
		case extracrafting:
			return new guiextracrafting(player.inventory, (TileEntityextracrafting) world.getTileEntity(pos));
		case ultracrafting:
			return new guiultracrafting(getServerGuiElement(ID, player, world, x, y, z), player.inventory);	
		case treebreeder:
			return new guitreebreeder(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		case pedestal:
			return new guipedestal(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		default:
			return null;
	    }
	}

}
