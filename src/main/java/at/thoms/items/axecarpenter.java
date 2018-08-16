package at.thoms.items;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Sets;

import at.thoms.Carpentercraft;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class axecarpenter extends ItemTool{

	  private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] { Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE });

	  public axecarpenter(float damage, float attackSpeed, Item.ToolMaterial toolMaterial) {
	    super(damage, attackSpeed, toolMaterial, EFFECTIVE_ON);
		this.setCreativeTab(Carpentercraft.Tabbasic);
	    this.setHarvestLevel("axe", 3);
	    this.setUnlocalizedName("axecarpenter");
		this.setRegistryName("axecarpenter");
		
	}
}
