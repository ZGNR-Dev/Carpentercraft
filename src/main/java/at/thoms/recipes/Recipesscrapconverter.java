package at.thoms.recipes;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import at.thoms.tileentitys.TileEntityscrapconverter;
import at.thoms.utils.ItemStackKey;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipesscrapconverter
{
  static class FoundryRecipe
  {
    ItemStackKey source;
    HashMap<ItemStackKey, Float> result;
    float fire;
    float explosion;
    
    public FoundryRecipe() {}
    
    public FoundryRecipe(String s)
    {
      this.result = new HashMap();
      int i = s.indexOf("={");
      if (i != -1) {
        this.source = ItemStackKey.createKey(s.substring(0, i).trim());
      } else {
        this.source = ItemStackKey.createKey(s.trim());
      }
      i = s.indexOf("{");
      if (i != -1)
      {
        s = s.substring(i + 1, s.length() - 1).trim();
        if (s.length() > 0)
        {
          String[] data = s.split(",");
          for (int j = 0; j < data.length; j++)
          {
            String[] data2 = data[j].split("=");
            data2[0] = data2[0].trim();
            data2[1] = data2[1].trim();
            if (data2[0].equals("fire"))
            {
              this.fire = Float.parseFloat(data2[1]);
            }
            else if (data2[0].equals("explosion"))
            {
              this.explosion = Float.parseFloat(data2[1]);
            }
            else
            {
              ItemStackKey key = ItemStackKey.createKey(data2[0].trim());
              Float value = Float.valueOf(Float.parseFloat(data2[1]));
              this.result.put(key, value);
            }
          }
        }
      }
    }
    
    public String toString()
    {
      return this.result.toString();
    }
  }
  
  static float debug = 10.0F;
  static Configuration configuration;
  public static Hashtable<ItemStackKey, Hashtable<ItemStackKey, Float>> materialiPerItem;
  static Hashtable<ItemStackKey, Float> esplosivoPerItem = new Hashtable();
  static Hashtable<ItemStackKey, Float> fuocoPerItem = new Hashtable();
  public static Hashtable<ItemStackKey, Float> resaPerItem;
  static HashMap<ItemStackKey, FoundryRecipe> recipes;
  static Vector<String> skippedRecipes = new Vector();
  
  public static Hashtable<ItemStackKey, Float> getMateriali(ItemStack stack)
  {
    if (debug > 90.0F) {
      System.err.println("creo la chiave per " + stack);
    }
    ItemStackKey key = ItemStackKey.createKey(stack);
    if (key == null)
    {
      if (debug > 90.0F) {
        System.err.println("chiave non creata");
      }
      return null;
    }
    if (debug > 90.0F) {
      System.err.println("chiave creata key=" + key);
    }
    return (Hashtable)materialiPerItem.get(key);
  }
  
  public static float getPolvere(ItemStack stack)
  {
    ItemStackKey key = ItemStackKey.createKey(stack);
    if (key == null) {
      return 0.0F;
    }
    Float polvere = (Float)esplosivoPerItem.get(key);
    return polvere == null ? 0.0F : polvere.floatValue();
  }
  
  public static float getFuoco(ItemStack stack)
  {
    ItemStackKey key = ItemStackKey.createKey(stack);
    if (key == null) {
      return 0.0F;
    }
    Float fuoco = (Float)fuocoPerItem.get(key);
    return fuoco == null ? 0.0F : fuoco.floatValue();
  }
  
  public static void init()
  {
    initRecipes();
    initResaPerItem();
    initMaterialiPerItem();
    initFuocoPerItem();
    initEsplosivoPerItem();
    overrideSmeltingFromCfg();
    try
    {
      File log = new File("./logs/giacomosfoundry.log");
      PrintWriter pw = new PrintWriter(log, "utf-8");
      
      pw.println("Giacomo's Foundry log file");
      try
      {
        pw.println("path: " + log.getCanonicalPath());
      }
      catch (Exception localException1) {}
      pw.println("created: " + new Date());
      pw.println();
      
      pw.println("This is the complete list of recyclable materials used for the calculations.");
      pw.println("For each item there is specified the corresponding output efficiency that is the yield.");
      pw.println("Modify the list \"recyclables\" in config/foundry.cfg to add modify or remove items.");
      pw.println("Used recyclable item list:");
      pw.println(log(resaPerItem));
      pw.println();
      
      pw.println("This is the complete list of recipes used for calculations.");
      pw.println("For each item in output from a recipe there is the corresponding list of items in input.");
      pw.println("Modify the list \"recipes\" in config/foundry.cfg to add modify or remove recipes.");
      pw.println("Used recipe list:");
      pw.println(log(recipes));
      pw.println();
      
      pw.println("This is the complete list of recipes skipped for various reasons.");
      pw.println("You can manually add recipes in the config file.");
      pw.println(log(skippedRecipes));
      pw.println();
      
      pw.println("This is the complete fire values list. For each item there is the corresponding fire value.");
      pw.println("If placed on a flat floor the foundry will generate fire on average every 75 seconds when burning items with fire value of 1, like blaze rods.");
      pw.println("The average time is inversely proportional to this value so burning an item with fire value of 0.5 will generate fire on average every 150 seconds.");
      pw.println("Modify the list \"fire\" in config/foundry.cfg to add modify or remove fire values that will affect calculations.");
      pw.println("You can set the parameter useFireItemsAsFuel to true in config file to use items with fire value>0 as fuel.");
      pw.println("Computed fire list:");
      Vector<String> validFuocoPerItem = new Vector();
      Vector<String> invalidFuocoPerItem = new Vector();
      for (Iterator<ItemStackKey> iterator = fuocoPerItem.keySet().iterator(); iterator.hasNext();)
      {
        ItemStackKey key = (ItemStackKey)iterator.next();
        Float value = (Float)fuocoPerItem.get(key);
        if (value.floatValue() > 0.0F) {
          validFuocoPerItem.addElement(key + "=" + value);
        } else {
          invalidFuocoPerItem.addElement(key + "=" + value);
        }
      }
      pw.println(log(validFuocoPerItem));
      pw.println();
      
      pw.println(log(invalidFuocoPerItem));
      pw.println();
      
      pw.println("This is the complete explosion values list. For each item there is the corresponding explosion value that is the radius of explosion.");
      pw.println("For TNT the radius is 4 as if you ignite it manually with flint.");
      pw.println("Modify the list \"explosion\" in config/foundry.cfg to add modify or remove explosion values that will affect calculations.");
      pw.println("Computed explosion list:");
      Vector<String> validEsplosivoPerItem = new Vector();
      Vector<String> invalidEsplosivoPerItem = new Vector();
      for (Iterator<ItemStackKey> iterator = esplosivoPerItem.keySet().iterator(); iterator.hasNext();)
      {
        ItemStackKey key = (ItemStackKey)iterator.next();
        Float value = (Float)esplosivoPerItem.get(key);
        if (value.floatValue() > 0.0F) {
          validEsplosivoPerItem.addElement(key + "=" + value);
        } else {
          invalidEsplosivoPerItem.addElement(key + "=" + value);
        }
      }
      pw.println(log(validEsplosivoPerItem));
      pw.println();
      
      pw.println(log(invalidEsplosivoPerItem));
      pw.println();
      
      pw.println("This is the complete materials list. For each item there is the corresponding list of materials that will be returned from smelting in the foundry.");
      pw.println("The calculations are based on recipes and recyclables.");
      pw.println("You can add remove or modify smelting results in the list \"smelting\" in the config file.");
      pw.println("Changing smelting results will not affect calculations for other items, it will only override that single smelting result.");
      pw.println("Computed smelting list:");
      Vector<String> validMaterialiPerItem = new Vector();
      Vector<String> invvalidMaterialiPerItem = new Vector();
      for (Iterator<ItemStackKey> iterator = materialiPerItem.keySet().iterator(); iterator.hasNext();)
      {
        ItemStackKey key = (ItemStackKey)iterator.next();
        Hashtable<ItemStackKey, Float> value = (Hashtable)materialiPerItem.get(key);
        if ((value != null) && (value.size() > 0)) {
          validMaterialiPerItem.addElement(key + "=" + value);
        } else {
          invvalidMaterialiPerItem.addElement(key + "=" + value);
        }
      }
      pw.println(log(validMaterialiPerItem));
      pw.println();
      
      pw.println(log(invvalidMaterialiPerItem));
      pw.println();
      
      pw.flush();
      pw.close();
    }
    catch (Exception e)
    {
      if (debug > 0.0F)
      {
        System.err.println("Error while writing log/foundry.log");
        e.printStackTrace();
      }
    }
    purgeTable(materialiPerItem);
    
    purgeFloat(resaPerItem);
    purgeFloat(fuocoPerItem);
    purgeFloat(esplosivoPerItem);
  }
  
  private static void purgeTable(Hashtable<ItemStackKey, Hashtable<ItemStackKey, Float>> table)
  {
    Vector<ItemStackKey> v = new Vector();
    for (Iterator<ItemStackKey> iterator = table.keySet().iterator(); iterator.hasNext();)
    {
      ItemStackKey key = (ItemStackKey)iterator.next();
      Hashtable<ItemStackKey, Float> value = (Hashtable)table.get(key);
      if ((value == null) || (value.size() == 0)) {
        v.addElement(key);
      }
    }
    for (Iterator iterator = v.iterator(); iterator.hasNext();)
    {
      ItemStackKey key = (ItemStackKey)iterator.next();
      table.remove(key);
    }
  }
  
  private static void purgeFloat(Hashtable<ItemStackKey, Float> table)
  {
    Vector<ItemStackKey> v = new Vector();
    for (Iterator<ItemStackKey> iterator = table.keySet().iterator(); iterator.hasNext();)
    {
      ItemStackKey key = (ItemStackKey)iterator.next();
      Float value = (Float)table.get(key);
      if ((value == null) || (value.floatValue() == 0.0F)) {
        v.addElement(key);
      }
    }
    for (Iterator iterator = v.iterator(); iterator.hasNext();)
    {
      ItemStackKey key = (ItemStackKey)iterator.next();
      table.remove(key);
    }
  }
  
  private static String log(Vector<String> v)
  {
    Collections.sort(v);
    
    String ret = "";
    for (int i = 0; i < v.size(); i++) {
      ret = ret + (String)v.elementAt(i) + "\n";
    }
    return ret;
  }
  
  private static String log(Map map)
  {
    Vector<String> v = new Vector();
    Collection c = map.keySet();
    for (Iterator iterator = c.iterator(); iterator.hasNext();)
    {
      Object object = iterator.next();
      v.add("" + object + "=" + map.get(object));
    }
    return log(v);
  }
  
  private static void overrideSmeltingFromCfg()
  {
    String[] defaultValues = { "minecraft:brick={}", "minecraft:coal@0={}", "minecraft:coal@1={}", "minecraft:diamond={}", "minecraft:dye@2={}", "minecraft:dye@4={}", "minecraft:emerald={}", "minecraft:flint={}", "minecraft:glass={}", "minecraft:gold_ingot={}", "minecraft:hardened_clay={}", "minecraft:iron_ingot={}", "minecraft:netherbrick={}", "minecraft:quartz={}", "minecraft:redstone={}", "minecraft:stone@0={}", "minecraft:stonebrick@2={}" };
    
    String comment = "\nYou can override computed smelting results or add new smelting results\nspecifying materials amounts, fire probability or explosion radius for items that you want to modify or that was not processed.\nThe pattern is item={item=float,item=float,...,fire=float,explosion=float}\nIf not specified fire and explosion default value is 0.\nIf specified material list is empty or values are 0 the item will not be considered a valid input for foundry and will not be melted.\n";
    
    String[] addList = configuration.get("foundry", "smelting", defaultValues, comment).getStringList();
    for (int i = 0; i < addList.length; i++) {
      try
      {
        FoundryRecipe foundryRecipe = new FoundryRecipe(addList[i]);
        if (!materialiPerItem.containsKey(foundryRecipe.source))
        {
          if (foundryRecipe.result.size() == 0)
          {
            if (debug > 70.0F) {
              System.err.println("Smelting: skipped " + foundryRecipe.source + "=" + foundryRecipe + " from cfg");
            }
          }
          else
          {
            if (debug > 60.0F) {
              System.err.println("Smelting: added " + foundryRecipe.source + "=" + foundryRecipe + " from cfg");
            }
            Hashtable<ItemStackKey, Float> materiali = new Hashtable();
            Collection<ItemStackKey> keys = foundryRecipe.result.keySet();
            for (Iterator iterator = keys.iterator(); iterator.hasNext();)
            {
              ItemStackKey key = (ItemStackKey)iterator.next();
              Float value = (Float)foundryRecipe.result.get(key);
              materiali.put(key, value);
            }
            materialiPerItem.put(foundryRecipe.source, materiali);
            fuocoPerItem.put(foundryRecipe.source, Float.valueOf(foundryRecipe.fire));
            esplosivoPerItem.put(foundryRecipe.source, Float.valueOf(foundryRecipe.explosion));
          }
        }
        else if (foundryRecipe.result.size() == 0)
        {
          if (debug > 60.0F) {
            System.err.println("Recipes: removed " + foundryRecipe.source + "=" + foundryRecipe + " from cfg");
          }
          skippedRecipes.addElement("CFG Removed: " + foundryRecipe.source + "=" + foundryRecipe);
          materialiPerItem.remove(foundryRecipe.source);
        }
        else
        {
          if (debug > 60.0F) {
            System.err.println("Recipes: replaced " + foundryRecipe.source + "=" + foundryRecipe + " from cfg");
          }
          skippedRecipes.addElement("CFG overridden: " + foundryRecipe.source + "=" + foundryRecipe);
          Hashtable<ItemStackKey, Float> materiali = new Hashtable();
          Collection<ItemStackKey> keys = foundryRecipe.result.keySet();
          for (Iterator iterator = keys.iterator(); iterator.hasNext();)
          {
            ItemStackKey key = (ItemStackKey)iterator.next();
            Float value = (Float)foundryRecipe.result.get(key);
            materiali.put(key, value);
          }
          materialiPerItem.put(foundryRecipe.source, materiali);
          fuocoPerItem.put(foundryRecipe.source, Float.valueOf(foundryRecipe.fire));
          esplosivoPerItem.put(foundryRecipe.source, Float.valueOf(foundryRecipe.explosion));
        }
      }
      catch (Exception e)
      {
        if (debug > 0.0F)
        {
          System.err.println("Errore cfg: smelting: recipes: " + addList[i]);
          e.printStackTrace();
        }
      }
    }
  }
  
  private static void initMaterialiPerItem()
  {
    materialiPerItem = new Hashtable();
    for (Iterator<ItemStackKey> iterator = recipes.keySet().iterator(); iterator.hasNext();)
    {
      ItemStackKey key = (ItemStackKey)iterator.next();
      getMateriali(key, 0);
    }
  }
  
  private static void initResaPerItem()
  {
    resaPerItem = new Hashtable();
    if (debug > 70.0F) {
      System.err.println("Inizializzo resaPerItem");
    }
    HashMap<ItemStackKey, Float> map = getRecyclables();
    for (Iterator<ItemStackKey> iterator = map.keySet().iterator(); iterator.hasNext();)
    {
      ItemStackKey key = (ItemStackKey)iterator.next();
      Float value = (Float)map.get(key);
      resaPerItem.put(key, value);
      if (debug > 70.0F) {
        System.err.println("resaPerItem: added " + key + "=" + value);
      }
    }
  }
  
  private static void initRecipes()
  {
    recipes = getRecipes();
  }
  
  private static void initFuocoPerItem()
  {
    fuocoPerItem = new Hashtable();
    if (debug > 70.0F) {
      System.err.println("Inizializzo fuocoPerItem");
    }
    String[] defaultValues = { "minecraft:paper=0.01", "minecraft:blaze_powder=0.5" };
    
    String comment = "\nAll the fuel items that you can burn in the furnace can generate fire on foundry.\nIn this list you can add more items to be considered as fuel or you can change the probability to generate fire for existing ones.\nIf placed on a flat floor the foundry will generate fire on average every 75 seconds when burning items with fire value of 1, like blaze rods.\nThe average time is inversely proportional to this value so burning items with fire value of 0.5 will generate fire on average every 150 seconds.\nIf not specified the default value is 0 and the object will not generate fire.\n";
    
    String[] addList = configuration.get("foundry", "fire", defaultValues, comment).getStringList();
    for (int i = 0; i < addList.length; i++) {
      try
      {
        String idString = addList[i].trim();
        Float value = Float.valueOf(0.0F);
        String[] data = addList[i].split("=");
        if (data.length >= 2)
        {
          idString = data[0].trim();
          try
          {
            value = Float.valueOf(Float.parseFloat(data[1].trim()));
            if (value.floatValue() < 0.0F) {
              value = Float.valueOf(0.0F);
            }
          }
          catch (Exception e)
          {
            if (debug > 0.0F)
            {
              System.err.println("Errore cfg: foundry: fire: " + addList[i]);
              e.printStackTrace();
            }
          }
        }
        ItemStackKey key = ItemStackKey.createKey(idString);
        fuocoPerItem.put(key, value);
        if (debug > 70.0F) {
          System.err.println("fuocoPerItem: added " + key + "=" + value + " from cfg");
        }
      }
      catch (Exception e)
      {
        if (debug > 0.0F)
        {
          System.err.println("Errore cfg: foundry: fire: " + addList[i]);
          e.printStackTrace();
        }
      }
    }
    for (Iterator<FoundryRecipe> iterator = recipes.values().iterator(); iterator.hasNext();)
    {
      FoundryRecipe recipe = (FoundryRecipe)iterator.next();
      ItemStackKey key = recipe.source;
      Float value = Float.valueOf(getFuocoPerItem(key));
      if ((debug > 70.0F) && (value.floatValue() > 0.0F)) {
        System.err.println("fuocoPerItem: added " + key + "=" + value);
      }
    }
  }
  
  private static void initEsplosivoPerItem()
  {
    esplosivoPerItem = new Hashtable();
    if (debug > 70.0F) {
      System.err.println("Inizializzo esplosivoPerItem");
    }
    String[] defaultValues = { "minecraft:gunpowder=0.8", "minecraft:firework_charge=1.2", "minecraft:fireworks=1.6" };
    
    String comment = "\nAll items crafted with explosives can generate explosion if smelted on foundry.\nIn this list you can change explosives values for items, and new values will affect calculations.\nGiven that the TNT explosion has a radius of 4 and is composed of 5 gunpowder the amount of a single gunpowder is 0.8.\nIf not specified the default value is 0 and the object will not generate explosions.\n";
    
    String[] addList = configuration.get("foundry", "explosion", defaultValues, comment).getStringList();
    for (int i = 0; i < addList.length; i++) {
      try
      {
        String idString = addList[i].trim();
        Float value = Float.valueOf(0.0F);
        String[] data = addList[i].split("=");
        if (data.length >= 2)
        {
          idString = data[0].trim();
          try
          {
            value = Float.valueOf(Float.parseFloat(data[1].trim()));
            if (value.floatValue() < 0.0F) {
              value = Float.valueOf(0.0F);
            }
          }
          catch (Exception e)
          {
            if (debug > 0.0F)
            {
              System.err.println("Errore cfg: foundry: fire: " + addList[i]);
              e.printStackTrace();
            }
          }
        }
        ItemStackKey key = ItemStackKey.createKey(idString);
        esplosivoPerItem.put(key, value);
        if (debug > 70.0F) {
          System.err.println("esplosivoPerItem: added " + key + "=" + value + " from cfg");
        }
      }
      catch (Exception e)
      {
        if (debug > 0.0F)
        {
          System.err.println("Errore cfg: foundry: explosion: " + addList[i]);
          e.printStackTrace();
        }
      }
    }
    for (Iterator<FoundryRecipe> iterator = recipes.values().iterator(); iterator.hasNext();)
    {
      FoundryRecipe recipe = (FoundryRecipe)iterator.next();
      Float value = Float.valueOf(getPolverePerItem(recipe.source, 0));
      if ((debug > 70.0F) && (value.floatValue() > 0.0F)) {
        System.err.println("esplosivoPerItem: added " + recipe.source + "=" + value);
      }
    }
  }
  
  private static float getPolverePerItem(ItemStackKey key, int depth)
  {
    ItemStack stack = key.getItemStack();
    if (stack == null)
    {
      if (debug > 80.0F) {
        System.err.println("\t* " + depth + " * getPolvere: itemstack nullo");
      }
    }
    else if (stack.getItem() == null)
    {
      if (debug > 80.0F) {
        System.err.println("\t* " + depth + " * getPolvere: item nullo");
      }
    }
    else if (debug > 80.0F) {
      System.err.println("\t* " + depth + " * getPolvere: key=" + key);
    }
    Float value = (Float)esplosivoPerItem.get(key);
    if (value != null)
    {
      if (debug > 80.0F) {
        System.err.println("\t* " + depth + " * trovato esplosivo per " + key + " valore=" + value);
      }
      return value.floatValue();
    }
    value = Float.valueOf(0.0F);
    esplosivoPerItem.put(key, value);
    if (debug > 80.0F) {
      System.err.println("\t* " + depth + " * calcolo esplosivo per " + key);
    }
    FoundryRecipe recipe = findRecipe(key, recipes);
    if (recipe == null)
    {
      if (debug > 80.0F) {
        System.err.println("\t* " + depth + " * " + " Salto " + key + ": recipe nullo");
      }
      return 0.0F;
    }
    if (debug > 80.0F) {
      System.err.println("\t* " + depth + " * " + " Cerco l'esplosivo per gli oggetti contenuti: " + recipe.result);
    }
    for (Iterator<ItemStackKey> iterator = recipe.result.keySet().iterator(); iterator.hasNext();)
    {
      ItemStackKey key2 = (ItemStackKey)iterator.next();
      Float esplosivo = (Float)esplosivoPerItem.get(key2);
      if (esplosivo == null)
      {
        if (debug > 90.0F) {
          System.err.println("\t* " + depth + " * " + " Salto " + key2 + " esplosivo nullo");
        }
      }
      else
      {
        Float value2 = (Float)recipe.result.get(key2);
        if (debug > 90.0F) {
          System.err.println("\t* " + depth + " * " + " Sommo " + key2 + " esplosivo=" + esplosivo + " quantita'=" + value2);
        }
        value = Float.valueOf(value.floatValue() + value2.floatValue() * esplosivo.floatValue());
      }
    }
    esplosivoPerItem.put(key, value);
    if (debug > 80.0F) {
      System.err.println("\t* " + depth + " * " + " Calcolo l'esplosivo per i rimanenti oggetti contenuti: " + recipe.result);
    }
    for (Iterator<ItemStackKey> iterator = recipe.result.keySet().iterator(); iterator.hasNext();)
    {
      ItemStackKey key2 = (ItemStackKey)iterator.next();
      Float esplosivo = (Float)esplosivoPerItem.get(key2);
      if (esplosivo != null)
      {
        if (debug > 90.0F) {
          System.err.println("\t* " + depth + " * " + " Salto " + key2 + " gia' considerato");
        }
      }
      else
      {
        if (debug > 90.0F) {
          System.err.println("\t* " + depth + " * " + " Cerco esplosivo per " + key2);
        }
        esplosivo = Float.valueOf(getPolverePerItem(key2, depth + 1));
        Float value2 = (Float)recipe.result.get(key2);
        if (debug > 90.0F) {
          System.err.println("\t* " + depth + " * " + " Sommo " + key2 + " esplosivo=" + esplosivo + " quantita'=" + value2);
        }
        value = Float.valueOf(value.floatValue() + value2.floatValue() * esplosivo.floatValue());
      }
    }
    esplosivoPerItem.put(key, value);
    
    return value.floatValue();
  }
  
  private static Hashtable<ItemStackKey, Float> getMateriali(ItemStackKey key, int depth)
  {
    if (debug > 80.0F) {
      System.err.println("\t* " + depth + " * " + "Cerco i materiali riciclabili per " + key);
    }
    Hashtable<ItemStackKey, Float> materiali = (Hashtable)materialiPerItem.get(key);
    if (materiali == null)
    {
      if (debug > 80.0F) {
        System.err.println("\t* " + depth + " * " + "Materiali non trovati per " + key + ", quindi li calcolo");
      }
      FoundryRecipe recipe = findRecipe(key, recipes);
      if (recipe == null)
      {
        if (debug > 80.0F) {
          System.err.println("\t* " + depth + " * " + "Recipe non trovato per " + key + ", quindi ritorno null");
        }
        return null;
      }
      if (debug > 80.0F) {
        System.err.println("\t* " + depth + " * " + "Calcolo i materiali per per " + recipe);
      }
      materiali = new Hashtable();
      materialiPerItem.put(key, materiali);
      for (Iterator<ItemStackKey> iterator = recipe.result.keySet().iterator(); iterator.hasNext();)
      {
        ItemStackKey key2 = (ItemStackKey)iterator.next();
        Float value2 = (Float)recipe.result.get(key2);
        Float resa2 = (Float)resaPerItem.get(key2);
        if (resa2 != null)
        {
          float amount = value2.floatValue() * resa2.floatValue();
          if (debug > 90.0F) {
            System.err.println("\t* " + depth + " * " + "Sommo a materiale " + key2 + " " + amount);
          }
          Float value = (Float)materiali.get(key2);
          if (value == null) {
            value = Float.valueOf(0.0F);
          }
          value = Float.valueOf(value.floatValue() + amount);
          materiali.put(key2, value);
        }
      }
      if (debug > 90.0F) {
        System.err.println("\t* " + depth + " * " + "Materiali di fornace calcolati per " + recipe + ": " + materiali);
      }
      for (Iterator<ItemStackKey> iterator = recipe.result.keySet().iterator(); iterator.hasNext();)
      {
        ItemStackKey key2 = (ItemStackKey)iterator.next();
        value2 = (Float)recipe.result.get(key2);
        Float resa2 = (Float)resaPerItem.get(key2);
        if (resa2 == null)
        {
          if (debug > 90.0F) {
            System.err.println("\t* " + depth + " * " + "Cerco i materiali per " + key2);
          }
          materiali2 = getMateriali(key2, depth + 1);
          if ((materiali2 == null) || (materiali2.size() == 0))
          {
            if (debug > 90.0F) {
              System.err.println("\t* " + depth + " * " + "Materiali non trovati per " + key2);
            }
          }
          else
          {
            if (debug > 90.0F) {
              System.err.println("\t* " + depth + " * " + "Sommo i materiali di " + key2 + ": " + materiali2);
            }
            for (iterator2 = materiali2.keySet().iterator(); iterator2.hasNext();)
            {
              ItemStackKey key3 = (ItemStackKey)iterator2.next();
              if (debug > 95.0F) {
                System.err.println("\t* " + depth + " * " + "key3=" + key3);
              }
              Float value3 = (Float)materiali2.get(key3);
              if (debug > 95.0F) {
                System.err.println("\t* " + depth + " * " + "value3=" + value3);
              }
              value3 = Float.valueOf(value3.floatValue() * value2.floatValue());
              Float value = (Float)materiali.get(key3);
              if (value == null) {
                value = Float.valueOf(0.0F);
              }
              if (debug > 90.0F) {
                System.err.println("\t* " + depth + " * " + "Sommo a materiale " + key3 + " " + value3);
              }
              materiali.put(key3, Float.valueOf(value.floatValue() + value3.floatValue()));
            }
          }
        }
      }
      Float value2;
      Hashtable<ItemStackKey, Float> materiali2;
      Iterator<ItemStackKey> iterator2;
      if (debug > 80.0F) {
        System.err.println("\t* " + depth + " * " + "Materiali calcolati per " + key + ": " + materiali);
      }
      return materiali;
    }
    if (debug > 80.0F) {
      System.err.println("\t* " + depth + " * " + "Materiali riciclabili trovati per " + key + ": " + materiali);
    }
    return materiali;
  }
  
  private static float getFuocoPerItem(ItemStackKey key)
  {
    Float value = (Float)fuocoPerItem.get(key);
    if (value != null) {
      return value.floatValue();
    }
    ItemStack stack = key.getItemStack();
    value = Float.valueOf(TileEntityscrapconverter.getItemBurnTime(stack) / 2400.0F);
    fuocoPerItem.put(key, value);
    if (value.floatValue() != 0.0F) {
      return value.floatValue();
    }
    FoundryRecipe recipe = findRecipe(key, recipes);
    if (recipe == null) {
      return 0.0F;
    }
    for (Iterator<ItemStackKey> iterator = recipe.result.keySet().iterator(); iterator.hasNext();)
    {
      ItemStackKey key2 = (ItemStackKey)iterator.next();
      Float fuoco = (Float)fuocoPerItem.get(key2);
      if (fuoco != null)
      {
        Float value2 = (Float)recipe.result.get(key2);
        value = Float.valueOf(value.floatValue() + value2.floatValue() * fuoco.floatValue());
      }
    }
    fuocoPerItem.put(key, value);
    for (Iterator<ItemStackKey> iterator = recipe.result.keySet().iterator(); iterator.hasNext();)
    {
      ItemStackKey key2 = (ItemStackKey)iterator.next();
      Float fuoco = (Float)fuocoPerItem.get(key2);
      if (fuoco == null)
      {
        fuoco = Float.valueOf(getFuocoPerItem(key2));
        Float value2 = (Float)recipe.result.get(key2);
        value = Float.valueOf(value.floatValue() + value2.floatValue() * fuoco.floatValue());
      }
    }
    fuocoPerItem.put(key, value);
    
    return value.floatValue();
  }
  
  public static HashMap<ItemStackKey, Float> getRecyclables()
  {
    HashMap<ItemStackKey, Float> map = new HashMap();
    
    Map smeltingList = FurnaceRecipes.instance().getSmeltingList();
    for (Iterator iterator = smeltingList.entrySet().iterator(); iterator.hasNext();)
    {
      Map.Entry entry = (Map.Entry)iterator.next();
      ItemStack result = (ItemStack)entry.getValue();
      ItemStackKey resultKey = ItemStackKey.createKey(result);
      if ((resultKey == null) || ((result.getItem() instanceof ItemFood)) || (result.getItem() == Item.getItemFromBlock(Blocks.SPONGE)))
      {
        if (debug > 70.0F) {
          System.err.println("Recyclables: Skipped: " + entry);
        }
      }
      else if (!map.containsKey(resultKey))
      {
        map.put(resultKey, Float.valueOf(1.0F));
        if (debug > 60.0F) {
          System.err.println("Recyclables: Added: " + resultKey.getIdString());
        }
      }
    }
    String[] ret = new String[map.size()];
    int index = 0;
    for (Iterator<ItemStackKey> iterator = map.keySet().iterator(); iterator.hasNext();)
    {
      ItemStackKey key = (ItemStackKey)iterator.next();
      ret[(index++)] = (key.getIdString() + "=1.0");
    }
    Arrays.sort(ret);
    
    String[] defaultValues = { "minecraft:flint=1.0" };
    
    String comment = "\nAll objects that can be obtained from the furnace are considered recyclables and their default yield is 1.0.\nIn this list you can add other items to be considered recyclables or change its yield and new values will affect calculations.\nThe yield is a float greater than 0. Specifying a yield less than or equal to 0, the object is considered not recyclable.\nFor example you can override all items setting all to 0 and specify all custom smelting results in the smelting list.\nIf not specified the default value is 1.\nThe material amount returned from foundry is proportional to the material yield.\n";
    
    String[] addList = configuration.get("foundry", "recyclables", defaultValues, comment).getStringList();
    for (int i = 0; i < addList.length; i++) {
      try
      {
        Float value = Float.valueOf(1.0F);
        String idString = addList[i].trim();
        String[] data = addList[i].split("=");
        if (data.length == 2)
        {
          value = Float.valueOf(Float.parseFloat(data[1].trim()));
          idString = data[0].trim();
        }
        ItemStackKey key = ItemStackKey.createKey(idString);
        idString = key.getIdString();
        if (!map.containsKey(key))
        {
          if (value.floatValue() <= 0.0F)
          {
            if (debug > 70.0F) {
              System.err.println("Recyclables: Skipped: " + idString + "=" + value + " from cfg");
            }
          }
          else
          {
            if (debug > 60.0F) {
              System.err.println("Recyclables: Added: " + idString + "=" + value + " from cfg");
            }
            map.put(key, value);
          }
        }
        else if (value.floatValue() <= 0.0F)
        {
          if (debug > 60.0F) {
            System.err.println("Recyclables: Removed: " + idString + "=" + value + " from cfg");
          }
          map.remove(key);
        }
        else
        {
          if (debug > 60.0F) {
            System.err.println("Recyclables: Replaced: " + idString + "=" + value + " from cfg");
          }
          map.put(key, value);
        }
      }
      catch (Exception e)
      {
        if (debug > 0.0F)
        {
          System.err.println("Errore cfg: furnace: add: " + addList[i]);
          e.printStackTrace();
        }
      }
    }
    return map;
  }
  
  static HashMap<ItemStackKey, FoundryRecipe> getRecipes()
  {
    List<? extends IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
    HashMap<ItemStackKey, FoundryRecipe> map = new HashMap();
    for (IRecipe recipe : recipes)
    {
      ItemStack stack = recipe.getRecipeOutput();
      ItemStackKey output = ItemStackKey.createKey(stack);
      if (output != null)
      {
        float size = stack.stackSize;
        if (debug > 70.0F) {
          System.err.println("processo recipe per " + output + " size=" + size + " class=" + recipe.getClass());
        }
        try
        {
          FoundryRecipe foundryRecipe = new FoundryRecipe();
          if (debug > 70.0F) {
            System.err.println("creato  foundryRecipe=" + foundryRecipe);
          }
        }
        catch (Exception e)
        {
          if (debug > 70.0F) {
            System.err.println("impossibile creare  foundryRecipe: " + e.getMessage());
          }
          skippedRecipes.addElement(recipe + (recipe != null ? " class=" + recipe.getClass() : "") + " invalid recipe: " + e.getMessage());
        }
        continue;
        Recipesscrapconverter foundryRecipe;
        foundryRecipe.source = output;
        try
        {
          if (debug > 70.0F) {
            System.err.println("cerco recipe input  per " + foundryRecipe);
          }
          foundryRecipe.result = getRecipeInput(recipe);
          if (foundryRecipe.result.size() > 9) {
            throw new RuntimeException("too many recipe input items");
          }
        }
        catch (Exception e)
        {
          if (debug > 70.0F) {
            System.err.println("invalid recipe input: " + e.getMessage());
          }
          skippedRecipes.addElement(recipe + (recipe != null ? " class=" + recipe.getClass() : "") + " invalid recipe input: " + e.getMessage());
        }
        continue;
        if (size <= 0.0F)
        {
          if (debug > 70.0F) {
            System.err.println("Salto recipe per " + output + " stackSize=" + size);
          }
          skippedRecipes.addElement(recipe + " class=" + recipe.getClass() + " invalid output: stack.stackSize=" + stack.stackSize);
        }
        else
        {
          for (Iterator<ItemStackKey> iterator = foundryRecipe.result.keySet().iterator(); iterator.hasNext();)
          {
            ItemStackKey key2 = (ItemStackKey)iterator.next();
            Float value = (Float)foundryRecipe.result.get(key2);
            if (value != null) {
              foundryRecipe.result.put(key2, Float.valueOf(value.floatValue() / size));
            }
          }
          if (!map.containsKey(output))
          {
            if (debug > 60.0F) {
              System.err.println("Aggiunto recipe: " + foundryRecipe);
            }
          }
          else
          {
            if (debug > 60.0F) {
              System.err.println("Sostituito recipe: " + foundryRecipe);
            }
            skippedRecipes.addElement(recipe + " class=" + recipe.getClass() + " overridden: " + output + "=" + map.get(output));
          }
          map.put(output, foundryRecipe);
        }
      }
      else
      {
        if (recipe != null)
        {
          String s = recipe + " class=" + recipe.getClass();
          try
          {
            s = s + " invalid output item: " + stack;
          }
          catch (Exception e)
          {
            s = s + " invalid output item: " + output;
          }
          skippedRecipes.addElement(s);
        }
        if (debug > 70.0F) {
          System.err.println("Salto " + recipe + " output non valido");
        }
      }
    }
    String[] defaultRecipes = { "minecraft:iron_horse_armor={minecraft:iron_ingot=12.0}", "minecraft:golden_horse_armor={minecraft:gold_ingot=12.0}", "minecraft:diamond_horse_armor={minecraft:diamond=12.0}", "minecraft:stonebrick@" + BlockStoneBrick.CRACKED_META + "={minecraft:stonebrick@0=1.0}", "minecraft:cobblestone={minecraft:stone@0=1.0}", "minecraft:chainmail_boots={minecraft:iron_ingot=2.666664}", "minecraft:chainmail_leggings={minecraft:iron_ingot=4.6666617}", "minecraft:chainmail_chestplate={minecraft:iron_ingot=5.333328}", "minecraft:chainmail_helmet={minecraft:iron_ingot=3.33333}", "minecraft:lava_bucket={minecraft:iron_ingot=3.0}", "minecraft:water_bucket={minecraft:iron_ingot=3.0}" };
    
    String comment = "\nAll registered recipes in crafting table are processed to determine recyclables to be returned with the fusion in the foundry.\nIn this list you can add or remove or modify recipes as if they were registered in the crafting table.\nThe syntax is: item={item=value,item=value, ...}.\nAccepted values are float greater than 0.\nIf a value is not acceptable or the list of materials is not specified the foundry will ignore the entire recipe.\nChanging this list will affect only foundry calculations and not crafting table results.\n";
    
    String[] addList = configuration.get("foundry", "recipes", defaultRecipes, comment).getStringList();
    for (int i = 0; i < addList.length; i++) {
      try
      {
        FoundryRecipe foundryRecipe = new FoundryRecipe(addList[i]);
        if (!map.containsKey(foundryRecipe.source))
        {
          if (foundryRecipe.result.size() == 0)
          {
            if (debug > 70.0F) {
              System.err.println("Recipes: skipped " + foundryRecipe + " from cfg");
            }
          }
          else
          {
            if (debug > 60.0F) {
              System.err.println("Recipes: added " + foundryRecipe + " from cfg");
            }
            map.put(foundryRecipe.source, foundryRecipe);
          }
        }
        else if (foundryRecipe.result.size() == 0)
        {
          if (debug > 60.0F) {
            System.err.println("Recipes: removed " + foundryRecipe + " from cfg");
          }
          skippedRecipes.addElement("CFG Removed: " + foundryRecipe.source + "=" + foundryRecipe);
          map.remove(foundryRecipe.source);
        }
        else
        {
          if (debug > 60.0F) {
            System.err.println("Recipes: replaced " + foundryRecipe + " from cfg");
          }
          skippedRecipes.addElement("CFG overridden: " + foundryRecipe.source + "=" + foundryRecipe);
          map.put(foundryRecipe.source, foundryRecipe);
        }
      }
      catch (Exception e)
      {
        if (debug > 0.0F)
        {
          System.err.println("Errore cfg: crafting_table: recipes: " + addList[i]);
          e.printStackTrace();
        }
      }
    }
    return map;
  }
  
  private static HashMap<ItemStackKey, Float> getRecipeInput(IRecipe recipe)
  {
    HashMap<ItemStackKey, Float> ret = new HashMap();
    Object[] inputObj = getRecipeInput(recipe, 0);
    if (inputObj != null)
    {
      if (debug > 80.0F) {
        System.err.println("Trovati " + inputObj.length + " oggetti in input");
      }
      for (int i = 0; i < inputObj.length; i++)
      {
        ItemStackKey key = ItemStackKey.createKey(toItemStack(inputObj[i]));
        if (key != null)
        {
          Float value = (Float)ret.get(key);
          if (value == null) {
            value = Float.valueOf(0.0F);
          }
          value = Float.valueOf(value.floatValue() + 1.0F);
          ret.put(key, value);
        }
        else
        {
          String s;
          try
          {
            s = "" + inputObj[i];
          }
          catch (Exception e)
          {
            String s;
            s = "oggetto " + i + " (" + e.getMessage() + ")";
          }
          if (debug > 80.0F) {
            System.err.println("Salto " + s);
          }
        }
      }
    }
    if (debug > 80.0F) {
      System.err.println("Materiali in input: " + ret);
    }
    return ret;
  }
  
  public FoundryRecipe findRecipe(ItemStack stack, HashMap<ItemStackKey, FoundryRecipe> map)
  {
    ItemStackKey key = ItemStackKey.createKey(stack);
    if (key == null)
    {
      if (debug > 80.0F) {
        try
        {
          System.err.println("Chiave non valida per " + stack);
        }
        catch (Exception e)
        {
          System.err.println("Chiave non valida: " + e.getMessage());
        }
      }
      return null;
    }
    return findRecipe(key, map);
  }
  
  public static FoundryRecipe findRecipe(ItemStackKey key, HashMap<ItemStackKey, FoundryRecipe> map)
  {
    if (key == null)
    {
      if (debug > 80.0F) {
        System.err.println("Chiave nulla");
      }
      return null;
    }
    FoundryRecipe ret = (FoundryRecipe)map.get(key);
    if ((ret == null) && (key.itemMeta == 32767)) {
      for (int i = 0; i < 16; i++)
      {
        ItemStackKey key2 = ItemStackKey.createKey(key.item, i);
        ret = (FoundryRecipe)map.get(key2);
        if (ret != null) {
          break;
        }
      }
    }
    return ret;
  }
  
  private static Object[] getRecipeInput(IRecipe recipe, int depth)
  {
    Object[] items = null;
    if ((recipe instanceof ShapedOreRecipe))
    {
      ShapedOreRecipe shapedOreRecipe = (ShapedOreRecipe)recipe;
      items = shapedOreRecipe.getInput();
    }
    else if ((recipe instanceof ShapedRecipes))
    {
      ShapedRecipes shapedRecipes = (ShapedRecipes)recipe;
      items = shapedRecipes.recipeItems;
    }
    else if ((recipe instanceof ShapelessOreRecipe))
    {
      ShapelessOreRecipe shapelessOreRecipe = (ShapelessOreRecipe)recipe;
      items = shapelessOreRecipe.getInput().toArray();
    }
    else if ((recipe instanceof ShapelessRecipes))
    {
      ShapelessRecipes shapelessRecipes = (ShapelessRecipes)recipe;
      items = shapelessRecipes.recipeItems.toArray();
    }
    if ((recipe != null) && ((items == null) || (items.length < 1) || (items.length > 9)))
    {
      if (debug > 80.0F) {
        System.err.println("\t* " + depth + " * Strange recipe found: " + recipe + " class=" + recipe.getClass());
      }
      try
      {
        if (debug > 90.0F) {
          System.err.println("\t* " + depth + " *    recipe.getRecipeOutput()=" + recipe.getRecipeOutput());
        }
      }
      catch (Exception e)
      {
        if (debug > 80.0F) {
          System.err.println("\t* " + depth + " *    recipe.getRecipeOutput(): " + e.getMessage());
        }
      }
      if (debug > 90.0F) {
        System.err.println("\t* " + depth + " *    recipe input: items=" + items);
      }
      if (items != null)
      {
        if (debug > 90.0F) {
          System.err.println("\t* " + depth + " *    recipe input: items.length=" + items.length);
        }
        for (int i = 0; (i < items.length) && (i < 9); i++) {
          try
          {
            if (debug > 90.0F) {
              System.err.println("\t* " + depth + " *    recipe input: items[" + i + "]=" + items[i]);
            }
          }
          catch (Exception e)
          {
            if (debug > 80.0F) {
              System.err.println("\t* " + depth + " *    recipe input: items[" + i + "]: " + e.getMessage());
            }
          }
        }
        if ((items.length > 9) && 
          (debug > 90.0F)) {
          System.err.println("\t* " + depth + " *    recipe input: " + (items.length - 9) + " more...");
        }
      }
      if (debug > 80.0F) {
        System.err.println("\t* " + depth + " *    skipping this recipe");
      }
      return null;
    }
    return items;
  }
  
  private static ItemStack toItemStack(Object o)
  {
    ItemStack stack2 = null;
    if (o != null) {
      try
      {
        if ((o instanceof Collection))
        {
          if (((Collection)o).size() != 1) {
            return null;
          }
          stack2 = (ItemStack)((Collection)o).iterator().next();
        }
        else
        {
          stack2 = (ItemStack)o;
        }
        if (debug > 90.0F) {
          System.err.println(o + "->" + stack2);
        }
      }
      catch (Exception e)
      {
        if (debug > 90.0F)
        {
          System.err.println(e.getMessage());
          e.printStackTrace();
        }
      }
    }
    return stack2;
  }
}
