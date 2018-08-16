package at.thoms.clientonly.gui;

import at.thoms.Carpentercraft;
import at.thoms.container.containerenergiestorage;
import at.thoms.tileentitys.TileEntityenergieStorage;
import cjminecraft.core.client.gui.GuiBase;
import cjminecraft.core.client.gui.element.ElementEnergyBar;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

/**
 * The gui for the energy cell
 * 
 * @author CJMinecraft
 *
 */
public class guienergiestorage extends GuiBase {

	/**
	 * The background texture of the energy cell
	 */
	public static final ResourceLocation TEXTURE = new ResourceLocation(Carpentercraft.MODID,
			"textures/gui/energiestorage.png");

	/**
	 * The {@link TileEntityEnergyCell} for the syncing of the
	 * {@link ElementEnergyBar}
	 */
	private TileEntityenergieStorage te;

	/**
	 * Initialise the gui for the energy cell
	 * 
	 * @param playerInv
	 *            The player's inventory (for the container)
	 * @param te
	 *            The {@link TileEntityEnergyCell} for syncing and the container
	 */
	public guienergiestorage(IInventory playerInv, TileEntityenergieStorage te) {
		// Sets the background texture
		super(new containerenergiestorage(playerInv, te));
		setGuiSize(176, 166); // Set the gui size
		this.te = te;
		this.name = "container.energy_cell"; // Will automatically be localised
												// using CJCore
	}

	/**
	 * Where we add all our gui elements
	 */
	@Override
	public void initGui() {
		super.initGui();
		addElement(new ElementEnergyBar(this, 79, 16, 18, 56).shouldSync(this.te.getPos(), null));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

	}

}