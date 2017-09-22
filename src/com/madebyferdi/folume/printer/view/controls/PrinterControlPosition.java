package com.madebyferdi.folume.printer.view.controls;


import com.madebyferdi.folume.controlP5.GuiSlider;
import com.madebyferdi.folume.controlP5.GuiSlider2D;
import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class PrinterControlPosition
{

	// Properties
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiSlider2D positionSliderXY;
	private GuiSlider positionSliderZ;


	public PrinterControlPosition(ControlP5 cp5, UltimakerHardware hardware, int offsetX, int offsetY)
	{
		// Store references
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create interface elements
		positionSliderXY = Gui.createSlider2D(cp5, true, "POS XY", offsetX, offsetY, 140, 140, 0, hardware.getBuildVolumeX().getMin(), hardware.getBuildVolumeY().getMax(), hardware.getBuildVolumeX().getMax(), hardware.getBuildVolumeY().getMin(), hardware.getHomeX().getValue(), hardware.getHomeY().getValue());
		positionSliderZ = Gui.createSlider(cp5, true, "POS Z", offsetX + 150, offsetY, 20, 140, 0, hardware.getBuildVolumeZ().getMax(), hardware.getBuildVolumeZ().getMin(), hardware.getHomeZ().getValue());
	}


	public GuiSlider2D getPositionSliderXY()
	{
		return positionSliderXY;
	}

	public GuiSlider getPositionSliderZ()
	{
		return positionSliderZ;
	}

}
