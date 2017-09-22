package com.madebyferdi.folume.controlP5;


import controlP5.ControlP5;
import controlP5.Textlabel;


final public class GuiTextlabel extends Textlabel
{

	public GuiTextlabel(ControlP5 cp5, String id)
	{
		super(cp5, cp5.controlWindow.getCurrentTab(), id, "", 0, 0);
	}

}
