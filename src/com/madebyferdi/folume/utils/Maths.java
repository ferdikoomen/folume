package com.madebyferdi.folume.utils;


final public class Maths
{

	// Constants
	final static public double E = 2.7182818284590452354;
	final static public double PI = 3.14159265358979323846;
	final static public double DEG2RAD = PI / 180.0;
	final static public double RAD2DED = 180.0 / PI;


	public static double abs(double n)
	{
		return n < 0.0F ? -n : n;
	}

	public static int abs(int n)
	{
		return n < 0 ? -n : n;
	}

	public static double sq(double n)
	{
		return n * n;
	}

	public static double sqrt(double n)
	{
		return Math.sqrt(n);
	}

	public static double pow(double n, double e)
	{
		return Math.pow(n, e);
	}

	public static int max(int a, int b)
	{
		return a > b ? a : b;
	}

	public static double max(double a, double b)
	{
		return a > b ? a : b;
	}

	public static int min(int a, int b)
	{
		return a < b ? a : b;
	}

	public static double min(double a, double b)
	{
		return a < b ? a : b;
	}

	public static double sin(double angle)
	{
		return Math.sin(angle);
	}

	public static double cos(double angle)
	{
		return Math.cos(angle);
	}

	public static double tan(double angle)
	{
		return Math.tan(angle);
	}

	public static double asin(double value)
	{
		return Math.asin(value);
	}

	public static double acos(double value)
	{
		return Math.acos(value);
	}

	public static double atan(double value)
	{
		return Math.atan(value);
	}

	public static double atan2(double y, double x)
	{
		return Math.atan2(y, x);
	}

	public static int ceil(double n)
	{
		return (int) Math.ceil(n);
	}

	public static int floor(double n)
	{
		return (int) Math.floor(n);
	}

	public static int round(double n)
	{
		return (int) Math.round(n);
	}

	public static double dist(double x1, double y1, double x2, double y2)
	{
		return sqrt(sq(x2 - x1) + sq(y2 - y1));
	}

	public static double dist(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		return sqrt(sq(x2 - x1) + sq(y2 - y1) + sq(z2 - z1));
	}

	public static double degrees(double radians)
	{
		return radians * RAD2DED;
	}

	public static double radians(double degrees)
	{
		return degrees * DEG2RAD;
	}
}
