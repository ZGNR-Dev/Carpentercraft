package at.thoms.tileentitys;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TileEntityblockcreator extends TileEntity
{

    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        NBTTagCompound compound = super.writeToNBT(tag);
        return compound;
    }

}