package com.madebyferdi.folume.shape.svg;


import java.util.ArrayList;
import java.util.List;

final public class SvgBezier
{


	/**
	 * New bezier curve
	 *
	 * @param x1:    Start X
	 * @param y1:    Start Y
	 * @param x2:    Control point 1 X
	 * @param y2:    Control point 1 Y
	 * @param x3:    Control point 2 X
	 * @param y3:    Control point 2 Y
	 * @param x4:    End X
	 * @param y4:    End y
	 * @param steps: Steps to tessellate
	 */
	static public List<SvgPoint> getPoints(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, int steps)
	{
		// Data holders
		List<SvgPoint> points = new ArrayList<>();

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
			double x = ((x1 * mu3) +
				(3.0 * mu2 * t * x2) +
				(3.0 * mu * t2 * x3) +
				(t3 * x4));

			double y = ((y1 * mu3) +
				(3.0 * mu2 * t * y2) +
				(3.0 * mu * t2 * y3) +
				(t3 * y4));

			// Store points
			points.add(new SvgPoint(x, y));
		}

		return points;
	}
}
