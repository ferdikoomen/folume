package com.madebyferdi.folume.design.view.controls;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.controlP5.GuiKnob;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class DesignControlRotation
{

	// Properties
	private Application app;
	private ControlP5 cp5;
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiKnob rotateInsideTopKnob;
	private GuiTextfield rotateInsideTopField;
	private GuiKnob rotateInsideBottomKnob;
	private GuiTextfield rotateInsideBottomField;
	private GuiKnob rotateOutsideTopKnob;
	private GuiTextfield rotateOutsideTopField;
	private GuiKnob rotateOutsideBottomKnob;
	private GuiTextfield rotateOutsideBottomField;


	public DesignControlRotation(Application app, ControlP5 cp5, int offsetX, int offsetY)
	{
		// Store references
		this.app = app;
		this.cp5 = cp5;
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create elements
		rotateOutsideTopKnob = Gui.createKnob(cp5, false, "ROTATE TOP OUT", offsetX, offsetY, 30, 0, -45, 45, 0);
		rotateOutsideTopField = Gui.createField(cp5, false, "", offsetX, offsetY + 80, 60, 20, 50);
		rotateInsideTopKnob = Gui.createKnob(cp5, false, "ROTATE TOP IN", offsetX + 80, offsetY, 30, 0, -45, 45, 0);
		rotateInsideTopField = Gui.createField(cp5, false, "", offsetX + 80, offsetY + 80, 60, 20, 50);
		rotateOutsideBottomKnob = Gui.createKnob(cp5, false, "ROTATE BOTTOM OUT", offsetX + 160, offsetY, 30, 0, -45, 45, 0);
		rotateOutsideBottomField = Gui.createField(cp5, false, "", offsetX + 160, offsetY + 80, 60, 20, 50);
		rotateInsideBottomKnob = Gui.createKnob(cp5, false, "ROTATE BOTTOM IN", offsetX + 240, offsetY, 30, 0, -45, 45, 0);
		rotateInsideBottomField = Gui.createField(cp5, false, "", offsetX + 240, offsetY + 80, 60, 20, 50);

		// Link fields and knobs
		rotateInsideTopKnob.linkField(rotateInsideTopField);
		rotateInsideBottomKnob.linkField(rotateInsideBottomField);
		rotateOutsideTopKnob.linkField(rotateOutsideTopField);
		rotateOutsideBottomKnob.linkField(rotateOutsideBottomField);
	}


	public void reset()
	{
		rotateInsideTopKnob.setValue(0);
		rotateInsideBottomKnob.setValue(0);
		rotateOutsideTopKnob.setValue(0);
		rotateOutsideBottomKnob.setValue(0);
	}


	/**
	 * Getters
	 */
	public GuiKnob getRotateInsideTopKnob()
	{
		return rotateInsideTopKnob;
	}

	public GuiKnob getRotateInsideBottomKnob()
	{
		return rotateInsideBottomKnob;
	}

	public GuiKnob getRotateOutsideTopKnob()
	{
		return rotateOutsideTopKnob;
	}

	public GuiKnob getRotateOutsideBottomKnob()
	{
		return rotateOutsideBottomKnob;
	}
}
