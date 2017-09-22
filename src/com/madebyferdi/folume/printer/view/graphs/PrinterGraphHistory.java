package com.madebyferdi.folume.printer.view.graphs;


import com.madebyferdi.folume.controlP5.GuiTextarea;
import com.madebyferdi.folume.controlP5.GuiTextlabel;
import com.madebyferdi.folume.utils.Gui;
import com.madebyferdi.folume.utils.Maths;
import com.madebyferdi.folume.utils.Utils;
import controlP5.ControlP5;

import java.util.List;


final public class PrinterGraphHistory
{

	// Constants
	final static private int WIDTH = 430;
	final static private int HEIGHT = 380;


	// Properties
	private int offsetX;
	private int offsetY;


	// Interface elements
	private GuiTextarea textarea;
	private GuiTextlabel textlabel;


	public PrinterGraphHistory(ControlP5 cp5, int offsetX, int offsetY)
	{
		// Store references
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		// Create label
		textarea = Gui.createTextarea(cp5, true, "", offsetX, offsetY, WIDTH, HEIGHT);
		textlabel = Gui.createTextlabel(cp5, "GCODE", offsetX, offsetY + HEIGHT + 4);
	}


	/**
	 * Reset the field
	 */
	public void reset()
	{
		// Clear all data
		textarea.clear();
	}


	/**
	 * Update text area
	 *
	 * @param data: Lines to add
	 */
	public void update(List<String> data)
	{
		// Clear old data
		textarea.clear();

		// Append lines (42 is max)
		int indexStart = Maths.max(0, data.size() - 42);
		int indexEnd = data.size();

		for (int i = indexStart; i < indexEnd; i++) {
			textarea.append(Utils.limit(data.get(i), 90));
			textarea.append(System.lineSeparator());
		}
	}
}
