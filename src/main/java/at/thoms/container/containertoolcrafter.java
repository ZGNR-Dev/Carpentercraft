package at.thoms.container;

import at.thoms.slots.Slottoolcraftercrafting;
import at.thoms.tileentitys.TileEntitytoolcrafter;
import at.thoms.oredict.toolcrafterCraftingManager;

import at.thoms.Carpentercraft;
import at.thoms.blocks.toolcrafter;
import at.thoms.slots.Slottoolcraftercrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class containertoolcrafter extends Container
{
    /*
    Crafting matrix:
    H H H H H
    H H H H H
    S S S S X
    H = hammer head
    S = stick (handle)
    X = not used
     */
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 5, 3);
    public IInventory craftResult = new InventoryCraftResult();
    private World worldObj;
    private BlockPos pos;
    /**
     * This number is increased when the slots are added (using this as their slot ID),
     * therefore giving the number of slots in the container
     */
    private int numSlots = 0;

    //Locations for slots in gui
    private String gridStartX = "Seventeen";
    private String gridStartY = "Seventeen";
    private int resultX = 139;
    private int resultY = 54;
    private int invStartX = 8;
    private int invStartY = 111;

    
   /*  public containertoolcrafter(InventoryPlayer invPlayer, World world, int x, int y, int z)
    {
        this(invPlayer, world, new BlockPos(x, y, z));
    } */

    public containertoolcrafter(InventoryPlayer invPlayer, TileEntitytoolcrafter tileEntity) {

//    public containertoolcrafter(InventoryPlayer invPlayer, World world, BlockPos position)
//    {
//        worldObj = world;
//        pos = position;

        //Add the slots
        this.addSlotToContainer(new Slottoolcraftercrafting(invPlayer.player, this.craftMatrix, this.craftResult, 0, resultX, resultY));

        /* int handleXStart = gridStartX + 18 * 2;
        int handleYStart = gridStartY + 18 * 2;
        for(int headY = 0; headY <= 2; headY++)
        {
            for(int headX = 0; headX <= 4; headX++)
            {
                //Slot(IInventory, Slot Index, X Position, Y Position)
                if(headY == 2)
                {
                    if(headX < 4)
                    {
                        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, handleXStart, handleYStart + headX * 18));
                    }
                }
                else
                    this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, gridStartX + headX * 18, gridStartY + headY * 18));
                numSlots++;
            }
        } */

        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 8, 16)); numSlots++; System.out.println(numSlots);
        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 26, 16)); numSlots++; System.out.println(numSlots);
        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 44, 16)); numSlots++; System.out.println(numSlots);
        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 62, 16)); numSlots++; System.out.println(numSlots);
        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 80, 16)); numSlots++; System.out.println(numSlots);

        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 8, 33)); numSlots++; System.out.println(numSlots);
        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 26, 33)); numSlots++; System.out.println(numSlots);
        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 44, 33)); numSlots++; System.out.println(numSlots);
        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 62, 33)); numSlots++; System.out.println(numSlots);
        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 80, 33)); numSlots++; System.out.println(numSlots);

        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 7, 52)); numSlots++; System.out.println(numSlots);
        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 44, 52)); numSlots++; System.out.println(numSlots);
        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 44, 70)); numSlots++; System.out.println(numSlots);
        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 44, 88)); numSlots++; System.out.println(numSlots);
        this.addSlotToContainer(new Slot(this.craftMatrix, numSlots, 80, 52)); numSlots++; System.out.println(numSlots);
        
        
        bindPlayerInventory(invPlayer);
    }

	public void onCraftMatrixChanged(IInventory inventory)
    {
        ItemStack stack = toolcrafterCraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj);

        //if(stack == null) LogHelper.info("Hammer Crafting Output: Null");
        //else LogHelper.info("Hammer Crafting Output: " + stack.getDisplayName());

        this.craftResult.setInventorySlotContents(0, stack);
    }

    public boolean canInteractWith(EntityPlayer player)
    {

    	return true;
    	
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, invStartX + j * 18, invStartY + i * 18));
            }
        }

        for (int i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(inventoryPlayer, i, invStartX + i * 18, invStartY + 18 * 3 + 4));
        }
    }

   /*  Drops items in the crafting grid when gui is closed
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);

        if (!this.worldObj.isRemote)
        {
            for (int i = 0; i < numSlots; ++i)
            {
                ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i); //Used to be .getStackInSlotOnClosing(i)

                if (itemstack != null)
                {
                    player.dropItem(itemstack, false);
                }
            }
        }
    } */

    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
    {
        ItemStack stack = null;
        Slot slotObject = this.inventorySlots.get(slot);

        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            int slotInvStart = this.numSlots;

            //If slot 0 (output)
            if (slot == 0)
            {
                if (!this.mergeItemStack(stackInSlot, slotInvStart, slotInvStart+36, true))
                    return null;

                slotObject.onSlotChange(stackInSlot, stack);
            }
            //If slot Inventory (not Hotbar)
            else if (slot >= slotInvStart && slot <= slotInvStart+26)
            {
                if (!this.mergeItemStack(stackInSlot, slotInvStart+28, slotInvStart+36, false))
                    return null;
            }
            //If slot Hotbar
            else if (slot >= slotInvStart+28 && slot <= slotInvStart+36)
            {
                if (!this.mergeItemStack(stackInSlot, slotInvStart, slotInvStart+27, false))
                    return null;
            }
            //If slot Crafting Grid
            else if (!this.mergeItemStack(stackInSlot, slotInvStart, slotInvStart+36, false))
                return null;

            if (stackInSlot.stackSize == 0)
                slotObject.putStack(null);
            else
                slotObject.onSlotChanged();

            if (stackInSlot.stackSize == stack.stackSize)
                return null;

            slotObject.onPickupFromSlot(player, stackInSlot);
        }

        return stack;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in
     * is null for the initial slot that was double-clicked.
     */
    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }
}