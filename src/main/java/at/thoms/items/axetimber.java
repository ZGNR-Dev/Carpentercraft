package at.thoms.items;

import com.google.common.collect.Sets;

import at.thoms.Carpentercraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;

public class axetimber extends ItemTool {
  private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] { Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE });
  
  public axetimber(float damage, float attackSpeed, Item.ToolMaterial toolMaterial)
  {
    super(damage, attackSpeed, toolMaterial, EFFECTIVE_ON);
//  public axetimber(){
    setUnlocalizedName("axetimber");
    setCreativeTab(Carpentercraft.Tabbasic);
    setHarvestLevel("axe", 3);
    setRegistryName("axetimber");
  }
  
  @Override
  public boolean onBlockStartBreak(ItemStack stack, BlockPos blockPos, EntityPlayer player)
  {
    World world = player.worldObj;
    
    if(!player.isSneaking()){
    
    if (isTreeBase(world, blockPos, null))
    {
      List<BlockPos> woodBlocks = getConnectedWoodBlocks(world, blockPos);
      for (BlockPos pos : woodBlocks) {
        if (!pos.equals(blockPos)) {
          if (!isLeaveBlock(world, pos))
          {
            IBlockState block = world.getBlockState(pos);
            
            world.setBlockToAir(pos);
            block.getBlock().dropBlockAsItem(world, pos, block, 0);
          }
          else{
              IBlockState block = world.getBlockState(pos);
              
              world.setBlockToAir(pos);
              block.getBlock().dropBlockAsItem(world, pos, block, 0);

          }
        }
      }
      stack.damageItem(1, player);
    }
    return false;
  }
    return false;
  }
  
  
  public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
  {
    return true;
  }
  
  private List<BlockPos> getConnectedWoodBlocks(World world, BlockPos blockPos)
  {
    List<BlockPos> res = new ArrayList();
    Stack<BlockPos> positions = new Stack();
    positions.push(blockPos);
    
    IBlockState start = world.getBlockState(blockPos);
    Block startBlock = start.getBlock();
    do
    {
      BlockPos pos = (BlockPos)positions.pop();
      res.add(pos);
      if (pos.distanceSq(blockPos) < 4900.0D) {
        for (BlockPos newPos : BlockPos.getAllInBox(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() - 1), new BlockPos(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)))
        {
          boolean isWood = isValidWoodBlock(world, newPos, startBlock);
          if ((isWood) && (!positions.contains(newPos)) && (!res.contains(newPos))) {
            positions.push(newPos);
          }
        }
      }
    } while (!positions.empty());
    return res;
  }
  
  private boolean isTreeBase(World world, BlockPos pos, Block start)
  {
    BlockPos down = pos.down();
    return (isValidWoodBlock(world, pos, start)) && (!world.isAirBlock(down)) && (!isValidWoodBlock(world, down, start)) && (!isLeaveBlock(world, down)) && (world.isBlockFullCube(down));
  }
  
  private boolean isValidWoodBlock(World world, BlockPos pos, Block start)
  {
    IBlockState state = world.getBlockState(pos);
    Block block = state.getBlock();
    if (start != null) {
      return block == start;
    }
    return (block != null) && ((block.isWood(world, pos)) || (ArrayUtils.contains(Carpentercraft.woodBlocks, block)));
  }
  
  private boolean isLeaveBlock(World world, BlockPos pos)
  {
    IBlockState state = world.getBlockState(pos);
    Block block = state.getBlock();
    
    return (block != null) && (block.isLeaves(state, world, pos));
  }
  
  public float getStrVsBloc(ItemStack stack, IBlockState state)
  {
    Material material = state.getMaterial();
    return (material != Material.WOOD) && (material != Material.PLANTS) && (material != Material.VINE) && (material != Material.LEAVES)? super.getStrVsBlock(stack, state) : this.efficiencyOnProperMaterial;
  }
}
