package com.madebyferdi.folume.design.view.preview;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.controlP5.GuiTextlabel;
import com.madebyferdi.folume.settings.Style;
import com.madebyferdi.folume.shape.Shape;
import com.madebyferdi.folume.shape.ShapePathPoint;
import com.madebyferdi.folume.utils.Camera;
import com.madebyferdi.folume.utils.Gui;
import controlP5.ControlP5;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PShape;

import java.util.List;


final public class DesignPreview
{

	// Constants
	final static private int WIDTH = 900;
	final static private int HEIGHT = 570;


	// Properties
	private Application app;
	private Shape shape;
	private ControlP5 cp5;
	private PGraphics pg;
	private PShape ps;
	private Camera camera;
	private int offsetX = 0;
	private int offsetY = 0;
	private GuiTextlabel textlabel;
	private boolean invalidatedShape = false;
	private boolean invalidatedCamera = false;


	public DesignPreview(Application app, Shape shape, ControlP5 cp5, int offsetX, int offsetY)
	{
		// Store references
		this.app = app;
		this.shape = shape;
		this.cp5 = cp5;
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create label
		textlabel = Gui.createTextlabel(cp5, "PREVIEW", offsetX, offsetY + HEIGHT + 4);

		// Create new graphics layer
		pg = app.createGraphics(WIDTH, HEIGHT, PConstants.P3D);

		// Somehow there is a bug: When you request a create shape before the
		// graphics layer had a draw command, the shape is invalid...
		pg.beginDraw();
		pg.endDraw();

		// Create camera control
		camera = new Camera(app, pg, offsetX, offsetY, WIDTH, HEIGHT, 0, 0, 0, 200, -90, 200);

		// Invalidate when camera changes
		camera.onUpdate(() -> invalidatedCamera = true);
	}


	/**
	 * Reset camera
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
		// Get points and counts
		List<ShapePathPoint> points = shape.getPathDesign().getPoints();

		// Get the number of layers and calculate the number
		// of points per layer: Each segment has 6 point, however
		// the last point of a segment is shared with the first
		// point of the next segment, therefore we have 5 points
		// per segment, plus 1 for the last segment in the layer.
		// These values are needed to calculate the quads in the
		// design preview area.
		int numLayers = shape.getNumLayers();
		int numPoints = shape.getNumPoints();

		// Check if we have enough data
		if (points.size() == (numLayers * numPoints)) {

			// Create new shape
			ps = pg.createShape();

			// Iterate over layers
			for (int layer = 0; layer < numLayers - 1; layer++) {

				// Set stroke options
				ps.beginShape(PShape.QUADS);
				ps.strokeWeight(1);
				ps.stroke(
					Style.RENDER_COLOR_STROKE.getRed(),
					Style.RENDER_COLOR_STROKE.getGreen(),
					Style.RENDER_COLOR_STROKE.getBlue()
				);
				ps.fill(
					Style.RENDER_COLOR_FILL.getRed(),
					Style.RENDER_COLOR_FILL.getGreen(),
					Style.RENDER_COLOR_FILL.getBlue()
				);

				// Create quad strip for each layer
				for (int index = 0; index < numPoints - 1; index++) {

					// Start index in point array
					int start1 = numPoints * layer;
					int start2 = numPoints * (layer + 1);

					// Get points for quad
					ShapePathPoint p1 = points.get(start1 + index);
					ShapePathPoint p2 = points.get(start1 + index + 1);
					ShapePathPoint p3 = points.get(start2 + index);
					ShapePathPoint p4 = points.get(start2 + index + 1);

					// Create quad
					ps.vertex((float) p1.getX(), (float) -p1.getZ(), (float) -p1.getY());
					ps.vertex((float) p2.getX(), (float) -p2.getZ(), (float) -p2.getY());
					ps.vertex((float) p4.getX(), (float) -p4.getZ(), (float) -p4.getY());
					ps.vertex((float) p3.getX(), (float) -p3.getZ(), (float) -p3.getY());
				}

				// Done!
				ps.endShape();
			}
		}

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
		pg.background(Style.DESIGN_COLOR_FOREGROUND.getRed(), Style.DESIGN_COLOR_FOREGROUND.getGreen(), Style.DESIGN_COLOR_FOREGROUND.getBlue());

		// Draw camera, we make sure to center the object
		camera.setCenterY(shape.getPathDesign().getCenterZ());
		camera.apply();

		// Draw lights
		pg.ambientLight(
			Style.RENDER_COLOR_AMBIENT.getRed(),
			Style.RENDER_COLOR_AMBIENT.getGreen(),
			Style.RENDER_COLOR_AMBIENT.getBlue()
		);
		pg.directionalLight(
			Style.RENDER_COLOR_LIGHT.getRed(),
			Style.RENDER_COLOR_LIGHT.getGreen(),
			Style.RENDER_COLOR_LIGHT.getBlue()
			, 0, 0, -1
		);
		pg.pointLight(
			Style.RENDER_COLOR_LIGHT.getRed(),
			Style.RENDER_COLOR_LIGHT.getGreen(),
			Style.RENDER_COLOR_LIGHT.getBlue(),
			(float) camera.getCameraX(),
			(float) camera.getCenterY(),
			(float) camera.getCameraZ()
		);

		// Draw lines
		if (ps != null) {
			ps.draw(pg);
		}

		// Done!
		pg.endDraw();
	}


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
