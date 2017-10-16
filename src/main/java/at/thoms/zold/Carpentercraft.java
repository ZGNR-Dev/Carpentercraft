/* package at.thoms.zold;

import at.thoms.proxy.proxycommons;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;


@Mod(modid = Carpentercraft.MODID, version = Carpentercraft.VERSION)
public class Carpentercraft {

    public static final String MODID = "carpentercraft";
    public static final String VERSION = "0.0.1a";
    
    
    /* Proxys */
    /*
    @SidedProxy(clientSide = "proxy.proxyclient", serverSide = "proxy.proxycommons")
    public static proxycommons proxy;
    
    
    
    /* Ores */
    /*
    public static Block orecopper = new at.thoms.blocks.orecopper();
    public static Block orefakegold = new at.thoms.blocks.orefakegold();
    public static Block oregold = new at.thoms.blocks.oregold();
    public static Block oreiron = new at.thoms.blocks.oreiron();
    public static Block oreosmium = new at.thoms.blocks.oreosmium();
    public static Block oretin = new at.thoms.blocks.oretin();
    public static Block oretungsten = new at.thoms.blocks.oretungsten();
    
    /* Blöcke */
/*
    public static Block sandglow = new at.thoms.blocks.sandglow();
    public static Block glasglow = new at.thoms.blocks.glasglow();
    public static Block glassglowraw = new at.thoms.blocks.glassglowraw();
    
   /*
    
     @EventHandler
    public void preinit(FMLPreInitializationEvent preinit){
    	//System.out.println("Was machst du heute noch so?");
    	
    	 
    	 
    	GameRegistry.registerBlock(orecopper, "orecopper" );
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(orecopper), 0, new ModelResourceLocation("carpentercraft:orecopper", "inventory" ));
    	
        GameRegistry.registerBlock(orefakegold, "orefakegold" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(orefakegold), 0, new ModelResourceLocation("carpentercraft:orefakegold", "inventory" ));

    	GameRegistry.registerBlock(oregold, "oregold");
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(oregold), 0, new ModelResourceLocation("carpentercraft:oregold", "inventory"));
    		
        GameRegistry.registerBlock(oreiron, "oreiron" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(oreiron), 0, new ModelResourceLocation("carpentercraft:oreiron", "inventory"));
    		
        GameRegistry.registerBlock(oreosmium, "oreosmium" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(oreosmium), 0, new ModelResourceLocation("carpentercraft:oreosmium", "inventory"));
    		
        GameRegistry.registerBlock(oretin, "oretin" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(oretin), 0, new ModelResourceLocation("carpentercraft:oretin", "inventory"));
    		
        GameRegistry.registerBlock(oretungsten, "oretungsten" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(oretungsten), 0, new ModelResourceLocation("carpentercraft:oretungsten", "inventory"));
    		
        GameRegistry.registerBlock(sandglow, "sandglow" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(sandglow), 0, new ModelResourceLocation("carpentercraft:sandglow", "inventory"));

    	GameRegistry.registerBlock(glasglow, "glasglow" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(null), 0, new ModelResourceLocation("carpentercraft:glassglow", "inventory"));

    	GameRegistry.registerBlock(glassglowraw, "glassglowraw" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(glassglowraw), 0, new ModelResourceLocation("carpentercraft:glassglowraw", "inventory"));



    }
   
    
    
    @EventHandler
    public void init(FMLInitializationEvent init){

    	System.out.println("Die Initphase.");
    	
    	ItemStack stackIron_Block  = new ItemStack(Blocks.IRON_BLOCK);
		ItemStack stackGold_Block = new ItemStack(Blocks.GOLD_BLOCK);
		ItemStack stackDiamond_Block = new ItemStack(Blocks.DIAMOND_BLOCK);
		ItemStack stackNether_Star = new ItemStack(Items.NETHER_STAR);
		
		*/
		
		/* Items */
		/*GameRegistry.addRecipe(stackNether_Star, "igi", "gdg", "igi",
				'i', stackIron_Block, 'g', stackGold_Block, 'd', stackDiamond_Block);
    	
    }
    
    */
    
   /* 
    @EventHandler
    public void preInit(FMLPreInitializationEvent preinit){
    	
    	//System.out.println("Was machst du heute noch so?");
    	/*
    	GameRegistry.registerBlock(orecopper, "orecopper" );
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(orecopper), 0, new ModelResourceLocation("carpentercraft:orecopper", "inventory" ));
    	
        GameRegistry.registerBlock(orefakegold, "orefakegold" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(orefakegold), 0, new ModelResourceLocation("carpentercraft:orefakegold", "inventory" ));

    	GameRegistry.registerBlock(oregold, "oregold");
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(oregold), 0, new ModelResourceLocation("carpentercraft:oregold", "inventory"));
    		
        GameRegistry.registerBlock(oreiron, "oreiron" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(oreiron), 0, new ModelResourceLocation("carpentercraft:oreiron", "inventory"));
    		
        GameRegistry.registerBlock(oreosmium, "oreosmium" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(oreosmium), 0, new ModelResourceLocation("carpentercraft:oreosmium", "inventory"));
    		
        GameRegistry.registerBlock(oretin, "oretin" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(oretin), 0, new ModelResourceLocation("carpentercraft:oretin", "inventory"));
    		
        GameRegistry.registerBlock(oretungsten, "oretungsten" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(oretungsten), 0, new ModelResourceLocation("carpentercraft:oretungsten", "inventory"));
    		
        GameRegistry.registerBlock(sandglow, "sandglow" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(sandglow), 0, new ModelResourceLocation("carpentercraft:sandglow", "inventory"));

    	GameRegistry.registerBlock(glasglow, "glasglow" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(null), 0, new ModelResourceLocation("carpentercraft:glassglow", "inventory"));

    	GameRegistry.registerBlock(glassglowraw, "glassglowraw" );
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(glassglowraw), 0, new ModelResourceLocation("carpentercraft:glassglowraw", "inventory"));

		*/ /*
    }
    
    @EventHandler
    public void init(FMLInitializationEvent init){
    	
    
    	ItemStack stackIron_Block  = new ItemStack(Blocks.IRON_BLOCK);
		ItemStack stackGold_Block = new ItemStack(Blocks.GOLD_BLOCK);
		ItemStack stackDiamond_Block = new ItemStack(Blocks.DIAMOND_BLOCK);
		ItemStack stackNether_Star = new ItemStack(Items.NETHER_STAR);
		
		/* Items */ /*
		GameRegistry.addRecipe(stackNether_Star, "igi", "gdg", "igi",
				'i', stackIron_Block, 'g', stackGold_Block, 'd', stackDiamond_Block);

    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent postinit){
    	
    	System.out.println("Bereit, die Welt zu übernehmen?");
    	
    }
}
*/