package at.thoms.utils;

import at.thoms.blocks.*;
import at.thoms.blocks.blocktypes.blockbasic;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	public static ultracrafting blockuc;
	public static cookieclicker blockcc;
	public static pedestal blockp;

	public static void init() {
		
//		blockuc = register(new ultracrafting());
	}

	private static <T extends Block> T register(T block, ItemBlock itemBlock) {
		GameRegistry.register(block);
		GameRegistry.register(itemBlock);

		if (block instanceof blockbasic) {
			((blockbasic)block).registerItemModel(itemBlock);
		}
		
		if (block instanceof blockprimitivemachine) {
			GameRegistry.registerTileEntity(((blockprimitivemachine<?>)block).getTileEntityClass(), block.getRegistryName().toString());
		}

		return block;
	}

	private static <T extends Block> T register(T block) {
		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
		return register(block, itemBlock);
	}

}