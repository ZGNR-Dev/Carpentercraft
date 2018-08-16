package at.thoms.events;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import at.thoms.Carpentercraft;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventRegistry
{
  private static Random rand = new Random();
  
  @SubscribeEvent
  public void onHarvestDrops(BlockEvent.HarvestDropsEvent event)
  {
    if (event.isSilkTouching()) {
      return;
    }
    IBlockState state = event.getState();
    if (state == null) {
      return;
    }
    Block block = state.getBlock();
    int meta = block.damageDropped(state);
    ItemStack planks = getPlanks(block, meta);
    if (planks != null)
    {
      EntityPlayer player = event.getHarvester();
      if (player == null) {
        return;
      }
      ItemStack heldItem = player.getHeldItemMainhand();
      if (heldItem == null) {
        return;
      }
      if (Carpentercraft.AxeList.contains(heldItem.getItem()))
      {
        List<ItemStack> drops = event.getDrops();
        for (Iterator<ItemStack> iter = drops.iterator(); iter.hasNext(); iter.remove())
        {
          ItemStack stack = (ItemStack)iter.next();
          if ((stack == null) || (stack.getItem() != Item.getItemFromBlock(block))) {}
        }
        drops.add(new ItemStack(planks.getItem(), planks.stackSize + bonusDrop(event.getFortuneLevel()), planks.getItemDamage()));
      }
    }
  }
  
  public static int bonusDrop(int level)
  {
    return level >= rand.nextInt(3) + 1 ? 1 : 0;
  }
  
  public static ItemStack getPlanks(Block block, int meta)
  {
    if (block == null) {
      return null;
    }
    String itemstring = block.getRegistryName() + "|" + meta;
    if (Carpentercraft.WoodDictionary.containsKey(itemstring)) {
      return (ItemStack)Carpentercraft.WoodDictionary.get(itemstring);
    }
    return null;
  }
  
  
  @SubscribeEvent
  public void onRenderGameOverlay(RenderGameOverlayEvent RGOevent) {
	  if(!RGOevent.isCancelable() && RGOevent.getType() == ElementType.EXPERIENCE) {
		  Minecraft mc = Minecraft.getMinecraft();
		  
		if(!mc.thePlayer.capabilities.isCreativeMode) {
			int posX = RGOevent.getResolution().getScaledWidth() / 2 + 1;
			int posY = RGOevent.getResolution().getScaledHeight() - 48;
			
			mc.renderEngine.bindTexture(new ResourceLocation("carpentercraft:textures/gui/toxic.png"));
			
			mc.ingameGUI.drawTexturedModalRect(posX, posY, 0, 0, 81, 8);
			mc.ingameGUI.drawTexturedModalRect(posX + 1, posY + 1, 0, 8, 80, 7);
		}
	  }
  }
  
  
  
  
  
  
  
  
  
  
  
}
