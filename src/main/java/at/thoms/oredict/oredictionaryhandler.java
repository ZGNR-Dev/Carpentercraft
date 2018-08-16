package at.thoms.oredict;

import at.thoms.Carpentercraft;
import at.thoms.utils.ModBlocks;
import net.minecraftforge.oredict.OreDictionary;

public class oredictionaryhandler {
	
	public static void registerOreDictionary(){
		
		
		/* Ores */
		
		OreDictionary.registerOre("oreCopper", ModBlocks.orecopper);
		OreDictionary.registerOre("oreFakegold", ModBlocks.orefakegold);
		OreDictionary.registerOre("oreGold", ModBlocks.oregold);
		OreDictionary.registerOre("oreIron", ModBlocks.oreiron);
		OreDictionary.registerOre("oreOsmium", ModBlocks.oreosmium);
		OreDictionary.registerOre("oreTin", ModBlocks.oretin);
		OreDictionary.registerOre("oreSilver", ModBlocks.oresilver);
		OreDictionary.registerOre("orePlutonium", ModBlocks.oreplutonium);
		
		/* Ingots */
		
		OreDictionary.registerOre("ingotCopper", Carpentercraft.ingotcopper);
		OreDictionary.registerOre("ingotOsmium", Carpentercraft.ingotosmium);
		OreDictionary.registerOre("ingotTin", Carpentercraft.ingottin);
		OreDictionary.registerOre("ingotSilver", Carpentercraft.ingotsilver);
		OreDictionary.registerOre("ingotplutonium", Carpentercraft.ingotplutonium);
		
		/* Ingots Alloys */
		
		OreDictionary.registerOre("ingotBonium", Carpentercraft.ingotbonium);
		OreDictionary.registerOre("ingotCarbonium", Carpentercraft.ingotcarbonium);
		OreDictionary.registerOre("ingotBronze", Carpentercraft.ingotbronze);
		OreDictionary.registerOre("ingotDiamondium", Carpentercraft.ingotdiamondium);
		OreDictionary.registerOre("ingotEnderium", Carpentercraft.ingotenderium);
		OreDictionary.registerOre("ingotFire", Carpentercraft.ingotfire);
		OreDictionary.registerOre("ingotLumium", Carpentercraft.ingotlumium);
		OreDictionary.registerOre("ingotSignalum", Carpentercraft.ingotsignalum);
		OreDictionary.registerOre("ingotSteel", Carpentercraft.ingotsteel);
		
	}

}
