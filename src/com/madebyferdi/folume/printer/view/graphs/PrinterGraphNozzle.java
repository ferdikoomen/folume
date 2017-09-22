package com.madebyferdi.folume.printer.view.graphs;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.controlP5.GuiTextlabel;
import com.madebyferdi.folume.settings.Style;
import com.madebyferdi.folume.utils.Filament;
import com.madebyferdi.folume.utils.Gui;
import com.madebyferdi.folume.utils.Utils;
import controlP5.ControlP5;
import processing.core.PConstants;
import processing.core.PGraphics;


final public class PrinterGraphNozzle
{

	// Size
	final static private int WIDTH = 200;
	final static private int HEIGHT = 140;


	// Properties
	private Application app;
	private ControlP5 cp5;
	private int offsetX;
	private int offsetY;
	private PGraphics pg;
	private GuiTextlabel label;
	private double currentFilamentDiameter = 0;
	private double currentNozzleDiameter = 0;
	private double currentLayerHeight = 0;
	private double currentLayerWidth = 0;
	private boolean invalidated = false;


	public PrinterGraphNozzle(Application app, ControlP5 cp5, int offsetX, int offsetY)
	{
		// Store references
		this.app = app;
		this.cp5 = cp5;
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create new graphics layer
		pg = app.createGraphics(WIDTH, HEIGHT, PConstants.P2D);

		// Create interface element
		label = Gui.createTextlabel(cp5, "NOZZLE", offsetX, offsetY + HEIGHT + 4);
	}


	/**
	 * Update nozzle shape
	 */
	private void updateShape()
	{
		// Draw updated graph
		pg.beginDraw();
		pg.background(
			Style.PRINT_COLOR_FOREGROUND.getRed(),
			Style.PRINT_COLOR_FOREGROUND.getGreen(),
			Style.PRINT_COLOR_FOREGROUND.getBlue()
		);

		// Draw print bed
		pg.noStroke();
		pg.fill(
			Style.PRINT_COLOR_INACTIVE.getRed(),
			Style.PRINT_COLOR_INACTIVE.getGreen(),
			Style.PRINT_COLOR_INACTIVE.getBlue()
		);
		pg.rect(0, HEIGHT - 2, WIDTH, 2);

		// Draw extruder
		pg.noStroke();
		pg.fill(
			Style.PRINT_COLOR_INACTIVE.getRed(),
			Style.PRINT_COLOR_INACTIVE.getGreen(),
			Style.PRINT_COLOR_INACTIVE.getBlue()
		);

		double center = WIDTH / 2;
		double bottom = HEIGHT - 2;

		pg.beginShape();
		pg.vertex((float) (center - (currentFilamentDiameter * 15) - 10), 0);
		pg.vertex((float) (center + (currentFilamentDiameter * 15) + 10), 0);
		pg.vertex((float) (center + (currentFilamentDiameter * 15) + 10), (float) (bottom - (currentLayerHeight * 30) - 80));
		pg.vertex((float) (center + (currentNozzleDiameter * 15) + 2), (float) (bottom - (currentLayerHeight * 30)));
		pg.vertex((float) (center - (currentNozzleDiameter * 15) - 2), (float) (bottom - (currentLayerHeight * 30)));
		pg.vertex((float) (center - (currentFilamentDiameter * 15) - 10), (float) (bottom - (currentLayerHeight * 30) - 80));
		pg.vertex((float) (center - (currentFilamentDiameter * 15) - 10), 0);
		pg.endShape(PConstants.CLOSE);

		// Draw material inside of extruder
		pg.fill(255, 255, 255);
		pg.beginShape();
		pg.vertex((float) (center - (currentFilamentDiameter * 15)), 0);
		pg.vertex((float) (center + (currentFilamentDiameter * 15)), 0);
		pg.vertex((float) (center + (currentFilamentDiameter * 15)), (float) (bottom - (currentLayerHeight * 30) - 80));
		pg.vertex((float) (center + (currentNozzleDiameter * 15)), (float) (bottom - (currentLayerHeight * 30)));
		pg.vertex((float) (center - (currentNozzleDiameter * 15)), (float) (bottom - (currentLayerHeight * 30)));
		pg.vertex((float) (center - (currentFilamentDiameter * 15)), (float) (bottom - (currentLayerHeight * 30) - 80));
		pg.vertex((float) (center - (currentFilamentDiameter * 15)), 0);
		pg.endShape(PConstants.CLOSE);

		// Draw material outside of extruder
		// Note that we use a bezier curve here to simulate a bit of material flow
		pg.fill(255, 255, 255);
		pg.beginShape();
		pg.vertex((float) (center - (currentNozzleDiameter * 15)), (float) (bottom - (currentLayerHeight * 30)));
		pg.vertex((float) (center + (currentNozzleDiameter * 15)), (float) (bottom - (currentLayerHeight * 30)));
		pg.bezierVertex((float) (center + (currentNozzleDiameter * 15)), (float) (bottom - (currentLayerHeight * 15)), (float) (center + (currentLayerWidth * 15)), (float) (bottom - (currentLayerHeight * 15)), (float) (center + (currentLayerWidth * 15)), (float) (bottom));
		pg.vertex((float) (center - (currentLayerWidth * 15)), (float) (bottom));
		pg.bezierVertex((float) (center - (currentLayerWidth * 15)), (float) (bottom - (currentLayerHeight * 15)), (float) (center - (currentNozzleDiameter * 15)), (float) (bottom - (currentLayerHeight * 15)), (float) (center - (currentNozzleDiameter * 15)), (float) (bottom - (currentLayerHeight * 30)));
		pg.endShape(PConstants.CLOSE);
		pg.endDraw();

		// Calculate how much filament is needed for 1 mm of the printing layer
		// First we calculate the volume of the filament and nozzle
		double filament = Filament.getDistance(1, currentFilamentDiameter, currentNozzleDiameter, currentLayerHeight, currentLayerWidth);

		// Set rounded value
		label.setText("NOZZLE (1.00 MM : " + Utils.getString(1 / filament, 2) + " MM)");
	}


	/**
	 * Update graph
	 */
	public void update(double filamentDiameter, double nozzleDiameter, double layerHeight, double layerWidth)
	{
		// Check if values have changed
		if (currentFilamentDiameter != filamentDiameter ||
			currentNozzleDiameter != nozzleDiameter ||
			currentLayerHeight != layerHeight ||
			currentLayerWidth != layerWidth) {

			currentFilamentDiameter = filamentDiameter;
			currentNozzleDiameter = nozzleDiameter;
			currentLayerHeight = layerHeight;
			currentLayerWidth = nozzleDiameter * layerWidth;
			invalidated = true;
		}
	}


	/**
	 * Update controls
	 */
	public void draw()
	{
		// Update shape if needed
		if (invalidated) {
			invalidated = false;
			updateShape();
		}

		// Show image
		app.image(pg, offsetX, offsetY);
	}
}
