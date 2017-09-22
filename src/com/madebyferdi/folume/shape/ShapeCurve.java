package com.madebyferdi.folume.shape;

import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.utils.Utils;
import processing.core.PGraphics;
import processing.core.PShape;

import java.util.ArrayList;
import java.util.List;

final public class ShapeCurve
{

	// Properties
	private Application app;
	private PShape shapeLineP1;
	private PShape shapeLineP2;
	private PShape shapePointC1;
	private PShape shapePointC2;
	private PShape shapePointP1;
	private PShape shapePointP2;
	private PShape shapeCurve;
	private List<ShapeCurvePoint> points;
	private ShapeCurvePoint p1;
	private ShapeCurvePoint p2;
	private ShapeCurvePoint c1;
	private ShapeCurvePoint c2;


	public ShapeCurve()
	{
		p1 = new ShapeCurvePoint(0, 0);
		c1 = new ShapeCurvePoint(0, 0);
		c2 = new ShapeCurvePoint(0, 0);
		p2 = new ShapeCurvePoint(0, 0);
		points = new ArrayList<>();
	}


	/**
	 * Getters
	 */
	public ShapeCurvePoint getP1()
	{
		return p1;
	}

	public ShapeCurvePoint getP2()
	{
		return p2;
	}

	public ShapeCurvePoint getC1()
	{
		return c1;
	}

	public ShapeCurvePoint getC2()
	{
		return c2;
	}


	/**
	 * Calculate
	 *
	 * @param steps: Resolution
	 */
	public void calculate(int steps)
	{
		// Clear points
		points.clear();

		// Divide the bezier curve in points
		for (int n = 0; n <= steps; n++) {

			// Calculate T and MU values
			double t = (1.0 / steps) * n;
			double t2 = t * t;
			double t3 = t * t * t;
			double mu = 1.0 - t;
			double mu2 = mu * mu;
			double mu3 = mu * mu * mu;

			// Calculate X and Y positions
			double x = ((p1.getX() * mu3) +
				(3.0 * mu2 * t * c1.getX()) +
				(3.0 * mu * t2 * c2.getX()) +
				(t3 * p2.getX()));

			double y = ((p1.getY() * mu3) +
				(3.0 * mu2 * t * c1.getY()) +
				(3.0 * mu * t2 * c2.getY()) +
				(t3 * p2.getY()));

			// Add points
			points.add(new ShapeCurvePoint(x, y));
		}
	}


	/**
	 * Get position based on height
	 * TODO: Rewrite to calculation instead of loop
	 *
	 * @param height: Height value
	 */
	public double getRadius(double height)
	{
		// Iterate over points and find match
		for (int i = 0; i < points.size() - 1; i++) {
			ShapeCurvePoint a = points.get(i);
			ShapeCurvePoint b = points.get(i + 1);
			if (height >= a.getY() && height <= b.getY()) {
				return Utils.map(height, a.getY(), b.getY(), a.getX(), b.getX());
			}
		}
		return p2.getX();
	}


	/**
	 * Draw SVG shape to graphics layer
	 *
	 * @param pg:     Graphics object
	 * @param colorA: Color for this layer
	 * @param colorB: Color for this layer
	 * @param scale:  Scale to draw
	 */
	public void draw(PGraphics pg, int colorA, int colorB, double scale)
	{
		// Draw curve
		shapeCurve = pg.createShape();
		shapeCurve.setFill(false);
		shapeCurve.setStroke(colorA);
		shapeCurve.setStrokeWeight(2);
		shapeCurve.beginShape();
		for (ShapeCurvePoint point : points) {
			shapeCurve.vertex((float) (point.getX() * scale), (float) (point.getY() * scale), (float) (point.getX() * scale), (float) (point.getY() * scale));
		}
		shapeCurve.endShape();

		// Create lines from anchor point to control point
		shapeLineP1 = pg.createShape();
		shapeLineP1.setFill(false);
		shapeLineP1.setStroke(colorB);
		shapeLineP1.setStrokeWeight(1);
		shapeLineP1.beginShape(PShape.LINES);
		shapeLineP1.vertex((float) (p1.getX() * scale), (float) (p1.getY() * scale));
		shapeLineP1.vertex((float) (c1.getX() * scale), (float) (c1.getY() * scale));
		shapeLineP1.endShape();

		// Create lines from anchor point to control point
		shapeLineP2 = pg.createShape();
		shapeLineP2.setFill(false);
		shapeLineP2.setStroke(colorB);
		shapeLineP2.setStrokeWeight(1);
		shapeLineP2.beginShape(PShape.LINES);
		shapeLineP2.vertex((float) (p2.getX() * scale), (float) (p2.getY() * scale));
		shapeLineP2.vertex((float) (c2.getX() * scale), (float) (c2.getY() * scale));
		shapeLineP2.endShape();

		// Create point
		shapePointP1 = pg.createShape(PShape.ELLIPSE, (float) (p1.getX() * scale), (float) (p1.getY() * scale), 5, 5);
		shapePointP1.setFill(true);
		shapePointP1.setFill(colorA);
		shapePointP1.setStroke(false);

		// Create point
		shapePointP2 = pg.createShape(PShape.ELLIPSE, (float) (p2.getX() * scale), (float) (p2.getY() * scale), 5, 5);
		shapePointP2.setFill(true);
		shapePointP2.setFill(colorA);
		shapePointP2.setStroke(false);

		// Create point
		shapePointC1 = pg.createShape(PShape.ELLIPSE, (float) (c1.getX() * scale), (float) (c1.getY() * scale), 10, 10);
		shapePointC1.setFill(true);
		shapePointC1.setFill(colorB);
		shapePointC1.setStroke(false);

		// Create point
		shapePointC2 = pg.createShape(PShape.ELLIPSE, (float) (c2.getX() * scale), (float) (c2.getY() * scale), 10, 10);
		shapePointC2.setFill(true);
		shapePointC2.setFill(colorB);
		shapePointC2.setStroke(false);

		// Append to graphics object
		shapeLineP1.draw(pg);
		shapeLineP2.draw(pg);
		shapePointC1.draw(pg);
		shapePointC2.draw(pg);
		shapeCurve.draw(pg);
		shapePointP1.draw(pg);
		shapePointP2.draw(pg);
	}
}
