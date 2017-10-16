package at.thoms.container;

import javax.annotation.Nullable;

import at.thoms.Carpentercraft;
import at.thoms.tileentitys.TileEntityextracrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class containerextracrafting extends Container
{
    /** The crafting matrix inventory (4x4). */
    public InventoryCrafting x4craftmatrix = new InventoryCrafting(this, 4, 4);
    public IInventory x4craftresult = new InventoryCraftResult();
    private final World worldObj;
    private EntityPlayer playerIn;
    private TileEntityextracrafting ec;
    private World worldIn;
    /** Position of the workbench */
    //private final BlockPos pos;
    private IItemHandler handler;

    public containerextracrafting(IInventory playerInv, TileEntityextracrafting tileentity)
    {
        this.worldObj = worldIn;
        this.ec = tileentity;
		IItemHandler handler = ec.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        //this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.x4craftmatrix, this.x4craftresult, 0, 124, 35));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 0, 8, 8));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 1, 8, 26));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 2, 8, 44));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 3, 8, 62));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 4, 26, 8));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 5, 26, 26));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 6, 26, 44));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 7, 26, 62));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 8, 44, 8));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 9, 44, 26));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 10, 44, 44));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 11, 44, 62));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 12, 62, 8));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 13, 62, 26));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 14, 62, 44));
        this.addSlotToContainer(new SlotCrafting(playerIn, x4craftmatrix, x4craftresult, 15, 62, 62));

        

        int counting = 0;
        for (int i = 0; i < 4; ++i)
        {
            for (int j = 0; j < 4; ++j)
            {
                this.addSlotToContainer(new Slot(this.x4craftmatrix, counting++, 30 + j * 18, 17 + i * 18));
            }
        }

        for (int k = 0; k < 3; ++k)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(playerInv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(playerInv, l, 8 + l * 18, 142));
        }

        this.onCraftMatrixChanged(this.x4craftmatrix);
        
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        this.x4craftresult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.x4craftmatrix, this.worldObj));
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.worldObj.isRemote)
        {
            for (int i = 0; i < 9; ++i)
            {
                ItemStack itemstack = this.x4craftmatrix.removeStackFromSlot(i);

                if (itemstack != null)
                {
                    playerIn.dropItem(itemstack, false);
                }
            }
        }
    }

    /**
     * Determines whether supplied player can use this container
     */
    	public boolean canInteractWith(EntityPlayer player) {
    		if(!worldIn.isRemote && !player.isSpectator()){
    			System.out.println("geht");
    		return true;
    		}
    		else{
    			System.out.println("geht nicht");
			return false;
    		}
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 0)
            {
                if (!this.mergeItemStack(itemstack1, 10, 46, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index >= 10 && index < 37)
            {
                if (!this.mergeItemStack(itemstack1, 37, 46, false))
                {
                    return null;
                }
            }
            else if (index >= 37 && index < 46)
            {
                if (!this.mergeItemStack(itemstack1, 10, 37, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 10, 46, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in
     * is null for the initial slot that was double-clicked.
     */
    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return slotIn.inventory != this.x4craftresult && super.canMergeSlot(stack, slotIn);
    }
}