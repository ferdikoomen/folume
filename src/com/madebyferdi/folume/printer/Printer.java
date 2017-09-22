package com.madebyferdi.folume.printer;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.printer.view.PrinterView;
import com.madebyferdi.folume.printer.view.PrinterViewController;
import com.madebyferdi.folume.shape.Shape;
import com.madebyferdi.folume.shape.ShapePathPoint;
import com.madebyferdi.folume.ultimaker.Ultimaker;
import com.madebyferdi.folume.ultimaker.connection.UltimakerCommands;
import com.madebyferdi.folume.ultimaker.connection.UltimakerConnection;
import com.madebyferdi.folume.ultimaker.connection.UltimakerInstruction;
import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;
import com.madebyferdi.folume.ultimaker.state.UltimakerState;
import com.madebyferdi.folume.utils.Filament;
import processing.data.JSONObject;

import java.io.File;


final public class Printer
{

	// Properties
	private Application app;
	private Shape shape;
	private Ultimaker ultimaker;
	private UltimakerHardware ultimakerHardware;
	private UltimakerConnection ultimakerConnection;
	private UltimakerState ultimakerState;
	private PrinterView view;
	private PrinterViewController viewController;


	public Printer(Application app, Shape shape, Ultimaker ultimaker)
	{
		// Store references
		this.app = app;
		this.shape = shape;
		this.ultimaker = ultimaker;
		this.ultimakerHardware = ultimaker.getHardware();
		this.ultimakerConnection = ultimaker.getConnection();
		this.ultimakerState = ultimaker.getState();

		// Create parts
		view = new PrinterView(app, shape, ultimaker);
		viewController = new PrinterViewController(app, shape, view, ultimaker);

		// Link connect button
		view.getControlConnect().getConnectSwitch().onSwitch(() -> {
			if (view.getControlConnect().getConnectSwitch().getState()) {
				ultimakerConnection.clear();
				ultimakerConnection.connect(viewController.getSelectedPort());
			} else {
				ultimakerConnection.clear();
				ultimakerConnection.disconnect();
			}
		});

		// Link buttons
		view.getControlConnect().getStartupButton().onClick(ultimaker::startup);
		view.getControlConnect().getShutdownButton().onClick(ultimaker::shutdown);
		view.getControlConnect().getPrintButton().onClick(this::print);
		view.getControlConnect().getResetButton().onClick(this::reset);

		// Load and save files
		view.getControlConnect().getLoadButton().onClick(() -> {
			app.selectInput("Open", "loadSettingsFile", new File("printer.json"), this);
		});
		view.getControlConnect().getSaveButton().onClick(() -> {
			app.selectOutput("Save", "saveSettingsFile", new File("printer.json"), this);
		});
		view.getControlConnect().getGcodeButton().onClick(() -> {
			app.selectOutput("Save", "saveGcodeFile", new File("lamp.gcode"), this);
		});
	}


	/**
	 * Getters
	 */
	public PrinterViewController getViewController()
	{
		return viewController;
	}

	public PrinterView getView()
	{
		return view;
	}


	/**
	 * Load settings file
	 *
	 * @param file: JSON file reference
	 */
	public void loadSettingsFile(File file)
	{
		if (file != null) {
			try {
				JSONObject json = app.loadJSONObject(file.getAbsolutePath());
				ultimakerState.getExtruderTemperature().setTarget(json.getDouble("extruderTemperature"));
				ultimakerState.getHeatbedTemperature().setTarget(json.getDouble("heatbedTemperature"));
				ultimakerState.getLedBrightness().setTarget(json.getDouble("ledBrightness"));
				ultimakerState.getFanSpeed().setTarget(json.getDouble("fanSpeed"));
				ultimakerState.getSpeedTravel().setTarget(json.getDouble("speedTravel"));
				ultimakerState.getSpeedPrint().setTarget(json.getDouble("speedPrint"));
				ultimakerState.getSpeedZ().setTarget(json.getDouble("speedZ"));
				ultimakerState.getFilamentDiameter().setTarget(json.getDouble("filamentDiameter"));
				ultimakerState.getNozzleDiameter().setTarget(json.getDouble("nozzleDiameter"));
				ultimakerState.getLayerHeight().setTarget(json.getDouble("layerHeight"));
				ultimakerState.getLayerWidth().setTarget(json.getDouble("layerWidth"));
				ultimakerState.getLayerBottom().setTarget(json.getDouble("layerBottom"));
				ultimakerState.getRetractLength().setTarget(json.getDouble("retractLength"));
				ultimakerState.getRetractSpeed().setTarget(json.getDouble("retractSpeed"));
				ultimakerState.getRetractZ().setTarget(json.getDouble("retractZ"));
				ultimakerState.getStepX().setTarget(json.getDouble("stepX"));
				ultimakerState.getStepY().setTarget(json.getDouble("stepY"));
				ultimakerState.getStepZ().setTarget(json.getDouble("stepZ"));
				ultimakerState.getStepE().setTarget(json.getDouble("stepE"));
				ultimakerState.getFeedrateX().setTarget(json.getDouble("feedrateX"));
				ultimakerState.getFeedrateY().setTarget(json.getDouble("feedrateY"));
				ultimakerState.getFeedrateZ().setTarget(json.getDouble("feedrateZ"));
				ultimakerState.getFeedrateE().setTarget(json.getDouble("feedrateE"));
				ultimakerState.getAccelerationX().setTarget(json.getDouble("accelerationX"));
				ultimakerState.getAccelerationY().setTarget(json.getDouble("accelerationY"));
				ultimakerState.getAccelerationZ().setTarget(json.getDouble("accelerationZ"));
				ultimakerState.getAccelerationE().setTarget(json.getDouble("accelerationE"));
				ultimakerState.getJerkXY().setTarget(json.getDouble("jerkXY"));
				ultimakerState.getJerkZ().setTarget(json.getDouble("jerkZ"));
				ultimakerState.getJerkE().setTarget(json.getDouble("jerkE"));
				ultimakerState.getZ().setTarget(json.getDouble("z"));
				ultimakerState.getX().setTarget(json.getDouble("x"));
				ultimakerState.getY().setTarget(json.getDouble("y"));
			} catch (Exception e) {
				// Ignore
			}
		}
	}


	/**
	 * Save settings file
	 *
	 * @param file: JSON file reference
	 */
	public void saveSettingsFile(File file)
	{
		if (file != null) {
			JSONObject json = new JSONObject();
			json.setDouble("extruderTemperature", ultimakerState.getExtruderTemperature().getTarget());
			json.setDouble("heatbedTemperature", ultimakerState.getHeatbedTemperature().getTarget());
			json.setDouble("ledBrightness", ultimakerState.getLedBrightness().getTarget());
			json.setDouble("fanSpeed", ultimakerState.getFanSpeed().getTarget());
			json.setDouble("speedTravel", ultimakerState.getSpeedTravel().getTarget());
			json.setDouble("speedPrint", ultimakerState.getSpeedPrint().getTarget());
			json.setDouble("speedZ", ultimakerState.getSpeedZ().getTarget());
			json.setDouble("filamentDiameter", ultimakerState.getFilamentDiameter().getTarget());
			json.setDouble("nozzleDiameter", ultimakerState.getNozzleDiameter().getTarget());
			json.setDouble("layerHeight", ultimakerState.getLayerHeight().getTarget());
			json.setDouble("layerWidth", ultimakerState.getLayerWidth().getTarget());
			json.setDouble("layerBottom", ultimakerState.getLayerBottom().getTarget());
			json.setDouble("retractLength", ultimakerState.getRetractLength().getTarget());
			json.setDouble("retractSpeed", ultimakerState.getRetractSpeed().getTarget());
			json.setDouble("retractZ", ultimakerState.getRetractZ().getTarget());
			json.setDouble("stepX", ultimakerState.getStepX().getTarget());
			json.setDouble("stepY", ultimakerState.getStepY().getTarget());
			json.setDouble("stepZ", ultimakerState.getStepZ().getTarget());
			json.setDouble("stepE", ultimakerState.getStepE().getTarget());
			json.setDouble("feedrateX", ultimakerState.getFeedrateX().getTarget());
			json.setDouble("feedrateY", ultimakerState.getFeedrateY().getTarget());
			json.setDouble("feedrateZ", ultimakerState.getFeedrateZ().getTarget());
			json.setDouble("feedrateE", ultimakerState.getFeedrateE().getTarget());
			json.setDouble("accelerationX", ultimakerState.getAccelerationX().getTarget());
			json.setDouble("accelerationY", ultimakerState.getAccelerationY().getTarget());
			json.setDouble("accelerationZ", ultimakerState.getAccelerationZ().getTarget());
			json.setDouble("accelerationE", ultimakerState.getAccelerationE().getTarget());
			json.setDouble("jerkXY", ultimakerState.getJerkXY().getTarget());
			json.setDouble("jerkZ", ultimakerState.getJerkZ().getTarget());
			json.setDouble("jerkE", ultimakerState.getJerkE().getTarget());
			json.setDouble("z", ultimakerState.getZ().getTarget());
			json.setDouble("x", ultimakerState.getX().getTarget());
			json.setDouble("y", ultimakerState.getY().getTarget());
			app.saveJSONObject(json, file.getAbsolutePath());
		}
	}


	/**
	 * Save settings file
	 *
	 * @param file: JSON file reference
	 */
	public void saveGcodeFile(File file)
	{
		if (file != null) {
			ultimaker.getConnection().clear();
			ultimaker.getConnection().disconnect();
			ultimaker.getConnection().beginWrite(file.getAbsolutePath());
			print();
			ultimaker.getConnection().endWrite();
		}
	}


	/**
	 * Reset parts
	 */
	private void reset()
	{
		ultimaker.reset();
		viewController.reset();
	}


	/**
	 * Print design
	 */
	private void print()
	{
		// Calculate center of the print area, since we want to center the prints
		double printVolumeMinX = ultimakerHardware.getPrintVolumeX().getMin();
		double printVolumeMinY = ultimakerHardware.getPrintVolumeY().getMin();
		double printVolumeMinZ = ultimakerHardware.getPrintVolumeZ().getMin();
		double printVolumeWidth = ultimakerHardware.getPrintVolumeX().getMax() - printVolumeMinX;
		double printVolumeDepth = ultimakerHardware.getPrintVolumeY().getMax() - printVolumeMinY;
		double printVolumeCenterX = printVolumeMinX + (printVolumeWidth / 2);
		double printVolumeCenterY = printVolumeMinY + (printVolumeDepth / 2);

		// Calculate first layer height
		double layerHeight = ultimakerState.getLayerHeight().getTarget();
		double layerBottom = ultimakerState.getLayerBottom().getTarget();
		double layerStart = layerHeight * (layerBottom / 100);

		// Calculate first layer height
		double speed = 250; // 15000 mm/min

		// Update config
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_POSITION_ABSOLUTE));
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_EXTURDER_ABSOLUTE));
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.HOME_AXIS));
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_STEP_PER_UNIT).x(ultimakerState.getStepX()).y(ultimakerState.getStepY()).z(ultimakerState.getStepZ()).e(ultimakerState.getStepE()));
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_MAXIMUM_FEEDRATE).x(ultimakerState.getFeedrateX()).y(ultimakerState.getFeedrateY()).z(ultimakerState.getFeedrateZ()).e(ultimakerState.getFeedrateE()));
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_MAXIMUM_ACCELERATION).x(ultimakerState.getAccelerationX()).y(ultimakerState.getAccelerationY()).z(ultimakerState.getAccelerationZ()).e(ultimakerState.getAccelerationE()));
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_ADVANCED_VARIABLES).x(ultimakerState.getJerkXY()).z(ultimakerState.getJerkZ()).e(ultimakerState.getJerkE()));
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_RETRACT).s(ultimakerState.getRetractLength()).f(ultimakerState.getRetractSpeed()).z(ultimakerState.getRetractZ()));

		// Move to start position (while the printer is heating up, we can safely drop some material here)
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.MOVE).x(10).y(10).f(speed)); // Move head
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.MOVE).z(10).f(speed)); // Move bed

		// Set fan speed, warm up heatbed, extruder and wait...
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_FAN_SPEED).s(ultimakerState.getFanSpeed()));
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_LED_BRIGHTNESS).s(ultimakerState.getLedBrightness()));
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_HEATBED_TEMPERATURE).s(ultimakerState.getHeatbedTemperature()));
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_HEATBED_TEMPERATURE_WAIT).s(ultimakerState.getHeatbedTemperature()));
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_EXTRUDER_TEMPERATURE).s(ultimakerState.getExtruderTemperature()));
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_EXTRUDER_TEMPERATURE_WAIT).s(ultimakerState.getExtruderTemperature()));

		// Extrude a bit of extra material, to make sure the printer is ready to go!
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_POSITION).e(0)); // Reset extruder position
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.PRINT).e(20).f(4)); // Extrude fast
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.PRINT).e(30).f(1)); // Extrude slow
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_POSITION).e(0)); // Reset extruder position
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.PRINT).e(-5).f(6)); // Retract material
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.MOVE).z(20).f(speed)); // Lower platform
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.PRINT).e(0)); // Feed filament back

		// Move to start position
		ultimakerConnection.send(
			new UltimakerInstruction(UltimakerCommands.MOVE)
				.x(printVolumeMinX)
				.y(printVolumeMinY + (printVolumeWidth / 2))
				.f(speed)
		);
		ultimakerConnection.send(
			new UltimakerInstruction(UltimakerCommands.MOVE)
				.z(layerStart)
				.f(speed)
		);

		// Draw bounds
		for (ShapePathPoint point : shape.generateBounds()) {
			ultimakerConnection.send(
				new UltimakerInstruction(UltimakerCommands.MOVE)
					.x(printVolumeCenterX + point.getX())
					.y(printVolumeCenterY + point.getY())
					.z(layerStart + point.getZ())
					.e(point.getTotalFilament())
					.f(point.getSpeed())
			);
		}

		// Reset extruder position (since the shape points gave
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_POSITION).e(0));

		// Iterate over points
		for (ShapePathPoint point : shape.getPathPrint().getPoints()) {
			ultimakerConnection.send(
				new UltimakerInstruction(UltimakerCommands.MOVE)
					.x(printVolumeCenterX + point.getX() - shape.getPathPrint().getCenterX())
					.y(printVolumeCenterY + point.getY() - shape.getPathPrint().getCenterY())
					.z(layerStart + point.getZ())
					.e(point.getTotalFilament())
					.f(point.getSpeed())
			);
		}

		// Retract material and lower build platform, so we get a nice clean cut
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_POSITION).e(0)); // Reset extuder position
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.PRINT).e(-5)); // Retract material
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.MOVE).z(210).f(speed)); // Lower build platform

		// Shutdown sequence
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.HOME_AXIS).x(0)); // Home axis
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_HEATBED_TEMPERATURE).s(0)); // Turn off heat
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_EXTRUDER_TEMPERATURE).s(0)); // Turn off heat
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_FAN_SPEED).s(0)); // Turn off fans
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.SET_LED_BRIGHTNESS).s(0)); // Turn off lights
		ultimakerConnection.send(new UltimakerInstruction(UltimakerCommands.DISABLE_MOTORS)); // Turn off motors
	}


	/**
	 * Update loop
	 */
	public void update()
	{
		viewController.update();
	}
}
