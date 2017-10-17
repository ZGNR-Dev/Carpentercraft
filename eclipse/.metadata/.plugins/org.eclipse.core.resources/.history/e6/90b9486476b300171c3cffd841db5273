package at.thoms.oredict;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import at.thoms.Carpentercraft;
import at.thoms.blocks.*;
import at.thoms.items.*;

public class craftingmanager {

	
	public static void craftingregister(){
		
    	ItemStack stackIron_Block  = new ItemStack(Blocks.IRON_BLOCK);
		ItemStack stackGold_Block = new ItemStack(Blocks.GOLD_BLOCK);
		ItemStack stackDiamond_Block = new ItemStack(Blocks.DIAMOND_BLOCK);
		ItemStack stackNether_Star = new ItemStack(Items.NETHER_STAR);
		
		
		ItemStack stackDiamond = new ItemStack(Items.DIAMOND);
	    ItemStack stackBlaze_Powder = new ItemStack(Items.BLAZE_POWDER);
	    ItemStack stackGold_Ingot = new ItemStack(Items.GOLD_INGOT);
	    ItemStack stackingotfire = new ItemStack(Carpentercraft.ingotfire);
	    ItemStack stackingotdiamondium = new ItemStack(Carpentercraft.ingotdiamondium);
	    ItemStack stackStone = new ItemStack(Blocks.STONE);
	    ItemStack stackingotcopper = new ItemStack(Carpentercraft.ingotcopper);
	    ItemStack stackCoal = new ItemStack(Items.COAL);
	    ItemStack stackpelletccoal = new ItemStack(Carpentercraft.pelletcoal, 9);
	    ItemStack stackIron_Ingot = new ItemStack(Items.IRON_INGOT);
	    ItemStack stackingotsteel = new ItemStack(Carpentercraft.ingotsteel);
	    ItemStack stackpelletcoal = new ItemStack(Carpentercraft.pelletcoal);
	    ItemStack stackingottin = new ItemStack(Carpentercraft.ingottin);
	    ItemStack stackingotbronze = new ItemStack(Carpentercraft.ingotbronze, 4);
	    ItemStack stackingotosmium = new ItemStack(Carpentercraft.ingotosmium);
	    //ItemStack stackingottungsten = new ItemStack(ingottungsten);
	    ItemStack stackingotsilver = new ItemStack(Carpentercraft.ingotsilver);
	    ItemStack stackRedstone_Block = new ItemStack(Blocks.REDSTONE_BLOCK);
	    ItemStack stackRedstone = new ItemStack(Items.REDSTONE);
	    ItemStack stackGlowstone = new ItemStack(Blocks.GLOWSTONE);
	    ItemStack stackingotsignalum = new ItemStack(Carpentercraft.ingotsignalum, 4);
	    ItemStack stackingotlumium = new ItemStack(Carpentercraft.ingotlumium, 4);
	    ItemStack stackGold_Nugget = new ItemStack(Items.GOLD_NUGGET);
	    ItemStack stackingotenderium = new ItemStack(Carpentercraft.ingotenderium, 4);
	    ItemStack stackEnder_Pearl = new ItemStack(Items.ENDER_PEARL);
	    ItemStack stackBone = new ItemStack(Items.BONE);
	    ItemStack stackingotbonium = new ItemStack(Carpentercraft.ingotbonium);
	    ItemStack stackingotcarbonium = new ItemStack(Carpentercraft.ingotcarbonium);
	    ItemStack stackingotosmiuum = new ItemStack(Carpentercraft.ingotosmium);
	    ItemStack stackoreosmium = new ItemStack(Carpentercraft.oreosmium);
	    ItemStack stackoresilver = new ItemStack(Carpentercraft.oresilver);
	    ItemStack stackoretin = new ItemStack(Carpentercraft.oretin);
	    
	    
	    		/* Items */
		GameRegistry.addRecipe(stackNether_Star, "igi", "gdg", "igi",
				'i', stackIron_Block, 'g', stackGold_Block, 'd', stackDiamond_Block);

	    GameRegistry.addRecipe(stackingotfire, new Object[] { "fff", "dgd", "fff", 
	      Character.valueOf('f'), stackBlaze_Powder, Character.valueOf('d'), stackDiamond, Character.valueOf('g'), stackGold_Ingot });
	    GameRegistry.addRecipe(stackingotdiamondium, new Object[] { "dgd", "gdg", "dgd", 
	      Character.valueOf('d'), stackDiamond, Character.valueOf('g'), stackGold_Ingot });
	    GameRegistry.addRecipe(stackingotsteel, new Object[] { "ip ", "p  ", "   ", 
	      Character.valueOf('i'), stackIron_Ingot, Character.valueOf('p'), stackpelletcoal });
	    GameRegistry.addRecipe(stackingotbronze, new Object[] { "ct ", "cc ", "   ", 
	      Character.valueOf('c'), stackingotcopper, Character.valueOf('t'), stackingottin });
	    GameRegistry.addRecipe(stackingotlumium, new Object[] { "tt ", "ts ", "g  ", 
	      Character.valueOf('t'), stackingottin, Character.valueOf('s'), stackingotsilver, Character.valueOf('g'), stackGlowstone });
	    GameRegistry.addRecipe(stackingotsignalum, new Object[] { "cc ", "cs ", "br ", 
	      Character.valueOf('c'), stackingotcopper, Character.valueOf('s'), stackingotsilver, Character.valueOf('b'), stackRedstone_Block, Character.valueOf('r'), stackRedstone });
	    GameRegistry.addRecipe(stackingotenderium, new Object[] { "ttp", "ssp", "pp ", 
	      Character.valueOf('s'), stackingotsilver, Character.valueOf('p'), stackEnder_Pearl });
	    GameRegistry.addRecipe(stackingotbonium, new Object[] { " b ", "bib", " b ", 
	      Character.valueOf('b'), stackBone, Character.valueOf('i'), stackIron_Ingot });
	    GameRegistry.addRecipe(stackingotcarbonium, new Object[] { "pbp", "bib", "pbp", 
	      Character.valueOf('p'), stackpelletcoal, Character.valueOf('b'), stackBone, Character.valueOf('i'), stackIron_Ingot });
	    	    
	    GameRegistry.addShapelessRecipe(stackpelletccoal, new Object[] { stackCoal });
	    
	    GameRegistry.addSmelting(Carpentercraft.orecopper, stackingotcopper, 100.0F);
	    GameRegistry.addSmelting(Carpentercraft.oretin, stackingottin, 100.0F);
	    GameRegistry.addSmelting(Carpentercraft.oreosmium, stackingotosmium, 100.0F);
	    GameRegistry.addSmelting(Carpentercraft.orefakegold, stackGold_Nugget, 100.0F);
	    GameRegistry.addSmelting(Carpentercraft.oregold, stackGold_Ingot, 100.0F);
	    GameRegistry.addSmelting(Carpentercraft.oreiron, stackIron_Ingot, 100.0F);

	}
}
