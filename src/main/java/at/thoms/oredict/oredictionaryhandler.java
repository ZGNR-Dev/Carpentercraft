package at.thoms.oredict;

import at.thoms.Carpentercraft;
import net.minecraftforge.oredict.OreDictionary;

public class oredictionaryhandler {
	
	public static void registerOreDictionary(){
		
		
		/* Ores */
		
		OreDictionary.registerOre("oreCopper", Carpentercraft.orecopper);
		OreDictionary.registerOre("oreFakegold", Carpentercraft.orefakegold);
		OreDictionary.registerOre("oreGold", Carpentercraft.oregold);
		OreDictionary.registerOre("oreIron", Carpentercraft.oreiron);
		OreDictionary.registerOre("oreOsmium", Carpentercraft.oreosmium);
		OreDictionary.registerOre("oreTin", Carpentercraft.oretin);
		OreDictionary.registerOre("oreSilver", Carpentercraft.oresilver);
		
		/* Ingots */
		
		OreDictionary.registerOre("ingotCopper", Carpentercraft.ingotcopper);
		OreDictionary.registerOre("ingotOsmium", Carpentercraft.ingotosmium);
		OreDictionary.registerOre("ingotTin", Carpentercraft.ingottin);
		OreDictionary.registerOre("ingotSilver", Carpentercraft.ingotsilver);
		
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
