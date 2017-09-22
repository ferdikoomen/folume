package com.madebyferdi.folume.printer.view.controls;


import com.madebyferdi.folume.controlP5.GuiKnob;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class PrinterControlRetract
{

	// Properties
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiKnob retractLengthKnob;
	private GuiTextfield retractLengthField;
	private GuiKnob retractSpeedKnob;
	private GuiTextfield retractSpeedField;
	private GuiKnob retractZKnob;
	private GuiTextfield retractZField;


	public PrinterControlRetract(ControlP5 cp5, UltimakerHardware hardware, int offsetX, int offsetY)
	{
		// Store references
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create interface elements
		retractLengthKnob = Gui.createKnob(cp5, true, "RETRACT LENGTH", offsetX, offsetY, 30, 2, hardware.getRetractLength().getMin(), hardware.getRetractLength().getMax(), hardware.getRetractLength().getValue());
		retractLengthField = Gui.createField(cp5, true, "", offsetX, offsetY + 80, 60, 20, hardware.getRetractLength().getValue());
		retractSpeedKnob = Gui.createKnob(cp5, true, "RETRACT SPEED", offsetX + 80, offsetY, 30, 0, hardware.getRetractSpeed().getMin(), hardware.getRetractSpeed().getMax(), hardware.getRetractSpeed().getValue());
		retractSpeedField = Gui.createField(cp5, true, "", offsetX + 80, offsetY + 80, 60, 20, hardware.getRetractSpeed().getValue());
		retractZKnob = Gui.createKnob(cp5, true, "RETRACT Z", offsetX + 160, offsetY, 30, 2, hardware.getRetractZ().getMin(), hardware.getRetractZ().getMax(), hardware.getRetractZ().getValue());
		retractZField = Gui.createField(cp5, true, "", offsetX + 160, offsetY + 80, 60, 20, hardware.getRetractZ().getValue());

		// Link fields and knobs
		retractLengthKnob.linkField(retractLengthField);
		retractSpeedKnob.linkField(retractSpeedField);
		retractZKnob.linkField(retractZField);
	}


	public GuiKnob getRetractLengthKnob()
	{
		return retractLengthKnob;
	}

	public GuiKnob getRetractSpeedKnob()
	{
		return retractSpeedKnob;
	}

	public GuiKnob getRetractZKnob()
	{
		return retractZKnob;
	}
}
