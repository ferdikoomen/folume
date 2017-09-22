package com.madebyferdi.folume.utils;


import processing.core.PConstants;
import processing.core.PGraphics;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


final public class Utils
{

	// Reusable format
	final static private DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
	final static private DecimalFormat decimalFormat = new DecimalFormat();


	/**
	 * Get float as string with decimal precision
	 *
	 * @param value:     Number value
	 * @param precision: Decimal precision
	 */
	static public String getString(float value, int precision)
	{
		return getString((double) value, precision);
	}


	/**
	 * Get double as string with decimal precision
	 *
	 * @param value:     Number value
	 * @param precision: Decimal precision
	 */
	static public String getString(double value, int precision)
	{
		decimalFormatSymbols.setDecimalSeparator('.');
		decimalFormat.setGroupingUsed(false);
		decimalFormat.setMinimumFractionDigits(precision);
		decimalFormat.setMaximumFractionDigits(precision);
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
		return decimalFormat.format(value);
	}


	/**
	 * Get string as double
	 *
	 * @param value: String value
	 */
	static public double getDouble(String value)
	{
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}


	/**
	 * Limit string
	 *
	 * @param s:      String to cap
	 * @param length: Max length
	 * @return
	 */
	static public String limit(String s, int length)
	{
		if (s.length() > length) {
			s = s.substring(0, length) + "...";
		}
		return s;
	}


	/**
	 * Map value to range
	 *
	 * @param val:       Input value
	 * @param rangeMin:  Min input
	 * @param rangeMax:  Max input
	 * @param returnMin: Range min
	 * @param returnMax: Range max
	 */
	static public double map(double val, double rangeMin, double rangeMax, double returnMin, double returnMax)
	{
		double value = (((val - rangeMin) / (rangeMax - rangeMin)) * (returnMax - returnMin)) + returnMin;
		if (Double.isNaN(value) || Double.isInfinite(value)) {
			value = 0;
		}
		return value;
	}


	/**
	 * Get x position for angle in circle
	 *
	 * @param centerX: Center X
	 * @param radius:  Radius of circle
	 * @param angle:   Angle
	 */
	static public double getCircleX(double centerX, double radius, double angle)
	{
		return centerX + radius * Maths.cos(Maths.radians(angle));
	}


	/**
	 * Get y position for angle in circle
	 *
	 * @param centerY: Center Y
	 * @param radius:  Radius of circle
	 * @param angle:   Angle
	 */
	static public double getCircleY(double centerY, double radius, double angle)
	{
		return centerY + radius * Maths.sin(Maths.radians(angle));
	}


	/**
	 * Get time it will take to reach full acceleration
	 *
	 * @param vi: Velocity start (mm/s)
	 * @param vf: Velocity end (mm/s)
	 * @param a:  Acceleration (mm/s2)
	 */
	static public double accelerationTime(double vi, double vf, double a)
	{
		return (vf - vi) / a;
	}


	/**
	 * Return distance it will take to reach full acceleration
	 * Note that this method uses linear acceleration...
	 *
	 * @param vi: Velocity start (mm/s)
	 * @param vf: Velocity end (mm/s)
	 * @param t:  Time it will take to reach full acceleration
	 */
	static public double accelerationDistance(double vi, double vf, double t)
	{
		return ((vi + vf) / 2) * t;
	}


	/**
	 * Return quad curve based on input
	 *
	 * @param time:     Current time in seconds
	 * @param start:    Start value
	 * @param end:      End value
	 * @param duration: Duration in seconds
	 */
	static public double quad(double time, double start, double end, double duration)
	{
		if (time >= duration) {
			return end;
		}
		if ((time /= duration * 0.5f) < 1) {
			return (end - start) * 0.5f * time * time + start;
		}
		return -(end - start) * 0.5f * ((--time) * (time - 2) - 1) + start;
	}


	/**
	 * Render cross
	 *
	 * @param pg: Graphics layer
	 */
	static public void renderCross(PGraphics pg)
	{
		// Center
		pg.fill(255);
		pg.noStroke();
		pg.sphere(4);

		// X > RED
		pg.fill(255, 0, 0);
		pg.stroke(255, 0, 0);
		pg.strokeWeight(1);
		pg.beginShape(PConstants.LINES);
		pg.vertex(0, 0, 0);
		pg.vertex(100, 0, 0);
		pg.endShape();
		pg.translate(100, 0, 0);
		pg.sphere(4);
		pg.translate(-100, 0, 0);

		// Y > GREEN
		pg.fill(0, 255, 0);
		pg.stroke(0, 255, 0);
		pg.strokeWeight(1);
		pg.beginShape(PConstants.LINES);
		pg.vertex(0, 0, 0);
		pg.vertex(0, 100, 0);
		pg.endShape();
		pg.translate(0, 100, 0);
		pg.sphere(4);
		pg.translate(0, -100, 0);

		// Z > BLUE
		pg.fill(0, 0, 255);
		pg.stroke(0, 0, 255);
		pg.strokeWeight(1);
		pg.beginShape(PConstants.LINES);
		pg.vertex(0, 0, 0);
		pg.vertex(0, 0, 100);
		pg.endShape();
		pg.translate(0, 0, 100);
		pg.sphere(4);
		pg.translate(0, 0, -100);
	}

}
