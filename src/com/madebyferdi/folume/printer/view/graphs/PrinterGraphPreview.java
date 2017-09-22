package com.madebyferdi.folume.printer.view.graphs;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.controlP5.GuiTextlabel;
import com.madebyferdi.folume.settings.Style;
import com.madebyferdi.folume.shape.Shape;
import com.madebyferdi.folume.shape.ShapePathPoint;
import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;
import com.madebyferdi.folume.utils.Camera;
import com.madebyferdi.folume.utils.Cube;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PShape;


final public class PrinterGraphPreview
{

	// Constants
	final static private int WIDTH = 550;
	final static private int HEIGHT = 380;


	// Properties
	private Application app;
	private Shape shape;
	private ControlP5 cp5;
	private UltimakerHardware hardware;
	private int offsetX;
	private int offsetY;
	private PGraphics pg;
	private PShape ps;
	private Camera camera;
	private Cube buildVolumeSize;
	private Cube printVolumeSize;
	private Cube printBedSize;
	private PShape buildVolume;
	private PShape printVolume;
	private PShape printBed;
	private PShape extruder;
	private GuiTextlabel textlabel;
	private double currentX = 0;
	private double currentY = 0;
	private double currentZ = 0;
	private boolean invalidatedShape = false;
	private boolean invalidatedCamera = false;


	public PrinterGraphPreview(Application app, Shape shape, ControlP5 cp5, UltimakerHardware hardware, int offsetX, int offsetY)
	{
		// Store references
		this.app = app;
		this.shape = shape;
		this.cp5 = cp5;
		this.hardware = hardware;
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Calculate volumes
		buildVolumeSize = new Cube(
			hardware.getBuildVolumeX().getMin(),
			hardware.getBuildVolumeY().getMin(),
			hardware.getBuildVolumeZ().getMin(),
			hardware.getBuildVolumeX().getMax(),
			hardware.getBuildVolumeY().getMax(),
			hardware.getBuildVolumeZ().getMax()
		);
		printVolumeSize = new Cube(
			hardware.getPrintVolumeX().getMin(),
			hardware.getPrintVolumeY().getMin(),
			hardware.getPrintVolumeZ().getMin(),
			hardware.getPrintVolumeX().getMax(),
			hardware.getPrintVolumeY().getMax(),
			hardware.getPrintVolumeZ().getMax()
		);
		printBedSize = new Cube(
			hardware.getPrintVolumeX().getMin(),
			hardware.getPrintVolumeY().getMin(),
			0,
			hardware.getPrintVolumeX().getMax(),
			hardware.getPrintVolumeY().getMax(),
			0
		);

		// Create new graphics layer
		pg = app.createGraphics(WIDTH, HEIGHT, PConstants.P3D);

		// Somehow there is a bug: When you request a create shape before the
		// graphics layer had a draw command, the shape is invalid...
		pg.beginDraw();
		pg.endDraw();

		// Draw shapes that we can reuse
		drawShapes();

		// Create camera control
		camera = new Camera(app, pg, offsetX, offsetY, WIDTH, HEIGHT, -(buildVolumeSize.getWidth() / 2), // Half build volume width (negative)
			-(buildVolumeSize.getHeight() / 2), // Half build volume height (negative)
			(buildVolumeSize.getDepth() / 2), // Half build volume depth
			180, -90, 400);

		// Invalidate when camera changes
		camera.onUpdate(() -> invalidatedCamera = true);

		// Create label
		textlabel = Gui.createTextlabel(cp5, "PREVIEW", offsetX, offsetY + HEIGHT + 4);
	}


	/**
	 * Draw basic shapes that we can reuse
	 */
	private void drawShapes()
	{
		buildVolume = pg.createShape(PShape.BOX, (float) buildVolumeSize.getWidth(), (float) buildVolumeSize.getHeight(), (float) buildVolumeSize.getDepth());
		buildVolume.setFill(false);
		buildVolume.setStrokeWeight(1);
		buildVolume.setStroke(Style.PRINT_COLOR_INACTIVE.getArgb());
		buildVolume.translate((float) buildVolumeSize.getCenterX(), (float) buildVolumeSize.getCenterZ(), (float) -buildVolumeSize.getCenterY());

		printVolume = pg.createShape(PShape.BOX, (float) printVolumeSize.getWidth(), (float) printVolumeSize.getHeight(), (float) printVolumeSize.getDepth());
		printVolume.setFill(false);
		printVolume.setStrokeWeight(1);
		printVolume.setStroke(Style.PRINT_COLOR_ACTIVE.getArgb());
		printVolume.translate((float) printVolumeSize.getCenterX(), (float) printVolumeSize.getCenterZ(), (float) -printVolumeSize.getCenterY());

		printBed = pg.createShape(PShape.BOX, (float) printBedSize.getWidth(), (float) printBedSize.getHeight(), (float) printBedSize.getDepth());
		printBed.setFill(false);
		printBed.setStrokeWeight(2);
		printBed.setStroke(Style.PRINT_COLOR_ACTIVE.getArgb());
		printBed.translate((float) printBedSize.getCenterX(), (float) printBedSize.getCenterZ(), (float) -printBedSize.getCenterY());

		extruder = pg.createShape(PShape.SPHERE, 4);
		extruder.setFill(Style.PRINT_COLOR_ACTIVE.getArgb());
		extruder.setStroke(false);
	}


	/**
	 * Reset camera position
	 */
	public void reset()
	{
		camera.reset();
	}


	/**
	 * Set the invalidation flag, this will update
	 * the shape on the next draw call
	 */
	public void invalidate()
	{
		invalidatedShape = true;
		invalidatedCamera = true;
	}


	/**
	 * Render the shape to 3D vertices
	 */
	private void updateShape()
	{
		// Create new shape
		ps = pg.createShape();
		ps.beginShape();

		// Set stroke options
		ps.noFill();
		ps.stroke(255, 255, 255);
		ps.strokeWeight(1);

		// Store vertices
		for (ShapePathPoint point : shape.getPathPrint().getPoints()) {
			ps.vertex(
				(float) (printVolumeSize.getCenterX() + point.getX() - shape.getPathPrint().getCenterX()),
				(float) (-point.getZ()),
				(float) (-printVolumeSize.getCenterY() + -point.getY() + shape.getPathPrint().getCenterY())
			);
		}

		// Done!
		ps.endShape();

		// Update the graphics when done
		updateCamera();
	}


	/**
	 * Take the pre rendered shape and render it on a graphics layer
	 * using the camera transformations. Again we only do this on
	 * very specific updates to minimize render times
	 */
	private void updateCamera()
	{
		// Draw background
		pg.beginDraw();
		pg.background(Style.PRINT_COLOR_FOREGROUND.getRed(), Style.PRINT_COLOR_FOREGROUND.getGreen(), Style.PRINT_COLOR_FOREGROUND.getBlue());

		// Apply camera transformations
		camera.apply();

		// Draw build and print volume boxes
		buildVolume.draw(pg);
		printVolume.draw(pg);

		// Draw print bed on correct location
		pg.translate(0, (float) currentZ, 0);
		printBed.draw(pg);
		pg.translate(0, (float) -currentZ, 0);

		// Draw extruder on correct location
		pg.translate((float) currentX, 0, (float) -currentY);
		extruder.draw(pg);
		pg.translate((float) -currentX, 0, (float) currentY);

		// Move lines to build plate
		pg.translate(0, (float) currentZ, 0);

		// Draw lines
		if (ps != null) {
			ps.draw(pg);
		}

		// Done!
		pg.endDraw();
	}


	/**
	 * Update the position of the print bed and extruder
	 */
	public void update(double x, double y, double z)
	{
		// Check if values have changed
		if (currentX != x ||
			currentY != y ||
			currentZ != z) {

			currentX = x;
			currentY = y;
			currentZ = z;
			invalidatedCamera = true;
		}
	}


	/**
	 * Update controls
	 */
	public void draw()
	{
		// Update the camera. If any values have changed, then the camera
		// will invalidate the current render. This will trigger a redraw
		// of the shape from the new camera position.
		camera.update();

		// Update shape if needed
		if (invalidatedShape) {
			invalidatedShape = false;
			updateShape();
		}

		// Update camera if needed
		if (invalidatedCamera) {
			invalidatedCamera = false;
			updateCamera();
		}

		// Show image
		app.image(pg, offsetX, offsetY);
	}
}
