package at.thoms.blocks;

import at.thoms.Carpentercraft;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.tileentitys.TileEntitytoolcrafter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

//public class toolcrafter extends Block
public class toolcrafter extends blockprimitivemachine<TileEntitytoolcrafter> {
    public toolcrafter()
    {
        super("toolcrafter", Material.ROCK);
        setUnlocalizedName("toolcrafter");
        setCreativeTab(Carpentercraft.Tabbasic);
        setHardness(2f);
        setResistance(10f);
    }

    //Return 3 for standard block models
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			player.openGui(Carpentercraft.instance, guihandler.toolcrafter, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public Class<TileEntitytoolcrafter> getTileEntityClass() {
		return TileEntitytoolcrafter.class;
	}

	@Override
	public TileEntitytoolcrafter createTileEntity(World world, IBlockState state) {
		return new TileEntitytoolcrafter();
	}
}