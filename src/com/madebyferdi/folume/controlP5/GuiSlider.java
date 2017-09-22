package com.madebyferdi.folume.controlP5;


import com.madebyferdi.folume.utils.Listener;
import com.madebyferdi.folume.utils.Utils;
import controlP5.ControlP5;
import controlP5.Slider;


final public class GuiSlider extends Slider
{
	public GuiSlider(ControlP5 cp5, String id)
	{
		super(cp5, id);
	}


	/**
	 * Set value for double (just a cast)
	 *
	 * @param value: Double value
	 */
	public Slider setValue(double value)
	{
		return setValue((float) value);
	}


	/**
	 * Get value as string (with correct decimal rounding)
	 */
	public String getString()
	{
		return Utils.getString(getValue(), getDecimalPrecision());
	}


	/**
	 * Get value as double (with correct decimal rounding)
	 */
	public double getDouble()
	{
		return Double.parseDouble(getString());
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
