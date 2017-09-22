package com.madebyferdi.folume;


import com.madebyferdi.folume.controlP5.GuiButton;
import com.madebyferdi.folume.settings.Style;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;
import processing.core.PImage;


final public class ApplicationView
{

	// Properties
	private Application app;
	private ControlP5 cp5;
	private PImage img;
	private GuiButton toggle;


	public ApplicationView(Application app)
	{
		// Store references
		this.app = app;

		// Load logo
		img = app.loadImage("data/logo.png");

		// Create GUI layer
		cp5 = new ControlP5(app);
		cp5.setAutoDraw(false);

		// Create buttons
		toggle = Gui.createButton(cp5, false, "PRINT MODE", Style.WIDTH - 120, 20, 100, 30);
		toggle.setColorActive(0xFFFF0044);
		toggle.setColorForeground(0xFFFF0044);
		toggle.setColorBackground(0xFFDD0033);
	}


	public GuiButton getToggle()
	{
		return toggle;
	}


	public void draw()
	{
		// Draw background
		app.background(0, 0, 0);

		// Draw logo
		app.image(img, 0, 0);

		// Update GUI elements
		cp5.draw();
	}

}
