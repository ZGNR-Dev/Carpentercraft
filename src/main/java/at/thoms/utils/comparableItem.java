package at.thoms.utils;

import at.thoms.utils.itemHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.RegistryDelegate;

public class comparableItem
{
  public Item item;
  public int metadata;
  
  public static comparableItem fromItemStack(ItemStack stack)
  {
    return new comparableItem(stack);
  }
  
  protected comparableItem()
  {
    this.item = null;
    this.metadata = 0;
  }
  
  public comparableItem(Item item, int metadata)
  {
    this.item = item;
    this.metadata = metadata;
  }
  
  public comparableItem(ItemStack stack)
  {
    if (stack != null)
    {
      this.item = stack.getItem();
      this.metadata = itemHelper.getItemDamage(stack);
    }
    else
    {
      this.item = null;
      this.metadata = 0;
    }
  }
  
  public comparableItem(comparableItem stack)
  {
    this.item = stack.item;
    this.metadata = stack.metadata;
  }
  
  public comparableItem set(ItemStack stack)
  {
    if (stack != null)
    {
      this.item = stack.getItem();
      this.metadata = itemHelper.getItemDamage(stack);
    }
    else
    {
      this.item = null;
      this.metadata = 0;
    }
    return this;
  }
  
  protected final int getId()
  {
    return Item.getIdFromItem(this.item);
  }
  
  public boolean isEqual(comparableItem other)
  {
    if (other == null) {
      return false;
    }
    if (this.metadata == other.metadata)
    {
      if (this.item == other.item) {
        return true;
      }
      if ((this.item != null) && (other.item != null)) {
        return this.item.delegate.get() == other.item.delegate.get();
      }
    }
    return false;
  }
  
  public comparableItem clone()
  {
    return new comparableItem(this);
  }
  
  public boolean equals(Object o)
  {
    if (!(o instanceof comparableItem)) {
      return false;
    }
    return isEqual((comparableItem)o);
  }
  
  public int hashCode()
  {
    return this.metadata & 0xFFFF | getId() << 16;
  }
  
  public String toString()
  {
    String b = getClass().getName() + '@' + System.identityHashCode(this) + '{' + "m:" + this.metadata + ", i:" + (this.item == null ? null : this.item.getClass().getName()) + '@' + System.identityHashCode(this.item) + ", v:" + getId() + '}';
    return b;
  }
}
