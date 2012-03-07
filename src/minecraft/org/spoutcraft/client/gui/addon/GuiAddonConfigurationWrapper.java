package org.spoutcraft.client.gui.addon;

import org.spoutcraft.client.SpoutClient;
import org.spoutcraft.client.gui.GuiSpoutScreen;
import org.spoutcraft.spoutcraftapi.Spoutcraft;
import org.spoutcraft.spoutcraftapi.addon.Addon;
import org.spoutcraft.spoutcraftapi.gui.Button;
import org.spoutcraft.spoutcraftapi.gui.GenericButton;
import org.spoutcraft.spoutcraftapi.gui.GenericLabel;
import org.spoutcraft.spoutcraftapi.gui.GenericScrollArea;

public class GuiAddonConfigurationWrapper extends GuiSpoutScreen {
	private GuiAddonsLocal parent = null;
	private GenericScrollArea contents = new GenericScrollArea();
	private Addon addon;
	private GenericLabel title;
	private GenericButton buttonDone;
	
	public GuiAddonConfigurationWrapper(Addon addon, GuiAddonsLocal parent) {
		this.parent = parent;
		this.addon = addon;
	}
	
	public GenericScrollArea getContentsView() {
		return contents;
	}

	@Override
	protected void createInstances() {
		buttonDone = new GenericButton("Done");
		title = new GenericLabel(addon.getDescription().getName() + " Configuration");
		
		Addon spoutcraft = Spoutcraft.getAddonManager().getAddon("Spoutcraft");
		getScreen().attachWidgets(spoutcraft, buttonDone, title, contents);
	}

	@Override
	protected void layoutWidgets() {
		int top = 5;

		int swidth = mc.fontRenderer.getStringWidth(title.getText());
		title.setY(top + 7).setX(width / 2 - swidth / 2).setHeight(11).setWidth(swidth);

		top+=25;
		
		contents.setX(5).setY(top).setWidth(width - 10).setHeight(height - top - 30);
		contents.removeWidgets(addon);
		
		boolean oldLock = SpoutClient.enableSandbox();
		try {
			addon.setupConfigurationGUI(contents);
		} finally {
			SpoutClient.enableSandbox(oldLock);
		}
		
		contents.updateInnerSize();
		
		top+= contents.getHeight() + 5;
		
		buttonDone.setX(width - 10 - 200).setY(top).setHeight(20).setWidth(200);
	}
	
	@Override 
	public void buttonClicked(Button btn) {
		if(btn == buttonDone) {
			mc.displayGuiScreen(parent);
		}
	}

}