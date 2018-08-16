package at.thoms.clientonly.gui;

import at.thoms.Carpentercraft;
import at.thoms.tileentitys.TileEntityenergiegenerator;
import at.thoms.utils.ProgressBar.ProgressBarDirection;
import at.thoms.utils.ProgressBar;
import at.thoms.container.containerenergiegenerator;
import at.thoms.network.PacketGetWorker;
import at.thoms.network.PacketHandler;
import at.thoms.tileentitys.TileEntityenergiegenerator;
import cjminecraft.core.client.gui.element.ElementProgressBar;
import cjminecraft.core.client.gui.GuiBase;
import cjminecraft.core.client.gui.element.ElementEnergyBar;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class guienergiegenerator extends GuiBase {
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(Carpentercraft.MODID, "textures/gui/energiegenerator.png");
	
	private TileEntityenergiegenerator te;
	private IInventory playerInv;
	
	public static int cooldown, maxCooldown;
	
	public static int sync = 0;
	
	private ElementProgressBar progressBar;
	private ElementEnergyBar energyBar;
	private ProgressBar myBar;
	
	public guienergiegenerator(IInventory playerInv, TileEntityenergiegenerator te) {
		super(new containerenergiegenerator(playerInv, te));
		
		this.xSize = 176;
		this.ySize = 166;
		
		this.te = te;
		this.playerInv = playerInv;
		
//		this.progressBar = new ElementProgressBar(TEXTURE, ProgressBarDirection.UP_TO_DOWN, 14, 13, 81, 53, 176, 0);
		this.myBar = new ProgressBar(TEXTURE, ProgressBarDirection.UP_TO_DOWN, 14, 13, 81, 53, 176, 0);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.energyBar = new ElementEnergyBar(this, this.width / 2 - this.xSize / 2 +7, this.height / 2 - this.ySize / 2 + 16, 0, 0);
		addElement(new ElementEnergyBar(this, 7, 16, 18, 54).shouldSync(this.te.getPos(), null));
	}
	
	
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = I18n.format("container.furnace_generator");
		this.mc.fontRendererObj.drawString(s, this.xSize / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2, 6, 4210752); //Draws the block breaker name in the center on the top of the gui
		this.mc.fontRendererObj.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 73, 4210752); //The player's inventory name
		
		if(cooldown == 0)
			cooldown = maxCooldown;
//		this.progressBar.setMin(cooldown).setMax(maxCooldown);
//		this.progressBar.draw(this.mc);
		this.myBar.setMin(cooldown).setMax(maxCooldown);
		this.myBar.draw(this.mc);
		sync++;
		sync %= 10;
		if(sync == 0) {
			PacketHandler.INSTANCE.sendToServer(new PacketGetWorker(this.te.getPos(), this.mc.thePlayer.getAdjustedHorizontalFacing(), "cjminecraft.bitofeverything.client.gui.GuiFurnaceGenerator", "cooldown", "maxCooldown"));
			this.energyBar.shouldSync(this.te.getPos(), EnumFacing.NORTH);
		}
	}

}