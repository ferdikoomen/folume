package com.madebyferdi.folume.ultimaker.state;


import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;

final public class UltimakerState
{

	// Properties
	private UltimakerStateValue extruderTemperature;
	private UltimakerStateValue heatbedTemperature;
	private UltimakerStateValue ledBrightness;
	private UltimakerStateValue fanSpeed;
	private UltimakerStateValue speedTravel;
	private UltimakerStateValue speedPrint;
	private UltimakerStateValue speedZ;
	private UltimakerStateValue filamentDiameter;
	private UltimakerStateValue nozzleDiameter;
	private UltimakerStateValue layerHeight;
	private UltimakerStateValue layerWidth;
	private UltimakerStateValue layerBottom;
	private UltimakerStateValue retractLength;
	private UltimakerStateValue retractSpeed;
	private UltimakerStateValue retractZ;
	private UltimakerStateValue stepX;
	private UltimakerStateValue stepY;
	private UltimakerStateValue stepZ;
	private UltimakerStateValue stepE;
	private UltimakerStateValue feedrateX;
	private UltimakerStateValue feedrateY;
	private UltimakerStateValue feedrateZ;
	private UltimakerStateValue feedrateE;
	private UltimakerStateValue accelerationX;
	private UltimakerStateValue accelerationY;
	private UltimakerStateValue accelerationZ;
	private UltimakerStateValue accelerationE;
	private UltimakerStateValue jerkXY;
	private UltimakerStateValue jerkZ;
	private UltimakerStateValue jerkE;
	private UltimakerStateValue defaultAcceleration;
	private UltimakerStateValue defaultAcceletationRetract;
	private UltimakerStateValue homeOffsetX;
	private UltimakerStateValue homeOffsetY;
	private UltimakerStateValue homeOffsetZ;
	private UltimakerStateValue x;
	private UltimakerStateValue y;
	private UltimakerStateValue z;
	private UltimakerStateValue e;
	private UltimakerStateValue f;


	public UltimakerState(UltimakerHardware hardware)
	{
		extruderTemperature = new UltimakerStateValue("extruderTemperature", hardware.getExtruderTemperature().getValue());
		heatbedTemperature = new UltimakerStateValue("heatbedTemperature", hardware.getHeatbedTemperature().getValue());
		ledBrightness = new UltimakerStateValue("ledBrightness", hardware.getLedBrightness().getValue());
		fanSpeed = new UltimakerStateValue("fanSpeed", hardware.getFanSpeed().getValue());
		speedTravel = new UltimakerStateValue("speedTravel", hardware.getSpeedTravel().getValue());
		speedPrint = new UltimakerStateValue("speedPrint", hardware.getSpeedPrint().getValue());
		speedZ = new UltimakerStateValue("speedZ", hardware.getSpeedZ().getValue());
		filamentDiameter = new UltimakerStateValue("filamentDiameter", hardware.getFilamentDiameter().getValue());
		nozzleDiameter = new UltimakerStateValue("nozzleDiameter", hardware.getNozzleDiameter().getValue());
		layerHeight = new UltimakerStateValue("layerHeight", hardware.getLayerHeight().getValue());
		layerWidth = new UltimakerStateValue("layerWidth", hardware.getLayerWidth().getValue());
		layerBottom = new UltimakerStateValue("layerBottom", hardware.getLayerBottom().getValue());
		retractLength = new UltimakerStateValue("retractLength", hardware.getRetractLength().getValue());
		retractSpeed = new UltimakerStateValue("retractSpeed", hardware.getRetractSpeed().getValue());
		retractZ = new UltimakerStateValue("retractZ", hardware.getRetractZ().getValue());
		stepX = new UltimakerStateValue("stepX", hardware.getStepX().getValue());
		stepY = new UltimakerStateValue("stepY", hardware.getStepY().getValue());
		stepZ = new UltimakerStateValue("stepZ", hardware.getStepZ().getValue());
		stepE = new UltimakerStateValue("stepE", hardware.getStepE().getValue());
		feedrateX = new UltimakerStateValue("feedrateX", hardware.getFeedrateX().getValue());
		feedrateY = new UltimakerStateValue("feedrateY", hardware.getFeedrateY().getValue());
		feedrateZ = new UltimakerStateValue("feedrateZ", hardware.getFeedrateZ().getValue());
		feedrateE = new UltimakerStateValue("feedrateE", hardware.getFeedrateE().getValue());
		accelerationX = new UltimakerStateValue("accelerationX", hardware.getAccelerationX().getValue());
		accelerationY = new UltimakerStateValue("accelerationY", hardware.getAccelerationY().getValue());
		accelerationZ = new UltimakerStateValue("accelerationZ", hardware.getAccelerationZ().getValue());
		accelerationE = new UltimakerStateValue("accelerationE", hardware.getAccelerationE().getValue());
		defaultAcceleration = new UltimakerStateValue("defaultAcceleration", hardware.getDefaultAcceleration().getValue());
		defaultAcceletationRetract = new UltimakerStateValue("defaultAcceletationRetract", hardware.getDefaultAccelerationRetract().getValue());
		jerkXY = new UltimakerStateValue("jerkXY", hardware.getJerkXY().getValue());
		jerkZ = new UltimakerStateValue("jerkZ", hardware.getJerkZ().getValue());
		jerkE = new UltimakerStateValue("jerkE", hardware.getJerkE().getValue());
		homeOffsetX = new UltimakerStateValue("homeOffsetX", 0);
		homeOffsetY = new UltimakerStateValue("homeOffsetY", 0);
		homeOffsetZ = new UltimakerStateValue("homeOffsetZ", 0);
		x = new UltimakerStateValue("x", hardware.getHomeX().getValue());
		y = new UltimakerStateValue("y", hardware.getHomeY().getValue());
		z = new UltimakerStateValue("z", hardware.getHomeZ().getValue());
		e = new UltimakerStateValue("e", 0);
		f = new UltimakerStateValue("f", 0);
	}


	/**
	 * Print all values
	 */
	public String toString()
	{
		return "; ULTIMAKER STATE:" +
			System.lineSeparator() +
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
			defaultAcceleration.toString() +
			defaultAcceletationRetract.toString() +
			jerkXY.toString() +
			jerkZ.toString() +
			jerkE.toString() +
			homeOffsetX.toString() +
			homeOffsetY.toString() +
			homeOffsetZ.toString() +
			x.toString() +
			y.toString() +
			z.toString() +
			e.toString() +
			f.toString() +
			System.lineSeparator();
	}


	/**
	 * Reset the model to defaults
	 */
	public void reset()
	{
		extruderTemperature.reset();
		heatbedTemperature.reset();
		ledBrightness.reset();
		fanSpeed.reset();
		speedTravel.reset();
		speedPrint.reset();
		speedZ.reset();
		filamentDiameter.reset();
		nozzleDiameter.reset();
		layerHeight.reset();
		layerWidth.reset();
		layerBottom.reset();
		retractLength.reset();
		retractSpeed.reset();
		retractZ.reset();
		stepX.reset();
		stepY.reset();
		stepZ.reset();
		stepE.reset();
		feedrateX.reset();
		feedrateY.reset();
		feedrateZ.reset();
		feedrateE.reset();
		accelerationX.reset();
		accelerationY.reset();
		accelerationZ.reset();
		accelerationE.reset();
		defaultAcceleration.reset();
		defaultAcceletationRetract.reset();
		jerkXY.reset();
		jerkZ.reset();
		jerkE.reset();
		homeOffsetX.reset();
		homeOffsetY.reset();
		homeOffsetZ.reset();
		x.reset();
		y.reset();
		z.reset();
		e.reset();
		f.reset();
	}


	/**
	 * Getters
	 */
	public UltimakerStateValue getExtruderTemperature()
	{
		return extruderTemperature;
	}

	public UltimakerStateValue getHeatbedTemperature()
	{
		return heatbedTemperature;
	}

	public UltimakerStateValue getLedBrightness()
	{
		return ledBrightness;
	}

	public UltimakerStateValue getFanSpeed()
	{
		return fanSpeed;
	}

	public UltimakerStateValue getSpeedTravel()
	{
		return speedTravel;
	}

	public UltimakerStateValue getSpeedPrint()
	{
		return speedPrint;
	}

	public UltimakerStateValue getSpeedZ()
	{
		return speedZ;
	}

	public UltimakerStateValue getFilamentDiameter()
	{
		return filamentDiameter;
	}

	public UltimakerStateValue getNozzleDiameter()
	{
		return nozzleDiameter;
	}

	public UltimakerStateValue getLayerHeight()
	{
		return layerHeight;
	}

	public UltimakerStateValue getLayerWidth()
	{
		return layerWidth;
	}

	public UltimakerStateValue getLayerBottom()
	{
		return layerBottom;
	}

	public UltimakerStateValue getRetractLength()
	{
		return retractLength;
	}

	public UltimakerStateValue getRetractSpeed()
	{
		return retractSpeed;
	}

	public UltimakerStateValue getRetractZ()
	{
		return retractZ;
	}

	public UltimakerStateValue getStepX()
	{
		return stepX;
	}

	public UltimakerStateValue getStepY()
	{
		return stepY;
	}

	public UltimakerStateValue getStepZ()
	{
		return stepZ;
	}

	public UltimakerStateValue getStepE()
	{
		return stepE;
	}

	public UltimakerStateValue getFeedrateX()
	{
		return feedrateX;
	}

	public UltimakerStateValue getFeedrateY()
	{
		return feedrateY;
	}

	public UltimakerStateValue getFeedrateZ()
	{
		return feedrateZ;
	}

	public UltimakerStateValue getFeedrateE()
	{
		return feedrateE;
	}

	public UltimakerStateValue getAccelerationX()
	{
		return accelerationX;
	}

	public UltimakerStateValue getAccelerationY()
	{
		return accelerationY;
	}

	public UltimakerStateValue getAccelerationZ()
	{
		return accelerationZ;
	}

	public UltimakerStateValue getAccelerationE()
	{
		return accelerationE;
	}

	public UltimakerStateValue getJerkXY()
	{
		return jerkXY;
	}

	public UltimakerStateValue getJerkZ()
	{
		return jerkZ;
	}

	public UltimakerStateValue getJerkE()
	{
		return jerkE;
	}

	public UltimakerStateValue getDefaultAcceleration()
	{
		return defaultAcceleration;
	}

	public UltimakerStateValue getDefaultAcceletationRetract()
	{
		return defaultAcceletationRetract;
	}

	public UltimakerStateValue getHomeOffsetX()
	{
		return homeOffsetX;
	}

	public UltimakerStateValue getHomeOffsetY()
	{
		return homeOffsetY;
	}

	public UltimakerStateValue getHomeOffsetZ()
	{
		return homeOffsetZ;
	}

	public UltimakerStateValue getX()
	{
		return x;
	}

	public UltimakerStateValue getY()
	{
		return y;
	}

	public UltimakerStateValue getZ()
	{
		return z;
	}

	public UltimakerStateValue getE()
	{
		return e;
	}

	public UltimakerStateValue getF()
	{
		return f;
	}
}
