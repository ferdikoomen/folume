package com.madebyferdi.folume.shape;


import com.madebyferdi.folume.utils.Filament;
import com.madebyferdi.folume.utils.Maths;

import java.util.List;

final public class ShapeOptimizer
{


	/**
	 * Draw the shape
	 */
	static public void optimize(List<ShapePathPoint> pointsIn, List<ShapePathPoint> pointsOut, double speedTravel, double speedPrint, double speedZ, double filamentDiameter, double nozzleDiameter, double layerHeight, double layerWidth)
	{
		// Check if we even have points
		if (!pointsIn.isEmpty()) {

			// Properties for loop
			boolean first = true;
			double totalDistance = 0;
			double totalFilament = 0;
			double totalDuration = 0;
			double prevX = 0;
			double prevY = 0;
			double prevZ = 0;

			// Iterate over points
			for (ShapePathPoint point : pointsIn) {

				// Store properties
				double x = point.getX();
				double y = point.getY();
				double z = point.getZ();
				int layer = point.getLayer();

				// Store motion flags
				boolean moveHorizontal = (prevX != x || prevY != y);
				boolean moveVertical = (prevZ != z);

				// If this is the first point then add it directly,
				// off course this point does not extrude any material
				if (first) {
					first = false;
					pointsOut.add(new ShapePathPoint(x, y, z, 0, totalDistance, 0, totalFilament, 0, totalDuration, speedTravel, layer));

				} else if (moveVertical && moveHorizontal) {

					// Move the heat bed and the extruder, in this case
					// we split this up into two commands: One quick vertical
					// move and a even quicker horizontal move.
					ShapePathPoint p1 = addPoint(prevX, prevY, z, prevX, prevY, prevZ, totalDistance, totalFilament, totalDuration, filamentDiameter, nozzleDiameter, layerHeight, layerWidth, speedZ, layer);

					totalDistance = p1.getTotalDistance();
					totalFilament = p1.getTotalFilament();
					totalDuration = p1.getTotalDuration();
					pointsOut.add(p1);

					ShapePathPoint p2 = addPoint(x, y, z, prevX, prevY, z, totalDistance, totalFilament, totalDuration, filamentDiameter, nozzleDiameter, layerHeight, layerWidth, speedTravel, layer);

					totalDistance = p2.getTotalDistance();
					totalFilament = p2.getTotalFilament();
					totalDuration = p2.getTotalDuration();
					pointsOut.add(p2);

				} else if (moveHorizontal || moveVertical) {

					// This is either a horizontal or vertical move, this means
					// we are either performing a straight forward vertical move
					// or a print action.
					double speed = moveHorizontal ? speedPrint : speedZ;

					ShapePathPoint p3 = addPoint(x, y, z, prevX, prevY, prevZ, totalDistance, totalFilament, totalDuration, filamentDiameter, nozzleDiameter, layerHeight, layerWidth, speed, layer);

					totalDistance = p3.getTotalDistance();
					totalFilament = p3.getTotalFilament();
					totalDuration = p3.getTotalDuration();
					pointsOut.add(p3);
				}

				// Note that we do not store points that have both no vertical and
				// no horizontal movement! This means that any double points will be
				// ignored by this method.

				// Store previous position
				prevX = x;
				prevY = y;
				prevZ = z;
			}
		}
	}


	static private ShapePathPoint addPoint(double x1, double y1, double z1, double x2, double y2, double z2, double totalDistance, double totalFilament, double totalDuration, double filamentDiameter, double nozzleDiameter, double layerHeight, double layerWidth, double speed, int layer)
	{
		// Calculate distance in MM
		double distanceX = Maths.abs(x1 - x2);
		double distanceY = Maths.abs(y1 - y2);
		double distanceZ = Maths.abs(z1 - z2);
		double distance = Maths.sqrt((distanceX * distanceX) + (distanceY * distanceY) + (distanceZ * distanceZ));

		// Get filament length in MM
		double filament = Filament.getDistance(distance, filamentDiameter, nozzleDiameter, layerHeight, layerWidth);

		// Get duration in seconds
		double duration = distance / speed;

		// Return a new point with all these properties
		return new ShapePathPoint(x1, y1, z1, distance, totalDistance + distance, filament, totalFilament + filament, duration, totalDuration + duration, speed, layer);
	}
}
