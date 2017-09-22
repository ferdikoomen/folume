package com.madebyferdi.folume.utils;


final public class Color
{

	// Properties
	private int argb;
	private int alpha;
	private int red;
	private int green;
	private int blue;


	public Color(int argb)
	{
		this.argb = argb;
		this.alpha = argb >> 24;
		this.red = argb >> 16 & 0xFF;
		this.green = argb >> 8 & 0xFF;
		this.blue = argb & 0xFF;
	}


	public int getArgb()
	{
		return argb;
	}

	public int getAlpha()
	{
		return alpha;
	}

	public int getRed()
	{
		return red;
	}

	public int getGreen()
	{
		return green;
	}

	public int getBlue()
	{
		return blue;
	}
}
