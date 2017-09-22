package com.madebyferdi.folume.printer.view.controls;


import com.madebyferdi.folume.controlP5.GuiKnob;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class PrinterControlJerk
{

	// Properties
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiKnob jerkXYKnob;
	private GuiTextfield jerkXYField;
	private GuiKnob jerkZKnob;
	private GuiTextfield jerkZField;
	private GuiKnob jerkEKnob;
	private GuiTextfield jerkEField;


	public PrinterControlJerk(ControlP5 cp5, UltimakerHardware hardware, int offsetX, int offsetY)
	{
		// Store references
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create interface elements
		jerkXYKnob = Gui.createKnob(cp5, true, "JERK XY", offsetX, offsetY, 30, 2, hardware.getJerkXY().getMin(), hardware.getJerkXY().getMax(), hardware.getJerkXY().getValue());
		jerkXYField = Gui.createField(cp5, true, "", offsetX, offsetY + 80, 60, 20, hardware.getJerkXY().getValue());
		jerkZKnob = Gui.createKnob(cp5, true, "JERK Z", offsetX + 80, offsetY, 30, 2, hardware.getJerkZ().getMin(), hardware.getJerkZ().getMax(), hardware.getJerkZ().getValue());
		jerkZField = Gui.createField(cp5, true, "", offsetX + 80, offsetY + 80, 60, 20, hardware.getJerkZ().getValue());
		jerkEKnob = Gui.createKnob(cp5, true, "JERK E", offsetX + 160, offsetY, 30, 2, hardware.getJerkE().getMin(), hardware.getJerkE().getMax(), hardware.getJerkE().getValue());
		jerkEField = Gui.createField(cp5, true, "", offsetX + 160, offsetY + 80, 60, 20, hardware.getJerkE().getValue());

		// Link fields and knobs
		jerkXYKnob.linkField(jerkXYField);
		jerkZKnob.linkField(jerkZField);
		jerkEKnob.linkField(jerkEField);
	}


	public GuiKnob getJerkXYKnob()
	{
		return jerkXYKnob;
	}

	public GuiKnob getJerkZKnob()
	{
		return jerkZKnob;
	}

	public GuiKnob getJerkEKnob()
	{
		return jerkEKnob;
	}

}
