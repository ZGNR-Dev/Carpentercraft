package at.thoms.utils;

import at.thoms.Carpentercraft;
import at.thoms.blocks.*;
import at.thoms.blocks.blocktypes.blockbasic;
import at.thoms.blocks.blocktypes.blockmachine;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	
    /* Ores */
    
    public static orecopper orecopper;
    public static orefakegold orefakegold;
    public static oregold oregold;
    public static oreiron oreiron;
    public static oreosmium oreosmium;
    public static oreplutonium oreplutonium;
    public static oretin oretin;
    public static oresilver oresilver;
    public static oretungsten oretungsten;
    public static oremystery oremystery;

    /* Blöcke */
    
    public static sandglow sandglow;
    public static glasglow glasglow;
    public static glassglowraw glassglowraw;
    public static glasshardened glasshardened;
    
    /* Bäume */
    
    public static woodpinelog woodpinelog;
    
    /* Hölzer */
    
    public static woodblood woodblood;    
    
    /* Primitive Maschinen */
    
    public static extracrafting extracrafting;
    public static ultracrafting ultracrafting;
    public static cookieclicker cookieclicker;
    public static pedestal pedestal;
    public static treebreeder treebreeder;
    public static chestcompressed chestcompressed;
    public static justchest justchest;
    public static hotchest hotchest;
    public static sharpeningtable sharpeningtable;
    public static hotcrafting hotcrafting;
    public static scrapconverter scrapconverter;
    public static toolcrafter toolcrafter;
    public static blockcreator blockcreator;
    public static justfurnace justfurnace;
    
    /* Maschinen */
    
    public static energiestorage energiestorage;
    public static energiegenerator energiegenerator;

    
    
	public static void init() {
		
		/* Ores */
		orecopper = new orecopper();
		orefakegold = new orefakegold();
		oregold = new oregold();
		oreiron = new oreiron();
		oreosmium = new oreosmium();
		oreplutonium = new oreplutonium();
		oretin = new oretin();
		oresilver = new oresilver();
		oretungsten = new oretungsten();
		oremystery = new oremystery();
		
		/* Blöcke */
		
		sandglow = new sandglow();
		glasglow = new glasglow();
		glassglowraw = new glassglowraw();
		glasshardened = new glasshardened();
		
		/* Bäume */
		
		woodpinelog = new woodpinelog();
	    
		/* Hölzer */
		
		woodblood = new woodblood();
		
	    /* Primitive Maschinen */
	    
		extracrafting = new extracrafting();
		ultracrafting = new ultracrafting();
		cookieclicker = new cookieclicker();
		pedestal = new pedestal();
		treebreeder = new treebreeder();
		chestcompressed = new chestcompressed();
		justchest = new justchest();		
		hotchest = new hotchest();
		sharpeningtable = new sharpeningtable();
		hotcrafting = new hotcrafting();
		scrapconverter = new scrapconverter();
		toolcrafter = new toolcrafter();
		blockcreator = new blockcreator();
		justfurnace = new justfurnace();
		
		/* Maschinen */
		
		energiestorage = new energiestorage("energiestorage");
		energiegenerator = new energiegenerator("energiegenerator");
		
	}
	
	public static void render() {
		
	}
	
	public static void register() {
		
		/* Ores */
		
		registerBlock(orecopper);
		registerBlock(orefakegold);
		registerBlock(oregold);
		registerBlock(oreiron);
		registerBlock(oreosmium);
		registerBlock(oreplutonium);
		registerBlock(oretin);
		registerBlock(oresilver);
		registerBlock(oretungsten);
		registerBlock(oremystery);
		
		/* Blöcke */
		
		registerBlock(sandglow);
		registerBlock(glasglow);
		registerBlock(glassglowraw);
		registerBlock(glasshardened);
		
		/* Bäume */
		
		registerBlock(woodpinelog);
		
		/* Hölzer */
		
		registerBlock(woodblood);
		
		/* Primitive Maschinen */
		
		registerBlock(extracrafting);
		registerBlock(ultracrafting);
		registerBlock(cookieclicker);
		registerBlock(pedestal);
		registerBlock(treebreeder);
		registerBlock(chestcompressed);
		registerBlock(justchest);
		registerBlock(hotchest);
		registerBlock(sharpeningtable);
		registerBlock(hotcrafting);
		registerBlock(scrapconverter);
		registerBlock(toolcrafter);
		registerBlock(blockcreator);
		registerBlock(justfurnace);
		
		/* Maschinen */
		
		registerBlock(energiestorage);
		registerBlock(energiegenerator);
	
	}
	
	
	/**
	 * Register the renders for the block
	 */
	public static void registerRenders() {
		
		/* Ores */
		
		registerRender(orecopper);
		registerRender(orefakegold);
		registerRender(oregold);
		registerRender(oreiron);
		registerRender(oreosmium);
		registerRender(oreplutonium);
		registerRender(oretin);
		registerRender(oresilver);
		registerRender(oretungsten);
		registerRender(oremystery);
		
		/* Blöcke */
		registerRender(sandglow);
		registerRender(glasglow);
		registerRender(glassglowraw);
		registerRender(glasshardened);
		
		/* Bäume */
		
		registerRender(woodpinelog);
		
		/* Hölzer */
		
		registerRender(woodblood);
		
		/* Primitive Maschinen */
		
		registerRender(extracrafting);
		registerRender(ultracrafting);
		registerRender(cookieclicker);
		registerRender(pedestal);
		registerRender(treebreeder);
		registerRender(chestcompressed);
		registerRender(justchest);
		registerRender(hotchest);
		registerRender(sharpeningtable);
		registerRender(hotcrafting);
		registerRender(scrapconverter);
		registerRender(toolcrafter);
		registerRender(blockcreator);
		registerRender(justfurnace);

		/* Maschinen */
		
		registerRender(energiestorage);
		registerRender(energiegenerator);
		
		
	}

	
	/**
	 * Registers the block
	 * 
	 * @param block
	 *            The block to register
	 */
	public static void registerBlock(Block block) {
		block.setCreativeTab(Carpentercraft.Tabbasic);
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		Utils.getLogger().info("Registered Block: " + block.getUnlocalizedName().substring(5));
	}

	/**
	 * Registers the block with a custom {@link ItemBlock}
	 * 
	 * @param block
	 *            The block
	 * @param itemBlock
	 *            The {@link ItemBlock}
	 */
	public static void registerBlock(Block block, ItemBlock itemBlock) {
		block.setCreativeTab(Carpentercraft.Tabbasic);
		GameRegistry.register(block);
		GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
		Utils.getLogger().info("Registered Block: " + block.getUnlocalizedName().substring(5));
	}

	
	
	

	/**
	 * 
	 * My old way of rendering blocks, deprecated
	 * 
	 * @param block
	 * @param itemBlock
	 * @return
	 */
	@Deprecated
	private static <T extends Block> T register(T block, ItemBlock itemBlock) {
		GameRegistry.register(block);
		GameRegistry.register(itemBlock);

		if (block instanceof blockbasic) {
			((blockbasic)block).registerItemModel(itemBlock);
		}
		
		if (block instanceof blockprimitivemachine){
			GameRegistry.registerTileEntity(((blockprimitivemachine<?>)block).getTileEntityClass(), block.getRegistryName().toString());
		}
	
		if (block instanceof blockmachine) {
			GameRegistry.registerTileEntity(((blockmachine<?>)block).getTileEntityClass(), block.getRegistryName().toString());
		}
		
		return block;
	}
	
	
/**
 * 
 * My old way of rendering Blocks, deprecated
 * 
 * @param block
 * @return
 */
	@Deprecated
	private static <T extends Block> T register(T block) {
		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
		return register(block, itemBlock);
	}
	
	/**
	 * Registers the blocks renders
	 * 
	 * @param block
	 *            The block
	 */
	public static void registerRender(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(
				new ResourceLocation(Carpentercraft.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
		Utils.getLogger().info("Registered render for " + block.getUnlocalizedName().substring(5));
	}

	/**
	 * Registers the blocks renders even if it has meta data
	 * 
	 * @param block
	 *            The block
	 * @param meta
	 *            The blocks meta data
	 * @param fileName
	 *            The file name
	 */
	public static void registerRender(Block block, int meta, String fileName) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta,
				new ModelResourceLocation(new ResourceLocation(Carpentercraft.MODID, fileName), "inventory"));
		Utils.getLogger().info("Registered render for " + block.getUnlocalizedName().substring(5));
	}
	
	
	
	

}