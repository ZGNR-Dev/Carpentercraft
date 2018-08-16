package at.thoms.clientonly.gui;

import at.thoms.Carpentercraft;
import at.thoms.blocks.toolcrafter;
import at.thoms.utils.ModBlocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class guitoolcrafter extends GuiContainer {
	private InventoryPlayer playerInv;
	private static final ResourceLocation bgtexturetoolcrafter = new ResourceLocation(Carpentercraft.MODID, "textures/gui/toolcrafter.png");

	public guitoolcrafter(Container container, InventoryPlayer playerInv) {
		super(container);
		this.playerInv = playerInv;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(bgtexturetoolcrafter);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, 192);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format(ModBlocks.toolcrafter.getUnlocalizedName() + ".name");
		fontRendererObj.drawString(name, 11, 6, 0x404040);
		fontRendererObj.drawString(playerInv.getDisplayName().getUnformattedText(), 3, 108, 0x404040);
		fontRendererObj.drawString("geiler Test", 100, 12, 0x9314A7);
	}
}