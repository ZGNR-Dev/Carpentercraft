package at.thoms.clientonly.gui;

import at.thoms.Carpentercraft;
import at.thoms.blocks.ultracrafting;
import at.thoms.utils.ModBlocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class guitreebreeder extends GuiContainer {
	private InventoryPlayer playerInv;
	private static final ResourceLocation bgtexturetreebreeder = new ResourceLocation(Carpentercraft.MODID, "textures/gui/treebreeding.png");

	public guitreebreeder(Container container, InventoryPlayer playerInv) {
		super(container);
		this.playerInv = playerInv;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(bgtexturetreebreeder);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		 String Treebreeder = null;
//		String name = I18n.format(Carpentercraft.treebreeder.getUnlocalizedName() + ".name");
		fontRendererObj.drawString(Treebreeder, xSize / 2 - fontRendererObj.getStringWidth("Treebreeder") / 2, 6, 0x404040);
		fontRendererObj.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
	}
}