package at.thoms.utils;

import com.google.common.base.Strings;

import at.thoms.interfaces.IInventoryContainerItem;
import at.thoms.proxy.proxyoredict;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public final class itemHelper
{
  public static final String BLOCK = "block";
  public static final String ORE = "ore";
  public static final String DUST = "dust";
  public static final String INGOT = "ingot";
  public static final String NUGGET = "nugget";
  public static final String LOG = "log";
  public static proxyoredict oreProxy = new proxyoredict();
  
  private static boolean isPlayerHoldingSomething(EntityPlayer player)
  {
    return (player.getHeldItemMainhand() != null) || (player.getHeldItemOffhand() != null);
  }
  
  public static ItemStack getMainhandStack(EntityPlayer player)
  {
    return player.getHeldItemMainhand();
  }
  
  public static ItemStack getOffhandStack(EntityPlayer player)
  {
    return player.getHeldItemOffhand();
  }
  
  public static ItemStack getHeldStack(EntityPlayer player)
  {
    ItemStack stack = player.getHeldItemMainhand();
    if (stack == null) {
      stack = player.getHeldItemOffhand();
    }
    return stack;
  }
  
  public static ItemStack cloneStack(Item item, int stackSize)
  {
    if (item == null) {
      return null;
    }
    return new ItemStack(item, stackSize);
  }
  
  public static ItemStack cloneStack(Block item, int stackSize)
  {
    if (item == null) {
      return null;
    }
    return new ItemStack(item, stackSize);
  }
  
  public static ItemStack cloneStack(ItemStack stack, int stackSize)
  {
    if (stack == null) {
      return null;
    }
    ItemStack retStack = stack.copy();
    retStack.stackSize = stackSize;
    
    return retStack;
  }
  
  public static ItemStack cloneStack(ItemStack stack)
  {
    return stack == null ? null : stack.copy();
  }
  
  public static ItemStack copyTag(ItemStack container, ItemStack other)
  {
    if ((other != null) && (other.hasTagCompound())) {
      container.setTagCompound(other.getTagCompound().copy());
    }
    return container;
  }
  
  public static NBTTagCompound setItemStackTagName(NBTTagCompound tag, String name)
  {
    if (Strings.isNullOrEmpty(name)) {
      return null;
    }
    if (tag == null) {
      tag = new NBTTagCompound();
    }
    if (!tag.hasKey("display")) {
      tag.setTag("display", new NBTTagCompound());
    }
    tag.getCompoundTag("display").setString("Name", name);
    
    return tag;
  }
  
  public static ItemStack readItemStackFromNBT(NBTTagCompound nbt)
  {
    ItemStack stack = new ItemStack(Item.getItemById(nbt.getShort("id")));
    stack.stackSize = nbt.getInteger("Count");
    stack.setItemDamage(Math.max(0, nbt.getShort("Damage")));
    if (nbt.hasKey("tag", 10)) {
      stack.setTagCompound(nbt.getCompoundTag("tag"));
    }
    return stack;
  }
  
  public static NBTTagCompound writeItemStackToNBT(ItemStack stack, NBTTagCompound nbt)
  {
    nbt.setShort("id", (short)Item.getIdFromItem(stack.getItem()));
    nbt.setInteger("Count", stack.stackSize);
    nbt.setShort("Damage", (short)getItemDamage(stack));
    if (stack.hasTagCompound()) {
      nbt.setTag("tag", stack.getTagCompound());
    }
    return nbt;
  }
  
  public static NBTTagCompound writeItemStackToNBT(ItemStack stack, int amount, NBTTagCompound nbt)
  {
    nbt.setShort("id", (short)Item.getIdFromItem(stack.getItem()));
    nbt.setInteger("Count", amount);
    nbt.setShort("Damage", (short)getItemDamage(stack));
    if (stack.hasTagCompound()) {
      nbt.setTag("tag", stack.getTagCompound());
    }
    return nbt;
  }
  
  public static String getNameFromItemStack(ItemStack stack)
  {
    if ((stack == null) || (!stack.hasTagCompound()) || (!stack.getTagCompound().hasKey("display"))) {
      return "";
    }
    return stack.getTagCompound().getCompoundTag("display").getString("Name");
  }
  
  public static ItemStack damageItem(ItemStack stack, int amt, Random rand)
  {
    if ((stack != null) && (stack.isItemStackDamageable()) && (stack.attemptDamageItem(amt, rand))) {
      if (--stack.stackSize <= 0) {
        stack = null;
      } else {
        stack.setItemDamage(0);
      }
    }
    return stack;
  }
  
  public static ItemStack consumeItem(ItemStack stack)
  {
    if (stack == null) {
      return null;
    }
    Item item = stack.getItem();
    boolean largerStack = stack.stackSize > 1;
    if (largerStack) {
      stack.stackSize -= 1;
    }
    if (item.hasContainerItem(stack))
    {
      ItemStack ret = item.getContainerItem(stack);
      if (ret == null) {
        return null;
      }
      if ((ret.isItemStackDamageable()) && (ret.getItemDamage() > ret.getMaxDamage())) {
        ret = null;
      }
      return ret;
    }
    return largerStack ? stack : null;
  }
  
  public static ItemStack consumeItem(ItemStack stack, EntityPlayer player)
  {
    if (stack == null) {
      return null;
    }
    Item item = stack.getItem();
    boolean largerStack = stack.stackSize > 1;
    if (largerStack) {
      stack.stackSize -= 1;
    }
    if (item.hasContainerItem(stack))
    {
      ItemStack ret = item.getContainerItem(stack);
      if ((ret == null) || ((ret.isItemStackDamageable()) && (ret.getItemDamage() > ret.getMaxDamage()))) {
        ret = null;
      }
      if (stack.stackSize < 1) {
        return ret;
      }
      if ((ret != null) && (!player.inventory.addItemStackToInventory(ret))) {
        player.dropItem(ret, false, true);
      }
    }
    return largerStack ? stack : null;
  }
  
  public static boolean disposePlayerItem(ItemStack stack, ItemStack dropStack, EntityPlayer entityplayer, boolean allowDrop)
  {
    return disposePlayerItem(stack, dropStack, entityplayer, allowDrop, true);
  }
  
  public static boolean disposePlayerItem(ItemStack stack, ItemStack dropStack, EntityPlayer entityplayer, boolean allowDrop, boolean allowReplace)
  {
    if ((entityplayer == null) || (entityplayer.capabilities.isCreativeMode)) {
      return true;
    }
    if ((allowReplace) && (stack.stackSize <= 1))
    {
      entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
      entityplayer.inventory.addItemStackToInventory(dropStack);
      return true;
    }
    if (allowDrop)
    {
      stack.stackSize -= 1;
      if ((dropStack != null) && (!entityplayer.inventory.addItemStackToInventory(dropStack))) {
        entityplayer.dropItem(dropStack, false, true);
      }
      return true;
    }
    return false;
  }
  
  public static int getItemDamage(ItemStack stack)
  {
    return Items.DIAMOND.getDamage(stack);
  }
  

  
  public static ItemStack getOre(String oreName)
  {
    return oreProxy.getOre(oreName);
  }
  
  public static String getOreName(ItemStack stack)
  {
    return oreProxy.getOreName(stack);
  }
  
  public static boolean isOreIDEqual(ItemStack stack, int oreID)
  {
    return oreProxy.isOreIDEqual(stack, oreID);
  }
  
  public static boolean isOreNameEqual(ItemStack stack, String oreName)
  {
    return oreProxy.isOreNameEqual(stack, oreName);
  }
  
  public static boolean oreNameExists(String oreName){

	    return OreDictionary.doesOreNameExist(oreName);
	    
  }
  
  public static boolean hasOreName(ItemStack stack)
  {
    return !getOreName(stack).equals("Unknown");
  }
  
  public static boolean isBlock(ItemStack stack)
  {
    return getOreName(stack).startsWith("block");
  }
  
  public static boolean isOre(ItemStack stack)
  {
    return getOreName(stack).startsWith("ore");
  }
  
  public static boolean isDust(ItemStack stack)
  {
    return getOreName(stack).startsWith("dust");
  }
  
  public static boolean isIngot(ItemStack stack)
  {
    return getOreName(stack).startsWith("ingot");
  }
  
  public static boolean isNugget(ItemStack stack)
  {
    return getOreName(stack).startsWith("nugget");
  }
  
  public static boolean isLog(ItemStack stack)
  {
    return getOreName(stack).startsWith("log");
  }
  
  public static ItemStack stack(Item t)
  {
    return new ItemStack(t);
  }
  
  public static ItemStack stack(Item t, int s)
  {
    return new ItemStack(t, s);
  }
  
  public static ItemStack stack(Item t, int s, int m)
  {
    return new ItemStack(t, s, m);
  }
  
  public static ItemStack stack(Block t)
  {
    return new ItemStack(t);
  }
  
  public static ItemStack stack(Block t, int s)
  {
    return new ItemStack(t, s);
  }
  
  public static ItemStack stack(Block t, int s, int m)
  {
    return new ItemStack(t, s, m);
  }
  
  public static ItemStack stack2(Item t)
  {
    return new ItemStack(t, 1, 32767);
  }
  
  public static ItemStack stack2(Item t, int s)
  {
    return new ItemStack(t, s, 32767);
  }
  
  public static ItemStack stack2(Block t)
  {
    return new ItemStack(t, 1, 32767);
  }
  
  public static ItemStack stack2(Block t, int s)
  {
    return new ItemStack(t, s, 32767);
  }
  
  public static IRecipe ShapedRecipe(Block result, Object... recipe)
  {
    return new ShapedOreRecipe(result, recipe);
  }
  
  public static IRecipe ShapedRecipe(Item result, Object... recipe)
  {
    return new ShapedOreRecipe(result, recipe);
  }
  
  public static IRecipe ShapedRecipe(ItemStack result, Object... recipe)
  {
    return new ShapedOreRecipe(result, recipe);
  }
  
  public static IRecipe ShapedRecipe(Block result, int s, Object... recipe)
  {
    return new ShapedOreRecipe(stack(result, s), recipe);
  }
  
  public static IRecipe ShapedRecipe(Item result, int s, Object... recipe)
  {
    return new ShapedOreRecipe(stack(result, s), recipe);
  }
  
  public static IRecipe ShapedRecipe(ItemStack result, int s, Object... recipe)
  {
    return new ShapedOreRecipe(cloneStack(result, s), recipe);
  }
  
  public static IRecipe ShapelessRecipe(Block result, Object... recipe)
  {
    return new ShapelessOreRecipe(result, recipe);
  }
  
  public static IRecipe ShapelessRecipe(Item result, Object... recipe)
  {
    return new ShapelessOreRecipe(result, recipe);
  }
  
  public static IRecipe ShapelessRecipe(ItemStack result, Object... recipe)
  {
    return new ShapelessOreRecipe(result, recipe);
  }
  
  public static IRecipe ShapelessRecipe(Block result, int s, Object... recipe)
  {
    return new ShapelessOreRecipe(stack(result, s), recipe);
  }
  
  public static IRecipe ShapelessRecipe(Item result, int s, Object... recipe)
  {
    return new ShapelessOreRecipe(stack(result, s), recipe);
  }
  
  public static IRecipe ShapelessRecipe(ItemStack result, int s, Object... recipe)
  {
    return new ShapelessOreRecipe(cloneStack(result, s), recipe);
  }
  
  public static boolean addGearRecipe(ItemStack gear, String ingot)
  {
    if ((gear == null) || (!oreNameExists(ingot))) {
      return false;
    }
    GameRegistry.addRecipe(ShapedRecipe(gear, new Object[] { " X ", "XIX", " X ", Character.valueOf('X'), ingot, Character.valueOf('I'), "ingotIron" }));
    return true;
  }
  
  public static boolean addGearRecipe(ItemStack gear, String ingot, String center)
  {
    if ((gear == null) || (!oreNameExists(ingot)) || (!oreNameExists(center))) {
      return false;
    }
    GameRegistry.addRecipe(ShapedRecipe(gear, new Object[] { " X ", "XIX", " X ", Character.valueOf('X'), ingot, Character.valueOf('I'), center }));
    return true;
  }
  
  public static boolean addGearRecipe(ItemStack gear, String ingot, ItemStack center)
  {
    if ((((gear == null ? 1 : 0) | (center == null ? 1 : 0)) != 0) || (!oreNameExists(ingot))) {
      return false;
    }
    GameRegistry.addRecipe(ShapedRecipe(gear, new Object[] { " X ", "XIX", " X ", Character.valueOf('X'), ingot, Character.valueOf('I'), center }));
    return true;
  }
  
  public static boolean addGearRecipe(ItemStack gear, ItemStack ingot, String center)
  {
    if ((((gear == null ? 1 : 0) | (ingot == null ? 1 : 0)) != 0) || (!oreNameExists(center))) {
      return false;
    }
    GameRegistry.addRecipe(ShapedRecipe(gear, new Object[] { " X ", "XIX", " X ", Character.valueOf('X'), ingot, Character.valueOf('I'), center }));
    return true;
  }
  
  public static boolean addGearRecipe(ItemStack gear, ItemStack ingot, ItemStack center)
  {
    if (((gear == null ? 1 : 0) | (ingot == null ? 1 : 0) | (center == null ? 1 : 0)) != 0) {
      return false;
    }
    GameRegistry.addRecipe(cloneStack(gear), new Object[] { " X ", "XIX", " X ", Character.valueOf('X'), cloneStack(ingot, 1), Character.valueOf('I'), cloneStack(center, 1) });
    return true;
  }
  
  public static boolean addRotatedGearRecipe(ItemStack gear, String ingot, String center)
  {
    if ((gear == null) || (!oreNameExists(ingot)) || (!oreNameExists(center))) {
      return false;
    }
    GameRegistry.addRecipe(ShapedRecipe(gear, new Object[] { "X X", " I ", "X X", Character.valueOf('X'), ingot, Character.valueOf('I'), center }));
    return true;
  }
  
  public static boolean addRotatedGearRecipe(ItemStack gear, String ingot, ItemStack center)
  {
    if ((((gear == null ? 1 : 0) | (center == null ? 1 : 0)) != 0) || (!oreNameExists(ingot))) {
      return false;
    }
    GameRegistry.addRecipe(ShapedRecipe(gear, new Object[] { "X X", " I ", "X X", Character.valueOf('X'), ingot, Character.valueOf('I'), center }));
    return true;
  }
  
  public static boolean addRotatedGearRecipe(ItemStack gear, ItemStack ingot, String center)
  {
    if ((((gear == null ? 1 : 0) | (ingot == null ? 1 : 0)) != 0) || (!oreNameExists(center))) {
      return false;
    }
    GameRegistry.addRecipe(ShapedRecipe(gear, new Object[] { "X X", " I ", "X X", Character.valueOf('X'), ingot, Character.valueOf('I'), center }));
    return true;
  }
  
  public static boolean addRotatedGearRecipe(ItemStack gear, ItemStack ingot, ItemStack center)
  {
    if (((gear == null ? 1 : 0) | (ingot == null ? 1 : 0) | (center == null ? 1 : 0)) != 0) {
      return false;
    }
    GameRegistry.addRecipe(cloneStack(gear), new Object[] { "X X", " I ", "X X", Character.valueOf('X'), cloneStack(ingot, 1), Character.valueOf('I'), cloneStack(center, 1) });
    return true;
  }
  
  public static boolean addSurroundRecipe(ItemStack out, ItemStack one, ItemStack eight)
  {
    if (((out == null ? 1 : 0) | (one == null ? 1 : 0) | (eight == null ? 1 : 0)) != 0) {
      return false;
    }
    GameRegistry.addRecipe(cloneStack(out), new Object[] { "XXX", "XIX", "XXX", Character.valueOf('X'), cloneStack(eight, 1), Character.valueOf('I'), cloneStack(one, 1) });
    return true;
  }
  
  public static boolean addSurroundRecipe(ItemStack out, String one, ItemStack eight)
  {
    if ((((out == null ? 1 : 0) | (eight == null ? 1 : 0)) != 0) || (!oreNameExists(one))) {
      return false;
    }
    GameRegistry.addRecipe(ShapedRecipe(out, new Object[] { "XXX", "XIX", "XXX", Character.valueOf('X'), eight, Character.valueOf('I'), one }));
    return true;
  }
  
  public static boolean addSurroundRecipe(ItemStack out, ItemStack one, String eight)
  {
    if ((((out == null ? 1 : 0) | (one == null ? 1 : 0)) != 0) || (!oreNameExists(eight))) {
      return false;
    }
    GameRegistry.addRecipe(ShapedRecipe(out, new Object[] { "XXX", "XIX", "XXX", Character.valueOf('X'), eight, Character.valueOf('I'), one }));
    return true;
  }
  
  public static boolean addSurroundRecipe(ItemStack out, String one, String eight)
  {
    if ((out == null) || (!oreNameExists(one)) || (!oreNameExists(eight))) {
      return false;
    }
    GameRegistry.addRecipe(ShapedRecipe(out, new Object[] { "XXX", "XIX", "XXX", Character.valueOf('X'), eight, Character.valueOf('I'), one }));
    return true;
  }
  
  public static boolean addFenceRecipe(ItemStack out, ItemStack in)
  {
    if (((out == null ? 1 : 0) | (in == null ? 1 : 0)) != 0) {
      return false;
    }
    GameRegistry.addRecipe(cloneStack(out), new Object[] { "XXX", "XXX", Character.valueOf('X'), cloneStack(in, 1) });
    return true;
  }
  
  public static boolean addFenceRecipe(ItemStack out, String in)
  {
    if ((out == null) || (!oreNameExists(in))) {
      return false;
    }
    GameRegistry.addRecipe(ShapedRecipe(out, new Object[] { "XXX", "XXX", Character.valueOf('X'), in }));
    return true;
  }
  
  public static boolean addReverseStorageRecipe(ItemStack nine, String one)
  {
    if ((nine == null) || (!oreNameExists(one))) {
      return false;
    }
    GameRegistry.addRecipe(ShapelessRecipe(cloneStack(nine, 9), new Object[] { one }));
    return true;
  }
  
  public static boolean addReverseStorageRecipe(ItemStack nine, ItemStack one)
  {
    if (((nine == null ? 1 : 0) | (one == null ? 1 : 0)) != 0) {
      return false;
    }
    GameRegistry.addShapelessRecipe(cloneStack(nine, 9), new Object[] { cloneStack(one, 1) });
    return true;
  }
  
  public static boolean addSmallReverseStorageRecipe(ItemStack four, String one)
  {
    if ((four == null) || (!oreNameExists(one))) {
      return false;
    }
    GameRegistry.addRecipe(ShapelessRecipe(cloneStack(four, 4), new Object[] { one }));
    return true;
  }
  
  public static boolean addSmallReverseStorageRecipe(ItemStack four, ItemStack one)
  {
    if (((four == null ? 1 : 0) | (one == null ? 1 : 0)) != 0) {
      return false;
    }
    GameRegistry.addShapelessRecipe(cloneStack(four, 4), new Object[] { cloneStack(one, 1) });
    return true;
  }
  
  public static boolean addStorageRecipe(ItemStack one, String nine)
  {
    if ((one == null) || (!oreNameExists(nine))) {
      return false;
    }
    GameRegistry.addRecipe(ShapelessRecipe(one, new Object[] { nine, nine, nine, nine, nine, nine, nine, nine, nine }));
    return true;
  }
  
  public static boolean addStorageRecipe(ItemStack one, ItemStack nine)
  {
    if (((one == null ? 1 : 0) | (nine == null ? 1 : 0)) != 0) {
      return false;
    }
    nine = cloneStack(nine, 1);
    GameRegistry.addShapelessRecipe(one, new Object[] { nine, nine, nine, nine, nine, nine, nine, nine, nine });
    return true;
  }
  
  public static boolean addSmallStorageRecipe(ItemStack one, String four)
  {
    if ((one == null) || (!oreNameExists(four))) {
      return false;
    }
    GameRegistry.addRecipe(ShapedRecipe(one, new Object[] { "XX", "XX", Character.valueOf('X'), four }));
    return true;
  }
  
  public static boolean addSmallStorageRecipe(ItemStack one, ItemStack four)
  {
    if (((one == null ? 1 : 0) | (four == null ? 1 : 0)) != 0) {
      return false;
    }
    GameRegistry.addRecipe(cloneStack(one), new Object[] { "XX", "XX", Character.valueOf('X'), cloneStack(four, 1) });
    return true;
  }
  
  public static boolean addTwoWayStorageRecipe(ItemStack one, ItemStack nine)
  {
    return (addStorageRecipe(one, nine)) && (addReverseStorageRecipe(nine, one));
  }
  
  public static boolean addTwoWayStorageRecipe(ItemStack one, String one_ore, ItemStack nine, String nine_ore)
  {
    return (addStorageRecipe(one, nine_ore)) && (addReverseStorageRecipe(nine, one_ore));
  }
  
  public static boolean addSmallTwoWayStorageRecipe(ItemStack one, ItemStack four)
  {
    return (addSmallStorageRecipe(one, four)) && (addSmallReverseStorageRecipe(four, one));
  }
  
  public static boolean addSmallTwoWayStorageRecipe(ItemStack one, String one_ore, ItemStack four, String four_ore)
  {
    return (addSmallStorageRecipe(one, four_ore)) && (addSmallReverseStorageRecipe(four, one_ore));
  }
  
  public static boolean addSmelting(ItemStack out, Item in)
  {
    if (((out == null ? 1 : 0) | (in == null ? 1 : 0)) != 0) {
      return false;
    }
    FurnaceRecipes.instance().addSmeltingRecipe(cloneStack(in, 1), cloneStack(out), 0.0F);
    return true;
  }
  
  public static boolean addSmelting(ItemStack out, Block in)
  {
    if (((out == null ? 1 : 0) | (in == null ? 1 : 0)) != 0) {
      return false;
    }
    FurnaceRecipes.instance().addSmeltingRecipe(cloneStack(in, 1), cloneStack(out), 0.0F);
    return true;
  }
  
  public static boolean addSmelting(ItemStack out, ItemStack in)
  {
    if (((out == null ? 1 : 0) | (in == null ? 1 : 0)) != 0) {
      return false;
    }
    FurnaceRecipes.instance().addSmeltingRecipe(cloneStack(in, 1), cloneStack(out), 0.0F);
    return true;
  }
  
  public static boolean addSmelting(ItemStack out, Item in, float XP)
  {
    if (((out == null ? 1 : 0) | (in == null ? 1 : 0)) != 0) {
      return false;
    }
    FurnaceRecipes.instance().addSmeltingRecipe(cloneStack(in, 1), cloneStack(out), XP);
    return true;
  }
  
  public static boolean addSmelting(ItemStack out, Block in, float XP)
  {
    if (((out == null ? 1 : 0) | (in == null ? 1 : 0)) != 0) {
      return false;
    }
    FurnaceRecipes.instance().addSmeltingRecipe(cloneStack(in, 1), cloneStack(out), XP);
    return true;
  }
  
  public static boolean addSmelting(ItemStack out, ItemStack in, float XP)
  {
    if (((out == null ? 1 : 0) | (in == null ? 1 : 0)) != 0) {
      return false;
    }
    FurnaceRecipes.instance().addSmeltingRecipe(cloneStack(in, 1), cloneStack(out), XP);
    return true;
  }
  
  public static boolean addWeakSmelting(ItemStack out, Item in)
  {
    if (((out == null ? 1 : 0) | (in == null ? 1 : 0)) != 0) {
      return false;
    }
    FurnaceRecipes.instance().addSmeltingRecipe(cloneStack(in, 1), cloneStack(out), 0.1F);
    return true;
  }
  
  public static boolean addWeakSmelting(ItemStack out, Block in)
  {
    if (((out == null ? 1 : 0) | (in == null ? 1 : 0)) != 0) {
      return false;
    }
    FurnaceRecipes.instance().addSmeltingRecipe(cloneStack(in, 1), cloneStack(out), 0.1F);
    return true;
  }
  
  public static boolean addWeakSmelting(ItemStack out, ItemStack in)
  {
    if (((out == null ? 1 : 0) | (in == null ? 1 : 0)) != 0) {
      return false;
    }
    FurnaceRecipes.instance().addSmeltingRecipe(cloneStack(in, 1), cloneStack(out), 0.1F);
    return true;
  }
  
  public static boolean addTwoWayConversionRecipe(ItemStack a, ItemStack b)
  {
    if (((a == null ? 1 : 0) | (b == null ? 1 : 0)) != 0) {
      return false;
    }
    GameRegistry.addShapelessRecipe(cloneStack(a, 1), new Object[] { cloneStack(b, 1) });
    GameRegistry.addShapelessRecipe(cloneStack(b, 1), new Object[] { cloneStack(a, 1) });
    return true;
  }
  
  public static void registerWithHandlers(String oreName, ItemStack stack)
  {
    OreDictionary.registerOre(oreName, stack);
    
    FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", stack);
  }
  
  public static void addRecipe(IRecipe recipe)
  {
    GameRegistry.addRecipe(recipe);
  }
  
  public static void addRecipe(ItemStack out, Object... recipe)
  {
    GameRegistry.addRecipe(out, recipe);
  }
  
  public static void addShapedRecipe(ItemStack out, Object... recipe)
  {
    GameRegistry.addRecipe(out, recipe);
  }
  
  public static void addShapedRecipe(Item out, Object... recipe)
  {
    addRecipe(new ItemStack(out), recipe);
  }
  
  public static void addShapedRecipe(Block out, Object... recipe)
  {
    addRecipe(new ItemStack(out), recipe);
  }
  
  public static void addShapelessRecipe(ItemStack out, Object... recipe)
  {
    GameRegistry.addShapelessRecipe(out, recipe);
  }
  
  public static void addShapelessRecipe(Item out, Object... recipe)
  {
    addShapelessRecipe(new ItemStack(out), recipe);
  }
  
  public static void addShapelessRecipe(Block out, Object... recipe)
  {
    addShapelessRecipe(new ItemStack(out), recipe);
  }
  
  public static void addShapedOreRecipe(ItemStack out, Object... recipe)
  {
    GameRegistry.addRecipe(ShapedRecipe(out, recipe));
  }
  
  public static void addShapedOreRecipe(Item out, Object... recipe)
  {
    GameRegistry.addRecipe(ShapedRecipe(out, recipe));
  }
  
  public static void addShapedOreRecipe(Block out, Object... recipe)
  {
    GameRegistry.addRecipe(ShapedRecipe(out, recipe));
  }
  
  public static void addShapelessOreRecipe(ItemStack out, Object... recipe)
  {
    GameRegistry.addRecipe(ShapelessRecipe(out, recipe));
  }
  
  public static void addShapelessOreRecipe(Item out, Object... recipe)
  {
    GameRegistry.addRecipe(ShapelessRecipe(out, recipe));
  }
  
  public static void addShapelessOreRecipe(Block out, Object... recipe)
  {
    GameRegistry.addRecipe(ShapelessRecipe(out, recipe));
  }
    
  public static boolean isPlayerHoldingNothing(EntityPlayer player)
  {
    return getHeldStack(player) == null;
  }
  
  public static Item getItemFromStack(ItemStack theStack)
  {
    return theStack == null ? null : theStack.getItem();
  }
  
  public static boolean areItemsEqual(Item itemA, Item itemB)
  {
    if (((itemA == null ? 1 : 0) | (itemB == null ? 1 : 0)) != 0) {
      return false;
    }
    return (itemA == itemB) || (itemA.equals(itemB));
  }
  
  public static boolean isPlayerHoldingItem(Class<?> item, EntityPlayer player)
  {
    return item.isInstance(getItemFromStack(getHeldStack(player)));
  }
  
  public static boolean isPlayerHoldingItem(Item item, EntityPlayer player)
  {
    return areItemsEqual(item, getItemFromStack(getHeldStack(player)));
  }
  
  public static boolean isPlayerHoldingMainhand(Item item, EntityPlayer player)
  {
    return areItemsEqual(item, getItemFromStack(getMainhandStack(player)));
  }
  
  public static boolean isPlayerHoldingOffhand(Item item, EntityPlayer player)
  {
    return areItemsEqual(item, getItemFromStack(getOffhandStack(player)));
  }
  
  public static boolean isPlayerHoldingItemStack(ItemStack stack, EntityPlayer player)
  {
    return itemsEqualWithMetadata(stack, getHeldStack(player));
  }
  
  public static boolean itemsDamageEqual(ItemStack stackA, ItemStack stackB)
  {
    return ((!stackA.getHasSubtypes()) && (stackA.getMaxDamage() == 0)) || (getItemDamage(stackA) == getItemDamage(stackB));
  }
  
  public static boolean itemsEqualWithoutMetadata(ItemStack stackA, ItemStack stackB)
  {
    if ((stackA == null) || (stackB == null)) {
      return false;
    }
    return areItemsEqual(stackA.getItem(), stackB.getItem());
  }
  
  public static boolean itemsEqualWithoutMetadata(ItemStack stackA, ItemStack stackB, boolean checkNBT)
  {
    return (itemsEqualWithoutMetadata(stackA, stackB)) && ((!checkNBT) || (doNBTsMatch(stackA.getTagCompound(), stackB.getTagCompound())));
  }
  
  public static boolean itemsEqualWithMetadata(ItemStack stackA, ItemStack stackB)
  {
    return (itemsEqualWithoutMetadata(stackA, stackB)) && (itemsDamageEqual(stackA, stackB));
  }
  
  public static boolean itemsEqualWithMetadata(ItemStack stackA, ItemStack stackB, boolean checkNBT)
  {
    return (itemsEqualWithMetadata(stackA, stackB)) && ((!checkNBT) || (doNBTsMatch(stackA.getTagCompound(), stackB.getTagCompound())));
  }
  
  public static boolean itemsIdentical(ItemStack stackA, ItemStack stackB)
  {
    return (itemsEqualWithoutMetadata(stackA, stackB)) && (getItemDamage(stackA) == getItemDamage(stackB)) && (doNBTsMatch(stackA.getTagCompound(), stackB.getTagCompound()));
  }
  
  public static boolean doNBTsMatch(NBTTagCompound nbtA, NBTTagCompound nbtB)
  {
    if (((nbtA == null ? 1 : 0) & (nbtB == null ? 1 : 0)) != 0) {
      return true;
    }
    if (((nbtA != null ? 1 : 0) & (nbtB != null ? 1 : 0)) != 0) {
      return nbtA.equals(nbtB);
    }
    return false;
  }
  
  public static boolean itemsEqualForCrafting(ItemStack stackA, ItemStack stackB)
  {
    return (itemsEqualWithoutMetadata(stackA, stackB)) && ((!stackA.getHasSubtypes()) || (getItemDamage(stackA) == 32767) || (getItemDamage(stackB) == 32767) || (getItemDamage(stackB) == getItemDamage(stackA)));
  }
  
  public static boolean craftingEquivalent(ItemStack checked, ItemStack source, String oreDict, ItemStack output)
  {
    if (itemsEqualForCrafting(checked, source)) {
      return true;
    }
    if ((output != null) && (isBlacklist(output))) {
      return false;
    }
    if ((oreDict == null) || (oreDict.equals("Unknown"))) {
      return false;
    }
    return getOreName(checked).equalsIgnoreCase(oreDict);
  }
  
  public static boolean doOreIDsMatch(ItemStack stackA, ItemStack stackB)
  {
    int id = oreProxy.getOreID(stackA);
    return (id >= 0) && (id == oreProxy.getOreID(stackB));
  }
  
  public static boolean isBlacklist(ItemStack output)
  {
    Item item = output.getItem();
    return (Item.getItemFromBlock(Blocks.BIRCH_STAIRS) == item) || (Item.getItemFromBlock(Blocks.JUNGLE_STAIRS) == item) || (Item.getItemFromBlock(Blocks.OAK_STAIRS) == item) || (Item.getItemFromBlock(Blocks.SPRUCE_STAIRS) == item) || (Item.getItemFromBlock(Blocks.PLANKS) == item) || (Item.getItemFromBlock(Blocks.WOODEN_SLAB) == item);
  }
  
  public static String getItemNBTString(ItemStack theItem, String nbtKey, String invalidReturn)
  {
    return (theItem.getTagCompound() != null) && (theItem.getTagCompound().hasKey(nbtKey)) ? theItem.getTagCompound().getString(nbtKey) : invalidReturn;
  }
  
  public static void addInventoryInformation(ItemStack stack, List<String> list)
  {
    addInventoryInformation(stack, list, 0, Integer.MAX_VALUE);
  }
  
  public static void addInventoryInformation(ItemStack stack, List<String> list, int minSlot, int maxSlot)
  {
    if (stack.getTagCompound() == null)
    {
      return;
    }
    if (((stack.getItem() instanceof IInventoryContainerItem)) && (stack.getTagCompound().hasKey("Accessible")))
    {
      addAccessibleInventoryInformation(stack, list, minSlot, maxSlot);
      return;
    }
    if ((!stack.getTagCompound().hasKey("Inventory", 9)) || (stack.getTagCompound().getTagList("Inventory", stack.getTagCompound().getId()).tagCount() <= 0))
    {
      return;
    }
    NBTTagList nbtList = stack.getTagCompound().getTagList("Inventory", stack.getTagCompound().getId());
    
    ArrayList<ItemStack> containedItems = new ArrayList();
    
    boolean[] visited = new boolean[nbtList.tagCount()];
    for (int i = 0; i < nbtList.tagCount(); i++)
    {
      NBTTagCompound tag = nbtList.getCompoundTagAt(i);
      int slot = tag.getInteger("Slot");
      if ((visited[i] == false) && (slot >= minSlot) && (slot <= maxSlot))    //steht original 0
      {
        visited[i] = true;
        ItemStack curStack = ItemStack.loadItemStackFromNBT(tag);
        if (curStack != null)
        {
          containedItems.add(curStack);
          for (int j = 0; j < nbtList.tagCount(); j++)
          {
            NBTTagCompound tag2 = nbtList.getCompoundTagAt(j);
            int slot2 = tag.getInteger("Slot");
            if ((visited[j] == false) && (slot2 >= minSlot) && (slot2 <= maxSlot))    //steht original 0
            {
              ItemStack curStack2 = ItemStack.loadItemStackFromNBT(tag2);
              if (curStack2 != null) {
                if (itemsIdentical(curStack, curStack2))
                {
                  curStack.stackSize += curStack2.stackSize;
                  visited[j] = true;
                }
              }
            }
          }
        }
      }
    }
    if (containedItems.size() > 0) {
    }
    for (ItemStack item : containedItems)
    {
      int maxStackSize = item.getMaxStackSize();
    }
  }
  
  public static void addAccessibleInventoryInformation(ItemStack stack, List<String> list, int minSlot, int maxSlot)
  {
    int invSize = ((IInventoryContainerItem)stack.getItem()).getsizeInventory(stack);
    
    ArrayList<ItemStack> containedItems = new ArrayList();
    
    boolean[] visited = new boolean[invSize];
    
    NBTTagCompound tag = stack.getTagCompound();
    if (tag.hasKey("Inventory")) {
      tag = tag.getCompoundTag("Inventory");
    }
    for (int i = minSlot; i < Math.min(invSize, maxSlot); i++) {
      if (visited[i] == false) {  //steht original 0
        if (tag.hasKey("Slot" + i))
        {
          ItemStack curStack = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Slot" + i));
          visited[i] = true;
          if (curStack != null)
          {
            containedItems.add(curStack);
            for (int j = minSlot; j < Math.min(invSize, maxSlot); j++) {
              if (visited[j] == false) {  //steht original 0
                if (tag.hasKey("Slot" + j))
                {
                  ItemStack curStack2 = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Slot" + j));
                  if (curStack2 != null) {
                    if (itemsIdentical(curStack, curStack2))
                    {
                      curStack.stackSize += curStack2.stackSize;
                      visited[j] = true;
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  public static boolean areItemStacksEqualIgnoreTags(ItemStack stackA, ItemStack stackB, String... nbtTagsToIgnore)
  {
    if ((stackA == null) && (stackB == null)) {
      return true;
    }
    if ((stackA == null) && (stackB != null)) {
      return false;
    }
    if ((stackA != null) && (stackB == null)) {
      return false;
    }
    if (stackA.getItem() != stackB.getItem()) {
      return false;
    }
    if (stackA.getItemDamage() != stackB.getItemDamage()) {
      return false;
    }
    if (stackA.stackSize != stackB.stackSize) {
      return false;
    }
    if ((stackA.getTagCompound() == null) && (stackB.getTagCompound() == null)) {
      return true;
    }
    if ((stackA.getTagCompound() == null) && (stackB.getTagCompound() != null)) {
      return false;
    }
    if ((stackA.getTagCompound() != null) && (stackB.getTagCompound() == null)) {
      return false;
    }
    int numberOfKeys = stackA.getTagCompound().getKeySet().size();
    if (numberOfKeys != stackB.getTagCompound().getKeySet().size()) {
      return false;
    }
    NBTTagCompound tagA = stackA.getTagCompound();
    NBTTagCompound tagB = stackB.getTagCompound();
    
    String[] keys = new String[numberOfKeys];
    keys = (String[])tagA.getKeySet().toArray(keys);
    label254:
    for (int i = 0; i < numberOfKeys; i++)
    {
      for (int j = 0; j < nbtTagsToIgnore.length; j++) {
        if (nbtTagsToIgnore[j].equals(keys[i])) {
          break label254;
        }
      }
      if (!tagA.getTag(keys[i]).equals(tagB.getTag(keys[i]))) {
        return false;
      }
    }
    return true;
  }
}