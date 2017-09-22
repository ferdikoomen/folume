package com.madebyferdi.folume.design.view.controls;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.controlP5.GuiKnob;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class DesignControlSize
{

	// Properties
	private Application app;
	private ControlP5 cp5;
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiKnob heightKnob;
	private GuiTextfield heightField;
	private GuiKnob stepsKnob;
	private GuiTextfield stepsField;
	private GuiKnob degreesKnob;
	private GuiTextfield degreesField;


	public DesignControlSize(Application app, ControlP5 cp5, int offsetX, int offsetY)
	{
		// Store references
		this.app = app;
		this.cp5 = cp5;
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create elements
		heightKnob = Gui.createKnob(cp5, false, "HEIGHT", offsetX, offsetY, 30, 0, 1, 200, 100);
		heightField = Gui.createField(cp5, false, "", offsetX, offsetY + 80, 60, 20, 50);
		stepsKnob = Gui.createKnob(cp5, false, "STEPS", offsetX + 80, offsetY, 30, 0, 5, 50, 10);
		stepsField = Gui.createField(cp5, false, "", offsetX + 80, offsetY + 80, 60, 20, 50);
		degreesKnob = Gui.createKnob(cp5, false, "DEGREES", offsetX + 160, offsetY, 30, 0, 1, 360, 360);
		degreesField = Gui.createField(cp5, false, "", offsetX + 160, offsetY + 80, 60, 20, 50);

		// Link fields and knobs
		heightKnob.linkField(heightField);
		stepsKnob.linkField(stepsField);
		degreesKnob.linkField(degreesField);
	}

	public void reset()
	{
		heightKnob.setValue(100);
		degreesKnob.setValue(360);
		stepsKnob.setValue(10);
	}


	/**
	 * Getters
	 */
	public GuiKnob getHeightKnob()
	{
		return heightKnob;
	}

	public GuiKnob getDegreesKnob()
	{
		return degreesKnob;
	}

	public GuiKnob getStepsKnob()
	{
		return stepsKnob;
	}
}
