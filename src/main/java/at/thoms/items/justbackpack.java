package at.thoms.items;

import at.thoms.Carpentercraft;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.inventory.Inventoryjustbackpack;
import at.thoms.items.itemtypes.itembasic;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class justbackpack extends itembasic {

	public justbackpack() {
		super("justbackpack");
			setCreativeTab(CreativeTabs.MISC);
			setRegistryName("justbackpack");
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		super.onCreated(stack, worldIn, playerIn);
		new Inventoryjustbackpack(stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		
		if(!worldIn.isRemote){
			playerIn.openGui(Carpentercraft.instance, guihandler.justbackpack, worldIn, 0, 0, 0);
		}
		
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
}
