package com.madebyferdi.folume.design.view.controls;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.controlP5.GuiKnob;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class DesignControlRibs
{

	// Properties
	private Application app;
	private ControlP5 cp5;
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiKnob ribCountKnob;
	private GuiTextfield ribCountField;
	private GuiKnob ribWidthInsideTopKnob;
	private GuiTextfield ribWidthInsideTopField;
	private GuiKnob ribWidthInsideBottomKnob;
	private GuiTextfield ribWidthInsideBottomField;
	private GuiKnob ribWidthOutsideTopKnob;
	private GuiTextfield ribWidthOutsideTopField;
	private GuiKnob ribWidthOutsideBottomKnob;
	private GuiTextfield ribWidthOutsideBottomField;


	public DesignControlRibs(Application app, ControlP5 cp5, int offsetX, int offsetY)
	{
		// Store references
		this.app = app;
		this.cp5 = cp5;
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create elements
		ribCountKnob = Gui.createKnob(cp5, false, "RIB COUNT", offsetX, offsetY, 30, 0, 6, 60, 20);
		ribCountField = Gui.createField(cp5, false, "", offsetX, offsetY + 80, 60, 20, 50);
		ribWidthOutsideTopKnob = Gui.createKnob(cp5, false, "RIB TOP OUT", offsetX + 80, offsetY, 30, 0, 0, 100, 50);
		ribWidthOutsideTopField = Gui.createField(cp5, false, "", offsetX + 80, offsetY + 80, 60, 20, 50);
		ribWidthInsideTopKnob = Gui.createKnob(cp5, false, "RIB TOP IN", offsetX + 160, offsetY, 30, 0, 0, 100, 50);
		ribWidthInsideTopField = Gui.createField(cp5, false, "", offsetX + 160, offsetY + 80, 60, 20, 50);
		ribWidthOutsideBottomKnob = Gui.createKnob(cp5, false, "RIB BOTTOM OUT", offsetX + 240, offsetY, 30, 0, 0, 100, 50);
		ribWidthOutsideBottomField = Gui.createField(cp5, false, "", offsetX + 240, offsetY + 80, 60, 20, 50);
		ribWidthInsideBottomKnob = Gui.createKnob(cp5, false, "RIB BOTTOM IN", offsetX + 320, offsetY, 30, 0, 0, 100, 50);
		ribWidthInsideBottomField = Gui.createField(cp5, false, "", offsetX + 320, offsetY + 80, 60, 20, 50);

		// Link fields and knobs
		ribCountKnob.linkField(ribCountField);
		ribWidthInsideTopKnob.linkField(ribWidthInsideTopField);
		ribWidthInsideBottomKnob.linkField(ribWidthInsideBottomField);
		ribWidthOutsideTopKnob.linkField(ribWidthOutsideTopField);
		ribWidthOutsideBottomKnob.linkField(ribWidthOutsideBottomField);
	}

	public void reset()
	{
		ribCountKnob.setValue(20);
		ribWidthInsideTopKnob.setValue(50);
		ribWidthInsideBottomKnob.setValue(50);
		ribWidthOutsideTopKnob.setValue(50);
		ribWidthOutsideBottomKnob.setValue(50);
	}


	/**
	 * Getters
	 */
	public GuiKnob getRibCountKnob()
	{
		return ribCountKnob;
	}

	public GuiKnob getRibWidthInsideTopKnob()
	{
		return ribWidthInsideTopKnob;
	}

	public GuiKnob getRibWidthInsideBottomKnob()
	{
		return ribWidthInsideBottomKnob;
	}

	public GuiKnob getRibWidthOutsideTopKnob()
	{
		return ribWidthOutsideTopKnob;
	}

	public GuiKnob getRibWidthOutsideBottomKnob()
	{
		return ribWidthOutsideBottomKnob;
	}

}
