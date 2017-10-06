package com.madebyferdi.folume.printer.view;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.controlP5.GuiContainer;
import com.madebyferdi.folume.printer.view.controls.*;
import com.madebyferdi.folume.printer.view.graphs.PrinterGraphAcceleration;
import com.madebyferdi.folume.printer.view.graphs.PrinterGraphHistory;
import com.madebyferdi.folume.printer.view.graphs.PrinterGraphNozzle;
import com.madebyferdi.folume.printer.view.graphs.PrinterGraphPreview;
import com.madebyferdi.folume.settings.Style;
import com.madebyferdi.folume.shape.Shape;
import com.madebyferdi.folume.ultimaker.Ultimaker;
import processing.core.PConstants;
import processing.core.PGraphics;


final public class PrinterView
{

	// Properties
	private Application app;
	private GuiContainer cp5;
	private PGraphics pg;


	// Parts
	private PrinterControlTemperature controlTemperature;
	private PrinterControlFan controlFan;
	private PrinterControlSpeed controlSpeed;
	private PrinterControlLayer controlLayer;
	private PrinterControlRetract controlRetract;
	private PrinterControlStep controlStep;
	private PrinterControlFeedrate controlFeedrate;
	private PrinterControlAcceleration controlAcceleleration;
	private PrinterControlJerk controlJerk;
	private PrinterControlPosition graphPosition;
	private PrinterGraphNozzle graphNozzle;
	private PrinterGraphAcceleration graphAcceleration;
	private PrinterControlConnect controlConnect;
	private PrinterGraphPreview graphPreview;
	private PrinterGraphHistory graphHistory;


	public PrinterView(Application app, Shape shape, Ultimaker ultimaker)
	{
		// Store references
		this.app = app;

		// Create GUI layer
		cp5 = new GuiContainer(app);
		cp5.setAutoDraw(false);

		// Row 1
		controlTemperature = new PrinterControlTemperature(cp5, ultimaker.getHardware(), Style.PRINT_X + 20, Style.PRINT_Y + 20);
		controlFan = new PrinterControlFan(cp5, ultimaker.getHardware(), Style.PRINT_X + 180, Style.PRINT_Y + 20);
		controlSpeed = new PrinterControlSpeed(cp5, ultimaker.getHardware(), Style.PRINT_X + 360, Style.PRINT_Y + 20);
		controlLayer = new PrinterControlLayer(cp5, ultimaker.getHardware(), Style.PRINT_X + 620, Style.PRINT_Y + 20);
		controlRetract = new PrinterControlRetract(cp5, ultimaker.getHardware(), Style.PRINT_X + 1040, Style.PRINT_Y + 20);

		// Row 2
		controlStep = new PrinterControlStep(cp5, ultimaker.getHardware(), Style.PRINT_X + 20, Style.PRINT_Y + 160);
		controlFeedrate = new PrinterControlFeedrate(cp5, ultimaker.getHardware(), Style.PRINT_X + 360, Style.PRINT_Y + 160);
		controlAcceleleration = new PrinterControlAcceleration(cp5, ultimaker.getHardware(), Style.PRINT_X + 700, Style.PRINT_Y + 160);
		controlJerk = new PrinterControlJerk(cp5, ultimaker.getHardware(), Style.PRINT_X + 1040, Style.PRINT_Y + 160);

		// Row 3
		graphPosition = new PrinterControlPosition(cp5, ultimaker.getHardware(), Style.PRINT_X + 20, Style.PRINT_Y + 300);
		graphNozzle = new PrinterGraphNozzle(app, cp5, Style.PRINT_X + 240, Style.PRINT_Y + 300);
		graphAcceleration = new PrinterGraphAcceleration(cp5, Style.PRINT_X + 480, Style.PRINT_Y + 300);

		// Row 4
		controlConnect = new PrinterControlConnect(cp5, Style.PRINT_X + 20, Style.PRINT_Y + 490);
		graphPreview = new PrinterGraphPreview(app, shape, cp5, ultimaker.getHardware(), Style.PRINT_X + 240, Style.PRINT_Y + 490);
		graphHistory = new PrinterGraphHistory(cp5, Style.PRINT_X + 830, Style.PRINT_Y + 490);

		// Create new background
		createBrackground();
	}


	public PrinterControlTemperature getControlTemperature()
	{
		return controlTemperature;
	}

	public PrinterControlFan getControlFan()
	{
		return controlFan;
	}

	public PrinterControlSpeed getControlSpeed()
	{
		return controlSpeed;
	}

	public PrinterControlLayer getControlLayer()
	{
		return controlLayer;
	}

	public PrinterControlRetract getControlRetract()
	{
		return controlRetract;
	}

	public PrinterControlStep getControlStep()
	{
		return controlStep;
	}

	public PrinterControlFeedrate getControlFeedrate()
	{
		return controlFeedrate;
	}

	public PrinterControlAcceleration getControlAcceleleration()
	{
		return controlAcceleleration;
	}

	public PrinterControlJerk getControlJerk()
	{
		return controlJerk;
	}

	public PrinterControlPosition getGraphPosition()
	{
		return graphPosition;
	}

	public PrinterGraphNozzle getGraphNozzle()
	{
		return graphNozzle;
	}

	public PrinterGraphAcceleration getGraphAcceleration()
	{
		return graphAcceleration;
	}

	public PrinterControlConnect getControlConnect()
	{
		return controlConnect;
	}

	public PrinterGraphPreview getGraphPreview()
	{
		return graphPreview;
	}

	public PrinterGraphHistory getGraphHistory()
	{
		return graphHistory;
	}


	/**
	 * Create background layer, note that we only need to do this once
	 * so it's faster to store it in a separate graphics object
	 */
	public void createBrackground()
	{
		// Create new background layer
		pg = app.createGraphics(Style.PRINT_WIDTH, Style.PRINT_HEIGHT, PConstants.P2D);

		// Draw background
		pg.beginDraw();
		pg.background(Style.PRINT_COLOR_BACKGROUND.getRed(), Style.PRINT_COLOR_BACKGROUND.getGreen(), Style.PRINT_COLOR_BACKGROUND.getBlue());

		// Line style
		pg.noFill();
		pg.strokeWeight(1);
		pg.stroke(Style.PRINT_COLOR_FOREGROUND.getRed(), Style.PRINT_COLOR_FOREGROUND.getGreen(), Style.PRINT_COLOR_FOREGROUND.getBlue());

		// Draw horizontal lines
		pg.line(0, 140, Style.PRINT_WIDTH, 140);
		pg.line(0, 280, Style.PRINT_WIDTH, 280);
		pg.line(0, 470, Style.PRINT_WIDTH, 470);

		// Draw vertical lines (row 1)
		pg.line(340, 0, 340, 140);
		pg.line(600, 0, 600, 140);
		pg.line(1020, 0, 1020, 140);

		// Draw vertical lines (row 2)
		pg.line(340, 140, 340, 280);
		pg.line(680, 140, 680, 280);
		pg.line(1020, 140, 1020, 280);

		// Draw vertical lines (row 3)
		pg.line(220, 280, 220, 470);
		pg.line(460, 280, 460, 470);

		// Draw vertical lines (row 4)
		pg.line(220, 470, 220, Style.PRINT_HEIGHT);
		pg.line(810, 470, 810, Style.PRINT_HEIGHT);
		pg.endDraw();
	}


	public void draw()
	{
		// Show background
		app.image(pg, Style.PRINT_X, Style.PRINT_Y);

		// Draw graphs
		graphNozzle.draw();
		graphPreview.draw();

		// Draw interface elements
		cp5.draw();
	}
}
