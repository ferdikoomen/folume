package com.madebyferdi.folume.design.view.controls;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.controlP5.GuiKnob;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class DesignControlRadius
{

	// Properties
	private Application app;
	private ControlP5 cp5;
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiKnob radiusInsideTopKnob;
	private GuiTextfield radiusInsideTopField;
	private GuiKnob radiusInsideBottomKnob;
	private GuiTextfield radiusInsideBottomField;
	private GuiKnob radiusOutsideTopKnob;
	private GuiTextfield radiusOutsideTopField;
	private GuiKnob radiusOutsideBottomKnob;
	private GuiTextfield radiusOutsideBottomField;


	public DesignControlRadius(Application app, ControlP5 cp5, int offsetX, int offsetY)
	{
		// Store references
		this.app = app;
		this.cp5 = cp5;
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create elements
		radiusOutsideTopKnob = Gui.createKnob(cp5, false, "RADIUS TOP OUT", offsetX, offsetY, 30, 0, 0, 100, 50);
		radiusOutsideTopField = Gui.createField(cp5, false, "", offsetX, offsetY + 80, 60, 20, 50);
		radiusInsideTopKnob = Gui.createKnob(cp5, false, "RADIUS TOP IN", offsetX + 80, offsetY, 30, 0, 0, 100, 25);
		radiusInsideTopField = Gui.createField(cp5, false, "", offsetX + 80, offsetY + 80, 60, 20, 50);
		radiusOutsideBottomKnob = Gui.createKnob(cp5, false, "RADIUS BOTTOM OUT", offsetX + 160, offsetY, 30, 0, 0, 100, 50);
		radiusOutsideBottomField = Gui.createField(cp5, false, "", offsetX + 160, offsetY + 80, 60, 20, 50);
		radiusInsideBottomKnob = Gui.createKnob(cp5, false, "RADIUS BOTTOM IN", offsetX + 240, offsetY, 30, 0, 0, 100, 25);
		radiusInsideBottomField = Gui.createField(cp5, false, "", offsetX + 240, offsetY + 80, 60, 20, 50);

		// Link fields and knobs
		radiusInsideTopKnob.linkField(radiusInsideTopField);
		radiusInsideBottomKnob.linkField(radiusInsideBottomField);
		radiusOutsideTopKnob.linkField(radiusOutsideTopField);
		radiusOutsideBottomKnob.linkField(radiusOutsideBottomField);
	}

	public void reset()
	{
		radiusInsideTopKnob.setValue(25);
		radiusInsideBottomKnob.setValue(25);
		radiusOutsideTopKnob.setValue(50);
		radiusOutsideBottomKnob.setValue(50);
	}


	/**
	 * Getters
	 */
	public GuiKnob getRadiusInsideTopKnob()
	{
		return radiusInsideTopKnob;
	}

	public GuiKnob getRadiusInsideBottomKnob()
	{
		return radiusInsideBottomKnob;
	}

	public GuiKnob getRadiusOutsideTopKnob()
	{
		return radiusOutsideTopKnob;
	}

	public GuiKnob getRadiusOutsideBottomKnob()
	{
		return radiusOutsideBottomKnob;
	}

}
