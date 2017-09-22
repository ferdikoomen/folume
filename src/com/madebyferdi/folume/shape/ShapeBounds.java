package com.madebyferdi.folume.shape;


import com.madebyferdi.folume.utils.Filament;

import java.util.ArrayList;
import java.util.List;

final public class ShapeBounds
{


	/**
	 * Draw the shape
	 */
	static public List<ShapePathPoint> generate(double width, double depth, double speed, double filamentDiameter, double nozzleDiameter, double layerHeight, double layerWidth)
	{
		List<ShapePathPoint> points = new ArrayList<>();
		double halfWidth = width / 2;
		double halfDepth = depth / 2;

		// Draw these points
		//  2------3
		//  |      |
		// 1/6  *  | <- Center
		//  |      |
		//  5------4

		ShapePathPoint p1 = addPoint(-halfWidth, 0, 0, 0, 0, 0, filamentDiameter, nozzleDiameter, layerHeight, layerWidth, speed);
		ShapePathPoint p2 = addPoint(-halfWidth, halfDepth, 0, p1.getTotalDistance(), p1.getTotalFilament(), halfDepth, filamentDiameter, nozzleDiameter, layerHeight, layerWidth, speed);
		ShapePathPoint p3 = addPoint(halfWidth, halfDepth, 0, p2.getTotalDistance(), p2.getTotalFilament(), halfWidth * 2, filamentDiameter, nozzleDiameter, layerHeight, layerWidth, speed);
		ShapePathPoint p4 = addPoint(halfWidth, -halfDepth, 0, p3.getTotalDistance(), p3.getTotalFilament(), halfDepth * 2, filamentDiameter, nozzleDiameter, layerHeight, layerWidth, speed);
		ShapePathPoint p5 = addPoint(-halfWidth, -halfDepth, 0, p4.getTotalDistance(), p4.getTotalFilament(), halfWidth * 2, filamentDiameter, nozzleDiameter, layerHeight, layerWidth, speed);
		ShapePathPoint p6 = addPoint(-halfWidth, 0, 0, p5.getTotalDistance(), p5.getTotalFilament(), halfDepth, filamentDiameter, nozzleDiameter, layerHeight, layerWidth, speed);

		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
		points.add(p5);
		points.add(p6);
		return points;
	}


	static private ShapePathPoint addPoint(double x, double y, double z, double totalDistance, double totalFilament, double distance, double filamentDiameter, double nozzleDiameter, double layerHeight, double layerWidth, double speed)
	{
		// Calculate amount of filament needed
		double filament = Filament.getDistance(distance, filamentDiameter, nozzleDiameter, layerHeight, layerWidth);

		// Return new point (without duration)
		return new ShapePathPoint(x, y, z, distance, totalDistance + distance, filament, totalFilament + filament, 0, 0, speed, 0);
	}
}
