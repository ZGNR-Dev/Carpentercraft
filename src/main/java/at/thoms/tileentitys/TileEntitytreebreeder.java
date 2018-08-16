package at.thoms.tileentitys;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import at.thoms.Carpentercraft;
import at.thoms.blocks.treebreeder;
import at.thoms.recipes.Recipestreebreeder;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.awt.List;

public class TileEntitytreebreeder extends TileEntity implements IInventory, ITickable, ISidedInventory
{
//	@Nonnull
//	private ItemStack[] inventory = List.<ItemStack>withSize(4, ItemStack.isEmpty());
	
	private String customName;
	
	private int burnTime;
	private int currentBurnTime;
	private int cookTime;
	private int totalCookTime;
    private ItemStack[] furnaceItemStacks = new ItemStack[4];

	
	@Override
	public String getName() 
	{
		return this.hasCustomName() ? this.customName : "container.sintering_furnace";
	}

	@Override
	public boolean hasCustomName() 
	{
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public void setCustomName(String customName) 
	{
		this.customName = customName;
	}
	
	@Override
	public ITextComponent getDisplayName() 
	{
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory() 
	{
		return this.furnaceItemStacks.length;
	}


    @Nullable
    public ItemStack getStackInSlot(int index)
    {
        return this.furnaceItemStacks[index];
    }

	@Override
	public ItemStack decrStackSize(int index, int count) 
	{
		return ItemStackHelper.getAndSplit(this.furnaceItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) 
	{
		return ItemStackHelper.getAndRemove(this.furnaceItemStacks, index);
	}

    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        boolean flag = stack != null && stack.isItemEqual(this.furnaceItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.furnaceItemStacks[index]);
        this.furnaceItemStacks[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }

        if (index == 0 && !flag)
        {
            this.totalCookTime = this.getCookTime(stack);
            this.cookTime = 0;
            this.markDirty();
        }
    }

    public int getCookTime(@Nullable ItemStack stack)
    {
        return 200;
    }
   
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");

            if (j >= 0 && j < this.furnaceItemStacks.length)
            {
                this.furnaceItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }

		
		this.furnaceItemStacks = List.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);
		this.burnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.currentBurnTime = getItemBuzzingTime((ItemStack)this.furnaceItemStacks.get(2));
		
		if(compound.hasKey("CustomName", 8)) this.setCustomName(compound.getString("CustomName"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short)this.burnTime);
		compound.setInteger("CookTime", (short)this.cookTime);
		compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.furnaceItemStacks.length; ++i)
        {
            if (this.furnaceItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.furnaceItemStacks[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        compound.setTag("Items", nbttaglist);
		
		if(this.hasCustomName()) compound.setString("CustomName", this.customName);
		return compound;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}
	
	public boolean isBurning() 
	{
		return this.burnTime > 0;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory) 
	{
		return inventory.getField(0) > 0;
	}
	
	public void update() 
	{
		boolean flag = this.isBurning();
		boolean flag1 = false;
		
		if(this.isBurning()) --this.burnTime;
		
		if(!this.worldObj.isRemote) 
		{
			ItemStack stack = (ItemStack)this.furnaceItemStacks.get(2);
			
			if(this.isBurning() || stack != null && !((((ItemStack)this.furnaceItemStacks.get(0)) == null) || ((ItemStack)this.furnaceItemStacks.get(1)) == null)) 
			{
				if(!this.isBurning() && this.canSmelt()) 
				{
					this.burnTime = getItemBuzzingTime(stack);
					this.currentBurnTime = this.burnTime;
					
					if(this.isBurning()) 
					{
						flag1 = true;
						
						if(stack != null) 
						{
							Item item = stack.getItem();
							stack.stackSize--;
							
							if(stack == null) 
							{
								ItemStack item1 = item.getContainerItem(stack);
								this.furnaceItemStacks.set(2, item1);
							}
						}
					}
				} 
				if(this.isBurning() && this.canSmelt()) 
				{
					++this.cookTime;
					
					if(this.cookTime == this.totalCookTime) 
					{
						this.cookTime = 0;
						this.totalCookTime = this.getCookTime((ItemStack)this.furnaceItemStacks.get(0), (ItemStack)this.furnaceItemStacks.get(1));
						this.smeltItem();
						flag1 = true;
					}
				} 
				else this.cookTime = 0;
			} 
			else if(!this.isBurning() && this.cookTime > 0) 
			{
				this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
			}
			if(flag != this.isBurning()) 
			{
				flag1 = true;
				treebreeder.setState(this.isBurning(), this.worldObj, this.pos);
			}
		} 
		if(flag1) this.markDirty();
	}
	
	public int getCookTime(ItemStack input1, ItemStack input2) 
	{
		return 200;
	}
	
	private boolean canSmelt() 
	{
		if(((ItemStack)this.furnaceItemStacks[0]) == null || ((ItemStack)this.furnaceItemStacks[1]) == null) return false;
		else 
		{
			ItemStack result = Recipestreebreeder.getInstance().getSinteringResult((ItemStack)this.furnaceItemStacks.get(0), (ItemStack)this.furnaceItemStacks.get(1));	
			if(result == null) return false;
			else
			{
				ItemStack output = (ItemStack)this.furnaceItemStacks.get(3);
				if(output == null) return true;
				if(!output.isItemEqual(result)) return false;
				int res = output.stackSize + result.stackSize;
				return res <= getInventoryStackLimit() && res <= output.getMaxStackSize();
			}
		}
	}
	
	public void smeltItem() 
	{
		if(this.canSmelt()) 
		{
			ItemStack input1 = (ItemStack)this.furnaceItemStacks[0];
			ItemStack input2 = (ItemStack)this.furnaceItemStacks.get(1);
			ItemStack result = Recipestreebreeder.getInstance().getSinteringResult(input1, input2);
			ItemStack output = (ItemStack)this.furnaceItemStacks.get(3);
			
			if(output == null) this.furnaceItemStacks.set(3, result.copy());
			else if(output.getItem() == result.getItem()) output.grow(result.getCount());
			
//			input1.shrink(1);
//			input2.shrink(1);
			input1.stackSize--;
			input2.stackSize--;
		}
	}
	
    /**
     * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
     * fuel
     */
    public static int getItemBuzzingTime(ItemStack stack)
    {
        if (stack == null)
        {
            return 0;
        }
        else
        {
            Item item = stack.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR)
            {
                Block block = Block.getBlockFromItem(item);

            }

            if (item == Carpentercraft.bee) return 3200;
            return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(stack);
        }
    }

    public static boolean isItemBee(ItemStack stack)
    {
        return getItemBuzzingTime(stack) > 0;
    }
	
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) 
	{
		
		if(index == 3) return false;
		else if(index != 2) return true;
		else 
		{
			return isItemBee(stack);
		}
	}
	
	public String getGuiID() 
	{
		return Carpentercraft.MODID + ":tile_entity_treebreeder";
	}

	@Override
	public int getField(int id) 
	{
		switch(id) 
		{
		case 0:
			return this.burnTime;
		case 1:
			return this.currentBurnTime;
		case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		default:
			return 0;
		}
	}

	@Override
	public void setField(int id, int value) 
	{
		switch(id) 
		{
		case 0:
			this.burnTime = value;
			break;
		case 1:
			this.currentBurnTime = value;
			break;
		case 2:
			this.cookTime = value;
			break;
		case 3:
			this.totalCookTime = value;
		}
	}

	@Override
	public int getFieldCount() 
	{
		return 4;
	}

	@Override
    public void clear()
    {
        for (int i = 0; i < this.furnaceItemStacks.length; ++i)
        {
            this.furnaceItemStacks[i] = null;
        }
    }
}