package at.thoms.clientonly.gui;

import at.thoms.Carpentercraft;
import at.thoms.utils.ModBlocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class guijustbackpack extends GuiContainer {
	
	private InventoryPlayer playerInv;
	private static final ResourceLocation bgtexturebackpackzero = new ResourceLocation(Carpentercraft.MODID, "textures/gui/hotchest.png");

	public guijustbackpack(Container container, InventoryPlayer playerInv) {
		super(container);
		this.playerInv = playerInv;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(bgtexturebackpackzero);
//		int myxSize = 194;
//		int myySize = 166;
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format(Carpentercraft.justbackpack.getUnlocalizedName() + ".name");
		fontRendererObj.drawString(name, xSize / 2 - fontRendererObj.getStringWidth(name) / 2, 6, 0x404040);
		fontRendererObj.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
	}
}