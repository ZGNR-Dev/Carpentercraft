package at.thoms.oredict;

import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

import at.thoms.Carpentercraft;
import at.thoms.recipes.Recipestoolcrafter;
import at.thoms.utils.CompatLoadingHelper;
import at.thoms.utils.CompatModNames;

public class toolcrafterCraftingManager
{
    /** The static instance of this class */
    private static final toolcrafterCraftingManager instance = new toolcrafterCraftingManager();
    /** A list of all the recipes added */
    private List<IRecipe> recipes = Lists.newArrayList();

    /**
     * Returns the static instance of this class
     */
    public static toolcrafterCraftingManager getInstance()
    {
        /** The static instance of this class */
        return instance;
    }

    @SuppressWarnings("all")
    private toolcrafterCraftingManager()
    {
        //Add recipes here

        //Vanilla
    	addRecipe(new ItemStack(Carpentercraft.axecarpenter), new Object[]{"III  ", "III  ", "ISSS ", 'I', Items.IRON_INGOT, 'S', Items.STICK});
    	addRecipe(new ItemStack(Carpentercraft.axetimber), new Object[]{"IIII ", "III I","ISSSI", 'I', Items.IRON_INGOT, 'S', Items.STICK});
//        addRecipe(new ItemStack(SHItems.hammerWood), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', "plankWood", 'S', "stickWood"});
//        addRecipe(new ItemStack(SHItems.hammerIron), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', Items.IRON_INGOT, 'S', "stickWood"});
//        addRecipe(new ItemStack(SHItems.hammerGold), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', Items.GOLD_INGOT, 'S', "stickWood"});
//        addRecipe(new ItemStack(SHItems.hammerDiamond), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', Items.DIAMOND, 'S', "stickWood"});

//        addRecipe(new ItemStack(SHItems.hammerMini), new Object[]{" HHH ", " HHH ", "SSSS ", 'H', Items.IRON_INGOT, 'S', "stickWood"});
//        addRecipe(new ItemStack(SHItems.hammerGiant), new Object[]{"HHHHH", "HHDHH", "SSSS ", 'H', Blocks.IRON_BLOCK, 'S', "stickWood", 'D', new ItemStack(Items.DYE, 1, 5)});
//        addRecipe(new ItemStack(SHItems.hammerNetherStar), new Object[]{"HHBHH", "HBNBH", "SSSS ", 'H', Items.DIAMOND, 'B', Blocks.GOLD_BLOCK, 'N', Items.NETHER_STAR, 'S', "stickWood"});

//        addRecipe(new ItemStack(SHItems.excavatorWood), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', "plankWood", 'S', "stickWood"});
//        addRecipe(new ItemStack(SHItems.excavatorIron), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', Items.IRON_INGOT, 'S', "stickWood"});
//        addRecipe(new ItemStack(SHItems.excavatorGold), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', Items.GOLD_INGOT, 'S', "stickWood"});
//        addRecipe(new ItemStack(SHItems.excavatorDiamond), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', Items.DIAMOND, 'S', "stickWood"});

        //Make stone recipes made of compressed cobble when Extra Utilities is installed
        if(CompatLoadingHelper.isModLoaded(CompatModNames.Mods.EXTRA_UTILITIES))
        {
//            Item compressedCobble = (Item) Item.REGISTRY.getObject(new ResourceLocation(Names.ModItemIds.COMPRESSED_COBBLE));
//            ItemStack cobble1x = new ItemStack(compressedCobble, 1, 0);
//            addRecipe(new ItemStack(SHItems.hammerStone), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', cobble1x, 'S', "stickWood"});
//            addRecipe(new ItemStack(SHItems.excavatorStone), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', cobble1x, 'S', "stickWood"});
        }
        else
        {
//            addRecipe(new ItemStack(SHItems.hammerStone), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', Blocks.COBBLESTONE, 'S', "stickWood"});
//            addRecipe(new ItemStack(SHItems.excavatorStone), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', Blocks.COBBLESTONE, 'S', "stickWood"});
        }

        //Modded
//        if(Config.includeOtherModItems)
//        {
            //Botania
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_MANASTEEL))
            {
//                addRecipe(new ItemStack(SHModItems.hammerManasteel), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_MANASTEEL, 'S', "stickWood"});
//                addRecipe(new ItemStack(SHModItems.excavatorManasteel), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_MANASTEEL, 'S', "stickWood"});
            }
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_TERRASTEEL))
            {
//                addRecipe(new ItemStack(SHModItems.hammerTerrasteel), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_TERRASTEEL, 'S', "stickWood"});
//                addRecipe(new ItemStack(SHModItems.excavatorTerrasteel), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_TERRASTEEL, 'S', "stickWood"});
            }
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_ELEMENTIUM))
            {
//                addRecipe(new ItemStack(SHModItems.hammerElementium), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_ELEMENTIUM, 'S', "stickWood"});
//                addRecipe(new ItemStack(SHModItems.excavatorElementium), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_ELEMENTIUM, 'S', "stickWood"});
            }
            //EnderIO
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_DARKSTEEL))
            {
//                addRecipe(new ItemStack(SHModItems.hammerDarksteel), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_DARKSTEEL, 'S', "stickWood"});
//                addRecipe(new ItemStack(SHModItems.excavatorDarksteel), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_DARKSTEEL, 'S', "stickWood"});
            }
            //Extra Utilities
            /*
            if(!Config.useEasyUnstableRecipe && LoaderHelper.doesOreExist(Names.ModOreDicts.INGOT_UNSTABLE))
            {
                addRecipe(new ItemStack(SHModItems.hammerUnstable), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_UNSTABLE, 'S', "stickWood"});
                addRecipe(new ItemStack(SHModItems.excavatorUnstable), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_UNSTABLE, 'S', "stickWood"});
            }
            */
            //MobHunter
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_MACHALITE))
            {
//                addRecipe(new ItemStack(SHModItems.hammerMachalite), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_MACHALITE, 'S', "stickWood"});
//                addRecipe(new ItemStack(SHModItems.excavatorMachalite), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_MACHALITE, 'S', "stickWood"});
            }
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_DRAGONITE))
            {
//                addRecipe(new ItemStack(SHModItems.hammerDragonite), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_DRAGONITE, 'S', "stickWood"});
//                addRecipe(new ItemStack(SHModItems.excavatorDragonite), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_DRAGONITE, 'S', "stickWood"});
            }
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_GOSSAMITE))
            {
//                addRecipe(new ItemStack(SHModItems.hammerGossamite), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_GOSSAMITE, 'S', "stickWood"});
//                addRecipe(new ItemStack(SHModItems.excavatorGossamite), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_GOSSAMITE, 'S', "stickWood"});
            }
            //Misc
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_BRONZE))
            {
//                addRecipe(new ItemStack(SHModItems.hammerBronze), new Object[]{"HHHHH", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_BRONZE, 'S', "stickWood"});
//                addRecipe(new ItemStack(SHModItems.excavatorBronze), new Object[]{" HHH ", "HHHHH", "SSSS ", 'H', Names.ModOreDicts.INGOT_BRONZE, 'S', "stickWood"});
            }
        }
//    }

    public void addRecipe(ItemStack stack, Object ... recipeObj)
    {
        recipes.add(new Recipestoolcrafter(stack, recipeObj));
    }

    public ItemStack findMatchingRecipe(InventoryCrafting invCrafting, World world)
    {
        for(IRecipe irecipe : recipes)
            if(irecipe.matches(invCrafting, world))
                return irecipe.getCraftingResult(invCrafting);

        return null;
    }

    public ItemStack[] getRemainingItems(InventoryCrafting craftMatrix, World worldIn)
    {
        for (IRecipe irecipe : this.recipes)
        {
            if (irecipe.matches(craftMatrix, worldIn))
            {
                return irecipe.getRemainingItems(craftMatrix);
            }
        }

        ItemStack[] aitemstack = new ItemStack[craftMatrix.getSizeInventory()];

        for (int i = 0; i < aitemstack.length; ++i)
        {
            aitemstack[i] = craftMatrix.getStackInSlot(i);
        }

        return aitemstack;
    }

    /**
     * Returns the List<> of all recipes
     */
    public List getRecipeList()
    {
        return this.recipes;
    }
}