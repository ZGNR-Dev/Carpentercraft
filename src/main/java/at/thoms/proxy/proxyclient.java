package at.thoms.proxy;

import at.thoms.Carpentercraft;
import at.thoms.blocks.blocktypes.blockbasic;
import at.thoms.blocks.blocktypes.blockmachine;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.utils.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class proxyclient extends proxycommons{

	@Override
	public void clientRegistering(){
		
	  /* Ore-Registering */
		
		registerBlockModel(ModBlocks.orecopper);
		registerBlockModel(ModBlocks.orefakegold);
		registerBlockModel(ModBlocks.oregold);
		registerBlockModel(ModBlocks.oreiron);
		registerBlockModel(ModBlocks.oreosmium);
		registerBlockModel(ModBlocks.oretin);
		registerBlockModel(ModBlocks.oreosmium);
		registerBlockModel(ModBlocks.oreplutonium);
		registerBlockModel(ModBlocks.oretin);
		registerBlockModel(ModBlocks.oresilver);
		registerBlockModel(ModBlocks.oretungsten);
		registerBlockModel(ModBlocks.oremystery);
	
		
	    /* Block-Registering */
		
		registerBlockModel(ModBlocks.glasglow);
		registerBlockModel(ModBlocks.sandglow);
		registerBlockModel(ModBlocks.glassglowraw);
		registerBlockModel(ModBlocks.glasshardened);
		registerBlockModel(ModBlocks.extracrafting);
		
		/* Baum-Registering */
		
		registerBlockModel(ModBlocks.woodpinelog);
		
		/* Holz-Registering */
		
		registerBlockModel(ModBlocks.woodblood);
		
	    /* Primitive Maschine-Registering */
		
		registerBlockPrimitiveMachineModel(ModBlocks.ultracrafting);
//		registerBlockPrimitiveMachineModel(ModBlocks.treebreeder);
		registerBlockPrimitiveMachineModel(ModBlocks.chestcompressed);
		registerBlockPrimitiveMachineModel(ModBlocks.justchest);
		registerBlockPrimitiveMachineModel(ModBlocks.hotchest);
		registerBlockPrimitiveMachineModel(ModBlocks.sharpeningtable);
		registerBlockPrimitiveMachineModel(ModBlocks.hotcrafting);
		registerBlockPrimitiveMachineModel(ModBlocks.scrapconverter);
		registerBlockPrimitiveMachineModel(ModBlocks.toolcrafter);
		registerBlockPrimitiveMachineModel(ModBlocks.blockcreator);
		registerBlockPrimitiveMachineModel(ModBlocks.justfurnace);
		
		/* Mascjinen-Registering */
		
		registerBlockMachineModel(ModBlocks.energiestorage);
		registerBlockMachineModel(ModBlocks.energiegenerator);
		
		
	  /* Ingot-Registering */
		
		registerItemModel(Carpentercraft.ingotbonium);
		registerItemModel(Carpentercraft.ingotbronze);
		registerItemModel(Carpentercraft.ingotcarbonium);
		registerItemModel(Carpentercraft.ingotcopper);
		registerItemModel(Carpentercraft.ingotdiamondium);
		registerItemModel(Carpentercraft.ingotenderium);
		registerItemModel(Carpentercraft.ingotfire);
		registerItemModel(Carpentercraft.ingotlumium);
		registerItemModel(Carpentercraft.ingotosmium);
		registerItemModel(Carpentercraft.ingotplutonium);
		registerItemModel(Carpentercraft.ingotsignalum);
		registerItemModel(Carpentercraft.ingotsilver);
		registerItemModel(Carpentercraft.ingotsteel);
		registerItemModel(Carpentercraft.ingottin);
		
		/* Tool-Registering */
		
		registerItemModel(Carpentercraft.diamondpickaxesharpened);
		registerItemModel(Carpentercraft.goldpickaxesharpened);
		registerItemModel(Carpentercraft.ironpickaxesharpened);
		registerItemModel(Carpentercraft.ironaxesharpened);
		registerItemModel(Carpentercraft.axecarpenter);
		registerItemModel(Carpentercraft.axetimber);
		
		/* Dust-Registering */
		
		registerItemModel(Carpentercraft.dustdiamondium);
		registerItemModel(Carpentercraft.dustenderium);
		registerItemModel(Carpentercraft.dustfire);
		registerItemModel(Carpentercraft.dustlumium);
		registerItemModel(Carpentercraft.dustsand);
		registerItemModel(Carpentercraft.dustsandglow);
		registerItemModel(Carpentercraft.dustsignalum);
		registerItemModel(Carpentercraft.duststeel);
		
		/* Armor-Registering */
		
		registerItemArmorModel(Carpentercraft.armorpowerHelmet);
		registerItemArmorModel(Carpentercraft.armorpowerChestplate);
		registerItemArmorModel(Carpentercraft.armorpowerLeggings);
		registerItemArmorModel(Carpentercraft.armowpowerBoots);
	
		
		/* Item-Registering */
		
		registerItemModel(Carpentercraft.pelletcoal);
		registerItemModel(Carpentercraft.bee);
		registerItemModel(Carpentercraft.whetstone);
		registerItemModel(Carpentercraft.justbackpack);
		registerItemModel(Carpentercraft.backpackzero);
		registerItemModel(Carpentercraft.woodTabIcon);
		
	    /* Tileentity-Registering */
		
		
	}
	
	@Override
	public void registerRenders() {
			ModBlocks.registerRenders();
		
		
		super.registerRenders();
	}

	public static void registerItemModel(Item item){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	public static void registerItemArmorModel(ItemArmor item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	
	public static void registerBlockModel(blockbasic block){
		registerItemModel(Item.getItemFromBlock(block));
	}
	
	public static void registerBlockPrimitiveMachineModel(blockprimitivemachine block){
		registerItemModel(Item.getItemFromBlock(block));
	}
	
	public static void registerBlockMachineModel(blockmachine block) {
		registerItemModel(Item.getItemFromBlock(block));
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent preinit){
		super.preInit(preinit);

	}
	
	@Override
	public void Init(FMLInitializationEvent init){
		super.Init(init);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(Carpentercraft.instance, new guihandler());
		
	}
	
	
	
	@Override
	public void postInit(FMLPostInitializationEvent postinit){
		super.postInit(postinit);
	}
	
	@Override
	public void serverAboutToStart(FMLServerAboutToStartEvent serverabouttostart){
		super.serverAboutToStart(serverabouttostart);
	}
	
	@Override
	public void serverStarted(FMLServerStartedEvent serverstarted){
		super.serverStarted(serverstarted);
	}
	
	@Override
	public void serverStopping(FMLServerStoppingEvent serverstopping){
		super.serverStopping(serverstopping);
	}
	
	@Override
	public void serverStopped(FMLServerStoppedEvent serverstopped){
		super.serverStopped(serverstopped);
	}
	
	@Override
	public void serverStarting(FMLServerStartingEvent serverstarting){
		super.serverStarting(serverstarting);
	}
	
	@Override
	public String localize(String unlocalized, Object... args) {
		return I18n.format(unlocalized, args);
	}	
}
