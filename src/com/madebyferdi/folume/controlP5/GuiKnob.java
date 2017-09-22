package com.madebyferdi.folume.controlP5;


import com.madebyferdi.folume.utils.Listener;
import com.madebyferdi.folume.utils.Utils;
import controlP5.ControlP5;
import controlP5.Knob;


final public class GuiKnob extends Knob
{

	public GuiKnob(ControlP5 cp5, String id)
	{
		super(cp5, id);
	}


	/**
	 * Better way to calculate label value!
	 *
	 * @param value:     Value to set
	 * @param precision: precision to use
	 */
	@Override
	protected String adjustValue(float value, int precision)
	{
		return Utils.getString(value, precision);
	}


	/**
	 * Set value for double (just a cast)
	 *
	 * @param value: Double value
	 */
	public Knob setValue(double value)
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


	/**
	 * Link knob with textfield (for easy input)
	 *
	 * @param field: Field to link
	 */
	public void linkField(GuiTextfield field)
	{
		// Update field value
		field.setValue(getString());

		// Listen to changes from knob and update field
		onChange(Double -> {
			field.setValue(getString());
		});

		// Listen to changes from field and update knob
		field.onChange(value -> {
			setValue(Utils.getDouble(field.getText()));
		});
	}
}
