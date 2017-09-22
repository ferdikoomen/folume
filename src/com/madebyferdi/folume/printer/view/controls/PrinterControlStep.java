package com.madebyferdi.folume.printer.view.controls;


import com.madebyferdi.folume.controlP5.GuiKnob;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class PrinterControlStep
{

	// Properties
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiKnob stepXKnob;
	private GuiTextfield stepXField;
	private GuiKnob stepYKnob;
	private GuiTextfield stepYField;
	private GuiKnob stepZKnob;
	private GuiTextfield stepZField;
	private GuiKnob stepEKnob;
	private GuiTextfield stepEField;


	public PrinterControlStep(ControlP5 cp5, UltimakerHardware hardware, int offsetX, int offsetY)
	{
		// Store references
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create interface elements
		stepXKnob = Gui.createKnob(cp5, true, "STEP X", offsetX, offsetY, 30, 0, hardware.getStepX().getMin(), hardware.getStepX().getMax(), hardware.getStepX().getValue());
		stepXField = Gui.createField(cp5, true, "", offsetX, offsetY + 80, 60, 20, hardware.getStepX().getValue());
		stepYKnob = Gui.createKnob(cp5, true, "STEP Y", offsetX + 80, offsetY, 30, 0, hardware.getStepY().getMin(), hardware.getStepY().getMax(), hardware.getStepY().getValue());
		stepYField = Gui.createField(cp5, true, "", offsetX + 80, offsetY + 80, 60, 20, hardware.getStepY().getValue());
		stepZKnob = Gui.createKnob(cp5, true, "STEP Z", offsetX + 160, offsetY, 30, 0, hardware.getStepZ().getMin(), hardware.getStepZ().getMax(), hardware.getStepZ().getValue());
		stepZField = Gui.createField(cp5, true, "", offsetX + 160, offsetY + 80, 60, 20, hardware.getStepZ().getValue());
		stepEKnob = Gui.createKnob(cp5, true, "STEP E", offsetX + 240, offsetY, 30, 0, hardware.getStepE().getMin(), hardware.getStepE().getMax(), hardware.getStepE().getValue());
		stepEField = Gui.createField(cp5, true, "", offsetX + 240, offsetY + 80, 60, 20, hardware.getStepE().getValue());

		// Link fields and knobs
		stepXKnob.linkField(stepXField);
		stepYKnob.linkField(stepYField);
		stepZKnob.linkField(stepZField);
		stepEKnob.linkField(stepEField);
	}


	public GuiKnob getStepXKnob()
	{
		return stepXKnob;
	}

	public GuiKnob getStepYKnob()
	{
		return stepYKnob;
	}

	public GuiKnob getStepZKnob()
	{
		return stepZKnob;
	}

	public GuiKnob getStepEKnob()
	{
		return stepEKnob;
	}

}
