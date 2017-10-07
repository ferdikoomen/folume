package com.madebyferdi.folume.printer.view.controls;


import com.madebyferdi.folume.controlP5.GuiButton;
import com.madebyferdi.folume.controlP5.GuiDropdownList;
import com.madebyferdi.folume.controlP5.GuiTextfield;
import com.madebyferdi.folume.controlP5.GuiToggle;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;


final public class PrinterControlConnect
{

	// Properties
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiToggle connectSwitch;
	private GuiToggle modeSwitch;
	private GuiTextfield durationField;
	private GuiTextfield filamentField;
	private GuiButton startupButton;
	private GuiButton shutdownButton;
	private GuiButton loadButton;
	private GuiButton saveButton;
	private GuiButton printButton;
	private GuiButton gcodeButton;
	private GuiButton resetButton;
	private GuiDropdownList portsList;


	public PrinterControlConnect(ControlP5 cp5, int offsetX, int offsetY)
	{
		// Store references
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create interface elements
		connectSwitch = Gui.createToggle(cp5, true, "ON / OFF", offsetX, offsetY + 40, 80, 20, false);
		modeSwitch = Gui.createToggle(cp5, true, "WRITE / READ", offsetX + 100, offsetY + 40, 80, 20, true);
		durationField = Gui.createField(cp5, true, "DURATION", offsetX, offsetY + 90, 80, 20, 0);
		filamentField = Gui.createField(cp5, true, "FILAMENT", offsetX + 100, offsetY + 90, 80, 20, 0);
		startupButton = Gui.createButton(cp5, true, "STARTUP", offsetX, offsetY + 150, 80, 25);
		shutdownButton = Gui.createButton(cp5, true, "SHUTDOWN", offsetX + 100, offsetY + 150, 80, 25);
		loadButton = Gui.createButton(cp5, true, "LOAD SETTING", offsetX, offsetY + 190, 80, 25);
		saveButton = Gui.createButton(cp5, true, "SAVE SETTINGS", offsetX + 100, offsetY + 190, 80, 25);
		printButton = Gui.createButton(cp5, true, "PRINT OVER USB", offsetX, offsetY + 230, 80, 25);
		gcodeButton = Gui.createButton(cp5, true, "SAVE GCODE", offsetX + 100, offsetY + 230, 80, 25);
		resetButton = Gui.createButton(cp5, true, "RESET", offsetX, offsetY + 270, 80, 25);

		// Add last since it needs to be on top
		portsList = Gui.createDropdownList(cp5, true, "NO PRINTER FOUND", offsetX, offsetY, 180, 20, 7);

		// Set default text
		durationField.setValue("00:00:00");
		filamentField.setValue("0 MM");
	}


	public GuiToggle getConnectSwitch()
	{
		return connectSwitch;
	}

	public GuiToggle getModeSwitch()
	{
		return modeSwitch;
	}

	public GuiTextfield getDurationField()
	{
		return durationField;
	}

	public GuiTextfield getFilamentField()
	{
		return filamentField;
	}

	public GuiButton getStartupButton()
	{
		return startupButton;
	}

	public GuiButton getShutdownButton()
	{
		return shutdownButton;
	}

	public GuiButton getLoadButton()
	{
		return loadButton;
	}

	public GuiButton getSaveButton()
	{
		return saveButton;
	}

	public GuiButton getPrintButton()
	{
		return printButton;
	}

	public GuiButton getResetButton()
	{
		return resetButton;
	}

	public GuiButton getGcodeButton()
	{
		return gcodeButton;
	}

	public GuiDropdownList getPortsList()
	{
		return portsList;
	}

}
