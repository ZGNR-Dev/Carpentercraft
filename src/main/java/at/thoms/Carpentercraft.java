package at.thoms;

import at.thoms.blocks.*;
import at.thoms.blocks.blocktypes.blockbasic;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import at.thoms.clientonly.gui.guiextracrafting;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.items.*;
import at.thoms.items.itemtypes.itembasic;
import at.thoms.oredict.craftingmanager;
import at.thoms.oredict.oredictionaryhandler;
import at.thoms.proxy.proxyclient;
import at.thoms.proxy.proxycommons;
import at.thoms.utils.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;


@Mod(modid = Carpentercraft.MODID, version = Carpentercraft.VERSION)
public class Carpentercraft {

    public static final String MODID = "carpentercraft";
    public static final String VERSION = "0.0.5a";
    
    /* Event-Handler */

    
    
    /* Gui */
    
    //public static final int guiextracrafting = 1;
    
    /* References */
    
    @Mod.Instance(Carpentercraft.MODID)
    public static Carpentercraft instance;
    
    /* Proxys */
    
    @SidedProxy(clientSide = "at.thoms.proxy.proxyclient", serverSide = "at.thoms.proxy.proxyserver")
    public static proxycommons proxy;
    
    
    /* Ores */
    
    public static orecopper orecopper = new orecopper();
    public static orefakegold orefakegold = new orefakegold();
    public static oregold oregold = new oregold();
    public static oreiron oreiron = new oreiron();
    public static oreosmium oreosmium = new oreosmium();
    public static oretin oretin = new oretin();
    public static oresilver oresilver = new oresilver();
    public static oretungsten oretungsten = new oretungsten();
    public static oremystery oremystery = new oremystery();
    
    /* Bl�cke */
    
    public static sandglow sandglow = new sandglow();
    public static glasglow glasglow = new glasglow();
    public static glassglowraw glassglowraw = new glassglowraw();
    public static glasshardened glasshardened = new glasshardened();
    public static woodbloodtier1 woodbloodtier1 = new woodbloodtier1();
    
    /* H�lzer */
    
    public static woodpinelog woodpinelog = new woodpinelog();
    
    /* Primitive Maschinen */
    
    public static extracrafting extracrafting = new extracrafting();
    public static ultracrafting ultracrafting = new ultracrafting();
    public static cookieclicker cookieclicker = new cookieclicker();
    public static pedestal pedestal = new pedestal();
    public static treebreeder treebreeder = new treebreeder();
    
    /* Ingots */
    
    public static ingotbonium ingotbonium = new ingotbonium();
    public static ingotbronze ingotbronze = new ingotbronze();
    public static ingotcarbonium ingotcarbonium = new ingotcarbonium();
    public static ingotcopper ingotcopper = new ingotcopper();
    public static ingotdiamondium ingotdiamondium = new ingotdiamondium();
    public static ingotenderium ingotenderium = new ingotenderium();
    public static ingotfire ingotfire = new ingotfire();
    public static ingotlumium ingotlumium = new ingotlumium();
    public static ingotosmium ingotosmium = new ingotosmium();
    public static ingotsignalum ingotsignalum = new ingotsignalum();
    public static ingotsilver ingotsilver = new ingotsilver();
    public static ingotsteel ingotsteel = new ingotsteel();
    public static ingottin ingottin = new ingottin();
    
    /* Dusts */
    
    public static dustdiamondium dustdiamondium = new dustdiamondium();
    public static dustenderium dustenderium = new dustenderium();
    public static dustfire dustfire = new dustfire();
    public static dustlumium dustlumium = new dustlumium();
    public static dustsand dustsand = new dustsand();
    public static dustsandglow dustsandglow = new dustsandglow();
    public static dustsignalum dustsignalum = new dustsignalum();
    public static duststeel duststeel = new duststeel();
    
    /* Items */
    
    public static pelletcoal pelletcoal = new pelletcoal();
    public static bee bee = new bee();
    
    
    @EventHandler
	public void preInit(FMLPreInitializationEvent preinit){
    System.out.println("Was machst du heute noch so");
    
    proxy.TileEntityRegistering();
    ModBlocks.init();
    NetworkRegistry.INSTANCE.registerGuiHandler(this, new guihandler());
    
    	
    /* Ore-Registry */
    
    blockRegister(orecopper);
    blockRegister(orefakegold);
    blockRegister(oregold);
    blockRegister(oreiron);
    blockRegister(oreosmium);
    blockRegister(oretin);
    blockRegister(oretungsten);
    blockRegister(oresilver);
    blockRegister(oremystery);
    
    /* Block-Registry */
    
    blockRegister(glasglow);
    blockRegister(glassglowraw);
    blockRegister(sandglow);
    blockRegister(glasshardened);
    blockRegister(woodbloodtier1);
    
    /* Wood-Registry */
    
    blockRegister(woodpinelog);
    
    /* PrimitiveMachine-Registry */
    
    blockRegister(extracrafting);
    blockRegister(ultracrafting);
    blockRegister(cookieclicker);
    blockRegister(pedestal);
    blockRegister(treebreeder);
    
    /* Ingot-Registry */
    
    GameRegistry.register(ingotbonium);
    GameRegistry.register(ingotbronze);
    GameRegistry.register(ingotcarbonium);
    GameRegistry.register(ingotcopper);
    GameRegistry.register(ingotdiamondium);
    GameRegistry.register(ingotenderium);
    GameRegistry.register(ingotfire);
    GameRegistry.register(ingotlumium);
    GameRegistry.register(ingotosmium);
    GameRegistry.register(ingotsignalum);
    GameRegistry.register(ingotsilver);
    GameRegistry.register(ingotsteel);
    GameRegistry.register(ingottin);
    
    /* Dust-Registry */
    
    GameRegistry.register(dustdiamondium);
    GameRegistry.register(dustenderium);
    GameRegistry.register(dustfire);
    GameRegistry.register(dustlumium);
    GameRegistry.register(dustsand);
    GameRegistry.register(dustsandglow);
    GameRegistry.register(dustsignalum);
    GameRegistry.register(duststeel);
    
    /* Item-Registry */
    
    GameRegistry.register(pelletcoal);
    GameRegistry.register(bee);
    
    /* Tile_entity */
    
    
    }
    
    public static void blockRegister(blockbasic block){
    	GameRegistry.register(block);
    	GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
    
//    public static void blockPrimitiveMachineRegister(blockprimitivemachine block){
//    	GameRegistry.register(block);
//    	GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
//    }
    
    @EventHandler
    public void init(FMLInitializationEvent init){
    System.out.println("Folow me on Curse");

    proxy.clientRegistering();
    oredictionaryhandler.registerOreDictionary();
    craftingmanager.craftingregister();
    

	System.out.println("Donate to enter the free giveaway!");
    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent postinit){
    	
    	System.out.println("Bereit, die Welt zu �bernehmen?");
    	
    }
}
