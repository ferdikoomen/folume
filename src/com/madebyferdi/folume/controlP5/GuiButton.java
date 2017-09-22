package com.madebyferdi.folume.controlP5;


import com.madebyferdi.folume.utils.Listener;
import controlP5.Button;
import controlP5.ControlP5;


final public class GuiButton extends Button
{

	public GuiButton(ControlP5 cp5, String id)
	{
		super(cp5, id);
	}


	/**
	 * Click listener
	 *
	 * @param listener: Method to call when button is clicked
	 */
	public void onClick(final Listener listener)
	{
		this.cp5.addCallback(event -> {
			if (event.getAction() == ControlP5.ACTION_RELEASE) {
				listener.callback();
			}
		}, this);
	}
}
