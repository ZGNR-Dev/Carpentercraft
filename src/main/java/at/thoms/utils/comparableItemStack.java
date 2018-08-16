package at.thoms.utils;

import at.thoms.utils.comparableItem;
import at.thoms.proxy.proxyoredict;
import at.thoms.utils.itemHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class comparableItemStack
  extends comparableItem
{
  public static comparableItemStack fromItemStack(ItemStack stack)
  {
    return new comparableItemStack(stack);
  }
  
  public int stackSize = -1;
  public int oreID = -1;
  
  protected static ItemStack getOre(String oreName)
  {
    if (itemHelper.oreNameExists(oreName)) {
      return itemHelper.oreProxy.getOre(oreName);
    }
    return null;
  }
  
  public comparableItemStack(String oreName)
  {
    this(getOre(oreName));
  }
  
  public comparableItemStack(ItemStack stack)
  {
    super(stack);
    if (stack != null)
    {
      this.stackSize = stack.stackSize;
      this.oreID = itemHelper.oreProxy.getOreID(stack);
    }
  }
  
  public comparableItemStack(Item item, int damage, int stackSize)
  {
    super(item, damage);
    this.stackSize = stackSize;
    this.oreID = itemHelper.oreProxy.getOreID(toItemStack());
  }
  
  public comparableItemStack(comparableItemStack stack)
  {
    super(stack.item, stack.metadata);
    this.stackSize = stack.stackSize;
    this.oreID = stack.oreID;
  }
  
  public comparableItemStack set(ItemStack stack)
  {
    if (stack != null)
    {
      this.item = stack.getItem();
      this.metadata = itemHelper.getItemDamage(stack);
      this.stackSize = stack.stackSize;
      this.oreID = itemHelper.oreProxy.getOreID(stack);
    }
    else
    {
      this.item = null;
      this.metadata = -1;
      this.stackSize = -1;
      this.oreID = -1;
    }
    return this;
  }
  
  public comparableItemStack set(comparableItemStack stack)
  {
    if (stack != null)
    {
      this.item = stack.item;
      this.metadata = stack.metadata;
      this.stackSize = stack.stackSize;
      this.oreID = stack.oreID;
    }
    else
    {
      this.item = null;
      this.metadata = -1;
      this.stackSize = -1;
      this.oreID = -1;
    }
    return this;
  }
  
  public boolean isItemEqual(comparableItemStack other)
  {
    return (other != null) && (((this.oreID != -1) && (this.oreID == other.oreID)) || (isEqual(other)));
  }
  
  public boolean isStackEqual(comparableItemStack other)
  {
    return (isItemEqual(other)) && (this.stackSize == other.stackSize);
  }
  
  public boolean isStackValid()
  {
    return this.item != null;
  }
  
  public ItemStack toItemStack()
  {
    return this.item != null ? new ItemStack(this.item, this.stackSize, this.metadata) : null;
  }
  
  public comparableItemStack clone()
  {
    return new comparableItemStack(this);
  }
  
  public int hashCode()
  {
    return this.oreID != -1 ? this.oreID : super.hashCode();
  }
  
  public boolean equals(Object o)
  {
    return ((o instanceof comparableItemStack)) && (isItemEqual((comparableItemStack)o));
  }
}
