package at.thoms.tileentitys;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.UUID;

public class TileEntityhotcrafting extends TileEntity
{



    private static GameProfile getGameProfile(UUID uuid)
    {
        if(uuid == null) return null;
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache().getProfileByUUID(uuid);
    }



    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        NBTTagCompound compound = super.writeToNBT(tag);
        //Sets the owner's UUID to the NBT
        return compound;
    }

}