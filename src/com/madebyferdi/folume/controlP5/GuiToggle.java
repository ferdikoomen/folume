package com.madebyferdi.folume.controlP5;


import com.madebyferdi.folume.utils.Listener;
import controlP5.ControlP5;
import controlP5.Toggle;


final public class GuiToggle extends Toggle
{

	public GuiToggle(ControlP5 cp5, String id)
	{
		super(cp5, id);
	}


	/**
	 * Clicked listener
	 *
	 * @param on: Method to call when switched
	 */
	public void onSwitch(Listener listener)
	{
		this.cp5.addCallback(event -> {
			if (event.getAction() == ControlP5.ACTION_RELEASE) {
				listener.callback();
			}
		}, this);
	}
}
