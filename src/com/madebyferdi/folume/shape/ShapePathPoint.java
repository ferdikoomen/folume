package com.madebyferdi.folume.shape;


final public class ShapePathPoint
{

	// Properties
	private double x;
	private double y;
	private double z;
	private double distance;
	private double filament;
	private double duration;
	private double totalDistance;
	private double totalFilament;
	private double totalDuration;
	private double speed;
	private int layer;


	public ShapePathPoint(double x, double y, double z, int layer)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.distance = 0;
		this.filament = 0;
		this.duration = 0;
		this.totalDistance = 0;
		this.totalFilament = 0;
		this.totalDuration = 0;
		this.speed = 0;
		this.layer = layer;
	}


	public ShapePathPoint(double x, double y, double z, double distance, double totalDistance, double filament, double totalFilament, double duration, double totalDuration, double speed, int layer)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.distance = distance;
		this.filament = filament;
		this.duration = duration;
		this.totalDistance = totalDistance;
		this.totalFilament = totalFilament;
		this.totalDuration = totalDuration;
		this.speed = speed;
		this.layer = layer;
	}


	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getZ()
	{
		return z;
	}

	public double getDistance()
	{
		return distance;
	}

	public double getFilament()
	{
		return filament;
	}

	public double getDuration()
	{
		return duration;
	}

	public double getTotalDistance()
	{
		return totalDistance;
	}

	public double getTotalFilament()
	{
		return totalFilament;
	}

	public double getTotalDuration()
	{
		return totalDuration;
	}

	public double getSpeed()
	{
		return speed;
	}

	public int getLayer()
	{
		return layer;
	}
}
