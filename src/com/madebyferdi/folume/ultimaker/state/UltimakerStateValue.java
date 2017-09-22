package com.madebyferdi.folume.ultimaker.state;


final public class UltimakerStateValue
{

	// Properties
	private String name;
	private double initial;
	private double target;
	private double targetSend;
	private double current;


	/**
	 * Creat new model value
	 *
	 * @param name:  Name of the value
	 * @param value: Default value
	 */
	public UltimakerStateValue(String name, double value)
	{
		this.name = name;
		this.initial = value;
		this.target = value;
		this.targetSend = value;
		this.current = value;
	}


	/**
	 * Print values
	 */
	public String toString()
	{
		return "; \"" + name + "\"" +
			" current: " + String.valueOf(current) +
			" target: " + String.valueOf(target) +
			System.lineSeparator();
	}


	/**
	 * Check if the values had changed since the last time we send it
	 */
	public boolean changed()
	{
		return target != targetSend;
	}


	/**
	 * Sync the target values
	 */
	public void sync()
	{
		this.targetSend = target;
	}


	/**
	 * Reset values to defaults
	 */
	public void reset()
	{
		this.target = initial;
		this.targetSend = initial;
		this.current = initial;
	}


	/**
	 * Getters
	 */
	public double getTarget()
	{
		return target;
	}

	public double getCurrent()
	{
		return current;
	}


	/**
	 * Setters
	 */
	public void setTarget(double value)
	{
		this.target = value;
	}

	public void setCurrent(double value)
	{
		this.current = value;
	}
}
