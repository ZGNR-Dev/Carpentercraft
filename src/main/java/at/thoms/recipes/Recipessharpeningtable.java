package at.thoms.recipes;

import java.util.Map;
import java.util.Map.Entry;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import at.thoms.Carpentercraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Recipessharpeningtable
{
    private static final Recipessharpeningtable SHARPENING_BASE = new Recipessharpeningtable();
    /** The list of smelting results. */
    private final Map<ItemStack, ItemStack> sharpeningList = Maps.<ItemStack, ItemStack>newHashMap();
    
//    private final Table<ItemStack, ItemStack, ItemStack> sharpeninglist = Tables.<ItemStack, ItemStack, ItemStack>newHashMap();

    private int diamondpickaxesharpened = 0;
    
    
    public static Recipessharpeningtable instance()
    {
        return SHARPENING_BASE;
    }

    private Recipessharpeningtable()
    {
    	this.addSmelting(Items.DIAMOND_PICKAXE, new ItemStack(Carpentercraft.diamondpickaxesharpened));
    	this.addSmelting(Items.GOLDEN_PICKAXE, new ItemStack(Carpentercraft.goldpickaxesharpened));
    	this.addSmelting(Items.IRON_PICKAXE, new ItemStack(Carpentercraft.ironpickaxesharpened));
    	this.addSmelting(Items.IRON_AXE, new ItemStack(Carpentercraft.ironaxesharpened));
    	
    	
//        this.addSmelting(Items.PORKCHOP, new ItemStack(Items.COOKED_PORKCHOP), 0.35F);

    }


    /**
     * Adds a smelting recipe using an Item as the input item.
     */
    public void addSmelting(Item input, ItemStack stack)
    {
        this.addSmeltingRecipe(new ItemStack(input, 1, 32767), stack);
    }

    /**
     * Adds a smelting recipe using an ItemStack as the input for the recipe.
     */
    public void addSmeltingRecipe(ItemStack input, ItemStack stack)
    {
        if (getSmeltingResult(input) != null) { net.minecraftforge.fml.common.FMLLog.info("Please don't kill me, Mojang: " + input + " = " + stack); return; }
        this.sharpeningList.put(input, stack);
    }

    /**
     * Returns the smelting result of an item.
     */
    @Nullable
    public ItemStack getSmeltingResult(ItemStack stack)
    {
        for (Entry<ItemStack, ItemStack> entry : this.sharpeningList.entrySet())
        {
            if (this.compareItemStacks(stack, (ItemStack)entry.getKey()))
            {
                return (ItemStack)entry.getValue();
            }
        }

        return null;
    }

    /**
     * Compares two itemstacks to ensure that they are the same. This checks both the item and the metadata of the item.
     */
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public Map<ItemStack, ItemStack> getSmeltingList()
    {
        return this.sharpeningList;
    }

}