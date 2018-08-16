package at.thoms.tileentitys;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import at.thoms.utils.ItemStackKey;
import at.thoms.Carpentercraft;
import at.thoms.blocks.scrapconverter;
import at.thoms.recipes.Recipesscrapconverter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockBehaviors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TileEntityscrapconverter extends TileEntity implements IInventory, ISidedInventory, ITickable, IBlockBehaviors {
	
  private static final int[] slotsTop = { 0 };
  private static final int[] slotsBottom = { 2, 1 };
  private static final int[] slotsSides = { 1 };
  private ItemStack[] inv = new ItemStack[3];
  private int burnTime;
  private int totalBurnTime;
  private int cookTime;
  private int totalCookTime;
  private int contentLevel;
  public static final float capacity = 16.0F;
  public static boolean checkOverflow = true;
  public static float timeFactor = 1.0F;
  public static float fuelFactor = 1.0F;
  private float contentSize = 0.0F;
  Hashtable<ItemStackKey, Float> content = new Hashtable();
  Hashtable<ItemStackKey, Float> toSmelt;
  private boolean willExplode;
  private float willBurn;
  private int oldStackSize0;
  private int oldStackSize2;
  private float oldContentSize;
  private boolean slotChanged;
  ArrayList strings;
  private String customName;
  
  public TileEntityscrapconverter()
  {
    this.inv = new ItemStack[3];
  }
  
  public static List<String> readStrings(NBTTagCompound compound)
  {
    NBTTagList nbttaglist = compound.getTagList("Residui", 10);
    if ((nbttaglist == null) || (nbttaglist.tagCount() == 0)) {
      return null;
    }
    ArrayList strings = new ArrayList();
    try
    {
      TextComponentTranslation cct = new TextComponentTranslation("container.tooltip.title", new Object[0]);
      
      String title = new TextComponentTranslation("container.tooltip.title", new Object[0]).getUnformattedText();
      
      strings.add(TextFormatting.WHITE + "" + title);
    }
    catch (Exception e)
    {
      return null;
    }
    float total = 0.0F;
    for (int i = 0; i < nbttaglist.tagCount(); i++) {
      try
      {
        NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
        if (nbttagcompound1 != null)
        {
          float value = nbttagcompound1.getFloat("ContentValue");
          ItemStack residuo = ItemStack.loadItemStackFromNBT(nbttagcompound1);
          ItemStackKey key = ItemStackKey.createKey(residuo);
          if ((value > 0.0F) && (key != null))
          {
            total += value;
            strings.add(TextFormatting.GRAY + "" + key.getItemStack().getDisplayName() + ": " + formatFloat(value));
          }
        }
      }
      catch (Exception localException1) {}
    }
    if (strings.size() == 1) {
      try
      {
        String empty = new TextComponentTranslation("container.tooltip.empty", new Object[0]).getUnformattedText();
        strings.add(TextFormatting.GRAY + "" + empty);
      }
      catch (Exception localException2) {}
    }
    try
    {
      String totalStr = new TextComponentTranslation("container.tooltip.total", new Object[0]).getUnformattedText();
      strings.add("");
      strings.add(TextFormatting.DARK_AQUA + totalStr + ": " + formatFloat(total) + "/" + formatFloat(16.0F));
    }
    catch (Exception localException3) {}
    return strings;
  }
  
  public void readSyncableDataFromNBT(NBTTagCompound compound)
  {
    this.burnTime = compound.getShort("BurnTime");
    this.totalBurnTime = compound.getShort("BurnTimeTotal");
    this.cookTime = compound.getShort("CookTime");
    this.totalCookTime = compound.getShort("CookTimeTotal");
    
    NBTTagList nbttaglist = compound.getTagList("Items", 10);
    this.inv = new ItemStack[3];
    for (int i = 0; i < nbttaglist.tagCount(); i++)
    {
      NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
      byte slot = nbttagcompound1.getByte("Slot");
      if ((slot >= 0) && (slot < this.inv.length)) {
        this.inv[slot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
      }
    }
    setContentSize(0.0F);
    this.content = new Hashtable();
    nbttaglist = compound.getTagList("Residui", 10);
    for (int i = 0; i < nbttaglist.tagCount(); i++)
    {
      NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
      float value = nbttagcompound1.getFloat("ContentValue");
      ItemStack residuo = ItemStack.loadItemStackFromNBT(nbttagcompound1);
      ItemStackKey key = ItemStackKey.createKey(residuo);
      if ((value > 0.0F) && (key != null))
      {
        this.content.put(key, Float.valueOf(value));
        setContentSize(getContentSize() + value);
      }
    }
    updateStrings();
    if (compound.hasKey("CustomName", 8)) {
      this.customName = compound.getString("CustomName");
    }
  }
  
  @Override
  public void readFromNBT(NBTTagCompound compound)
  {
    super.readFromNBT(compound);
    readSyncableDataFromNBT(compound);
  }
  
  public void writeSyncableDataToNBT(NBTTagCompound compound)
  {
    compound.setShort("BurnTime", (short)this.burnTime);
    compound.setShort("BurnTimeTotal", (short)this.totalBurnTime);
    compound.setShort("CookTime", (short)this.cookTime);
    compound.setShort("CookTimeTotal", (short)this.totalCookTime);
    
    NBTTagList nbttaglist = new NBTTagList();
    for (int i = 0; i < this.inv.length; i++) {
      if (this.inv[i] != null)
      {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.setByte("Slot", (byte)i);
        this.inv[i].writeToNBT(nbttagcompound1);
        nbttaglist.appendTag(nbttagcompound1);
      }
    }
    compound.setTag("Items", nbttaglist);
    
    nbttaglist = new NBTTagList();
    Enumeration<ItemStackKey> keys = this.content.keys();
    for (int i = 0; keys.hasMoreElements(); i++)
    {
      ItemStackKey key = (ItemStackKey)keys.nextElement();
      Float value = (Float)this.content.get(key);
      if (value == null)
      {
        value = Float.valueOf(0.0F);
      }
      else if (value.floatValue() > 0.0F)
      {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.setFloat("ContentValue", value.floatValue());
        key.getItemStack().writeToNBT(nbttagcompound1);
        nbttaglist.appendTag(nbttagcompound1);
      }
    }
    compound.setTag("Residui", nbttaglist);
    if (hasCustomName()) {
      compound.setString("CustomName", this.customName);
    }
  }
  
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound)
  {
    super.writeToNBT(compound);
    writeSyncableDataToNBT(compound);
    return compound;
  }
  
  @Override
  public NBTTagCompound getUpdateTag()
  {
    NBTTagCompound syncData = super.getUpdateTag();
    
    writeSyncableDataToNBT(syncData);
    
    return syncData;
  }
  
  @Override
  public SPacketUpdateTileEntity getUpdatePacket()
  {
    return getDescriptionPacket();
  }
  
  public SPacketUpdateTileEntity getDescriptionPacket()
  {
    return new SPacketUpdateTileEntity(this.pos, 0, getUpdateTag());
  }
  
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
  {
    readSyncableDataFromNBT(pkt.getNbtCompound());
  }
  
  public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
  {
    if (((oldState.getBlock() instanceof scrapconverter)) && ((newState.getBlock() instanceof scrapconverter))) {
      return false;
    }
    return super.shouldRefresh(world, pos, oldState, newState);
  }
  
  public List getContentAsList()
  {
    return this.strings;
  }
  
  private void updateStrings()
  {
//	  Original sogt world    field_145850_b
    if ((this.worldObj == null) || (!this.worldObj.isRemote)) {
      return;
    }
    this.strings = new ArrayList();
    
    String title = new TextComponentTranslation("container.tooltip.title", new Object[0]).getUnformattedText();
    this.strings.add(TextFormatting.WHITE + "" + title);
    if (this.content.size() > 0)
    {
      Enumeration<ItemStackKey> keys = this.content.keys();
      while (keys.hasMoreElements())
      {
        ItemStackKey key = (ItemStackKey)keys.nextElement();
        Float value = (Float)this.content.get(key);
        this.strings.add(TextFormatting.GRAY + "" + key.getItemStack().getDisplayName() + ": " + formatFloat(value.floatValue()));
      }
    }
    else
    {
      String empty = new TextComponentTranslation("container.tooltip.empty", new Object[0]).getUnformattedText();
      this.strings.add(TextFormatting.GRAY + "" + empty);
    }
    String total = new TextComponentTranslation("container.tooltip.total", new Object[0]).getUnformattedText();
    this.strings.add("");
    this.strings.add(TextFormatting.DARK_AQUA + total + ": " + formatFloat(getContentSize()) + "/" + formatFloat(16.0F));
  }
  
  public static String formatFloat(float f)
  {
    return "" + (int)(f * 100.0F) / 100.0F;
  }
  
  public static int getItemBurnTime(ItemStack stack)
  {
    if ((stack == null) || (stack.getItem() == null) || (stack.stackSize == 0)) {
      return 0;
    }
    return TileEntityFurnace.getItemBurnTime(stack);
  }
  
  @Override
  public void update()
  {
    if (this.worldObj.isRemote) {
      return;
    }
    boolean isBurningFlag = isBurning();
    boolean flag1 = false;
    
    boolean sposto = false;
    if ((getContentSize() >= 1.0F) && (!this.worldObj.isRemote))
    {
      ItemStackKey key = null;
      Float value = null;
      if (this.inv[2] != null)
      {
        if ((this.inv[2].getItem() != null) && (this.inv[2].stackSize > 0))
        {
          if (this.inv[2].stackSize < this.inv[2].getMaxStackSize())
          {
            key = ItemStackKey.createKey(this.inv[2]);
            if (key != null)
            {
              value = (Float)this.content.get(key);
              if ((value != null) && (value.floatValue() >= 1.0F))
              {
                this.inv[2].stackSize += 1;
                sposto = true;
              }
            }
          }
        }
        else {
          this.inv[2] = null;
        }
      }
      else
      {
        Enumeration<ItemStackKey> keys = this.content.keys();
        Float maxValue = Float.valueOf(-1.0F);
        ItemStackKey maxKey = null;
        while (keys.hasMoreElements())
        {
          key = (ItemStackKey)keys.nextElement();
          value = (Float)this.content.get(key);
          if (value.floatValue() > maxValue.floatValue())
          {
            maxValue = value;
            maxKey = key;
          }
        }
        if (maxValue.floatValue() >= 1.0F)
        {
          key = maxKey;
          value = maxValue;
          this.inv[2] = key.getItemStack();
          this.inv[2].stackSize = 1;
          sposto = true;
        }
      }
      if (sposto)
      {
        value = Float.valueOf(value.floatValue() - 1.0F);
        if (value.floatValue() > 0.0F) {
          this.content.put(key, value);
        } else {
          this.content.remove(key);
        }
        setContentSize(getContentSize() - 1.0F);
        if ((this.contentSize == 0.0F) && (!isBurning())) {
        	scrapconverter.setState(false, this.worldObj, this.pos);
        }
        markDirty();
        this.worldObj.notifyBlockUpdate(this.pos, this.worldObj.getBlockState(this.pos), this.worldObj.getBlockState(this.pos), 0);
      }
      else if ((getContentSize() > 16.0F) && 
        (spanaMagma()))
      {
        Enumeration<ItemStackKey> keys = this.content.keys();
        Float maxValue = Float.valueOf(-1.0F);
        ItemStackKey maxKey = null;
        while (keys.hasMoreElements())
        {
          key = (ItemStackKey)keys.nextElement();
          value = (Float)this.content.get(key);
          if (value.floatValue() > maxValue.floatValue())
          {
            maxValue = value;
            maxKey = key;
          }
        }
        if (maxValue.floatValue() > 1.0F)
        {
          key = maxKey;
          value = maxValue;
          sposto = true;
          value = Float.valueOf(value.floatValue() - 1.0F);
          if (value.floatValue() > 0.0F) {
            this.content.put(key, value);
          } else {
            this.content.remove(key);
          }
          setContentSize(getContentSize() - 1.0F);
          if ((this.contentSize == 0.0F) && (!isBurning())) {
            scrapconverter.setState(false, this.worldObj, this.pos);
          }
          markDirty();
          this.worldObj.notifyBlockUpdate(this.pos, this.worldObj.getBlockState(this.pos), this.worldObj.getBlockState(this.pos), 0);
        }
      }
    }
    int newStackSize0;
//    int newStackSize0;
    if (this.inv[0] != null) {
      newStackSize0 = this.inv[0].stackSize;
    } else {
      newStackSize0 = 0;
    }
    int newStackSize2;
//    int newStackSize2;
    if (this.inv[2] != null) {
      newStackSize2 = this.inv[2].stackSize;
    } else {
      newStackSize2 = 0;
    }
    if ((!sposto) && ((this.slotChanged) || (this.oldStackSize0 != newStackSize0) || (this.oldStackSize2 != newStackSize2) || (this.oldContentSize != getContentSize())))
    {
      this.slotChanged = false;
      this.oldStackSize0 = newStackSize0;
      this.oldStackSize2 = newStackSize2;
      this.oldContentSize = getContentSize();
      
      updateToSmelt();
    }
    if ((this.contentSize > 0.0F) && (!this.worldObj.isRemote) && (this.inv[1] != null) && (this.inv[1].stackSize > 0) && (this.inv[1].getItem() == Items.BUCKET))
    {
      Vector<ItemStackKey> toMod = new Vector();
      Vector<Float> toSub = new Vector();
      
      Enumeration<ItemStackKey> keys = this.content.keys();
      float totRes = 0.0F;
      while ((keys.hasMoreElements()) && (totRes < 1.0F))
      {
        ItemStackKey key = (ItemStackKey)keys.nextElement();
        Float value = (Float)this.content.get(key);
        int valueInt = value.intValue();
        float res = value.floatValue() - valueInt;
        if (res > 0.0F)
        {
          if (totRes + res > 1.0F)
          {
            res = 1.0F - totRes;
            totRes = 1.0F;
          }
          else
          {
            totRes += res;
          }
          toMod.add(key);
          toSub.add(Float.valueOf(res));
        }
      }
      if (totRes == 0.0F)
      {
        keys = this.content.keys();
        while (keys.hasMoreElements())
        {
          ItemStackKey key = (ItemStackKey)keys.nextElement();
          Float value = (Float)this.content.get(key);
          if (value.floatValue() >= 1.0F)
          {
            totRes += 1.0F;
            toMod.add(key);
            toSub.add(Float.valueOf(1.0F));
            break;
          }
        }
      }
      if (totRes > 0.0F)
      {
        for (int i = 0; i < toMod.size(); i++)
        {
          ItemStackKey key = (ItemStackKey)toMod.elementAt(i);
          Float value = Float.valueOf(((Float)this.content.get(key)).floatValue() - ((Float)toSub.elementAt(i)).floatValue());
          if (value.floatValue() > 0.0F) {
            this.content.put(key, value);
          } else {
            this.content.remove(key);
          }
          this.contentSize -= ((Float)toSub.elementAt(i)).floatValue();
        }
        if (this.inv[1].stackSize == 1)
        {
          this.inv[1] = new ItemStack(Items.LAVA_BUCKET);
        }
        else
        {
          this.inv[1].stackSize -= 1;
          if (this.inv[1].stackSize == 0) {
            this.inv[1] = null;
          }
          if ((!this.worldObj.isRemote) && (!this.worldObj.restoringBlockSnapshots)) {
            Block.spawnAsEntity(this.worldObj, this.pos, new ItemStack(Items.LAVA_BUCKET));
          }
        }
        if ((this.contentSize == 0.0F) && (!isBurning())) {
          scrapconverter.setState(false, this.worldObj, this.pos);
        }
        markDirty();
        this.worldObj.notifyBlockUpdate(this.pos, this.worldObj.getBlockState(this.pos), this.worldObj.getBlockState(this.pos), 0);
      }
    }
    if (isBurning()) {
      this.burnTime -= 1;
    }
    if ((!isBurning()) && ((this.inv[1] == null) || (this.inv[0] == null)))
    {
      if (this.cookTime > 0)
      {
        this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
        if (this.cookTime == 0)
        {
          flag1 = true;
          
          markDirty();
          this.worldObj.notifyBlockUpdate(this.pos, this.worldObj.getBlockState(this.pos), this.worldObj.getBlockState(this.pos), 0);
        }
      }
    }
    else
    {
      if ((!isBurning()) && (canSmelt()))
      {
        this.totalBurnTime = (this.burnTime = (int)(getItemBurnTime(this.inv[1]) * fuelFactor));
        if (isBurning())
        {
          flag1 = true;
          if (this.inv[1] != null)
          {
            this.inv[1].stackSize -= 1;
            if (this.inv[1].stackSize == 0) {
              this.inv[1] = this.inv[1].getItem().getContainerItem(this.inv[1]);
            }
          }
        }
      }
      if ((isBurning()) && (this.worldObj.rand.nextFloat() < 0.005D))
      {
        this.worldObj.playSound((EntityPlayer)null, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.AMBIENT, 0.3F, 0.7F + this.worldObj.rand.nextFloat() * 0.2F);
        if (this.worldObj.rand.nextFloat() < 0.1D)
        {
          this.worldObj.playSound((EntityPlayer)null, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.AMBIENT, 0.6F, 0.7F + this.worldObj.rand.nextFloat() * 0.2F);
          if (this.worldObj.rand.nextFloat() < 0.03D)
          {
            int radius = 1;
            int i = this.worldObj.rand.nextInt(radius * 2 + 1) - radius;
            int j = this.worldObj.rand.nextInt(radius * 2 + 1) - radius;
            int k = this.worldObj.rand.nextInt(radius * 2 + 1) - radius;
            BlockPos pos1 = this.pos.add(i, j, k);
            if (this.worldObj.isAirBlock(pos1)) {
              this.worldObj.setBlockState(pos1, Blocks.FIRE.getDefaultState());
            }
          }
        }
      }
      if ((isBurning()) && (canSmelt()))
      {
        this.cookTime += 1;
        if ((this.willExplode) && (this.cookTime == this.totalCookTime - 80)) {
          this.worldObj.playSound((EntityPlayer)null, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 0.8F, 1.0F);
        }
        if ((this.inv[0] != null) && (this.inv[0].getItem() == Items.WATER_BUCKET) && (this.worldObj.rand.nextFloat() < 0.01D))
        {
          waterEffect();
          this.inv[0] = new ItemStack(Items.BUCKET);
          this.cookTime = 0;
          this.totalCookTime = ((int)(getCookTime(this.inv[0]) * timeFactor));
          
          flag1 = true;
        }
        if ((this.willBurn > 0.0F) && (this.worldObj.rand.nextFloat() < 0.01D * this.willBurn))
        {
          if (this.worldObj.rand.nextFloat() < 0.2D) {
            this.worldObj.playSound((EntityPlayer)null, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.AMBIENT, 0.6F, 0.7F + this.worldObj.rand.nextFloat() * 0.2F);
          }
          int radius = 1;
          int i = this.worldObj.rand.nextInt(radius * 2 + 1) - radius;
          int j = this.worldObj.rand.nextInt(radius * 2 + 1) - radius;
          int k = this.worldObj.rand.nextInt(radius * 2 + 1) - radius;
          BlockPos pos1 = this.pos.add(i, j, k);
          if ((this.worldObj.isAirBlock(pos1)) && (this.worldObj.rand.nextFloat() < 0.2D)) {
            this.worldObj.setBlockState(pos1, Blocks.FIRE.getDefaultState());
          }
        }
        if (this.cookTime == this.totalCookTime)
        {
          this.cookTime = 0;
          this.totalCookTime = ((int)(getCookTime(this.inv[0]) * timeFactor));
          smeltItem();
          flag1 = true;
        }
      }
      else
      {
        this.cookTime = 0;
      }
    }
    if (isBurningFlag != isBurning())
    {
      flag1 = true;
      scrapconverter.setState(isBurning(), this.worldObj, this.pos);
    }
    if (flag1) {
    	markDirty();
    }
  }
  
  private boolean spanaMagma(BlockPos pos)
  {
    IBlockState state = this.worldObj.getBlockState(pos);
    Block block = state.getBlock();
    Material material = block.getMaterial(state);
    
    boolean isBlocked = ((block instanceof BlockDoor)) || (block == Blocks.STANDING_SIGN) || (block == Blocks.LADDER) || (material == Material.PORTAL) || (material.blocksMovement());
    if (!isBlocked)
    {
      if (material != Material.LAVA)
      {
        state.getBlock().dropBlockAsItem(this.worldObj, pos, state, 0);
        this.worldObj.setBlockState(pos, Blocks.FLOWING_LAVA.getDefaultState().withProperty(BlockLiquid.LEVEL, Integer.valueOf(0)));
      }
      return true;
    }
    return false;
  }
  
  private boolean spanaMagma()
  {
    IBlockState state = this.worldObj.getBlockState(this.pos);
    try
    {
      if ((state.getBlock() instanceof scrapconverter))
      {
        EnumFacing direction = (EnumFacing)state.getValue(scrapconverter.FACING);
        
        BlockPos front = this.pos.add(direction.getDirectionVec());
        if (spanaMagma(front)) {
          return true;
        }
        BlockPos back = this.pos.add(direction.getOpposite().getDirectionVec());
        if (spanaMagma(back)) {
          return true;
        }
        BlockPos right = this.pos.add(direction.rotateY().getDirectionVec());
        if (spanaMagma(right)) {
          return true;
        }
        BlockPos left = this.pos.add(direction.getOpposite().rotateY().getDirectionVec());
        if (spanaMagma(left)) {
          return true;
        }
        BlockPos up = this.pos.add(EnumFacing.UP.getDirectionVec());
        if (spanaMagma(up)) {
          return true;
        }
        BlockPos down = this.pos.add(EnumFacing.DOWN.getDirectionVec());
        if (spanaMagma(down)) {
          return true;
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public boolean isBurning()
  {
    return this.burnTime > 0;
  }
  
  public int getCookTime(ItemStack stack)
  {
    return 200;
  }
  
  private boolean canSmelt()
  {
    if (getContentSize() > 16.0F) {
      return false;
    }
    return this.toSmelt != null;
  }
  
  public void updateToSmelt()
  {
    if ((this.worldObj != null) && (this.worldObj.isRemote)) {
      return;
    }
    this.toSmelt = null;
    this.willExplode = false;
    this.willBurn = 0.0F;
    if ((this.inv[0] == null) || (this.inv[0].getItem() == null) || (this.inv[0].stackSize == 0)) {
      return;
    }
    Hashtable<ItemStackKey, Float> materiali = Recipesscrapconverter.getMateriali(this.inv[0]);
    if ((materiali == null) || (materiali.size() == 0)) {
      return;
    }
    if (Recipesscrapconverter.getPolvere(this.inv[0]) != 0.0F) {
      this.willExplode = true;
    }
    this.willBurn = Recipesscrapconverter.getFuoco(this.inv[0]);
    if (this.inv[0].getItem() == Item.getItemFromBlock(Carpentercraft.scrapconverter))
    {
      NBTTagCompound compound = this.inv[0].getTagCompound();
      if (compound != null)
      {
        materiali = (Hashtable)materiali.clone();
        TileEntityscrapconverter foundry = new TileEntityscrapconverter();
        foundry.readSyncableDataFromNBT(this.inv[0].getTagCompound().getCompoundTag("TileEntityFoundry"));
        
        Enumeration<ItemStackKey> keys = foundry.content.keys();
        while (keys.hasMoreElements())
        {
          ItemStackKey key = (ItemStackKey)keys.nextElement();
          Float value = (Float)foundry.content.get(key);
          
          Float value2 = (Float)materiali.get(key);
          if (value2 == null) {
            value2 = Float.valueOf(0.0F);
          }
          materiali.put(key, Float.valueOf(value.floatValue() + value2.floatValue()));
        }
      }
    }
    float healthFactor;
//    float healthFactor;
    if (this.inv[0].getMaxDamage() != 0) {
      healthFactor = 1.0F - this.inv[0].getItemDamage() / this.inv[0].getMaxDamage();
    } else {
      healthFactor = 1.0F;
    }
    this.toSmelt = checkTotal(materiali, healthFactor);
  }
  
  public Hashtable<ItemStackKey, Float> checkTotal(Hashtable<ItemStackKey, Float> ht, float health)
  {
    Hashtable<ItemStackKey, Float> ret = new Hashtable();
    
    Hashtable<ItemStackKey, Float> result = new Hashtable();
    
    float total = 0.0F;
    
    float maxValue = -1.0F;
    
    ItemStackKey maxKey = null;
    
    Enumeration<ItemStackKey> keys = ht.keys();
    while (keys.hasMoreElements())
    {
      ItemStackKey key = (ItemStackKey)keys.nextElement();
      Float value = Float.valueOf(((Float)ht.get(key)).floatValue() * health);
      total += value.floatValue();
      ret.put(key, value);
      result.put(key, value);
      if (value.floatValue() > maxValue)
      {
        maxValue = value.floatValue();
        maxKey = key;
      }
    }
    if (!checkOverflow) {
      return ret;
    }
    keys = this.content.keys();
    while (keys.hasMoreElements())
    {
      ItemStackKey key = (ItemStackKey)keys.nextElement();
      Float value = (Float)this.content.get(key);
      Float value2 = (Float)result.get(key);
      if (value2 == null) {
        value2 = Float.valueOf(0.0F);
      }
      Float resultValue = Float.valueOf(value.floatValue() + value2.floatValue());
      result.put(key, resultValue);
      if (resultValue.floatValue() > maxValue)
      {
        maxValue = resultValue.floatValue();
        maxKey = key;
      }
    }
    if (this.inv[2] != null)
    {
      if ((this.inv[2].getItem() != null) && (this.inv[2].stackSize > 0))
      {
        ItemStackKey inv2Key = ItemStackKey.createKey(this.inv[2]);
        Float inv2Value = Float.valueOf(0.0F);
        if (inv2Key != null)
        {
          inv2Value = (Float)this.content.get(inv2Key);
          if (inv2Value == null) {
            inv2Value = Float.valueOf(0.0F);
          }
        }
        int inv2Free = this.inv[2].getMaxStackSize() - this.inv[2].stackSize;
        
        Float newToMove = (Float)ret.get(inv2Key);
        if (newToMove == null) {
          newToMove = Float.valueOf(0.0F);
        }
        Float oldToMove = inv2Value;
        
        float toMove = oldToMove.floatValue() + newToMove.floatValue();
        int toBeMoved;
//        int toBeMoved;
        if (toMove < inv2Free) {
          toBeMoved = (int)toMove;
        } else {
          toBeMoved = inv2Free;
        }
        if (getContentSize() + total - toBeMoved > 16.0F) {
          return null;
        }
      }
      else
      {
        this.inv[2] = null;
      }
    }
    else if ((maxKey != null) && (maxKey.item != null))
    {
      int inv2Free = maxKey.getItemStack().getMaxStackSize();
      
      Float toMove = (Float)result.get(maxKey);
      if (toMove == null) {
        toMove = Float.valueOf(0.0F);
      }
      int toBeMoved;
//      int toBeMoved;
      if (toMove.floatValue() < inv2Free) {
        toBeMoved = toMove.intValue();
      } else {
        toBeMoved = inv2Free;
      }
      if (getContentSize() + total - toBeMoved > 16.0F) {
        return null;
      }
    }
    return ret;
  }
  
  private void smeltItem()
  {
    if (canSmelt())
    {
      Hashtable<ItemStackKey, Float> materiali = this.toSmelt;
      
      Enumeration<ItemStackKey> keys = materiali.keys();
      while (keys.hasMoreElements())
      {
        ItemStackKey key = (ItemStackKey)keys.nextElement();
        Float value = (Float)materiali.get(key);
        
        Float current = (Float)this.content.get(key);
        if (current == null) {
          current = Float.valueOf(0.0F);
        }
        current = Float.valueOf(current.floatValue() + value.floatValue());
        this.content.put(key, current);
        
        setContentSize(getContentSize() + value.floatValue());
      }
      float polvere = Recipesscrapconverter.getPolvere(this.inv[0]) * 0.8F;
      
      boolean acqua = this.inv[0].getItem() == Items.WATER_BUCKET;
      
      this.inv[0].stackSize -= 1;
      if (this.inv[0].stackSize <= 0)
      {
        if (acqua) {
          waterEffect();
        }
        this.inv[0] = null;
      }
      this.toSmelt = null;
      this.willExplode = false;
      this.willBurn = 0.0F;
      
      this.worldObj.updateComparatorOutputLevel(this.pos, getBlockType());
      
      markDirty();
      this.worldObj.notifyBlockUpdate(this.pos, this.worldObj.getBlockState(this.pos), this.worldObj.getBlockState(this.pos), 0);
      if (polvere > 0.0F)
      {
        this.worldObj.createExplosion(null, this.pos.getX() + 0.5F, this.pos.getY() + 0.5F, this.pos.getZ() + 0.5F, polvere + this.worldObj.rand.nextFloat() * 0.8F, true);
        
        int radius = (int)Math.ceil(polvere);
        for (int i = -radius; i <= radius; i++) {
          for (int j = -radius; j <= radius; j++) {
            for (int k = -radius; k <= radius; k++)
            {
              BlockPos pos1 = this.pos.add(i, j, k);
              if ((this.worldObj.isAirBlock(pos1)) && (this.worldObj.rand.nextFloat() < 0.4D)) {
                this.worldObj.setBlockState(pos1, Blocks.FIRE.getDefaultState());
              }
            }
          }
        }
      }
    }
  }
  
  private void waterEffect()
  {
    this.totalBurnTime = (this.burnTime = 0);
    
    double d0 = this.pos.getX();
    double d1 = this.pos.getY();
    double d2 = this.pos.getZ();
    
    this.worldObj.playSound((EntityPlayer)null, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.AMBIENT, 0.5F, 2.6F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F);
  }
  
  public void dropItems(World worldIn, BlockPos pos)
  {
    if ((!worldIn.isRemote) && (!worldIn.restoringBlockSnapshots))
    {
      Enumeration<ItemStackKey> keys = this.content.keys();
      while (keys.hasMoreElements())
      {
        ItemStackKey key = (ItemStackKey)keys.nextElement();
        
        float value = ((Float)this.content.get(key)).floatValue();
        
        int count = (int)value;
        if (count > 0)
        {
          ItemStack stack = key.getItemStack();
          stack.stackSize = count;
          Block.spawnAsEntity(worldIn, pos, stack);
          
          this.content.put(key, Float.valueOf(value - count));
          
          this.contentSize -= count;
        }
      }
      this.cookTime = (this.totalCookTime = this.totalBurnTime = this.burnTime = 0);
      if (this.inv[0] != null)
      {
        Block.spawnAsEntity(worldIn, pos, this.inv[0]);
        this.inv[0] = null;
      }
      if (this.inv[1] != null)
      {
        Block.spawnAsEntity(worldIn, pos, this.inv[1]);
        this.inv[1] = null;
      }
      if (this.inv[2] != null)
      {
        Block.spawnAsEntity(worldIn, pos, this.inv[2]);
        this.inv[2] = null;
      }
      ItemStack is;
      if (this.contentSize > 0.0F)
      {
        ItemStack is = new ItemStack(Carpentercraft.scrapconverter);
        
        NBTTagCompound compound1 = is.getTagCompound();
        if (compound1 == null)
        {
          compound1 = new NBTTagCompound();
          is.setTagCompound(compound1);
        }
        NBTTagCompound compound = new NBTTagCompound();
        writeSyncableDataToNBT(compound);
        compound1.setTag("TileEntityFoundry", compound);
      }
      else
      {
        is = new ItemStack(Carpentercraft.scrapconverter);
      }
      Block.spawnAsEntity(worldIn, pos, is);
    }
  }
  
  @Override
  public int getSizeInventory()
  {
    return this.inv.length;
  }
  
  @Override
  public ItemStack getStackInSlot(int slot)
  {
    return this.inv[slot];
  }
  
  @Override
  public void setInventorySlotContents(int slot, ItemStack stack)
  {
    boolean flag = (stack != null) && (stack.isItemEqual(this.inv[slot])) && (ItemStack.areItemStackTagsEqual(stack, this.inv[slot]));
    this.inv[slot] = stack;
    if ((stack != null) && (stack.stackSize > getInventoryStackLimit())) {
      stack.stackSize = getInventoryStackLimit();
    }
    if (!flag) {
      if (slot == 0)
      {
        this.slotChanged = true;
        this.totalCookTime = ((int)(getCookTime(stack) * timeFactor));
        this.cookTime = 0;
        markDirty();
      }
      else if (slot == 2)
      {
        this.slotChanged = true;
      }
    }
  }
  
  @Override
  public ItemStack decrStackSize(int slot, int amt)
  {
    ItemStack stack = getStackInSlot(slot);
    if (stack != null) {
      if (stack.stackSize <= amt)
      {
    	  setInventorySlotContents(slot, null);
      }
      else
      {
        stack = stack.splitStack(amt);
        if (stack.stackSize == 0) {
        	setInventorySlotContents(slot, null);
        }
      }
    }
    return stack;
  }
  
  @Override
  public int getInventoryStackLimit()
  {
    return 64;
  }
  
  public boolean isUsableByPlayer(EntityPlayer player)
  {
    return this.worldObj.getTileEntity(this.pos) == this;
  }
  
  @Override
  public String getName()
  {
    return hasCustomName() ? this.customName : "container.scrapconverter";
  }
  
  @Override
  public boolean hasCustomName()
  {
    return this.customName != null;
  }
  
  @Override
  public ITextComponent getDisplayName()
  {
    return null;
  }
  
  @Override
  public void openInventory(EntityPlayer player) {}
  
  @Override
  public void closeInventory(EntityPlayer player) {}
  
  @Override
  public boolean isItemValidForSlot(int index, ItemStack stack)
  {
    switch (index)
    {
    case 0: 
      Hashtable<ItemStackKey, Float> materiali = Recipesscrapconverter.getMateriali(stack);
      return materiali != null;
    case 1: 
      return (TileEntityFurnace.isItemFuel(stack)) || ((this.inv[1] == null) && (stack.getItem() == Items.BUCKET));
    case 2: 
      return false;
    }
    return false;
  }
  
  @Override
  public int[] getSlotsForFace(EnumFacing side)
  {
    return side == EnumFacing.UP ? slotsTop : side == EnumFacing.DOWN ? slotsBottom : slotsSides;
  }
  
  @Override
  public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
  {
    return isItemValidForSlot(index, itemStackIn);
  }
  
  @Override
  public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
  {
    if ((direction == EnumFacing.DOWN) && (index == 1)) {
      return (!TileEntityFurnace.isItemFuel(stack)) && (stack.getItem() != Items.BUCKET);
    }
    return true;
  }
  
  @Override
  public void clear()
  {
    for (int i = 0; i < this.inv.length; i++) {
      this.inv[i] = null;
    }
    this.toSmelt = null;
    this.willExplode = false;
    this.willBurn = 0.0F;
  }
  
  @Override
  public int getField(int id)
  {
    switch (id)
    {
    case 0: 
      return getBurnTime();
    case 2: 
      return getCookTime();
    case 3: 
      return getTotalCookTime();
    }
    return 0;
  }
  
  @Override
  public void setField(int id, int value)
  {
    switch (id)
    {
    case 0: 
      setBurnTime(value);
      break;
    case 2: 
      setCookTime(value);
      break;
    case 3: 
      setTotalCookTime(value);
    }
  }
  
  @Override
  public int getFieldCount()
  {
    return 4;
  }
  
  public int getCookTime()
  {
    return this.cookTime;
  }
  
  public void setCookTime(int cookTime)
  {
    this.cookTime = cookTime;
  }
  
  public int getBurnTime()
  {
    return this.burnTime;
  }
  
  public void setBurnTime(int burnTime)
  {
    this.burnTime = burnTime;
  }
  
  public int getTotalCookTime()
  {
    return this.totalCookTime;
  }
  
  public void setTotalCookTime(int totalCookTime)
  {
    this.totalCookTime = totalCookTime;
  }
  
  public int getContentLevel()
  {
    return this.contentLevel;
  }
  
  private void setContentLevel(int value)
  {
    this.contentLevel = value;
  }
  
  public void setContentSize(float value)
  {
    this.contentSize = value;
    
    int level = (int)Math.ceil(16.0F * Math.min(this.contentSize / 16.0F, 1.0F));
    setContentLevel(level);
  }
  
  public float getContentSize()
  {
    return this.contentSize;
  }
  
  public int getTotalBurnTime()
  {
    return this.totalBurnTime;
  }
  
  public void setTotalBurnTime(int totalBurnTime)
  {
    this.totalBurnTime = totalBurnTime;
  }
  
  @Override
  public ItemStack removeStackFromSlot(int index)
  {
    return ItemStackHelper.getAndRemove(this.inv, index);
  }
  
  @Override
  public boolean onBlockEventReceived(World worldIn, BlockPos pos, int id, int param)
  {
    return false;
  }
  
  @Override
  public void neighborChanged(World worldIn, BlockPos pos, Block p_189546_3_) {}

@Override
public boolean isUseableByPlayer(EntityPlayer player) {
	return true;
}
}
