package com.madebyferdi.folume.controlP5;


import com.madebyferdi.folume.utils.Listener;
import controlP5.ControlP5;
import controlP5.Textfield;


final public class GuiTextfield extends Textfield
{

	public GuiTextfield(ControlP5 cp5, String id)
	{
		super(cp5, id);
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
