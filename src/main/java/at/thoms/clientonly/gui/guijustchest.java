package at.thoms.clientonly.gui;

import at.thoms.Carpentercraft;
import at.thoms.blocks.justchest;
import at.thoms.utils.ModBlocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class guijustchest extends GuiContainer {
	private InventoryPlayer playerInv;
	private static final ResourceLocation bgtexturejustchest = new ResourceLocation(Carpentercraft.MODID, "textures/gui/justchest.png");

	public guijustchest(Container container, InventoryPlayer playerInv) {
		super(container);
		this.playerInv = playerInv;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(bgtexturejustchest);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format(ModBlocks.justchest.getUnlocalizedName() + ".name");
		fontRendererObj.drawString(name, xSize / 2 - fontRendererObj.getStringWidth(name) / 2, 6, 0x404040);
		fontRendererObj.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
	}
}








/*
package at.thoms.clientonly.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import at.thoms.Carpentercraft;
import at.thoms.tileentitys.TileEntityjustchest;
import at.thoms.container.containerjustchest;

public class guijustchest extends GuiContainer{
	
	private IInventory playerInv;
	private TileEntityjustchest te;
	
	public guijustchest(IInventory playerInv, TileEntityjustchest te) {
		super(new containerjustchest(playerInv, te));
		
		this.playerInv = playerInv;
		this.te = te;
		
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {		
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Carpentercraft.instance + ":" + "textures/gui/podest.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.te.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752);
	}
} */