package at.thoms.items;

import at.thoms.Carpentercraft;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.inventory.Inventorybackpackzero;
import at.thoms.items.itemtypes.itembasic;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class backpackzero extends itembasic {

	public backpackzero() {
		super("backpackzero");
			setCreativeTab(CreativeTabs.MISC);
			setRegistryName("backpackzero");
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		super.onCreated(stack, worldIn, playerIn);
		new Inventorybackpackzero(stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		
		if(!worldIn.isRemote){
			playerIn.openGui(Carpentercraft.instance, guihandler.backpackzero, worldIn, 0, 0, 0);
		}
		
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}
