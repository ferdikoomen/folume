package com.madebyferdi.folume.utils;


final public class Cube
{

	// Properties
	private double minX;
	private double minY;
	private double minZ;
	private double maxX;
	private double maxY;
	private double maxZ;
	private double width;
	private double depth;
	private double height;
	private double centerX;
	private double centerY;
	private double centerZ;


	public Cube(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
	{
		// Save properties
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;

		// Calculate volume
		this.width = maxX - minX;
		this.depth = maxY - minY;
		this.height = maxZ - minZ;

		// Calculate center
		this.centerX = minX + (this.width / 2);
		this.centerY = minY + (this.depth / 2);
		this.centerZ = minZ + (this.height / 2);
	}


	/**
	 * Getters
	 */
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

}
