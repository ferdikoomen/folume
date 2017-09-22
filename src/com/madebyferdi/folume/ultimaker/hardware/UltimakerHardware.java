package com.madebyferdi.folume.ultimaker.hardware;


public class UltimakerHardware
{

	private UltimakerHardwareValue buildVolumeX;
	private UltimakerHardwareValue buildVolumeY;
	private UltimakerHardwareValue buildVolumeZ;
	private UltimakerHardwareValue printVolumeX;
	private UltimakerHardwareValue printVolumeY;
	private UltimakerHardwareValue printVolumeZ;
	private UltimakerHardwareValue extruderTemperature;
	private UltimakerHardwareValue heatbedTemperature;
	private UltimakerHardwareValue ledBrightness;
	private UltimakerHardwareValue fanSpeed;
	private UltimakerHardwareValue speedTravel;
	private UltimakerHardwareValue speedPrint;
	private UltimakerHardwareValue speedZ;
	private UltimakerHardwareValue filamentDiameter;
	private UltimakerHardwareValue nozzleDiameter;
	private UltimakerHardwareValue layerHeight;
	private UltimakerHardwareValue layerWidth;
	private UltimakerHardwareValue layerBottom;
	private UltimakerHardwareValue retractLength;
	private UltimakerHardwareValue retractSpeed;
	private UltimakerHardwareValue retractZ;
	private UltimakerHardwareValue stepX;
	private UltimakerHardwareValue stepY;
	private UltimakerHardwareValue stepZ;
	private UltimakerHardwareValue stepE;
	private UltimakerHardwareValue feedrateX;
	private UltimakerHardwareValue feedrateY;
	private UltimakerHardwareValue feedrateZ;
	private UltimakerHardwareValue feedrateE;
	private UltimakerHardwareValue accelerationX;
	private UltimakerHardwareValue accelerationY;
	private UltimakerHardwareValue accelerationZ;
	private UltimakerHardwareValue accelerationE;
	private UltimakerHardwareValue jerkXY;
	private UltimakerHardwareValue jerkZ;
	private UltimakerHardwareValue jerkE;
	private UltimakerHardwareValue defaultAcceleration;
	private UltimakerHardwareValue defaultAccelerationRetract;
	private UltimakerHardwareValue homeX;
	private UltimakerHardwareValue homeY;
	private UltimakerHardwareValue homeZ;


	public UltimakerHardware()
	{
		buildVolumeX = new UltimakerHardwareValue("buildVolumeX", 0, 230); // Width
		buildVolumeY = new UltimakerHardwareValue("buildVolumeY", 0, 230); // Depth
		buildVolumeZ = new UltimakerHardwareValue("buildVolumeZ", 0, 220); // Height
		printVolumeX = new UltimakerHardwareValue("printVolumeX", 10, 220); // Width
		printVolumeY = new UltimakerHardwareValue("printVolumeY", 10, 220); // Depth
		printVolumeZ = new UltimakerHardwareValue("printVolumeZ", 0, 210); // Height
		extruderTemperature = new UltimakerHardwareValue("extruderTemperature", 0, 275, 250);
		heatbedTemperature = new UltimakerHardwareValue("heatbedTemperature", 0, 115, 50);
		ledBrightness = new UltimakerHardwareValue("ledBrightness", 0, 255, 255);
		fanSpeed = new UltimakerHardwareValue("fanSpeed", 0, 255, 0);
		speedTravel = new UltimakerHardwareValue("speedTravel", 5, 300, 300);
		speedPrint = new UltimakerHardwareValue("speedPrint", 5, 50, 50);
		speedZ = new UltimakerHardwareValue("speedZ", 5, 40, 40);
		filamentDiameter = new UltimakerHardwareValue("filamentDiameter", 2.0, 3.0, 2.85);
		nozzleDiameter = new UltimakerHardwareValue("nozzleDiameter", 0.1, 1.0, 0.8);
		layerHeight = new UltimakerHardwareValue("layerHeight", 0.1, 1.0, 0.6);
		layerWidth = new UltimakerHardwareValue("layerWidth", 0.1, 4.0, 1.5);
		layerBottom = new UltimakerHardwareValue("layerBottom", 0, 200, 50);
		retractLength = new UltimakerHardwareValue("retractLength", 0, 10, 4.5);
		retractSpeed = new UltimakerHardwareValue("retractSpeed", 0, 45, 25);
		retractZ = new UltimakerHardwareValue("retractZ", 0, 2, 0.8);
		stepX = new UltimakerHardwareValue("stepX", 70, 90, 80);
		stepY = new UltimakerHardwareValue("stepY", 70, 90, 80);
		stepZ = new UltimakerHardwareValue("stepZ", 190, 210, 200);
		stepE = new UltimakerHardwareValue("stepE", 272, 292, 282);
		feedrateX = new UltimakerHardwareValue("feedrateX", 5, 300, 300);
		feedrateY = new UltimakerHardwareValue("feedrateY", 5, 300, 300);
		feedrateZ = new UltimakerHardwareValue("feedrateZ", 5, 40, 40);
		feedrateE = new UltimakerHardwareValue("feedrateE", 5, 45, 45);
		accelerationX = new UltimakerHardwareValue("accelerationX", 5, 9000, 9000);
		accelerationY = new UltimakerHardwareValue("accelerationY", 5, 9000, 9000);
		accelerationZ = new UltimakerHardwareValue("accelerationZ", 5, 100, 100);
		accelerationE = new UltimakerHardwareValue("accelerationE", 5, 10000, 10000);
		jerkXY = new UltimakerHardwareValue("jerkXY", 0, 40, 20);
		jerkZ = new UltimakerHardwareValue("jerkZ", 0.0, 0.8, 0.4);
		jerkE = new UltimakerHardwareValue("jerkE", 0, 10, 5);
		defaultAcceleration = new UltimakerHardwareValue("defaultAcceleration", 3000);
		defaultAccelerationRetract = new UltimakerHardwareValue("defaultAccelerationRetract", 3000);
		homeX = new UltimakerHardwareValue("homeX", 0);
		homeY = new UltimakerHardwareValue("homeY", 230);
		homeZ = new UltimakerHardwareValue("homeZ", 210);
	}


	/**
	 * Print all values
	 */
	public String toString()
	{
		return "; ULTIMAKER HARDWARE:" +
			System.lineSeparator() +
			buildVolumeX.toString() +
			buildVolumeY.toString() +
			buildVolumeZ.toString() +
			printVolumeX.toString() +
			printVolumeY.toString() +
			printVolumeZ.toString() +
			extruderTemperature.toString() +
			heatbedTemperature.toString() +
			ledBrightness.toString() +
			fanSpeed.toString() +
			speedTravel.toString() +
			speedPrint.toString() +
			speedZ.toString() +
			filamentDiameter.toString() +
			nozzleDiameter.toString() +
			layerHeight.toString() +
			layerWidth.toString() +
			layerBottom.toString() +
			retractLength.toString() +
			retractSpeed.toString() +
			retractZ.toString() +
			stepX.toString() +
			stepY.toString() +
			stepZ.toString() +
			stepE.toString() +
			feedrateX.toString() +
			feedrateY.toString() +
			feedrateZ.toString() +
			feedrateE.toString() +
			accelerationX.toString() +
			accelerationY.toString() +
			accelerationZ.toString() +
			accelerationE.toString() +
			jerkXY.toString() +
			jerkZ.toString() +
			jerkE.toString() +
			defaultAcceleration.toString() +
			defaultAccelerationRetract.toString() +
			homeX.toString() +
			homeY.toString() +
			homeZ.toString() +
			System.lineSeparator();
	}


	public UltimakerHardwareValue getBuildVolumeX()
	{
		return buildVolumeX;
	}

	public UltimakerHardwareValue getBuildVolumeY()
	{
		return buildVolumeY;
	}

	public UltimakerHardwareValue getBuildVolumeZ()
	{
		return buildVolumeZ;
	}

	public UltimakerHardwareValue getPrintVolumeX()
	{
		return printVolumeX;
	}

	public UltimakerHardwareValue getPrintVolumeY()
	{
		return printVolumeY;
	}

	public UltimakerHardwareValue getPrintVolumeZ()
	{
		return printVolumeZ;
	}

	public UltimakerHardwareValue getExtruderTemperature()
	{
		return extruderTemperature;
	}

	public UltimakerHardwareValue getHeatbedTemperature()
	{
		return heatbedTemperature;
	}

	public UltimakerHardwareValue getLedBrightness()
	{
		return ledBrightness;
	}

	public UltimakerHardwareValue getFanSpeed()
	{
		return fanSpeed;
	}

	public UltimakerHardwareValue getSpeedTravel()
	{
		return speedTravel;
	}

	public UltimakerHardwareValue getSpeedPrint()
	{
		return speedPrint;
	}

	public UltimakerHardwareValue getSpeedZ()
	{
		return speedZ;
	}

	public UltimakerHardwareValue getFilamentDiameter()
	{
		return filamentDiameter;
	}

	public UltimakerHardwareValue getNozzleDiameter()
	{
		return nozzleDiameter;
	}

	public UltimakerHardwareValue getLayerHeight()
	{
		return layerHeight;
	}

	public UltimakerHardwareValue getLayerWidth()
	{
		return layerWidth;
	}

	public UltimakerHardwareValue getLayerBottom()
	{
		return layerBottom;
	}

	public UltimakerHardwareValue getRetractLength()
	{
		return retractLength;
	}

	public UltimakerHardwareValue getRetractSpeed()
	{
		return retractSpeed;
	}

	public UltimakerHardwareValue getRetractZ()
	{
		return retractZ;
	}

	public UltimakerHardwareValue getStepX()
	{
		return stepX;
	}

	public UltimakerHardwareValue getStepY()
	{
		return stepY;
	}

	public UltimakerHardwareValue getStepZ()
	{
		return stepZ;
	}

	public UltimakerHardwareValue getStepE()
	{
		return stepE;
	}

	public UltimakerHardwareValue getFeedrateX()
	{
		return feedrateX;
	}

	public UltimakerHardwareValue getFeedrateY()
	{
		return feedrateY;
	}

	public UltimakerHardwareValue getFeedrateZ()
	{
		return feedrateZ;
	}

	public UltimakerHardwareValue getFeedrateE()
	{
		return feedrateE;
	}

	public UltimakerHardwareValue getAccelerationX()
	{
		return accelerationX;
	}

	public UltimakerHardwareValue getAccelerationY()
	{
		return accelerationY;
	}

	public UltimakerHardwareValue getAccelerationZ()
	{
		return accelerationZ;
	}

	public UltimakerHardwareValue getAccelerationE()
	{
		return accelerationE;
	}

	public UltimakerHardwareValue getJerkXY()
	{
		return jerkXY;
	}

	public UltimakerHardwareValue getJerkZ()
	{
		return jerkZ;
	}

	public UltimakerHardwareValue getJerkE()
	{
		return jerkE;
	}

	public UltimakerHardwareValue getDefaultAcceleration()
	{
		return defaultAcceleration;
	}

	public UltimakerHardwareValue getDefaultAccelerationRetract()
	{
		return defaultAccelerationRetract;
	}

	public UltimakerHardwareValue getHomeX()
	{
		return homeX;
	}

	public UltimakerHardwareValue getHomeY()
	{
		return homeY;
	}

	public UltimakerHardwareValue getHomeZ()
	{
		return homeZ;
	}

}
