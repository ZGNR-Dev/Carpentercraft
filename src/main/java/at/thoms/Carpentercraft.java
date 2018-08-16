package at.thoms;

import at.thoms.blocks.*;
import at.thoms.blocks.blocktypes.blockbasic;
import at.thoms.blocks.blocktypes.blockmachine;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import at.thoms.clientonly.gui.guiextracrafting;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.creativetabs.*;
import at.thoms.events.EventRegistry;
import at.thoms.items.*;
//import at.thoms.items.itemtypes.ArmorMaterials;
import at.thoms.items.itemtypes.itemarmor;
import at.thoms.items.itemtypes.itembasic;
import at.thoms.oredict.craftingmanager;
import at.thoms.oredict.hotCraftingManager;
import at.thoms.oredict.oredictionaryhandler;
import at.thoms.oredict.toolcrafterCraftingManager;
import at.thoms.proxy.proxyclient;
import at.thoms.proxy.proxycommons;
import at.thoms.utils.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcraft.jorbis.Block;


@Mod(modid = Carpentercraft.MODID, version = Carpentercraft.VERSION)
public class Carpentercraft {

    public static final String MODID = "carpentercraft";
    public static final String VERSION = "0.0.62a";
    
    /* Event-Handler */

    public static List<Item> AxeList = new ArrayList();
    public static Map<String, ItemStack> WoodDictionary = new HashMap();
    public static Block[] woodBlocks;    

    /* All Wood Types for AxeCarpenter */
    public static String[] wood = { "minecraft:log|0 = minecraft:planks|0", "minecraft:log|1 = minecraft:planks|1", "minecraft:log|2 = minecraft:planks|2", "minecraft:log|3 = minecraft:planks|3", "minecraft:log2|0 = minecraft:planks|4", "minecraft:log2|1 = minecraft:planks|5",
    		"biomesoplenty:log_0|4 = biomesoplenty:planks_0|0", "biomesoplenty:log_0|5 = biomesoplenty:planks_0|1", "biomesoplenty:log_0|6 = biomesoplenty:planks_0|2", "biomesoplenty:log_0|7 = biomesoplenty:planks_0|3", "biomesoplenty:log_1|4 = biomesoplenty:planks_0|4", "biomesoplenty:log_1|5 = biomesoplenty:planks_0|5", "biomesoplenty:log_1|6 = biomesoplenty:planks_0|6", "biomesoplenty:log_1|7 = biomesoplenty:planks_0|7", "biomesoplenty:log_2|4 = biomesoplenty:planks_0|8", "biomesoplenty:log_2|5 = biomesoplenty:planks_0|9", "biomesoplenty:log_2|6 = biomesoplenty:planks_0|10", "biomesoplenty:log_2|7 = biomesoplenty:planks_0|11", "biomesoplenty:log_3|4 = biomesoplenty:planks_0|12", "biomesoplenty:log_3|5 = biomesoplenty:planks_0|13", "biomesoplenty:log_3|6 = biomesoplenty:planks_0|14", "biomesoplenty:log_3|7 = biomesoplenty:planks_0|15",
    		"forestry:logs.0|0 = forestry:planks.0|0", "forestry:logs.0|1 = forestry:planks.0|1", "forestry:logs.0|2 = forestry:planks.0|2", "forestry:logs.0|3 = forestry:planks.0|3", "forestry:logs.1|0 = forestry:planks.0|4", "forestry:logs.1|1 = forestry:planks.0|5", "forestry:logs.1|2 = forestry:planks.0|6", "forestry:logs.1|3 = forestry:planks.0|7", "forestry:logs.2|0 = forestry:planks.0|8", "forestry:logs.2|1 = forestry:planks.0|9", "forestry:logs.2|2 = forestry:planks.0|10", "forestry:logs.2|3 = forestry:planks.0|11", "forestry:logs.3|0 = forestry:planks.0|12", "forestry:logs.3|1 = forestry:planks.0|13", "forestry:logs.3|2 = forestry:planks.0|14", "forestry:logs.3|3 = forestry:planks.0|15", "forestry:logs.4|0 = forestry:planks.1|0", "forestry:logs.4|1 = forestry:planks.1|1", "forestry:logs.4|2 = forestry:planks.1|2", "forestry:logs.4|3 = forestry:planks.1|3", "forestry:logs.5|0 = forestry:planks.1|4", "forestry:logs.5|1 = forestry:planks.1|5", "forestry:logs.5|2 = forestry:planks.1|6", "forestry:logs.5|3 = forestry:planks.1|7", "forestry:logs.6|0 = forestry:planks.1|8", "forestry:logs.6|1 = forestry:planks.1|9", "forestry:logs.6|2 = forestry:planks.1|10", "forestry:logs.6|3 = forestry:planks.1|11", "forestry:logs.7|0 = forestry:planks.1|12", "forestry:logs.fireproof.0|0 = forestry:planks.fireproof.0|0", "forestry:logs.fireproof.0|1 = forestry:planks.fireproof.0|1", "forestry:logs.fireproof.0|2 = forestry:planks.fireproof.0|2", "forestry:logs.fireproof.0|3 = forestry:planks.fireproof.0|3", "forestry:logs.fireproof.1|0 = forestry:planks.fireproof.0|4", "forestry:logs.fireproof.1|1 = forestry:planks.fireproof.0|5", "forestry:logs.fireproof.1|2 = forestry:planks.fireproof.0|6", "forestry:logs.fireproof.1|3 = forestry:planks.fireproof.0|7", "forestry:logs.fireproof.2|0 = forestry:planks.fireproof.0|8", "forestry:logs.fireproof.2|1 = forestry:planks.fireproof.0|9", "forestry:logs.fireproof.2|2 = forestry:planks.fireproof.0|10", "forestry:logs.fireproof.2|3 = forestry:planks.fireproof.0|11", "forestry:logs.fireproof.3|0 = forestry:planks.fireproof.0|12", "forestry:logs.fireproof.3|1 = forestry:planks.fireproof.0|13", "forestry:logs.fireproof.3|2 = forestry:planks.fireproof.0|14", "forestry:logs.fireproof.3|3 = forestry:planks.fireproof.0|15", "forestry:logs.fireproof.4|0 = forestry:planks.fireproof.1|0", "forestry:logs.fireproof.4|1 = forestry:planks.fireproof.1|1", "forestry:logs.fireproof.4|2 = forestry:planks.fireproof.1|2", "forestry:logs.fireproof.4|3 = forestry:planks.fireproof.1|3", "forestry:logs.fireproof.5|0 = forestry:planks.fireproof.1|4", "forestry:logs.fireproof.5|1 = forestry:planks.fireproof.1|5", "forestry:logs.fireproof.5|2 = forestry:planks.fireproof.1|6", "forestry:logs.fireproof.5|3 = forestry:planks.fireproof.1|7", "forestry:logs.fireproof.6|0 = forestry:planks.fireproof.1|8", "forestry:logs.fireproof.6|1 = forestry:planks.fireproof.1|9", "forestry:logs.fireproof.6|2 = forestry:planks.fireproof.1|10", "forestry:logs.fireproof.6|3 = forestry:planks.fireproof.1|11", "forestry:logs.fireproof.7|0 = forestry:planks.fireproof.1|12", "forestry:logs.vanilla.fireproof.0|0 = forestry:planks.vanilla.fireproof.0|0", "forestry:logs.vanilla.fireproof.0|1 = forestry:planks.vanilla.fireproof.0|1", "forestry:logs.vanilla.fireproof.0|2 = forestry:planks.vanilla.fireproof.0|2", "forestry:logs.vanilla.fireproof.0|3 = forestry:planks.vanilla.fireproof.0|3", "forestry:logs.vanilla.fireproof.1|0 = forestry:planks.vanilla.fireproof.0|4", "forestry:logs.vanilla.fireproof.1|1 = forestry:planks.vanilla.fireproof.0|5",
    		"natura:overworld_logs|0 = natura:overworld_planks|0", "natura:overworld_logs|1 = natura:overworld_planks|1", "natura:overworld_logs|2 = natura:overworld_planks|2", "natura:overworld_logs|3 = natura:overworld_planks|3", "natura:overworld_logs2|0 = natura:overworld_planks|4", "natura:overworld_logs2|1 = natura:overworld_planks|5", "natura:overworld_logs2|2 = natura:overworld_planks|6", "natura:overworld_logs2|3 = natura:overworld_planks|7", "natura:nether_logs|0 = natura:nether_planks|0", "natura:nether_logs|1 = natura:nether_planks|1", "natura:nether_logs|2 = natura:nether_planks|2",
    		"abyssalcraft:dltlog|0 = abyssalcraft:dltplank|0", "abyssalcraft:dreadlog|0 = abyssalcraft:dreadplanks|0",
    		"calculator:AmethystLog|0 = calculator:AmethystPlanks|0", "calculator:TanzaniteLog|0 = calculator:TanzanitePlanks|0", "calculator:PearLog|0 = calculator:PearPlanks|0", "calculator:DiamondLog|0 = calculator:DiamondPlanks|0",
    		"randomthings:spectreLog|0 = randomthings:spectrePlank|0",
    		"techreborn:rubberLog|0 = techreborn:rubberPlanks|0",
    		"roots:logWildwood|0 = roots:plankWildwood|0" };
    
    
    /* Gui */
    
    //public static final int guiextracrafting = 1;
    
    /* References */
    
    @Mod.Instance(Carpentercraft.MODID)
    public static Carpentercraft instance;
    
    /* Proxys */
    
    @SidedProxy(clientSide = "at.thoms.proxy.proxyclient", serverSide = "at.thoms.proxy.proxyserver")
    public static proxycommons proxy;
    
    /* Utils */
        
    /* CreativeTabs */
    
    public static final Tabbasic Tabbasic = new Tabbasic();
    public static final TabWood tabWood = new TabWood();
    
    
    
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
    public static ingotplutonium ingotplutonium = new ingotplutonium();
    public static ingotsignalum ingotsignalum = new ingotsignalum();
    public static ingotsilver ingotsilver = new ingotsilver();
    public static ingotsteel ingotsteel = new ingotsteel();
    public static ingottin ingottin = new ingottin();
    
    /* Tools */
    
    public static diamondpickaxesharpened diamondpickaxesharpened = new diamondpickaxesharpened();
    public static goldpickaxesharpened goldpickaxesharpened = new goldpickaxesharpened();
    public static ironpickaxesharpened ironpickaxesharpened = new ironpickaxesharpened();
    public static ironaxesharpened ironaxesharpened = new ironaxesharpened();
    public static axecarpenter axecarpenter = new axecarpenter(6, 6, ToolMaterial.DIAMOND);
    public static axetimber axetimber = new axetimber(6, 6, ToolMaterial.DIAMOND);
    
    
    /* Dusts */
    
    public static dustdiamondium dustdiamondium = new dustdiamondium();
    public static dustenderium dustenderium = new dustenderium();
    public static dustfire dustfire = new dustfire();
    public static dustlumium dustlumium = new dustlumium();
    public static dustsand dustsand = new dustsand();
    public static dustsandglow dustsandglow = new dustsandglow();
    public static dustsignalum dustsignalum = new dustsignalum();
    public static duststeel duststeel = new duststeel();
    
    /* Armor */
    
	public static ItemArmor armorpowerHelmet = new armorpower(ArmorMaterial.DIAMOND, 0, EntityEquipmentSlot.HEAD);
	public static ItemArmor armorpowerChestplate = new armorpower(ArmorMaterial.DIAMOND, 0, EntityEquipmentSlot.CHEST);
	public static ItemArmor armorpowerLeggings = new armorpower(ArmorMaterial.DIAMOND, 0, EntityEquipmentSlot.LEGS);
	public static ItemArmor armowpowerBoots = new armorpower(ArmorMaterial.DIAMOND, 0, EntityEquipmentSlot.FEET);
    
    
    /* Items */
    
    public static pelletcoal pelletcoal = new pelletcoal();
    public static bee bee = new bee();
    public static whetstone whetstone = new whetstone();
    public static justbackpack justbackpack = new justbackpack();
    public static backpackzero backpackzero = new backpackzero();
    public static WoodTabIcon woodTabIcon = new WoodTabIcon();
    
    
    public static boolean isInt(String str)
    {
      try
      {
        Integer.parseInt(str);
        return true;
      }
      catch (NumberFormatException e) {}
      return false;
    }
    
    @EventHandler
	public void preInit(FMLPreInitializationEvent preinit){
    System.out.println("Was machst du heute noch so");
    
    proxy.TileEntityRegistering();
    ModBlocks.init();
    ModBlocks.register();
    NetworkRegistry.INSTANCE.registerGuiHandler(this, new guihandler());
    AxeList.add(axecarpenter);
    
    	
    for (String entry : wood)
    {
      entry = entry.replaceAll("\\s", "");
      String[] data = entry.split("\\=");
      if (data.length == 2)
      {
        String[] woodData = data[0].split("\\|");
        String[] plankData = data[1].split("\\|");
        if ((((woodData.length != 2 ? 1 : 0) | (plankData.length != 2 ? 1 : 0)) == 0) && 
          (isInt(woodData[1])) && (isInt(plankData[1])))
        {
          int plankMeta = Integer.parseInt(plankData[1]);
          Item woodItem = Item.getByNameOrId(woodData[0]);
          Item plankItem = Item.getByNameOrId(plankData[0]);
          if ((woodItem != null) && (plankItem != null)) {
            Carpentercraft.WoodDictionary.put(data[0], new ItemStack(plankItem, 5, plankMeta));
          }
        }
      }
    }    
    
//    /* Ingot-Registry
    
    GameRegistry.register(ingotbonium);
    GameRegistry.register(ingotbronze);
    GameRegistry.register(ingotcarbonium);
    GameRegistry.register(ingotcopper);
    GameRegistry.register(ingotdiamondium);
    GameRegistry.register(ingotenderium);
    GameRegistry.register(ingotfire);
    GameRegistry.register(ingotlumium);
    GameRegistry.register(ingotosmium);
    GameRegistry.register(ingotplutonium);
    GameRegistry.register(ingotsignalum);
    GameRegistry.register(ingotsilver);
    GameRegistry.register(ingotsteel);
    GameRegistry.register(ingottin);
    
    /* Tool-Registry */
    
    GameRegistry.register(diamondpickaxesharpened);
    GameRegistry.register(goldpickaxesharpened);
    GameRegistry.register(ironpickaxesharpened);
    GameRegistry.register(ironaxesharpened);
    GameRegistry.register(axecarpenter);
    GameRegistry.register(axetimber);
    
    /* Dust-Registry */
    
    GameRegistry.register(dustdiamondium);
    GameRegistry.register(dustenderium);
    GameRegistry.register(dustfire);
    GameRegistry.register(dustlumium);
    GameRegistry.register(dustsand);
    GameRegistry.register(dustsandglow);
    GameRegistry.register(dustsignalum);
    GameRegistry.register(duststeel);
    
    /* Armor-Registry */
    
//    registerArmorItem(armorpowerHelmet, "armorpowerHelmet");
//    registerArmorItem(armorpowerChestplate, "armorpowerChestplate");
//    registerArmorItem(armorpowerLeggings, "armorpowerLeggings");
//    registerArmorItem(armowpowerBoots, "armorpowerBoots");
    GameRegistry.register(armorpowerHelmet);
    GameRegistry.register(armorpowerChestplate);
    GameRegistry.register(armorpowerLeggings);
    GameRegistry.register(armowpowerBoots);
    
    /* Item-Registry */
    
    GameRegistry.register(pelletcoal);
    GameRegistry.register(bee);
    GameRegistry.register(whetstone);
    GameRegistry.register(justbackpack);
    GameRegistry.register(backpackzero);
    GameRegistry.register(woodTabIcon);
    
    /* Tile_entity */
    
    
    }
    
    public static void blockRegister(blockbasic block){
    	GameRegistry.register(block);
    	GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
    
    public static void blockMachineRegister(blockmachine block) {
    	GameRegistry.register(block);
    	GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
    
    /* public static void registerArmorItem(Item item, String itemName) {
		GameRegistry.registerItem(item, itemName);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation("carpentercraft:" + itemName, "inventory"));
	} */

    
    
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
    toolcrafterCraftingManager.getInstance();
    hotCraftingManager.getInstance();
    MinecraftForge.EVENT_BUS.register(new EventRegistry());
//    MinecraftForge.EVENT_BUS.register(new ConfigRegistry());
    
    

	System.out.println("Donate to enter the free giveaway!");
    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent postinit){
    	
    	System.out.println("Bereit, die Welt zu übernehmen?");
    	
    }
}
