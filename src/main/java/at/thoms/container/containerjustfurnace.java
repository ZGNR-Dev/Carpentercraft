package at.thoms.container;

import at.thoms.slots.Slotbeefuel;
import at.thoms.slots.SlottreebreederOutput;
import at.thoms.tileentitys.TileEntityjustfurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class containerjustfurnace extends Container
{
	private final TileEntityjustfurnace tileentity;
	private int cookTime, totalCookTime, burnTime, currentBurnTime;
	
	public containerjustfurnace(InventoryPlayer player, TileEntityjustfurnace tileentity) 
	{
		this.tileentity = tileentity;
		
		this.addSlotToContainer(new Slot(tileentity, 0, 26, 11));
		this.addSlotToContainer(new Slot(tileentity, 1, 26, 59));
		this.addSlotToContainer(new Slotbeefuel(tileentity, 2, 7, 35));
		this.addSlotToContainer(new SlottreebreederOutput(player.player, tileentity, 3, 81, 36));
		
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
			}
		}
		
		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public void addListener(IContainerListener listener) 
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileentity);
	}
	
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

            if (this.cookTime != this.tileentity.getField(2))
            {
                icontainerlistener.sendProgressBarUpdate(this, 2, this.tileentity.getField(2));
            }

            if (this.burnTime != this.tileentity.getField(0))
            {
                icontainerlistener.sendProgressBarUpdate(this, 0, this.tileentity.getField(0));
            }

            if (this.currentBurnTime != this.tileentity.getField(1))
            {
                icontainerlistener.sendProgressBarUpdate(this, 1, this.tileentity.getField(1));
            }

            if (this.totalCookTime != this.tileentity.getField(3))
            {
                icontainerlistener.sendProgressBarUpdate(this, 3, this.tileentity.getField(3));
            }
        }

        this.cookTime = this.tileentity.getField(2);
        this.burnTime = this.tileentity.getField(0);
        this.currentBurnTime = this.tileentity.getField(1);
        this.totalCookTime = this.tileentity.getField(3);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) 
	{
		this.tileentity.setField(id, data);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) 
	{
		return true;
	}
	
}