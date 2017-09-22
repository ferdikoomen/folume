package com.madebyferdi.folume.design.view.controls;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.controlP5.GuiButton;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class DesignControlButtons
{

	// Properties
	private Application app;
	private ControlP5 cp5;
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiButton loadButton;
	private GuiButton saveButton;
	private GuiButton svgButton;
	private GuiButton resetButton;


	public DesignControlButtons(Application app, ControlP5 cp5, int offsetX, int offsetY)
	{
		// Store references
		this.app = app;
		this.cp5 = cp5;
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create elements
		loadButton = Gui.createButton(cp5, false, "LOAD SETTINGS", offsetX, offsetY, 80, 25);
		saveButton = Gui.createButton(cp5, false, "SAVE SETTINGS", offsetX + 100, offsetY, 80, 25);
		svgButton = Gui.createButton(cp5, false, "LOAD CURVE", offsetX, offsetY + 40, 80, 25);
		resetButton = Gui.createButton(cp5, false, "RESET", offsetX + 100, offsetY + 40, 80, 25);
	}


	/**
	 * Getters
	 */
	public GuiButton getLoadButton()
	{
		return loadButton;
	}

	public GuiButton getSaveButton()
	{
		return saveButton;
	}

	public GuiButton getSvgButton()
	{
		return svgButton;
	}

	public GuiButton getResetButton()
	{
		return resetButton;
	}
}
