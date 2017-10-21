package at.thoms.blocks;

import javax.annotation.Nullable;

import at.thoms.Carpentercraft;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.tileentitys.TileEntitypedestal;
import at.thoms.tileentitys.TileEntityultracrafting;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ultracrafting extends blockprimitivemachine<TileEntityultracrafting> {

	public ultracrafting() {
		super("ultracrafting", Material.ROCK);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntityultracrafting tile = getTileEntity(world, pos);
			IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
			if (!player.isSneaking()) {
				if (heldItem == null) {
					player.setHeldItem(hand, itemHandler.extractItem(0, 64, false));
				} else {
					player.setHeldItem(hand, itemHandler.insertItem(0, heldItem, false));
				}
				tile.markDirty();
			} else {
				player.openGui(Carpentercraft.instance, guihandler.ultracrafting, world, pos.getX(), pos.getY(), pos.getZ());
				ItemStack stack = itemHandler.getStackInSlot(0);
				if (stack != null) {
					String localized = Carpentercraft.proxy.localize(stack.getUnlocalizedName() + ".name");
					player.addChatMessage(new TextComponentString(stack.stackSize + "x " + localized));
				} else {
					player.addChatMessage(new TextComponentString("Empty"));
				}
			}
		}
		return true;
	}	
	
	
	@Override
	public Class<TileEntityultracrafting> getTileEntityClass() {
		return TileEntityultracrafting.class;
	}
	
	@Nullable
	@Override
	public TileEntityultracrafting createTileEntity(World world, IBlockState state) {
		return new TileEntityultracrafting();
	}

}