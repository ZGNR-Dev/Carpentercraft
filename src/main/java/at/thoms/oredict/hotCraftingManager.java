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
import at.thoms.recipes.Recipeshotcrafting;
import at.thoms.utils.CompatLoadingHelper;
import at.thoms.utils.CompatModNames;

public class hotCraftingManager
{
    /** The static instance of this class */
    private static final hotCraftingManager instance = new hotCraftingManager();
    /** A list of all the recipes added */
    private List<IRecipe> recipes = Lists.newArrayList();

    /**
     * Returns the static instance of this class
     */
    public static hotCraftingManager getInstance()
    {
        /** The static instance of this class */
        return instance;
    }

    @SuppressWarnings("all")
    private hotCraftingManager()
    {
        //Add recipes here

        //Vanilla
    	addRecipe(new ItemStack(Carpentercraft.axecarpenter), new Object[]{"III", "III", "SSS", 'I', Items.IRON_INGOT, 'S', Items.STICK});
    	addRecipe(new ItemStack(Carpentercraft.axetimber), new Object[]{"II ", "III","ISS", 'I', Items.IRON_INGOT, 'S', Items.STICK});
    	addRecipe(new ItemStack(Carpentercraft.bee), new Object[]{"   ", " A ", "   ", 'A', Items.APPLE});

        //Make stone recipes made of compressed cobble when Extra Utilities is installed
        if(CompatLoadingHelper.isModLoaded(CompatModNames.Mods.EXTRA_UTILITIES))
        {
        }
        else
        {
        }

        //Modded
//        if(Config.includeOtherModItems)
//        {
            //Botania
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_MANASTEEL))
            {
            }
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_TERRASTEEL))
            {
            }
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_ELEMENTIUM))
            {
            }
            //EnderIO
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_DARKSTEEL))
            {
            }
            //MobHunter
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_MACHALITE))
            {
            }
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_DRAGONITE))
            {
            }
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_GOSSAMITE))
            {
            }
            //Misc
            if(CompatLoadingHelper.doesOreExist(CompatModNames.ModOreDicts.INGOT_BRONZE))
            {
            }
        }
//    }

    public void addRecipe(ItemStack stack, Object ... recipeObj)
    {
        recipes.add(new Recipeshotcrafting(stack, recipeObj));
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