package com.madebyferdi.folume.printer.view.controls;


import com.madebyferdi.folume.controlP5.GuiKnob;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class PrinterControlLayer
{

	// Properties
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiKnob filamentDiameterKnob;
	private GuiTextfield filamentDiameterField;
	private GuiKnob nozzleDiameterKnob;
	private GuiTextfield nozzleDiameterField;
	private GuiKnob layerHeightKnob;
	private GuiTextfield layerHeightField;
	private GuiKnob layerWidthKnob;
	private GuiTextfield layerWidthField;
	private GuiKnob layerBottomKnob;
	private GuiTextfield layerBottomField;


	public PrinterControlLayer(ControlP5 cp5, UltimakerHardware hardware, int offsetX, int offsetY)
	{
		// Store references
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create interface elements
		filamentDiameterKnob = Gui.createKnob(cp5, true, "FILAMENT DIAMETER", offsetX, offsetY, 30, 2, hardware.getFilamentDiameter().getMin(), hardware.getFilamentDiameter().getMax(), hardware.getFilamentDiameter().getValue());
		filamentDiameterField = Gui.createField(cp5, true, "", offsetX, offsetY + 80, 60, 20, hardware.getFilamentDiameter().getValue());
		nozzleDiameterKnob = Gui.createKnob(cp5, true, "NOZZLE DIAMETER", offsetX + 80, offsetY, 30, 2, hardware.getNozzleDiameter().getMin(), hardware.getNozzleDiameter().getMax(), hardware.getNozzleDiameter().getValue());
		nozzleDiameterField = Gui.createField(cp5, true, "", offsetX + 80, offsetY + 80, 60, 20, hardware.getNozzleDiameter().getValue());
		layerHeightKnob = Gui.createKnob(cp5, true, "LAYER HEIGHT", offsetX + 160, offsetY, 30, 2, hardware.getLayerHeight().getMin(), hardware.getLayerHeight().getMax(), hardware.getLayerHeight().getValue());
		layerHeightField = Gui.createField(cp5, true, "", offsetX + 160, offsetY + 80, 60, 20, hardware.getLayerHeight().getValue());
		layerWidthKnob = Gui.createKnob(cp5, true, "LAYER WIDTH", offsetX + 240, offsetY, 30, 2, hardware.getLayerWidth().getMin(), hardware.getLayerWidth().getMax(), hardware.getLayerWidth().getValue());
		layerWidthField = Gui.createField(cp5, true, "", offsetX + 240, offsetY + 80, 60, 20, hardware.getLayerWidth().getValue());
		layerBottomKnob = Gui.createKnob(cp5, true, "LAYER BOTTOM", offsetX + 320, offsetY, 30, 0, hardware.getLayerBottom().getMin(), hardware.getLayerBottom().getMax(), hardware.getLayerBottom().getValue());
		layerBottomField = Gui.createField(cp5, true, "", offsetX + 320, offsetY + 80, 60, 20, hardware.getLayerBottom().getValue());

		// Link fields and knobs
		filamentDiameterKnob.linkField(filamentDiameterField);
		nozzleDiameterKnob.linkField(nozzleDiameterField);
		layerHeightKnob.linkField(layerHeightField);
		layerWidthKnob.linkField(layerWidthField);
		layerBottomKnob.linkField(layerBottomField);
	}


	public GuiKnob getFilamentDiameterKnob()
	{
		return filamentDiameterKnob;
	}

	public GuiKnob getNozzleDiameterKnob()
	{
		return nozzleDiameterKnob;
	}

	public GuiKnob getLayerHeightKnob()
	{
		return layerHeightKnob;
	}

	public GuiKnob getLayerBottomKnob()
	{
		return layerBottomKnob;
	}

	public GuiKnob getLayerWidthKnob()
	{
		return layerWidthKnob;
	}


}
