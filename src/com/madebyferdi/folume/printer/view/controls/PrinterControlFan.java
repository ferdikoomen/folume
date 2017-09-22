package com.madebyferdi.folume.printer.view.controls;


import com.madebyferdi.folume.controlP5.GuiKnob;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class PrinterControlFan
{

	// Properties
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiKnob fanSpeedKnob;
	private GuiTextfield fanSpeedField;
	private GuiKnob ledBrightnessKnob;
	private GuiTextfield ledBrightnessField;


	public PrinterControlFan(ControlP5 cp5, UltimakerHardware hardware, int offsetX, int offsetY)
	{
		// Store references
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create interface elements
		fanSpeedKnob = Gui.createKnob(cp5, true, "FAN SPEED", offsetX, offsetY, 30, 0, hardware.getFanSpeed().getMin(), hardware.getFanSpeed().getMax(), hardware.getFanSpeed().getValue());
		fanSpeedField = Gui.createField(cp5, true, "", offsetX, offsetY + 80, 60, 20, hardware.getFanSpeed().getValue());
		ledBrightnessKnob = Gui.createKnob(cp5, true, "LED BRIGHTNESS", offsetX + 80, offsetY, 30, 0, hardware.getLedBrightness().getMin(), hardware.getLedBrightness().getMax(), hardware.getLedBrightness().getValue());
		ledBrightnessField = Gui.createField(cp5, true, "", offsetX + 80, offsetY + 80, 60, 20, hardware.getLedBrightness().getValue());

		// Link fields and knobs
		fanSpeedKnob.linkField(fanSpeedField);
		ledBrightnessKnob.linkField(ledBrightnessField);
	}


	public GuiKnob getFanSpeedKnob()
	{
		return fanSpeedKnob;
	}

	public GuiKnob getLedBrightnessKnob()
	{
		return ledBrightnessKnob;
	}

}
