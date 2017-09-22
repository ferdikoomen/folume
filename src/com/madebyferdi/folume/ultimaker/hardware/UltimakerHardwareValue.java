package com.madebyferdi.folume.ultimaker.hardware;

final public class UltimakerHardwareValue
{

	// Properties
	private String name;
	private double min;
	private double max;
	private double value;


	public UltimakerHardwareValue(String name, double min, double max, double value)
	{
		this.name = name;
		this.min = min;
		this.max = max;
		this.value = value;
	}

	public UltimakerHardwareValue(String name, double min, double max)
	{
		this.name = name;
		this.min = min;
		this.max = max;
		this.value = 0;
	}

	public UltimakerHardwareValue(String name, double value)
	{
		this.name = name;
		this.min = 0;
		this.max = 0;
		this.value = value;
	}


	/**
	 * Print values
	 */
	public String toString()
	{
		return "; \"" + name + "\"" +
			" min: " + String.valueOf(min) +
			" max: " + String.valueOf(max) +
			" value: " + String.valueOf(value) +
			System.lineSeparator();
	}


	/**
	 * Getters
	 */
	public double getMin()
	{
		return min;
	}

	public double getMax()
	{
		return max;
	}

	public double getValue()
	{
		return value;
	}
}
