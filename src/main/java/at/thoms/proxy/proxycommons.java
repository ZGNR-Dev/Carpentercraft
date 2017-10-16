package at.thoms.proxy;

import java.lang.ref.Reference;

import at.thoms.tileentitys.TileEntityultracrafting;
import at.thoms.tileentitys.TileEntityextracrafting;
import at.thoms.Carpentercraft;
import at.thoms.blocks.blocktypes.blockbasic;
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
import net.minecraftforge.fml.common.registry.GameRegistry;

public class proxycommons {

	public void clientRegistering(){}
	
	public void preInit(FMLPreInitializationEvent preinit)
    { 
    }

    public void Init(FMLInitializationEvent init)
    {
    }
    
    public void TileEntityRegistering(){
    	GameRegistry.registerTileEntity(TileEntityultracrafting.class, Carpentercraft.MODID + ":ultracrafting");
    	GameRegistry.registerTileEntity(TileEntityextracrafting.class, Carpentercraft.MODID + ":extracrafting");
    }
    public void registerRenders()
    {
    }

    public void postInit(FMLPostInitializationEvent postinit)
    {
    }

    public void serverAboutToStart(FMLServerAboutToStartEvent serverabouttostart) 
    {
    }

    public void serverStarted(FMLServerStartedEvent serverstarted) 
    {
    }

    public void serverStopping(FMLServerStoppingEvent serverstopping) 
    {
    }

    public void serverStopped(FMLServerStoppedEvent serverstopped) 
    {
    }

    public void serverStarting(FMLServerStartingEvent serverstarting) 
    {
    }

	public static void registerItemModel(Item item)
	{
	}

	public static void registerBlockModel(blockbasic block)
	{
	}

//	public String localize(String string) {
//		return null;
//	}
	
	public String localize(String unlocalized, Object... args) {
		return I18n.translateToLocalFormatted(unlocalized, args);
	}
	
}
