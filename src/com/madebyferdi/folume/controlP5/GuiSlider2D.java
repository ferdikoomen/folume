package com.madebyferdi.folume.controlP5;


import com.madebyferdi.folume.utils.Listener;
import com.madebyferdi.folume.utils.Utils;
import controlP5.ControlP5;
import controlP5.Slider2D;


final public class GuiSlider2D extends Slider2D
{

	public GuiSlider2D(ControlP5 cp5, String id)
	{
		super(cp5, id);
	}


	/**
	 * Set value for double (just a cast)
	 *
	 * @param x: Double value
	 * @param y: Double value
	 */
	public Slider2D setValue(double x, double y)
	{
		return setValue((float) x, (float) y);
	}


	/**
	 * Get X value as string (with correct decimal rounding)
	 */
	public String getStringX()
	{
		return Utils.getString(getArrayValue()[0], getDecimalPrecision());
	}


	/**
	 * Get Y value as string (with correct decimal rounding)
	 */
	public String getStringY()
	{
		return Utils.getString(getArrayValue()[1], getDecimalPrecision());
	}


	/**
	 * Get X value as double (with correct decimal rounding)
	 */
	public double getDoubleX()
	{
		return Double.parseDouble(getStringX());
	}


	/**
	 * Get Y value as double (with correct decimal rounding)
	 */
	public double getDoubleY()
	{
		return Double.parseDouble(getStringY());
	}


	/**
	 * Change listener
	 *
	 * @param listener: Method to call when value is changed
	 */
	public void onChange(Listener listener)
	{
		this.cp5.addCallback(event -> {
			if (event.getAction() == ControlP5.ACTION_BROADCAST) {
				listener.callback();
			}
		}, this);
	}
}
