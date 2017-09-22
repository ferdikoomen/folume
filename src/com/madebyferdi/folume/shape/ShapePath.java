package com.madebyferdi.folume.shape;


import com.madebyferdi.folume.utils.Maths;

import java.util.ArrayList;
import java.util.List;


final public class ShapePath
{

	// Properties
	private List<ShapePathPoint> points;
	private double minX = 10000;
	private double minY = 10000;
	private double minZ = 10000;
	private double maxX = -10000;
	private double maxY = -10000;
	private double maxZ = -10000;
	private double width = 0;
	private double depth = 0;
	private double height = 0;
	private double centerX = 0;
	private double centerY = 0;
	private double centerZ = 0;


	public ShapePath()
	{
		points = new ArrayList<>();
	}


	/**
	 * Getters
	 */
	public List<ShapePathPoint> getPoints()
	{
		return points;
	}

	public double getMinX()
	{
		return minX;
	}

	public double getMinY()
	{
		return minY;
	}

	public double getMinZ()
	{
		return minZ;
	}

	public double getMaxX()
	{
		return maxX;
	}

	public double getMaxY()
	{
		return maxY;
	}

	public double getMaxZ()
	{
		return maxZ;
	}

	public double getWidth()
	{
		return width;
	}

	public double getDepth()
	{
		return depth;
	}

	public double getHeight()
	{
		return height;
	}

	public double getCenterX()
	{
		return centerX;
	}

	public double getCenterY()
	{
		return centerY;
	}

	public double getCenterZ()
	{
		return centerZ;
	}


	/**
	 * Add segment
	 *
	 * @param x:     Start position X
	 * @param y:     Start position Y
	 * @param z:     Start position Z
	 * @param layer: Current layer
	 */
	public void add(double x, double y, double z, int layer)
	{
		add(x, y, z, 0, 0, 0, 0, 0, 0, 0, layer);
	}


	/**
	 * Add segment
	 *
	 * @param x:             Start position X
	 * @param y:             Start position Y
	 * @param z:             Start position Z
	 * @param distance:      Distance from last point
	 * @param totalDistance: Total distance until now
	 * @param filament:      Filament needed from last point
	 * @param totalFilament: Total filament needed until now
	 * @param duration:      Distance from last point
	 * @param totalDuration: Total duration until now
	 * @param speed:         Speed from last point
	 * @param layer:         Current layer
	 */
	public void add(double x, double y, double z, double distance, double totalDistance, double filament, double totalFilament, double duration, double totalDuration, double speed, int layer)
	{
		// Check if any values are outside of the known bounding box
		if (x < minX) minX = x;
		if (y < minY) minY = y;
		if (z < minZ) minZ = z;
		if (x > maxX) maxX = x;
		if (y > maxY) maxY = y;
		if (z > maxZ) maxZ = z;

		// Calculate volume
		width = Maths.abs(maxX - minX);
		depth = Maths.abs(maxY - minY);
		height = Maths.abs(maxZ - minZ);

		// Calculate volume
		centerX = minX + (width / 2);
		centerY = minY + (depth / 2);
		centerZ = minZ + (height / 2);

		// Add a new segment to the shape data
		points.add(new ShapePathPoint(x, y, z, distance, totalDistance, filament, totalFilament, duration, totalDuration, speed, layer));
	}


	/**
	 * Clear model
	 */
	public void clear()
	{
		points.clear();
		minX = 10000;
		minY = 10000;
		minZ = 10000;
		maxX = -10000;
		maxY = -10000;
		maxZ = -10000;
		width = 0;
		depth = 0;
		height = 0;
		centerX = 0;
		centerY = 0;
		centerZ = 0;
	}

}
