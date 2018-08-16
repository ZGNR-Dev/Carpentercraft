package at.thoms.clientonly.gui;

import at.thoms.Carpentercraft;
import at.thoms.utils.ModBlocks;
import at.thoms.utils.GuiVerticalSlider;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class guiblockcreator extends GuiContainer {
	
	private InventoryPlayer playerInv;
	private static final ResourceLocation bgtexturebackpackzero = new ResourceLocation(Carpentercraft.MODID, "textures/gui/blockcreator.png");

	public GuiVerticalSlider sliderOne;
	public GuiVerticalSlider sliderTwo;
	public GuiVerticalSlider sliderTre;
	
	public guiblockcreator(Container container, InventoryPlayer playerInv) {
		super(container);
		this.playerInv = playerInv;
	}
	
	@Override
	public void initGui() {
		buttonList.add(sliderOne = new GuiVerticalSlider(0, 257, 150, 50, 20, "", "", 1.0D, 50.0D, 3.0D, true, true));
		buttonList.add(sliderTwo = new GuiVerticalSlider(1, 250, 50, 50, 20, "", "", 1.0D, 50.0D, 3.0D, true, true)); 
		buttonList.add(sliderTre = new GuiVerticalSlider(2, 450, 50, 50, 20, "", "", 1.0D, 50.0D, 3.0D, true, true));
		
		super.initGui();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(bgtexturebackpackzero);
		int myxSize = 255;
		int myySize = 255;
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2 - 50;
		drawTexturedModalRect(x, y, 0, 0, myxSize, myySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format(Carpentercraft.backpackzero.getUnlocalizedName() + ".name");
		fontRendererObj.drawString(name, xSize / 2 - fontRendererObj.getStringWidth(name) / 2, 6, 0x404040);
		fontRendererObj.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
	}
}