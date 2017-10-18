package at.thoms.proxy;

import java.nio.channels.NetworkChannel;

import at.thoms.Carpentercraft;
import at.thoms.blocks.blocktypes.blockbasic;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import at.thoms.clientonly.gui.guihandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.text.translation.I18n;
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
		
		registerBlockModel(Carpentercraft.orecopper);
		registerBlockModel(Carpentercraft.orefakegold);
		registerBlockModel(Carpentercraft.oregold);
		registerBlockModel(Carpentercraft.oreiron);
		registerBlockModel(Carpentercraft.oreosmium);
		registerBlockModel(Carpentercraft.oretin);
		registerBlockModel(Carpentercraft.oreosmium);
		registerBlockModel(Carpentercraft.oretin);
		registerBlockModel(Carpentercraft.oresilver);
		registerBlockModel(Carpentercraft.oretungsten);
		registerBlockModel(Carpentercraft.oremystery);
	
		
	  /* Block-Registering */
		
		registerBlockModel(Carpentercraft.glasglow);
		registerBlockModel(Carpentercraft.sandglow);
		registerBlockModel(Carpentercraft.glassglowraw);
		registerBlockModel(Carpentercraft.glasshardened);
		registerBlockModel(Carpentercraft.woodbloodtier1);
		registerBlockModel(Carpentercraft.extracrafting);
		
		/* Wood-Registering */
		
		registerBlockModel(Carpentercraft.woodpinelog);
		
	  /* Primitive Maschine-Registering */
		
		registerBlockPrimitiveMachineModel(Carpentercraft.ultracrafting);
		
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
		registerItemModel(Carpentercraft.ingotsignalum);
		registerItemModel(Carpentercraft.ingotsilver);
		registerItemModel(Carpentercraft.ingotsteel);
		registerItemModel(Carpentercraft.ingottin);
		
		/* Dust-Registering */
		
		registerItemModel(Carpentercraft.dustdiamondium);
		registerItemModel(Carpentercraft.dustenderium);
		registerItemModel(Carpentercraft.dustfire);
		registerItemModel(Carpentercraft.dustlumium);
		registerItemModel(Carpentercraft.dustsand);
		registerItemModel(Carpentercraft.dustsandglow);
		registerItemModel(Carpentercraft.dustsignalum);
		registerItemModel(Carpentercraft.duststeel);
		
		/* Item-Registering */
		
		registerItemModel(Carpentercraft.pelletcoal);
		
	}
	
	

	public static void registerItemModel(Item item){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	public static void registerBlockModel(blockbasic block){
		registerItemModel(Item.getItemFromBlock(block));
	}
	
	public static void registerBlockPrimitiveMachineModel(blockprimitivemachine block){
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
		return I18n.translateToLocalFormatted(unlocalized, args);
	}
	
	
	
}
