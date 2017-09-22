package com.madebyferdi.folume.design.view.controls;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.controlP5.GuiKnob;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class DesignControlPosition
{

	// Properties
	private Application app;
	private ControlP5 cp5;
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiKnob xTopKnob;
	private GuiTextfield xTopField;
	private GuiKnob yTopKnob;
	private GuiTextfield yTopField;
	private GuiKnob xBottomKnob;
	private GuiTextfield xBottomField;
	private GuiKnob yBottomKnob;
	private GuiTextfield zBottomField;


	public DesignControlPosition(Application app, ControlP5 cp5, int offsetX, int offsetY)
	{
		// Store references
		this.app = app;
		this.cp5 = cp5;
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create elements
		xTopKnob = Gui.createKnob(cp5, false, "X POS TOP", offsetX, offsetY, 30, 0, -25, 25, 0);
		xTopField = Gui.createField(cp5, false, "", offsetX, offsetY + 80, 60, 20, 0);
		yTopKnob = Gui.createKnob(cp5, false, "Y POS TOP", offsetX + 80, offsetY, 30, 0, -25, 25, 0);
		yTopField = Gui.createField(cp5, false, "", offsetX + 80, offsetY + 80, 60, 20, 0);
		xBottomKnob = Gui.createKnob(cp5, false, "X POS BOTTOM", offsetX + 160, offsetY, 30, 0, -25, 25, 0);
		xBottomField = Gui.createField(cp5, false, "", offsetX + 160, offsetY + 80, 60, 20, 0);
		yBottomKnob = Gui.createKnob(cp5, false, "Y POS BOTTOM", offsetX + 240, offsetY, 30, 0, -25, 25, 0);
		zBottomField = Gui.createField(cp5, false, "", offsetX + 240, offsetY + 80, 60, 20, 0);

		// Link fields and knobs
		xTopKnob.linkField(xTopField);
		yTopKnob.linkField(yTopField);
		xBottomKnob.linkField(xBottomField);
		yBottomKnob.linkField(zBottomField);
	}

	public void reset()
	{
		xTopKnob.setValue(0);
		yTopKnob.setValue(0);
		xBottomKnob.setValue(0);
		yBottomKnob.setValue(0);
	}


	/**
	 * Getters
	 */
	public GuiKnob getXTopKnob()
	{
		return xTopKnob;
	}

	public GuiKnob getYTopKnob()
	{
		return yTopKnob;
	}

	public GuiKnob getXBottomKnob()
	{
		return xBottomKnob;
	}

	public GuiKnob getYBottomKnob()
	{
		return yBottomKnob;
	}
}
