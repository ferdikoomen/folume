package com.madebyferdi.folume.utils;

final public class Filament
{

	/**
	 * Return distance of filament needed for certain distance
	 *
	 * @param distance:         Distance to print
	 * @param filamentDiameter: Filament diameter
	 * @param nozzleDiameter:   Nozzle diameter
	 * @param layerHeight:      Layer height
	 * @param layerWidth:       Layer width
	 */
	static public double getDistance(double distance, double filamentDiameter, double nozzleDiameter, double layerHeight, double layerWidth)
	{
		// Calculate how much filament is needed for 1 mm of the printing layer
		// First we calculate the volume of the filament and nozzle
		double volumeFilament = Maths.PI * Maths.pow((filamentDiameter / 2), 2);
		double volumeNozzle = Maths.PI * Maths.pow((nozzleDiameter / 2), 2);
		double volumeRatio = volumeFilament / volumeNozzle;

		// TODO: Add layer height and layer width into the calculation
		return (distance / volumeRatio) * layerWidth;
	}

}
