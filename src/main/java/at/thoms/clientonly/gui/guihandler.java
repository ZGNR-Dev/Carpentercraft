package at.thoms.clientonly.gui;

import at.thoms.Carpentercraft;
import at.thoms.container.*;
import at.thoms.inventory.Inventorybackpackzero;
import at.thoms.inventory.Inventoryjustbackpack;
import at.thoms.tileentitys.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class guihandler implements IGuiHandler{
	
  /* GUI IDs */
	
	public static final int extracrafting = 1;
	public static final int ultracrafting = 2;
	public static final int treebreeder = 3;
	public static final int pedestal = 4;
	public static final int chestcompressed = 5;
	public static final int justchest = 6;
	public static final int hotchest = 7;
	public static final int sharpeningtable = 8;
	public static final int hotcrafting = 9;
	public static final int scrapconverter = 10;
	public static final int backpackzero = 11;
	public static final int backpackone = 12;
	public static final int backpacktwo = 13;
	public static final int backpackthree = 14;
	public static final int backpackfour = 15;
	public static final int backpackfive = 16;
	public static final int backpacksix = 17;
	public static final int justbackpack = 18;
	public static final int toolcrafter = 19;
	public static final int energiestorage = 20;
	public static final int energiegenerator = 21;
	public static final int blockcreator = 22;
	public static final int justfurnace = 23;
	
	

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
		case chestcompressed:
			return new containerchestcompressed(player.inventory, (TileEntitychestcompressed)world.getTileEntity(new BlockPos(x, y, z)));
		case justchest:
			return new containerjustchest(player.inventory, (TileEntityjustchest)world.getTileEntity(new BlockPos(x, y, z)));
		case hotchest:
			return new containerhotchest(player.inventory, (TileEntityhotchest)world.getTileEntity(new BlockPos(x, y, z)));
		case sharpeningtable:
			return new containersharpeningtable(player.inventory, (TileEntitysharpeningtable)world.getTileEntity(new BlockPos(x, y, z)));
		case hotcrafting:
			return new containerhotcrafting(player.inventory, (TileEntityhotcrafting)world.getTileEntity(new BlockPos(x, y, z)));
//		case scrapconverter:
//			return new containerscrapconverter(player.inventory, (TileEntityscrapconverter)world.getTileEntity(new BlockPos(x, y, z)));
		case backpackzero:
			return new containerbackpackzero(player.inventory, new Inventorybackpackzero(player.inventory.getCurrentItem()), player);
//			return new containerbackpackzero(player.inventory, backpackInventory, true, player);
		case justbackpack:
			return new containerjustbackpack(player.inventory, new Inventoryjustbackpack(player.inventory.getCurrentItem()), player);
		case toolcrafter:
			return new containertoolcrafter(player.inventory, (TileEntitytoolcrafter)world.getTileEntity(new BlockPos(x, y, z)));
		case energiestorage:
			return new containerenergiestorage(player.inventory, (TileEntityenergieStorage) world.getTileEntity(new BlockPos(x, y, z)));
		case energiegenerator:
			return new containerenergiegenerator(player.inventory, (TileEntityenergiegenerator) world.getTileEntity(new BlockPos(x, y, z)));
		case blockcreator:
			return new containerblockcreator(player.inventory, (TileEntityblockcreator) world.getTileEntity(new BlockPos(x, y, z)));
		case justfurnace:
			return new containerjustfurnace(player.inventory, (TileEntityjustfurnace) world.getTileEntity(new BlockPos(x, y, z)));
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
		case chestcompressed:
			return new guichestcompressed(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		case justchest:
			return new guijustchest(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
//			return new guijustchest(player.inventory, (TileEntityjustchest)world.getTileEntity(new BlockPos(x, y, z)));
		case hotchest:
			return new guihotchest(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		case sharpeningtable:
			return new guisharpeningtable(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		case hotcrafting:
			return new guihotcrafting(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
//		case scrapconverter:
//			return new guiscrapconverter(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		case backpackzero:
//			return new guibackpackzero(new Inventorybackpackzero(player.inventory.getCurrentItem());
			return new guibackpackzero(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		case justbackpack:
			return new guijustbackpack(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		case toolcrafter:
			return new guitoolcrafter(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		case energiestorage:
			return new guienergiestorage(player.inventory, (TileEntityenergieStorage)world.getTileEntity(pos));
//			return new guienergiestorage(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		case energiegenerator:
			return new guienergiegenerator(player.inventory, (TileEntityenergiegenerator)world.getTileEntity(pos));
		case blockcreator:
			return new guiblockcreator(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		case justfurnace:
			return new guijustfurnace(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		default:
			return null;
	    }
	}

}
