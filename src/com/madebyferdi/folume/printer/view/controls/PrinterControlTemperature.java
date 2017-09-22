package com.madebyferdi.folume.printer.view.controls;


import com.madebyferdi.folume.controlP5.GuiKnob;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class PrinterControlTemperature
{

	// Properties
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiKnob extruderTemperatureKnob;
	private GuiTextfield extruderTemperatureField;
	private GuiKnob heatbedTemperatureKnob;
	private GuiTextfield heatbedTemperatureField;


	public PrinterControlTemperature(ControlP5 cp5, UltimakerHardware hardware, int offsetX, int offsetY)
	{
		// Store references
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create interface elements
		extruderTemperatureKnob = Gui.createKnob(cp5, true, "EXTRUDER TEMP", offsetX, offsetY, 30, 0, hardware.getExtruderTemperature().getMin(), hardware.getExtruderTemperature().getMax(), hardware.getExtruderTemperature().getValue());
		extruderTemperatureField = Gui.createField(cp5, true, "", offsetX, offsetY + 80, 60, 20, hardware.getExtruderTemperature().getValue());
		heatbedTemperatureKnob = Gui.createKnob(cp5, true, "HEATBED TEMP", offsetX + 80, offsetY, 30, 0, hardware.getHeatbedTemperature().getMin(), hardware.getHeatbedTemperature().getMax(), hardware.getHeatbedTemperature().getValue());
		heatbedTemperatureField = Gui.createField(cp5, true, "", offsetX + 80, offsetY + 80, 60, 20, hardware.getHeatbedTemperature().getValue());

		// Link fields and knobs
		extruderTemperatureKnob.linkField(extruderTemperatureField);
		heatbedTemperatureKnob.linkField(heatbedTemperatureField);
	}


	public GuiKnob getExtruderTemperatureKnob()
	{
		return extruderTemperatureKnob;
	}

	public GuiKnob getHeatbedTemperatureKnob()
	{
		return heatbedTemperatureKnob;
	}


}
