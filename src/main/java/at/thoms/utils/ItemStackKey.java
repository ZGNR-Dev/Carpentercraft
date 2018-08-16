package at.thoms.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;

public class ItemStackKey
{
  public final Item item;
  public final int itemMeta;
  
  public static ItemStackKey createKey(Object o)
  {
    if (o == null) {
      return null;
    }
    if ((o instanceof Item)) {
      return createKey((Item)o);
    }
    if ((o instanceof Block)) {
      return createKey((Block)o);
    }
    if ((o instanceof ItemStack)) {
      return createKey((ItemStack)o);
    }
    if ((o instanceof ItemStackKey)) {
      return createKey((ItemStackKey)o);
    }
    return null;
  }
  
  public static ItemStackKey createKey(ItemStackKey key)
  {
    if ((key == null) || (key.item == null)) {
      return null;
    }
    return key;
  }
  
  public static ItemStackKey createKey(ItemStack stack)
  {
    if ((stack == null) || (stack.getItem() == null)) {
      return null;
    }
    return new ItemStackKey(stack);
  }
  
  public static ItemStackKey createKey(Item item)
  {
    if (item == null) {
      return null;
    }
    return new ItemStackKey(item);
  }
  
  public static ItemStackKey createKey(Item item, int itemMeta)
  {
    if (item == null) {
      return null;
    }
    return new ItemStackKey(item, itemMeta);
  }
  
  public static ItemStackKey createKey(Block block)
  {
    if (block == null) {
      return null;
    }
    return new ItemStackKey(block);
  }
  
  public static ItemStackKey createKey(String name)
  {
    int meta = 0;
    int at = name.indexOf("@");
    if (at != -1)
    {
      meta = Integer.parseInt(name.substring(at + 1));
      if (meta < 0) {
        meta = 0;
      }
      name = name.substring(0, at);
    }
    Item item = Item.getByNameOrId(name);
    
    return new ItemStackKey(item, meta);
  }
  
  private ItemStackKey(ItemStack stack)
  {
    this.item = stack.getItem();
    if (stack.getHasSubtypes()) {
      this.itemMeta = stack.getItemDamage();
    } else {
      this.itemMeta = 0;
    }
  }
  
  private ItemStackKey(Item item)
  {
    this(item, 0);
  }
  
  private ItemStackKey(Item item, int itemMeta)
  {
    if (item == null) {
      throw new NullPointerException();
    }
    this.item = item;
    if (itemMeta < 0) {
      itemMeta = 0;
    }
    this.itemMeta = itemMeta;
  }
  
  private ItemStackKey(Block block)
  {
    this.item = Item.getItemFromBlock(block);
    this.itemMeta = 0;
  }
  
  public ItemStack getItemStack()
  {
    if (this.item == null) {
      return null;
    }
    ItemStack ret = new ItemStack(this.item, 1, this.itemMeta);
    
    return ret;
  }
  
  public String getIdString()
  {
    ResourceLocation resourceLocation = (ResourceLocation)Item.REGISTRY.getNameForObject(this.item);
    return resourceLocation + (this.item.getHasSubtypes() ? "@" + this.itemMeta : "");
  }
  
  public String toString()
  {
    if (this.item == null) {
      return "###Item Nullo###";
    }
    ItemStack stack = getItemStack();
    
    ResourceLocation resourceLocation = (ResourceLocation)Item.REGISTRY.getNameForObject(this.item);
    
    String displayName = stack.getDisplayName();
    String name;
//    String name;
    if (resourceLocation != null) {
      name = resourceLocation.toString();
    } else {
      name = "###" + this.item.getUnlocalizedName() + "###";
    }
    if (this.item.getHasSubtypes()) {
      name = name + "@" + this.itemMeta;
    }
    int id = Item.getIdFromItem(this.item);
    String ret = name;
    
    return ret;
  }
  
  public int hashCode()
  {
    if (this.item == null) {
      return 0;
    }
    int prime = 31;
    int result = 1;
    result = 31 * result + this.item.hashCode();
    result = 31 * result + this.itemMeta;
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ItemStackKey other = (ItemStackKey)obj;
    if (this.item != other.item) {
      return false;
    }
    if (this.itemMeta != other.itemMeta) {
      return false;
    }
    return true;
  }
}