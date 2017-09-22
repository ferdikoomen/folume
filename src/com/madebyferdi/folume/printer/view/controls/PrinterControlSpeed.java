package com.madebyferdi.folume.printer.view.controls;


import com.madebyferdi.folume.controlP5.GuiKnob;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class PrinterControlSpeed
{

	// Properties
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiKnob speedTravelKnob;
	private GuiTextfield speedTravelField;
	private GuiKnob speedPrintKnob;
	private GuiTextfield speedPrintField;
	private GuiKnob speedZKnob;
	private GuiTextfield speedZField;


	public PrinterControlSpeed(ControlP5 cp5, UltimakerHardware hardware, int offsetX, int offsetY)
	{
		// Store references
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create interface elements
		speedTravelKnob = Gui.createKnob(cp5, true, "SPEED TRAVEL", offsetX, offsetY, 30, 0, hardware.getSpeedTravel().getMin(), hardware.getSpeedTravel().getMax(), hardware.getSpeedTravel().getValue());
		speedTravelField = Gui.createField(cp5, true, "", offsetX, offsetY + 80, 60, 20, hardware.getSpeedTravel().getValue());
		speedPrintKnob = Gui.createKnob(cp5, true, "SPEED PRINT", offsetX + 80, offsetY, 30, 0, hardware.getSpeedPrint().getMin(), hardware.getSpeedPrint().getMax(), hardware.getSpeedPrint().getValue());
		speedPrintField = Gui.createField(cp5, true, "", offsetX + 80, offsetY + 80, 60, 20, hardware.getSpeedPrint().getValue());
		speedZKnob = Gui.createKnob(cp5, true, "SPEED Z", offsetX + 160, offsetY, 30, 0, hardware.getSpeedZ().getMin(), hardware.getSpeedZ().getMax(), hardware.getSpeedZ().getValue());
		speedZField = Gui.createField(cp5, true, "", offsetX + 160, offsetY + 80, 60, 20, hardware.getSpeedZ().getValue());

		// Link fields and knobs
		speedTravelKnob.linkField(speedTravelField);
		speedPrintKnob.linkField(speedPrintField);
		speedZKnob.linkField(speedZField);
	}


	public GuiKnob getSpeedTravelKnob()
	{
		return speedTravelKnob;
	}

	public GuiKnob getSpeedPrintKnob()
	{
		return speedPrintKnob;
	}

	public GuiKnob getSpeedZKnob()
	{
		return speedZKnob;
	}
}
