package com.madebyferdi.folume.printer.view.graphs;


import controlP5.ControlP5;


final public class PrinterGraphAcceleration
{


	// Graphs
	private PrinterGraphChart accelerationX;
	private PrinterGraphChart accelerationY;
	private PrinterGraphChart accelerationZ;
	private PrinterGraphChart accelerationE;


	public PrinterGraphAcceleration(ControlP5 cp5, int offsetX, int offsetY)
	{
		accelerationX = new PrinterGraphChart(cp5, offsetX, offsetY, "ACCEL X");
		accelerationY = new PrinterGraphChart(cp5, offsetX + 200, offsetY, "ACCEL Y");
		accelerationZ = new PrinterGraphChart(cp5, offsetX + 400, offsetY, "ACCEL Z");
		accelerationE = new PrinterGraphChart(cp5, offsetX + 600, offsetY, "ACCEL E");
	}


	public PrinterGraphChart getAccelerationX()
	{
		return accelerationX;
	}

	public PrinterGraphChart getAccelerationY()
	{
		return accelerationY;
	}

	public PrinterGraphChart getAccelerationZ()
	{
		return accelerationZ;
	}

	public PrinterGraphChart getAccelerationE()
	{
		return accelerationE;
	}
}
