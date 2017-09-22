package com.madebyferdi.folume.utils;


import com.madebyferdi.folume.controlP5.*;
import com.madebyferdi.folume.settings.Style;
import controlP5.Chart;
import controlP5.ControlP5;


final public class Gui
{

	// Incremental ID for GUI elements
	static private int ID = 0;


	static public GuiKnob createKnob(ControlP5 cp5, boolean print, String label, int x, int y, double radius, int precision, double min, double max, double value)
	{
		GuiKnob knob = new GuiKnob(cp5, "instance" + String.valueOf(ID++));
		knob.setLabel(label);
		knob.setColorActive(print ? Style.PRINT_COLOR_ACTIVE.getArgb() : Style.DESIGN_COLOR_ACTIVE.getArgb());
		knob.setColorForeground(print ? Style.PRINT_COLOR_INACTIVE.getArgb() : Style.DESIGN_COLOR_INACTIVE.getArgb());
		knob.setColorBackground(print ? Style.PRINT_COLOR_FOREGROUND.getArgb() : Style.DESIGN_COLOR_FOREGROUND.getArgb());
		knob.setPosition(x, y);
		knob.setRadius((float) radius);
		knob.setDecimalPrecision(precision);
		knob.setRange((float) min, (float) max);
		knob.setValue((float) value);
		return knob;
	}


	static public GuiTextfield createField(ControlP5 cp5, boolean print, String label, int x, int y, int width, int height, double value)
	{
		GuiTextfield field = new GuiTextfield(cp5, "instance" + String.valueOf(ID++));
		field.setLabel(label);
		field.setColorActive(print ? Style.PRINT_COLOR_FOREGROUND.getArgb() : Style.DESIGN_COLOR_FOREGROUND.getArgb());
		field.setColorForeground(print ? Style.PRINT_COLOR_FOREGROUND.getArgb() : Style.DESIGN_COLOR_FOREGROUND.getArgb());
		field.setColorBackground(print ? Style.PRINT_COLOR_FOREGROUND.getArgb() : Style.DESIGN_COLOR_FOREGROUND.getArgb());
		field.setPosition(x, y);
		field.setSize(width, height);
		field.setAutoClear(false);
		field.setValue(String.valueOf(value));
		return field;
	}


	static public GuiToggle createToggle(ControlP5 cp5, boolean print, String label, int x, int y, int width, int height, boolean state)
	{
		GuiToggle toggle = new GuiToggle(cp5, "instance" + String.valueOf(ID++));
		toggle.setLabel(label);
		toggle.setColorActive(print ? Style.PRINT_COLOR_INACTIVE.getArgb() : Style.PRINT_COLOR_INACTIVE.getArgb());
		toggle.setColorForeground(print ? Style.PRINT_COLOR_INACTIVE.getArgb() : Style.DESIGN_COLOR_INACTIVE.getArgb());
		toggle.setColorBackground(print ? Style.PRINT_COLOR_FOREGROUND.getArgb() : Style.DESIGN_COLOR_FOREGROUND.getArgb());
		toggle.setPosition(x, y);
		toggle.setSize(width, height);
		toggle.setMode(ControlP5.SWITCH);
		toggle.setState(state);
		return toggle;
	}


	static public GuiButton createButton(ControlP5 cp5, boolean print, String label, int x, int y, int width, int height)
	{
		GuiButton button = new GuiButton(cp5, "instance" + String.valueOf(ID++));
		button.setLabel(label);
		button.setColorActive(print ? Style.PRINT_COLOR_ACTIVE.getArgb() : Style.DESIGN_COLOR_ACTIVE.getArgb());
		button.setColorForeground(print ? Style.PRINT_COLOR_INACTIVE.getArgb() : Style.DESIGN_COLOR_INACTIVE.getArgb());
		button.setColorBackground(print ? Style.PRINT_COLOR_FOREGROUND.getArgb() : Style.DESIGN_COLOR_FOREGROUND.getArgb());
		button.setPosition(x, y);
		button.setSize(width, height);
		return button;
	}


	static public GuiSlider2D createSlider2D(ControlP5 cp5, boolean print, String label, int x, int y, int width, int height, int precision, double minX, double minY, double maxX, double maxY, double valX, double valY)
	{
		GuiSlider2D slider = new GuiSlider2D(cp5, "instance" + String.valueOf(ID++));
		slider.setLabel(label);
		slider.setColorActive(print ? Style.PRINT_COLOR_ACTIVE.getArgb() : Style.DESIGN_COLOR_ACTIVE.getArgb());
		slider.setColorForeground(print ? Style.PRINT_COLOR_INACTIVE.getArgb() : Style.DESIGN_COLOR_INACTIVE.getArgb());
		slider.setColorBackground(print ? Style.PRINT_COLOR_FOREGROUND.getArgb() : Style.DESIGN_COLOR_FOREGROUND.getArgb());
		slider.setPosition(x, y);
		slider.setSize(width, height);
		slider.setDecimalPrecision(precision);
		slider.setMinMax((float) minX, (float) minY, (float) maxX, (float) maxY);
		slider.setValue((float) valX, (float) valY);
		return slider;
	}


	static public GuiSlider createSlider(ControlP5 cp5, boolean print, String label, int x, int y, int width, int height, int precision, double min, double max, double val)
	{
		GuiSlider slider = new GuiSlider(cp5, "instance" + String.valueOf(ID++));
		slider.setLabel(label);
		slider.setColorActive(print ? Style.PRINT_COLOR_ACTIVE.getArgb() : Style.DESIGN_COLOR_ACTIVE.getArgb());
		slider.setColorForeground(print ? Style.PRINT_COLOR_INACTIVE.getArgb() : Style.DESIGN_COLOR_INACTIVE.getArgb());
		slider.setColorBackground(print ? Style.PRINT_COLOR_FOREGROUND.getArgb() : Style.DESIGN_COLOR_FOREGROUND.getArgb());
		slider.setPosition(x, y);
		slider.setSize(width, height);
		slider.setDecimalPrecision(precision);
		slider.setRange((float) min, (float) max);
		slider.setValue((float) val);
		return slider;
	}


	static public GuiChart createChart(ControlP5 cp5, boolean print, String label, int x, int y, int width, int height, double min, double max)
	{
		GuiChart chart = new GuiChart(cp5, "instance" + String.valueOf(ID++));
		chart.setLabel(label);
		chart.setColorActive(print ? Style.PRINT_COLOR_ACTIVE.getArgb() : Style.DESIGN_COLOR_ACTIVE.getArgb());
		chart.setColorForeground(print ? Style.PRINT_COLOR_INACTIVE.getArgb() : Style.DESIGN_COLOR_INACTIVE.getArgb());
		chart.setColorBackground(print ? Style.PRINT_COLOR_FOREGROUND.getArgb() : Style.DESIGN_COLOR_FOREGROUND.getArgb());
		chart.setPosition(x, y);
		chart.setSize(width, height);
		chart.setView(Chart.LINE);
		chart.setStrokeWeight(1);
		chart.setRange((float) min, (float) max);
		return chart;
	}


	static public GuiDropdownList createDropdownList(ControlP5 cp5, boolean print, String text, int x, int y, int width, int height, int items)
	{
		GuiDropdownList list = new GuiDropdownList(cp5, "instance" + String.valueOf(ID++));
		list.setColorActive(print ? Style.PRINT_COLOR_ACTIVE.getArgb() : Style.DESIGN_COLOR_ACTIVE.getArgb());
		list.setColorForeground(print ? Style.PRINT_COLOR_INACTIVE.getArgb() : Style.DESIGN_COLOR_INACTIVE.getArgb());
		list.setColorBackground(print ? Style.PRINT_COLOR_FOREGROUND.getArgb() : Style.DESIGN_COLOR_FOREGROUND.getArgb());
		list.setPosition(x, y);
		list.setSize(width, height * items);
		list.setBarHeight(height);
		list.setItemHeight(height);
		list.getCaptionLabel().set(text);
		list.setOpen(false);
		return list;
	}


	static public GuiTextarea createTextarea(ControlP5 cp5, boolean print, String text, int x, int y, int width, int height)
	{
		GuiTextarea area = new GuiTextarea(cp5, "instance" + String.valueOf(ID++));
		area.setColorForeground(print ? Style.PRINT_COLOR_INACTIVE.getArgb() : Style.DESIGN_COLOR_INACTIVE.getArgb());
		area.setColorBackground(print ? Style.PRINT_COLOR_FOREGROUND.getArgb() : Style.DESIGN_COLOR_FOREGROUND.getArgb());
		area.setScrollActive(print ? Style.PRINT_COLOR_ACTIVE.getArgb() : Style.DESIGN_COLOR_ACTIVE.getArgb());
		area.setScrollForeground(print ? Style.PRINT_COLOR_INACTIVE.getArgb() : Style.DESIGN_COLOR_INACTIVE.getArgb());
		area.setScrollBackground(print ? Style.PRINT_COLOR_FOREGROUND.getArgb() : Style.DESIGN_COLOR_FOREGROUND.getArgb());
		area.setText(text);
		area.setPosition(x, y);
		area.setSize(width, height);
		return area;
	}


	static public GuiTextlabel createTextlabel(ControlP5 cp5, String text, int x, int y)
	{
		GuiTextlabel label = new GuiTextlabel(cp5, "instance" + String.valueOf(ID++));
		label.setText(text);
		label.setPosition(x, y);
		return label;
	}
}
