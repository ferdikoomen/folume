package com.madebyferdi.folume.printer.view.graphs;


import com.madebyferdi.folume.controlP5.GuiChart;
import com.madebyferdi.folume.controlP5.GuiSlider;
import com.madebyferdi.folume.settings.Style;
import com.madebyferdi.folume.utils.Gui;
import com.madebyferdi.folume.utils.Utils;
import controlP5.ControlP5;


final public class PrinterGraphChart
{

	// Size
	final static private int WIDTH = 150;
	final static private int HEIGHT = 60;


	// Properties
	private int offsetX;
	private int offsetY;
	private String label;
	private GuiChart chartTime;
	private GuiChart chartDistance;
	private GuiSlider sliderTime;
	private GuiSlider sliderDistance;
	private float[] dataTime;
	private float[] dataDistance;


	public PrinterGraphChart(ControlP5 cp5, int offsetX, int offsetY, String label)
	{
		// Store references
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.label = label;

		// Create interface elements
		chartTime = Gui.createChart(cp5, true, label + " TIME", offsetX, offsetY, WIDTH, HEIGHT, 0, HEIGHT);
		chartDistance = Gui.createChart(cp5, true, label + " DIST", offsetX, offsetY + 80, WIDTH, HEIGHT, 0, HEIGHT);
		sliderTime = Gui.createSlider(cp5, true, "MS", offsetX + WIDTH + 5, offsetY, 15, HEIGHT, 0, 1, 1000, 100);
		sliderDistance = Gui.createSlider(cp5, true, "MM", offsetX + WIDTH + 5, offsetY + 80, 15, HEIGHT, 0, 1, 50, 10);

		// Chart model
		dataTime = new float[WIDTH];
		dataDistance = new float[WIDTH];
		chartTime.addDataSet("model");
		chartDistance.addDataSet("model");

		// Set colors
		chartTime.setColors("model", Style.PRINT_COLOR_INACTIVE.getArgb());
		chartDistance.setColors("model", Style.PRINT_COLOR_INACTIVE.getArgb());
	}


	/**
	 * Getters
	 */
	public GuiChart getChartTime()
	{
		return chartTime;
	}

	public GuiChart getChartDistance()
	{
		return chartDistance;
	}

	public GuiSlider getSliderTime()
	{
		return sliderTime;
	}

	public GuiSlider getSliderDistance()
	{
		return sliderDistance;
	}


	/**
	 * Update chart
	 *
	 * @param feedrate:     Current feedrate (mm/s)
	 * @param acceleration: Current acceleration (mm/s2)
	 * @param jerk:         Current jerk (mm/s)
	 * @param max:          Maximum speed (mm/s)
	 */
	public void update(double feedrate, double acceleration, double jerk, double max)
	{
		// Grab settings for horizontal axes
		double accelerationTime = Utils.accelerationTime(jerk, feedrate, acceleration);
		double accelerationDistance = Utils.accelerationDistance(jerk, feedrate, accelerationTime);

		// Grab scales
		double scaleTime = sliderTime.getDouble();
		double scaleDistance = sliderDistance.getDouble();

		for (int i = 0; i < WIDTH; i++) {

			// Calculate the time value for this step
			double time = Utils.map(i, 0, WIDTH - 1, 0, scaleTime / 1000);

			// Calculate the distance value for this step
			double distance = Utils.map(i, 0, WIDTH - 1, 0, scaleDistance);

			// Calculate the velocity position for the time (using a simple quad graph)
			double velocity = Utils.quad(time, jerk, feedrate, accelerationTime);

			// Calculate the velocity position for the duration (using a simple quad graph)
			double position = Utils.quad(distance, jerk, feedrate, accelerationDistance);

			// Add values to model
			dataTime[i] = (float) Utils.map(velocity, 0, max, 0, HEIGHT - 1);
			dataDistance[i] = (float) Utils.map(position, 0, feedrate, 0, HEIGHT - 1);
		}

		// Set labels
		chartTime.setLabel(label + " TIME (" + Utils.getString(accelerationTime * 1000, 0) + " MS > " + Utils.getString(feedrate, 0) + " MM/S)");
		chartDistance.setLabel(label + " DIST (" + Utils.getString(accelerationDistance, 1) + " MM > " + Utils.getString(feedrate, 0) + " MM/S)");

		// Set model in chart
		chartTime.setData("model", dataTime);
		chartDistance.setData("model", dataDistance);
	}

}
