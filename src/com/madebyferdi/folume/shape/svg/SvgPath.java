package com.madebyferdi.folume.shape.svg;


import java.util.ArrayList;
import java.util.List;

final public class SvgPath
{

	// Properties
	private List<SvgPoint> points;


	public SvgPath()
	{
		points = new ArrayList<>();
	}


	/**
	 * Add point
	 *
	 * @param x: X coordinate
	 * @param y: Y coordinate
	 */
	public void add(double x, double y)
	{
		add(new SvgPoint(x, y));
	}


	/**
	 * Add point
	 *
	 * @param point: Point object
	 */
	public void add(SvgPoint point)
	{
		points.add(point);
	}


	/**
	 * Return points
	 */
	public List<SvgPoint> getPoints()
	{
		return points;
	}
}
