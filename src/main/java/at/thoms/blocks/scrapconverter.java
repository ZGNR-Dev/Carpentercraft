package at.thoms.blocks;

import java.util.Random;
import javax.annotation.Nullable;

import at.thoms.Carpentercraft;
import at.thoms.blocks.blocktypes.blockprimitivemachine;
import at.thoms.clientonly.gui.guihandler;
import at.thoms.container.containerscrapconverter;
import at.thoms.tileentitys.TileEntityscrapconverter;
import at.thoms.tileentitys.TileEntitysharpeningtable;
import at.thoms.utils.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//public class scrapconverter extends BlockContainer 
//{

public class scrapconverter extends blockprimitivemachine<TileEntityscrapconverter> { 

  public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
  
//  public scrapconverter(boolean lit)
//  {
  
    public scrapconverter() {
  
    super("scrapconverter", Material.ROCK);
//    setLightLevel(lit ? 0.888F : 0.0F);
    setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    setHardness(3.5F);
    setResistance(5.0F);
    
    setSoundType(SoundType.STONE);
  }
  
  @Override
  public int quantityDropped(Random random)
  {
    return 0;
  }
  
  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune)
  {
    return null;
  }
  
  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
  {
    TileEntity tileEntity = world.getTileEntity(pos);
    if (((tileEntity instanceof TileEntityscrapconverter)) && (!world.isRemote))
    {
      tileEntity.invalidate();
      if (!player.capabilities.isCreativeMode)
      {
        TileEntityscrapconverter tileEntityNet = (TileEntityscrapconverter)tileEntity;
        tileEntityNet.dropItems(world, pos);
      }
    }
    return super.removedByPlayer(state, world, pos, player, willHarvest);
  }
  
  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
  {
    IBlockState newState = worldIn.getBlockState(pos);
    TileEntity tileEntity = worldIn.getTileEntity(pos);
    if ((tileEntity instanceof TileEntityscrapconverter))
    {
      if (!(newState.getBlock() instanceof scrapconverter)) {
        if (!tileEntity.isInvalid())
        {
          ((TileEntityscrapconverter)tileEntity).dropItems(worldIn, pos);
          super.breakBlock(worldIn, pos, state);
        }
      }
    }
    else {
      super.breakBlock(worldIn, pos, state);
    }
  }
  
  @Override
  public EnumBlockRenderType getRenderType(IBlockState state)
  {
    return EnumBlockRenderType.MODEL;
  }
  
//  public boolean isLit()
//  {
//    return this == Carpentercraft.foundry_lit;
//  }
  
//  public void setLit(World worldIn, BlockPos pos, IBlockState state, boolean lit)
//  {
//    if ((state.getBlock() instanceof scrapconverter)) {
//      if (isLit())
//      {
//        if (!lit)
//        {
//          IBlockState newState = Carpentercraft.foundry.getDefaultState().withProperty(FACING, state.getValue(FACING));
//          worldIn.setBlockState(pos, newState);
//        }
//      }
//      else if (lit)
//      {
//        IBlockState newState = Carpentercraft.foundry_lit.getDefaultState().withProperty(FACING, state.getValue(FACING));
//        worldIn.setBlockState(pos, newState);
//      }
//    }
//  }
  
  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
  {
    if (worldIn.isRemote) {
      return true;
    }
    TileEntity tileEntity = worldIn.getTileEntity(pos);
    if (((tileEntity instanceof TileEntityscrapconverter)) && (!playerIn.isSneaking()))
    {
      playerIn.openGui(Carpentercraft.instance, guihandler.scrapconverter, worldIn, pos.getX(), pos.getY(), pos.getZ());
      return true;
    }
    return true;
  }
  
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
  {
    return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
  }
  
  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
  {
    worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    if (!worldIn.isRemote)
    {
      if ((stack == null) || (stack.getItem() == null))
      {
        new RuntimeException("stack" + (stack == null ? "=null" : ".getItem()=null") + ": skip loading tag compound").printStackTrace();
        return;
      }
      NBTTagCompound compound = stack.getTagCompound();
      if ((compound != null) && (compound.hasKey("TileEntityFoundry")))
      {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if ((tileentity instanceof TileEntityscrapconverter))
        {
          NBTTagCompound compound1 = (NBTTagCompound)compound.getTag("TileEntityFoundry");
          ((TileEntityscrapconverter)tileentity).readSyncableDataFromNBT(compound1);
        }
      }
    }
  }
  
  @Override
  public IBlockState getStateFromMeta(int meta)
  {
    int facing = meta & 0x3;
    switch (facing)
    {
    case 0: 
      return getDefaultState().withProperty(FACING, EnumFacing.WEST);
    case 1: 
      return getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    case 2: 
      return getDefaultState().withProperty(FACING, EnumFacing.EAST);
    }
    return getDefaultState().withProperty(FACING, EnumFacing.NORTH);
  }
  
  @Override
  public int getMetaFromState(IBlockState state)
  {
    EnumFacing facing = (EnumFacing)state.getValue(FACING);
    if (facing == EnumFacing.WEST) {
      return 0;
    }
    if (facing == EnumFacing.SOUTH) {
      return 1;
    }
    if (facing == EnumFacing.EAST) {
      return 2;
    }
    return 3;
  }
  
  @Override
  protected BlockStateContainer createBlockState()
  {
    return new BlockStateContainer(this, new IProperty[] { FACING });
  }
  
  @Override
  @SideOnly(Side.CLIENT)
  public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand)
//  {
//    if (isLit())
    {
      EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
      double d0 = pos.getX() + 0.5D;
      double d1 = pos.getY() + 0.8D - rand.nextDouble() * rand.nextDouble() * 0.4D;
      double d2 = pos.getZ() + 0.5D;
      double d3 = 0.52D;
      double d4 = rand.nextDouble() * 0.6D - 0.3D;
      if (enumfacing == EnumFacing.WEST)
      {
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
      }
      else if (enumfacing == EnumFacing.EAST)
      {
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
      }
      else if (enumfacing == EnumFacing.NORTH)
      {
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
      }
      else
      {
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
//  }
  
  
  @Override
  public boolean hasComparatorInputOverride(IBlockState state)
  {
    return true;
  }
  
  public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
  {
    TileEntity te = worldIn.getTileEntity(pos);
    if ((te instanceof TileEntityscrapconverter)) {
      return containerscrapconverter.calcRedstoneFromTileEntityFoundry((TileEntityscrapconverter)te);
    }
    return Container.calcRedstone(te);
  }
  
  public static void setState(boolean active, World worldIn, BlockPos pos)
  {
    IBlockState iblockstate = worldIn.getBlockState(pos);
    TileEntity tileEntity = worldIn.getTileEntity(pos);
    if ((tileEntity instanceof TileEntityscrapconverter))
    {
    	TileEntityscrapconverter tileEntityFoundry = (TileEntityscrapconverter)tileEntity;
      if (active) {
        worldIn.setBlockState(pos, ModBlocks.scrapconverter.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
      } else if (tileEntityFoundry.getContentSize() == 0.0F) {
        worldIn.setBlockState(pos, ModBlocks.scrapconverter.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
      } else {
        worldIn.setBlockState(pos, ModBlocks.scrapconverter.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
      }
    }
  }

	@Override
	public Class<TileEntityscrapconverter> getTileEntityClass() {
		return TileEntityscrapconverter.class;
	}

	@Override
	public TileEntityscrapconverter createTileEntity(World world, IBlockState state) {
		return new TileEntityscrapconverter();
	}
}
