package at.thoms.clientonly.gui;

import at.thoms.Carpentercraft;
import at.thoms.*;
import at.thoms.container.containerextracrafting;
import at.thoms.container.containerultracrafting;
import at.thoms.tileentitys.TileEntityextracrafting;
import at.thoms.tileentitys.TileEntityultracrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class guihandler implements IGuiHandler{
	
  /* GUI IDs */
	
	public static final int extracrafting = 1;
	public static final int ultracrafting = 2;
	

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		//BlockPos pos = new BlockPos(x, y, z);	
		switch(ID){
		case extracrafting:
			return new containerextracrafting(player.inventory, (TileEntityextracrafting) world.getTileEntity(new BlockPos(x, y, z)));
			case ultracrafting:
			return new containerultracrafting(player.inventory, (TileEntityultracrafting) world.getTileEntity(new BlockPos(x, y, z)));
			
		default: return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID){
		case extracrafting:
			return new guiextracrafting(player.inventory, (TileEntityextracrafting) world.getTileEntity(pos));
			case ultracrafting:
			return new containerultracrafting(player.inventory, (TileEntityultracrafting) world.getTileEntity(new BlockPos(x, y, z)));
			
		default: return null;
	    }
	}

}
