package com.madebyferdi.folume.shape;


import com.madebyferdi.folume.utils.Maths;
import com.madebyferdi.folume.utils.Utils;

import java.util.List;

final public class ShapeGenerator
{


	/**
	 * Generate the shape
	 */
	static public void draw(ShapeCurve curveInside, ShapeCurve curveOutside, List<ShapePathPoint> path, double height, double layers, double degrees, double xTop, double yTop, double xBottom, double yBottom, double radiusInsideTop, double radiusInsideBottom, double radiusOutsideTop, double radiusOutsideBottom, double ribCount, double ribWidthInsideTop, double ribWidthInsideBottom, double ribWidthOutsideTop, double ribWidthOutsideBottom, double rotateInsideTop, double rotateInsideBottom, double rotateOutsideTop, double rotateOutsideBottom)
	{
		// Calculate circle circumference and ratio
		double circumferenceInsideTop = (radiusInsideTop * 2) * Maths.PI;
		double circumferenceInsideBottom = (radiusInsideBottom * 2) * Maths.PI;
		double circumferenceOutsideTop = (radiusOutsideTop * 2) * Maths.PI;
		double circumferenceOutsideBottom = (radiusOutsideBottom * 2) * Maths.PI;
		double ratioTop = circumferenceOutsideTop / circumferenceInsideTop;
		double ratioBottom = circumferenceOutsideBottom / circumferenceInsideBottom;

		// Calculate changes per layer step
		double ratioStep = (ratioTop - ratioBottom) / layers;
		double ribWidthInsideStep = (ribWidthInsideTop - ribWidthInsideBottom) / layers;
		double ribWidthOutsideStep = (ribWidthOutsideTop - ribWidthOutsideBottom) / layers;
		double rotateInsideStep = (rotateInsideTop - rotateInsideBottom) / layers;
		double rotateOutsideStep = (rotateOutsideTop - rotateOutsideBottom) / layers;
		double xStep = (xTop - xBottom) / layers;
		double yStep = (yTop - yBottom) / layers;
		double zStep = (height / layers);

		// Print layers (top to bottom)
		for (int layer = 0; layer <= layers; layer++) {

			// Calculate values that change per layer
			double ratio = ratioBottom + (ratioStep * layer);
			double radiusInside = curveInside.getRadius(height - (zStep * layer));
			double radiusOutside = curveOutside.getRadius(height - (zStep * layer));
			double ribWidthInside = ribWidthInsideBottom + (ribWidthInsideStep * layer);
			double ribWidthOutside = ribWidthOutsideBottom + (ribWidthOutsideStep * layer);
			double rotateInside = rotateInsideBottom + (rotateInsideStep * layer);
			double rotateOutside = rotateOutsideBottom + (rotateOutsideStep * layer);
			double posX = xBottom + (xStep * layer);
			double posY = yBottom + (yStep * layer);
			double posZ = (zStep * layer);

			// Draw one layer
			drawLayer(path, degrees, posX, posY, posZ, radiusInside, radiusOutside, ribCount, ribWidthInside, ribWidthOutside, rotateInside, rotateOutside, ratio, layer);
		}
	}


	/**
	 * Draw one layer
	 */
	static private void drawLayer(List<ShapePathPoint> path, double degrees, double posX, double posY, double posZ, double radiusInside, double radiusOutside, double ribCount, double ribWidthInside, double ribWidthOutside, double rotateInside, double rotateOutside, double ratio, int layer)
	{
		// Calculate ange per step
		double angleStep = -(degrees / ribCount);

		// Draw ribs
		for (int rib = 0; rib < ribCount; rib++) {

			// Calculate start and end angle for step
			double angleStart = (rib * angleStep) - 180;
			double angleEnd = ((rib + 1) * angleStep) - 180;

			// Check if this is the last segment to draw. We need this
			// since the last point of a segment is also the first point
			// of the new segment, therefore we can ignore that point
			// when we calculate the segments.
			boolean last = rib == (ribCount - 1);

			// Draw single segment
			drawStep(
				path,
				posX, posY, posZ,
				radiusInside, radiusOutside,
				ribWidthInside, ribWidthOutside,
				rotateInside, rotateOutside,
				ratio,
				angleStart,
				angleEnd,
				layer,
				last
			);
		}
	}


	/**
	 * Draw one step
	 */
	static private void drawStep(List<ShapePathPoint> path, double posX, double posY, double posZ, double radiusInside, double radiusOutside, double ribWidthInside, double ribWidthOutside, double rotateInside, double rotateOutside, double ratio, double angleStart, double angleEnd, int layer, boolean last)
	{
		// We draw the following segment on a circle:
		// Where the last point is also the starting point for the next segment.
		// Since these points are set on a circle the distance between point 2 and 6
		// is different between point 5 and 6. Therefore we calculate these point
		// slightly different to have straight ribs.
		//
		// 7
		// |
		// 6 --- 5
		//       |
		//       4
		//       |
		// 2 --- 3
		// |
		// 1

		// First the angle difference and middle
		double angleDiff = angleEnd - angleStart;
		double angleDiffHalf = angleDiff / 2.0;
		double angleCenter = angleStart + angleDiffHalf;

		// Calculate the difference between the inside and outside rib
		double angleRibInside = angleDiff * (ribWidthInside / 100);
		double angleRibInsideHalf = angleRibInside / 2.0;
		double angleRibOutside = angleDiff * (ribWidthOutside / 100); // divide by ratio
		double angleRibOutsideHalf = angleRibOutside / 2.0;

		// Calculate the start and end angles
		double angleRibInsideStart = angleCenter - angleRibInsideHalf;
		double angleRibInsideEnd = angleCenter + angleRibInsideHalf;
		double angleRibOutsideStart = angleCenter - angleRibOutsideHalf;
		double angleRibOutsideEnd = angleCenter + angleRibOutsideHalf;

		// Get the points (where rotation is added to the angle to have a nice twist)
		double x1 = Utils.getCircleX(posX, radiusInside, angleStart + rotateInside);
		double y1 = Utils.getCircleY(posY, radiusInside, angleStart + rotateInside);
		double x2 = Utils.getCircleX(posX, radiusInside, angleRibInsideStart + rotateInside);
		double y2 = Utils.getCircleY(posY, radiusInside, angleRibInsideStart + rotateInside);
		double x3 = Utils.getCircleX(posX, radiusOutside, angleRibOutsideStart + rotateOutside);
		double y3 = Utils.getCircleY(posY, radiusOutside, angleRibOutsideStart + rotateOutside);
		double x4 = Utils.getCircleX(posX, radiusOutside, angleCenter + rotateOutside);
		double y4 = Utils.getCircleY(posY, radiusOutside, angleCenter + rotateOutside);
		double x5 = Utils.getCircleX(posX, radiusOutside, angleRibOutsideEnd + rotateOutside);
		double y5 = Utils.getCircleY(posY, radiusOutside, angleRibOutsideEnd + rotateOutside);
		double x6 = Utils.getCircleX(posX, radiusInside, angleRibInsideEnd + rotateInside);
		double y6 = Utils.getCircleY(posY, radiusInside, angleRibInsideEnd + rotateInside);
		double x7 = Utils.getCircleX(posX, radiusInside, angleEnd + rotateInside);
		double y7 = Utils.getCircleY(posY, radiusInside, angleEnd + rotateInside);

		// Add the points
		path.add(new ShapePathPoint(x1, y1, posZ, layer));
		path.add(new ShapePathPoint(x2, y2, posZ, layer));
		path.add(new ShapePathPoint(x3, y3, posZ, layer));
		path.add(new ShapePathPoint(x4, y4, posZ, layer));
		path.add(new ShapePathPoint(x5, y5, posZ, layer));
		path.add(new ShapePathPoint(x6, y6, posZ, layer));

		// Only add the closing point on the final segment
		if (last) {
			path.add(new ShapePathPoint(x7, y7, posZ, layer));
		}
	}
}
