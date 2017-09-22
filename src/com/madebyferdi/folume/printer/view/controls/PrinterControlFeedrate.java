package com.madebyferdi.folume.printer.view.controls;


import com.madebyferdi.folume.controlP5.GuiKnob;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class PrinterControlFeedrate
{

	// Properties
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiKnob feedrateXKnob;
	private GuiTextfield feedrateXField;
	private GuiKnob feedrateYKnob;
	private GuiTextfield feedrateYField;
	private GuiKnob feedrateZKnob;
	private GuiTextfield feedrateZField;
	private GuiKnob feedrateEKnob;
	private GuiTextfield feedrateEField;


	public PrinterControlFeedrate(ControlP5 cp5, UltimakerHardware hardware, int offsetX, int offsetY)
	{
		// Store references
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create interface elements
		feedrateXKnob = Gui.createKnob(cp5, true, "FEEDRATE X", offsetX, offsetY, 30, 0, hardware.getFeedrateX().getMin(), hardware.getFeedrateX().getMax(), hardware.getFeedrateX().getValue());
		feedrateXField = Gui.createField(cp5, true, "", offsetX, offsetY + 80, 60, 20, hardware.getFeedrateX().getValue());
		feedrateYKnob = Gui.createKnob(cp5, true, "FEEDRATE Y", offsetX + 80, offsetY, 30, 0, hardware.getFeedrateY().getMin(), hardware.getFeedrateY().getMax(), hardware.getFeedrateY().getValue());
		feedrateYField = Gui.createField(cp5, true, "", offsetX + 80, offsetY + 80, 60, 20, hardware.getFeedrateY().getValue());
		feedrateZKnob = Gui.createKnob(cp5, true, "FEEDRATE Z", offsetX + 160, offsetY, 30, 0, hardware.getFeedrateZ().getMin(), hardware.getFeedrateZ().getMax(), hardware.getFeedrateZ().getValue());
		feedrateZField = Gui.createField(cp5, true, "", offsetX + 160, offsetY + 80, 60, 20, hardware.getFeedrateZ().getValue());
		feedrateEKnob = Gui.createKnob(cp5, true, "FEEDRATE E", offsetX + 240, offsetY, 30, 0, hardware.getFeedrateE().getMin(), hardware.getFeedrateE().getMax(), hardware.getFeedrateE().getValue());
		feedrateEField = Gui.createField(cp5, true, "", offsetX + 240, offsetY + 80, 60, 20, hardware.getFeedrateE().getValue());

		// Link fields and knobs
		feedrateXKnob.linkField(feedrateXField);
		feedrateYKnob.linkField(feedrateYField);
		feedrateZKnob.linkField(feedrateZField);
		feedrateEKnob.linkField(feedrateEField);
	}


	public GuiKnob getFeedrateXKnob()
	{
		return feedrateXKnob;
	}

	public GuiKnob getFeedrateYKnob()
	{
		return feedrateYKnob;
	}

	public GuiKnob getFeedrateZKnob()
	{
		return feedrateZKnob;
	}

	public GuiKnob getFeedrateEKnob()
	{
		return feedrateEKnob;
	}

}
