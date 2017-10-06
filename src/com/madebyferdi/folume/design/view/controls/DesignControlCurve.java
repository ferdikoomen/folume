package com.madebyferdi.folume.design.view.controls;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.controlP5.GuiTextlabel;
import com.madebyferdi.folume.settings.Style;
import com.madebyferdi.folume.shape.Shape;
import com.madebyferdi.folume.shape.ShapeCurvePoint;
import com.madebyferdi.folume.utils.Gui;
import com.madebyferdi.folume.utils.Listener;
import com.madebyferdi.folume.utils.Maths;
import controlP5.ControlP5;
import processing.core.PConstants;
import processing.core.PGraphics;


final public class DesignControlCurve
{

	// Constants
	final static private int WIDTH = 300;
	final static private int HEIGHT = 570;
	final static private double SCALE = 2;
	final static private int GRID = 50;


	// Properties
	private Application app;
	private ControlP5 cp5;
	private Shape shape;
	private PGraphics pg;
	private int offsetX = 0;
	private int offsetY = 0;
	private GuiTextlabel textlabel;
	private double posInsideP1X = 0;
	private double posInsideP1Y = 0;
	private double posInsideP2X = 0;
	private double posInsideP2Y = 0;
	private double posInsideC1X = 0;
	private double posInsideC1Y = 0;
	private double posInsideC2X = 0;
	private double posInsideC2Y = 0;
	private double posOutsideP1X = 0;
	private double posOutsideP1Y = 0;
	private double posOutsideP2X = 0;
	private double posOutsideP2Y = 0;
	private double posOutsideC1X = 0;
	private double posOutsideC1Y = 0;
	private double posOutsideC2X = 0;
	private double posOutsideC2Y = 0;
	private double offsetInsideC1X = -10;
	private double offsetInsideC1Y = 10;
	private double offsetInsideC2X = -10;
	private double offsetInsideC2Y = -10;
	private double offsetOutsideC1X = -10;
	private double offsetOutsideC1Y = 10;
	private double offsetOutsideC2X = -10;
	private double offsetOutsideC2Y = -10;
	private boolean mouseDown = false;
	private boolean mouseOver = false;
	private double mousePosX = 0;
	private double mousePosY = 0;
	private double posX = 250;
	private double posY = 50;
	private double currentHeight = 0;
	private double currentRadiusInsideTop = 0;
	private double currentRadiusInsideBottom = 0;
	private double currentRadiusOutsideTop = 0;
	private double currentRadiusOutsideBottom = 0;
	private boolean invalidated = false;
	private boolean findSelected = true;
	private ShapeCurvePoint selected;
	private Listener listener;


	public DesignControlCurve(Application app, Shape shape, ControlP5 cp5, int offsetX, int offsetY)
	{
		// Store references
		this.app = app;
		this.shape = shape;
		this.cp5 = cp5;
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create label
		textlabel = Gui.createTextlabel(cp5, "CURVE", offsetX, offsetY + HEIGHT + 4);

		// Create new graphics layer
		pg = app.createGraphics(WIDTH, HEIGHT, PConstants.P2D);

		// Draw shape for this first time
		updateShape();
	}


	/**
	 * Getters
	 */
	public double getOffsetInsideC1X()
	{
		return offsetInsideC1X;
	}

	public double getOffsetInsideC1Y()
	{
		return offsetInsideC1Y;
	}

	public double getOffsetInsideC2X()
	{
		return offsetInsideC2X;
	}

	public double getOffsetInsideC2Y()
	{
		return offsetInsideC2Y;
	}

	public double getOffsetOutsideC1X()
	{
		return offsetOutsideC1X;
	}

	public double getOffsetOutsideC1Y()
	{
		return offsetOutsideC1Y;
	}

	public double getOffsetOutsideC2X()
	{
		return offsetOutsideC2X;
	}

	public double getOffsetOutsideC2Y()
	{
		return offsetOutsideC2Y;
	}


	/**
	 * Setters
	 */
	public void setOffsetInsideC1X(double value)
	{
		offsetInsideC1X = value;
		invalidated = true;
		updatePositions();
	}

	public void setOffsetInsideC1Y(double value)
	{
		offsetInsideC1Y = value;
		invalidated = true;
		updatePositions();
	}

	public void setOffsetInsideC2X(double value)
	{
		offsetInsideC2X = value;
		invalidated = true;
		updatePositions();
	}

	public void setOffsetInsideC2Y(double value)
	{
		offsetInsideC2Y = value;
		invalidated = true;
		updatePositions();
	}

	public void setOffsetOutsideC1X(double value)
	{
		offsetOutsideC1X = value;
		invalidated = true;
		updatePositions();
	}

	public void setOffsetOutsideC1Y(double value)
	{
		offsetOutsideC1Y = value;
		invalidated = true;
		updatePositions();
	}

	public void setOffsetOutsideC2X(double value)
	{
		offsetOutsideC2X = value;
		invalidated = true;
		updatePositions();
	}

	public void setOffsetOutsideC2Y(double value)
	{
		offsetOutsideC2Y = value;
		invalidated = true;
		updatePositions();
	}


	/**
	 * Reset control points
	 */
	public void reset()
	{
		offsetInsideC1X = -10;
		offsetInsideC1Y = 10;
		offsetInsideC2X = -10;
		offsetInsideC2Y = -10;
		offsetOutsideC1X = -10;
		offsetOutsideC1Y = 10;
		offsetOutsideC2X = -10;
		offsetOutsideC2Y = -10;
		posX = 250;
		posY = 50;
		invalidated = true;
		updatePositions();
	}


	/**
	 * Update listener
	 *
	 * @param listener: Method to execute when values have changed
	 */
	public void onChange(Listener listener)
	{
		this.listener = listener;
	}


	/**
	 * Update mouse positions and check if we should
	 * move any control points
	 */
	private void updateMouse()
	{
		// Store clicked button and last known mouse pos
		mouseDown = app.mouseButton == PConstants.LEFT;
		double mouseX = app.mouseX;
		double mouseY = app.mouseY;

		// Basic hit test
		mouseOver = (mouseX > offsetX && mouseX < (offsetX + WIDTH) &&
			mouseY > offsetY && mouseY < (offsetY + HEIGHT));

		// 1. When mouse button is up we clear the selection
		// 2. When mouse button is down we check if we can find a match
		// 3. When mouse button is down and we have a selection then we move the point
		if (!mouseDown) {
			selected = null;
			findSelected = true;

		} else if (mouseOver) {

			if (findSelected) {

				// Find selected point
				findSelected = false;
				selected = checkHit(shape.getCurveInside().getC1(), mouseX, mouseY);
				selected = checkHit(shape.getCurveInside().getC2(), mouseX, mouseY);
				selected = checkHit(shape.getCurveOutside().getC1(), mouseX, mouseY);
				selected = checkHit(shape.getCurveOutside().getC2(), mouseX, mouseY);

			} else if (selected != null) {

				// Move selected
				selected.setX(selected.getX() - ((mousePosX - mouseX) / SCALE));
				selected.setY(selected.getY() - ((mousePosY - mouseY) / SCALE));
				offsetInsideC1X = (shape.getCurveInside().getC1().getX() - shape.getCurveInside().getP1().getX());
				offsetInsideC1Y = (shape.getCurveInside().getC1().getY() - shape.getCurveInside().getP1().getY());
				offsetInsideC2X = (shape.getCurveInside().getC2().getX() - shape.getCurveInside().getP2().getX());
				offsetInsideC2Y = (shape.getCurveInside().getC2().getY() - shape.getCurveInside().getP2().getY());
				offsetOutsideC1X = (shape.getCurveOutside().getC1().getX() - shape.getCurveOutside().getP1().getX());
				offsetOutsideC1Y = (shape.getCurveOutside().getC1().getY() - shape.getCurveOutside().getP1().getY());
				offsetOutsideC2X = (shape.getCurveOutside().getC2().getX() - shape.getCurveOutside().getP2().getX());
				offsetOutsideC2Y = (shape.getCurveOutside().getC2().getY() - shape.getCurveOutside().getP2().getY());

				// Update curves
				updatePositions();
				invalidated = true;

				// Callback to update the design
				if (listener != null) {
					listener.callback();
				}

			} else {

				// Move canvas
				posX -= (mousePosX - mouseX);
				posY -= (mousePosY - mouseY);
				invalidated = true;
			}
		}

		// Store mouse position
		mousePosX = app.mouseX;
		mousePosY = app.mouseY;
	}


	/**
	 * Update positions of the points
	 */
	private void updatePositions()
	{
		// Calculate new positions for anchor points
		posInsideP1X = -currentRadiusInsideTop;
		posInsideP2X = -currentRadiusInsideBottom;
		posInsideP2Y = currentHeight;
		posOutsideP1X = -currentRadiusOutsideTop;
		posOutsideP2X = -currentRadiusOutsideBottom;
		posOutsideP2Y = currentHeight;

		// Calculate new positions for control points
		posInsideC1X = posInsideP1X + offsetInsideC1X;
		posInsideC1Y = posInsideP1Y + offsetInsideC1Y;
		posInsideC2X = posInsideP2X + offsetInsideC2X;
		posInsideC2Y = posInsideP2Y + offsetInsideC2Y;
		posOutsideC1X = posOutsideP1X + offsetOutsideC1X;
		posOutsideC1Y = posOutsideP1Y + offsetOutsideC1Y;
		posOutsideC2X = posOutsideP2X + offsetOutsideC2X;
		posOutsideC2Y = posOutsideP2Y + offsetOutsideC2Y;

		// Set coordinates
		shape.getCurveInside().getP1().setX(posInsideP1X);
		shape.getCurveInside().getP1().setY(posInsideP1Y);
		shape.getCurveInside().getP2().setX(posInsideP2X);
		shape.getCurveInside().getP2().setY(posInsideP2Y);
		shape.getCurveInside().getC1().setX(posInsideC1X);
		shape.getCurveInside().getC1().setY(posInsideC1Y);
		shape.getCurveInside().getC2().setX(posInsideC2X);
		shape.getCurveInside().getC2().setY(posInsideC2Y);
		shape.getCurveOutside().getP1().setX(posOutsideP1X);
		shape.getCurveOutside().getP1().setY(posOutsideP1Y);
		shape.getCurveOutside().getP2().setX(posOutsideP2X);
		shape.getCurveOutside().getP2().setY(posOutsideP2Y);
		shape.getCurveOutside().getC1().setX(posOutsideC1X);
		shape.getCurveOutside().getC1().setY(posOutsideC1Y);
		shape.getCurveOutside().getC2().setX(posOutsideC2X);
		shape.getCurveOutside().getC2().setY(posOutsideC2Y);

		// Calculate curve
		shape.getCurveInside().calculate(50); // 50 steps to define the curve
		shape.getCurveOutside().calculate(50); // 50 steps to define the curve
	}


	/**
	 * Render curves
	 */
	private void updateShape()
	{
		// Draw background
		pg.beginDraw();
		pg.background(
			Style.DESIGN_COLOR_FOREGROUND.getRed(),
			Style.DESIGN_COLOR_FOREGROUND.getGreen(),
			Style.DESIGN_COLOR_FOREGROUND.getBlue()
		);

		// Draw grid lines
		pg.noFill();
		pg.strokeWeight(1);
		pg.stroke(
			Style.DESIGN_COLOR_BACKGROUND.getRed(),
			Style.DESIGN_COLOR_BACKGROUND.getGreen(),
			Style.DESIGN_COLOR_BACKGROUND.getBlue()
		);
		for (double x = posX % GRID; x <= WIDTH; x += GRID) {
			pg.line((float) x, 0, (float) x, HEIGHT);
		}
		for (double y = posY % GRID; y <= HEIGHT; y += GRID) {
			pg.line(0, (float) y, WIDTH, (float) y);
		}
		pg.endDraw();

		// Draw curves
		pg.beginDraw();
		pg.translate((float) posX, (float) posY);
		shape.getCurveInside().draw(pg, 0xFF0099FF, 0xFF1A6697, SCALE);
		shape.getCurveOutside().draw(pg, 0xFFFF0044, 0xFF9A194b, SCALE);
		pg.endDraw();
	}


	/**
	 * Update the curves with the given radius
	 *
	 * @param height:              Height of the design
	 * @param radiusInsideTop:     Radius inside on top
	 * @param radiusInsideBottom:  Radius inside on bottom
	 * @param radiusOutsideTop:    Radius outside on top
	 * @param radiusOutsideBottom: Radius outside on bottom
	 */
	public void update(double height, double radiusInsideTop, double radiusInsideBottom, double radiusOutsideTop, double radiusOutsideBottom)
	{
		// Check if values have changed
		if (currentHeight != height ||
			currentRadiusInsideTop != radiusInsideTop ||
			currentRadiusInsideBottom != radiusInsideBottom ||
			currentRadiusOutsideTop != radiusOutsideTop ||
			currentRadiusOutsideBottom != radiusOutsideBottom) {

			currentHeight = height;
			currentRadiusInsideTop = radiusInsideTop;
			currentRadiusInsideBottom = radiusInsideBottom;
			currentRadiusOutsideTop = radiusOutsideTop;
			currentRadiusOutsideBottom = radiusOutsideBottom;
			invalidated = true;
			updatePositions();
		}
	}


	/**
	 * Check if we have a hit for a given point
	 *
	 * @param p:      Point to check
	 * @param mouseX: Mouse x position
	 * @param mouseY: Mouse y position
	 */
	private ShapeCurvePoint checkHit(ShapeCurvePoint p, double mouseX, double mouseY)
	{
		// Calculate distance in MM
		double distanceX = Maths.abs((posX + offsetX + (p.getX() * SCALE)) - mouseX);
		double distanceY = Maths.abs((posY + offsetY + (p.getY() * SCALE)) - mouseY);
		double distance = Maths.sqrt((distanceX * distanceX) + (distanceY * distanceY));

		// Return this point when we have a hit
		if (distance <= 15) {
			return p;
		}

		// Otherwise return the current selection
		return selected;
	}


	/**
	 * Draw the buffer
	 */
	public void draw()
	{
		// Check if we should select or move any control points
		updateMouse();

		// Update shape
		if (invalidated) {
			invalidated = false;
			updateShape();
		}

		// Show image
		app.image(pg, offsetX, offsetY);
	}
}
