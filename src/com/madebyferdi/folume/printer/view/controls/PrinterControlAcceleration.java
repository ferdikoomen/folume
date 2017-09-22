package com.madebyferdi.folume.printer.view.controls;


import com.madebyferdi.folume.controlP5.GuiKnob;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class PrinterControlAcceleration
{

	// Properties
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiKnob accelerationXKnob;
	private GuiTextfield accelerationXField;
	private GuiKnob accelerationYKnob;
	private GuiTextfield accelerationYField;
	private GuiKnob accelerationZKnob;
	private GuiTextfield accelerationZField;
	private GuiKnob accelerationEKnob;
	private GuiTextfield accelerationEField;


	public PrinterControlAcceleration(ControlP5 cp5, UltimakerHardware hardware, int offsetX, int offsetY)
	{
		// Store references
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create interface elements
		accelerationXKnob = Gui.createKnob(cp5, true, "ACCELERATION X", offsetX, offsetY, 30, 0, hardware.getAccelerationX().getMin(), hardware.getAccelerationX().getMax(), hardware.getAccelerationX().getValue());
		accelerationXField = Gui.createField(cp5, true, "", offsetX, offsetY + 80, 60, 20, hardware.getAccelerationX().getValue());
		accelerationYKnob = Gui.createKnob(cp5, true, "ACCELERATION Y", offsetX + 80, offsetY, 30, 0, hardware.getAccelerationY().getMin(), hardware.getAccelerationY().getMax(), hardware.getAccelerationY().getValue());
		accelerationYField = Gui.createField(cp5, true, "", offsetX + 80, offsetY + 80, 60, 20, hardware.getAccelerationY().getValue());
		accelerationZKnob = Gui.createKnob(cp5, true, "ACCELERATION Z", offsetX + 160, offsetY, 30, 0, hardware.getAccelerationZ().getMin(), hardware.getAccelerationZ().getMax(), hardware.getAccelerationZ().getValue());
		accelerationZField = Gui.createField(cp5, true, "", offsetX + 160, offsetY + 80, 60, 20, hardware.getAccelerationZ().getValue());
		accelerationEKnob = Gui.createKnob(cp5, true, "ACCELERATION E", offsetX + 240, offsetY, 30, 0, hardware.getAccelerationE().getMin(), hardware.getAccelerationE().getMax(), hardware.getAccelerationE().getValue());
		accelerationEField = Gui.createField(cp5, true, "", offsetX + 240, offsetY + 80, 60, 20, hardware.getAccelerationE().getValue());

		// Link fields and knobs
		accelerationXKnob.linkField(accelerationXField);
		accelerationYKnob.linkField(accelerationYField);
		accelerationEKnob.linkField(accelerationEField);
		accelerationZKnob.linkField(accelerationZField);
	}


	public GuiKnob getAccelerationXKnob()
	{
		return accelerationXKnob;
	}

	public GuiKnob getAccelerationYKnob()
	{
		return accelerationYKnob;
	}

	public GuiKnob getAccelerationZKnob()
	{
		return accelerationZKnob;
	}

	public GuiKnob getAccelerationEKnob()
	{
		return accelerationEKnob;
	}
}
