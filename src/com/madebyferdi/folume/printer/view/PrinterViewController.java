package com.madebyferdi.folume.printer.view;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.shape.Shape;
import com.madebyferdi.folume.ultimaker.Ultimaker;
import com.madebyferdi.folume.ultimaker.connection.UltimakerConnection;
import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;
import com.madebyferdi.folume.ultimaker.state.UltimakerState;
import com.madebyferdi.folume.utils.Maths;

import java.awt.*;

final public class PrinterViewController
{

	// Properties
	private Application app;
	private Shape shape;
	private PrinterView view;
	private Ultimaker ultimaker;
	private UltimakerHardware ultimakerHardware;
	private UltimakerConnection ultimakerConnection;
	private UltimakerState ultimakerState;
	private boolean writeMode = true;
	private boolean invalidated = false;


	public PrinterViewController(Application app, Shape shape, PrinterView view, Ultimaker ultimaker)
	{
		// Store references
		this.app = app;
		this.shape = shape;
		this.view = view;
		this.ultimaker = ultimaker;
		this.ultimakerHardware = ultimaker.getHardware();
		this.ultimakerConnection = ultimaker.getConnection();
		this.ultimakerState = ultimaker.getState();

		// Update acceleration chart when values have changed
		view.getControlAcceleleration().getAccelerationXKnob().onChange(this::updateAccelerationChart);
		view.getControlAcceleleration().getAccelerationYKnob().onChange(this::updateAccelerationChart);
		view.getControlAcceleleration().getAccelerationZKnob().onChange(this::updateAccelerationChart);
		view.getControlAcceleleration().getAccelerationEKnob().onChange(this::updateAccelerationChart);
		view.getControlFeedrate().getFeedrateXKnob().onChange(this::updateAccelerationChart);
		view.getControlFeedrate().getFeedrateYKnob().onChange(this::updateAccelerationChart);
		view.getControlFeedrate().getFeedrateZKnob().onChange(this::updateAccelerationChart);
		view.getControlFeedrate().getFeedrateEKnob().onChange(this::updateAccelerationChart);
		view.getControlJerk().getJerkXYKnob().onChange(this::updateAccelerationChart);
		view.getControlJerk().getJerkZKnob().onChange(this::updateAccelerationChart);
		view.getControlJerk().getJerkEKnob().onChange(this::updateAccelerationChart);
		view.getGraphAcceleration().getAccelerationX().getSliderTime().onChange(this::updateAccelerationChart);
		view.getGraphAcceleration().getAccelerationX().getSliderDistance().onChange(this::updateAccelerationChart);
		view.getGraphAcceleration().getAccelerationY().getSliderTime().onChange(this::updateAccelerationChart);
		view.getGraphAcceleration().getAccelerationY().getSliderDistance().onChange(this::updateAccelerationChart);
		view.getGraphAcceleration().getAccelerationZ().getSliderTime().onChange(this::updateAccelerationChart);
		view.getGraphAcceleration().getAccelerationZ().getSliderDistance().onChange(this::updateAccelerationChart);
		view.getGraphAcceleration().getAccelerationE().getSliderTime().onChange(this::updateAccelerationChart);
		view.getGraphAcceleration().getAccelerationE().getSliderDistance().onChange(this::updateAccelerationChart);

		// Update nozzle chart when values have changed
		view.getControlLayer().getFilamentDiameterKnob().onChange(this::updateNozzleChart);
		view.getControlLayer().getNozzleDiameterKnob().onChange(this::updateNozzleChart);
		view.getControlLayer().getLayerHeightKnob().onChange(this::updateNozzleChart);
		view.getControlLayer().getLayerWidthKnob().onChange(this::updateNozzleChart);
		view.getControlLayer().getLayerBottomKnob().onChange(this::updateNozzleChart);

		// Update preview when values have changed
		view.getControlSpeed().getSpeedTravelKnob().onChange(this::invalidate);
		view.getControlSpeed().getSpeedPrintKnob().onChange(this::invalidate);
		view.getControlSpeed().getSpeedZKnob().onChange(this::invalidate);
		view.getControlLayer().getFilamentDiameterKnob().onChange(this::invalidate);
		view.getControlLayer().getNozzleDiameterKnob().onChange(this::invalidate);
		view.getControlLayer().getLayerHeightKnob().onChange(this::invalidate);
		view.getControlLayer().getLayerWidthKnob().onChange(this::invalidate);
		view.getControlLayer().getLayerBottomKnob().onChange(this::invalidate);

		// Listen to mode switch
		view.getControlConnect().getModeSwitch().onSwitch(() -> {
			writeMode = view.getControlConnect().getModeSwitch().getState();
			view.getControlTemperature().getExtruderTemperatureKnob().setValue(ultimakerState.getExtruderTemperature().getTarget());
			view.getControlTemperature().getHeatbedTemperatureKnob().setValue(ultimakerState.getHeatbedTemperature().getTarget());
			view.getControlFan().getLedBrightnessKnob().setValue(ultimakerState.getLedBrightness().getTarget());
			view.getControlFan().getFanSpeedKnob().setValue(ultimakerState.getFanSpeed().getTarget());
			view.getControlSpeed().getSpeedTravelKnob().setValue(ultimakerState.getSpeedTravel().getTarget());
			view.getControlSpeed().getSpeedPrintKnob().setValue(ultimakerState.getSpeedPrint().getTarget());
			view.getControlSpeed().getSpeedZKnob().setValue(ultimakerState.getSpeedZ().getTarget());
			view.getControlLayer().getFilamentDiameterKnob().setValue(ultimakerState.getFilamentDiameter().getTarget());
			view.getControlLayer().getNozzleDiameterKnob().setValue(ultimakerState.getNozzleDiameter().getTarget());
			view.getControlLayer().getLayerHeightKnob().setValue(ultimakerState.getLayerHeight().getTarget());
			view.getControlLayer().getLayerWidthKnob().setValue(ultimakerState.getLayerWidth().getTarget());
			view.getControlLayer().getLayerBottomKnob().setValue(ultimakerState.getLayerBottom().getTarget());
			view.getControlRetract().getRetractLengthKnob().setValue(ultimakerState.getRetractLength().getTarget());
			view.getControlRetract().getRetractSpeedKnob().setValue(ultimakerState.getRetractSpeed().getTarget());
			view.getControlRetract().getRetractZKnob().setValue(ultimakerState.getRetractZ().getTarget());
			view.getControlStep().getStepXKnob().setValue(ultimakerState.getStepX().getTarget());
			view.getControlStep().getStepYKnob().setValue(ultimakerState.getStepY().getTarget());
			view.getControlStep().getStepZKnob().setValue(ultimakerState.getStepZ().getTarget());
			view.getControlStep().getStepEKnob().setValue(ultimakerState.getStepE().getTarget());
			view.getControlFeedrate().getFeedrateXKnob().setValue(ultimakerState.getFeedrateX().getTarget());
			view.getControlFeedrate().getFeedrateYKnob().setValue(ultimakerState.getFeedrateY().getTarget());
			view.getControlFeedrate().getFeedrateZKnob().setValue(ultimakerState.getFeedrateZ().getTarget());
			view.getControlFeedrate().getFeedrateEKnob().setValue(ultimakerState.getFeedrateE().getTarget());
			view.getControlAcceleleration().getAccelerationXKnob().setValue(ultimakerState.getAccelerationX().getTarget());
			view.getControlAcceleleration().getAccelerationYKnob().setValue(ultimakerState.getAccelerationY().getTarget());
			view.getControlAcceleleration().getAccelerationZKnob().setValue(ultimakerState.getAccelerationZ().getTarget());
			view.getControlAcceleleration().getAccelerationEKnob().setValue(ultimakerState.getAccelerationE().getTarget());
			view.getControlJerk().getJerkXYKnob().setValue(ultimakerState.getJerkXY().getTarget());
			view.getControlJerk().getJerkZKnob().setValue(ultimakerState.getJerkZ().getTarget());
			view.getControlJerk().getJerkEKnob().setValue(ultimakerState.getJerkE().getTarget());
			view.getGraphPosition().getPositionSliderZ().setValue(ultimakerState.getZ().getTarget());
			view.getGraphPosition().getPositionSliderXY().setValue(ultimakerState.getX().getTarget(), ultimakerState.getY().getTarget());
		});

		// Update history chart when the connection has new data
		ultimakerConnection.onData(this::updateHistoryChart);

		// Add available ports to list
		view.getControlConnect().getPortsList().addItems(ultimakerConnection.getList());

		// When the shape is updated we set the invalidation flag
		// this will trigger a recalculation of the shape
		shape.onUpdate(() -> invalidated = true);

		// Update interface
		updateValues();
		updateAccelerationChart();
		updateNozzleChart();
		updatePreviewChart();
		updateShape();

		// TODO: Store layer and speed options in current values!
	}


	/**
	 * Set the invalidation flag, this will update
	 * the design on the next draw call
	 */
	public void invalidate()
	{
		invalidated = true;
	}


	/**
	 * Get selected port
	 */
	public String getSelectedPort()
	{
		return view.getControlConnect().getPortsList().getCaptionLabel().getText();
	}


	/**
	 * Reset interface
	 */
	public void reset()
	{
		// Reset interface
		view.getControlConnect().getConnectSwitch().setState(false);
		view.getControlConnect().getModeSwitch().setState(true);
		view.getGraphHistory().reset();
		view.getGraphPreview().reset();
		writeMode = true;

		// Redraw
		updateValues();
		updateAccelerationChart();
		updateNozzleChart();
		updatePreviewChart();
		updateShape();
	}


	/**
	 * Update the values in the interface
	 * Depending on the mode we use the target or current values
	 */
	public void updateValues()
	{
		if (writeMode) {

			// In write mode we update the model with the latest values
			ultimakerState.getExtruderTemperature().setTarget(view.getControlTemperature().getExtruderTemperatureKnob().getDouble());
			ultimakerState.getHeatbedTemperature().setTarget(view.getControlTemperature().getHeatbedTemperatureKnob().getDouble());
			ultimakerState.getLedBrightness().setTarget(view.getControlFan().getLedBrightnessKnob().getDouble());
			ultimakerState.getFanSpeed().setTarget(view.getControlFan().getFanSpeedKnob().getDouble());
			ultimakerState.getSpeedTravel().setTarget(view.getControlSpeed().getSpeedTravelKnob().getDouble());
			ultimakerState.getSpeedPrint().setTarget(view.getControlSpeed().getSpeedPrintKnob().getDouble());
			ultimakerState.getSpeedZ().setTarget(view.getControlSpeed().getSpeedZKnob().getDouble());
			ultimakerState.getFilamentDiameter().setTarget(view.getControlLayer().getFilamentDiameterKnob().getDouble());
			ultimakerState.getNozzleDiameter().setTarget(view.getControlLayer().getNozzleDiameterKnob().getDouble());
			ultimakerState.getLayerHeight().setTarget(view.getControlLayer().getLayerHeightKnob().getDouble());
			ultimakerState.getLayerWidth().setTarget(view.getControlLayer().getLayerWidthKnob().getDouble());
			ultimakerState.getLayerBottom().setTarget(view.getControlLayer().getLayerBottomKnob().getDouble());
			ultimakerState.getRetractLength().setTarget(view.getControlRetract().getRetractLengthKnob().getDouble());
			ultimakerState.getRetractSpeed().setTarget(view.getControlRetract().getRetractSpeedKnob().getDouble());
			ultimakerState.getRetractZ().setTarget(view.getControlRetract().getRetractZKnob().getDouble());
			ultimakerState.getStepX().setTarget(view.getControlStep().getStepXKnob().getDouble());
			ultimakerState.getStepY().setTarget(view.getControlStep().getStepYKnob().getDouble());
			ultimakerState.getStepZ().setTarget(view.getControlStep().getStepZKnob().getDouble());
			ultimakerState.getStepE().setTarget(view.getControlStep().getStepEKnob().getDouble());
			ultimakerState.getFeedrateX().setTarget(view.getControlFeedrate().getFeedrateXKnob().getDouble());
			ultimakerState.getFeedrateY().setTarget(view.getControlFeedrate().getFeedrateYKnob().getDouble());
			ultimakerState.getFeedrateZ().setTarget(view.getControlFeedrate().getFeedrateZKnob().getDouble());
			ultimakerState.getFeedrateE().setTarget(view.getControlFeedrate().getFeedrateEKnob().getDouble());
			ultimakerState.getAccelerationX().setTarget(view.getControlAcceleleration().getAccelerationXKnob().getDouble());
			ultimakerState.getAccelerationY().setTarget(view.getControlAcceleleration().getAccelerationYKnob().getDouble());
			ultimakerState.getAccelerationZ().setTarget(view.getControlAcceleleration().getAccelerationZKnob().getDouble());
			ultimakerState.getAccelerationE().setTarget(view.getControlAcceleleration().getAccelerationEKnob().getDouble());
			ultimakerState.getJerkXY().setTarget(view.getControlJerk().getJerkXYKnob().getDouble());
			ultimakerState.getJerkZ().setTarget(view.getControlJerk().getJerkZKnob().getDouble());
			ultimakerState.getJerkE().setTarget(view.getControlJerk().getJerkEKnob().getDouble());
			ultimakerState.getX().setTarget(view.getGraphPosition().getPositionSliderXY().getDoubleX());
			ultimakerState.getY().setTarget(view.getGraphPosition().getPositionSliderXY().getDoubleY());
			ultimakerState.getZ().setTarget(view.getGraphPosition().getPositionSliderZ().getDouble());

		} else {

			// In read mode we set the current values in the interface
			view.getControlTemperature().getExtruderTemperatureKnob().setValue(ultimakerState.getExtruderTemperature().getCurrent());
			view.getControlTemperature().getHeatbedTemperatureKnob().setValue(ultimakerState.getHeatbedTemperature().getCurrent());
			view.getControlFan().getLedBrightnessKnob().setValue(ultimakerState.getLedBrightness().getCurrent());
			view.getControlFan().getFanSpeedKnob().setValue(ultimakerState.getFanSpeed().getCurrent());
			view.getControlSpeed().getSpeedTravelKnob().setValue(ultimakerState.getSpeedTravel().getCurrent());
			view.getControlSpeed().getSpeedPrintKnob().setValue(ultimakerState.getSpeedPrint().getCurrent());
			view.getControlSpeed().getSpeedZKnob().setValue(ultimakerState.getSpeedZ().getCurrent());
			view.getControlLayer().getFilamentDiameterKnob().setValue(ultimakerState.getFilamentDiameter().getCurrent());
			view.getControlLayer().getNozzleDiameterKnob().setValue(ultimakerState.getNozzleDiameter().getCurrent());
			view.getControlLayer().getLayerHeightKnob().setValue(ultimakerState.getLayerHeight().getCurrent());
			view.getControlLayer().getLayerWidthKnob().setValue(ultimakerState.getLayerWidth().getCurrent());
			view.getControlLayer().getLayerBottomKnob().setValue(ultimakerState.getLayerBottom().getCurrent());
			view.getControlRetract().getRetractLengthKnob().setValue(ultimakerState.getRetractLength().getCurrent());
			view.getControlRetract().getRetractSpeedKnob().setValue(ultimakerState.getRetractSpeed().getCurrent());
			view.getControlRetract().getRetractZKnob().setValue(ultimakerState.getRetractZ().getCurrent());
			view.getControlStep().getStepXKnob().setValue(ultimakerState.getStepX().getCurrent());
			view.getControlStep().getStepYKnob().setValue(ultimakerState.getStepY().getCurrent());
			view.getControlStep().getStepZKnob().setValue(ultimakerState.getStepZ().getCurrent());
			view.getControlStep().getStepEKnob().setValue(ultimakerState.getStepE().getCurrent());
			view.getControlFeedrate().getFeedrateXKnob().setValue(ultimakerState.getFeedrateX().getCurrent());
			view.getControlFeedrate().getFeedrateYKnob().setValue(ultimakerState.getFeedrateY().getCurrent());
			view.getControlFeedrate().getFeedrateZKnob().setValue(ultimakerState.getFeedrateZ().getCurrent());
			view.getControlFeedrate().getFeedrateEKnob().setValue(ultimakerState.getFeedrateE().getCurrent());
			view.getControlAcceleleration().getAccelerationXKnob().setValue(ultimakerState.getAccelerationX().getCurrent());
			view.getControlAcceleleration().getAccelerationYKnob().setValue(ultimakerState.getAccelerationY().getCurrent());
			view.getControlAcceleleration().getAccelerationZKnob().setValue(ultimakerState.getAccelerationZ().getCurrent());
			view.getControlAcceleleration().getAccelerationEKnob().setValue(ultimakerState.getAccelerationE().getCurrent());
			view.getControlJerk().getJerkXYKnob().setValue(ultimakerState.getJerkXY().getCurrent());
			view.getControlJerk().getJerkZKnob().setValue(ultimakerState.getJerkZ().getCurrent());
			view.getControlJerk().getJerkEKnob().setValue(ultimakerState.getJerkE().getCurrent());
			view.getGraphPosition().getPositionSliderZ().setValue(ultimakerState.getZ().getCurrent());
			view.getGraphPosition().getPositionSliderXY().setValue(ultimakerState.getX().getCurrent(), ultimakerState.getY().getCurrent());
		}
	}


	/**
	 * Update the acceleration charts
	 */
	private void updateAccelerationChart()
	{
		view.getGraphAcceleration().getAccelerationX().update(writeMode ? ultimakerState.getFeedrateX().getTarget() : ultimakerState.getFeedrateX().getCurrent(), writeMode ? ultimakerState.getAccelerationX().getTarget() : ultimakerState.getAccelerationX().getCurrent(), writeMode ? ultimakerState.getJerkXY().getTarget() : ultimakerState.getJerkXY().getCurrent(), ultimakerHardware.getFeedrateX().getMax());
		view.getGraphAcceleration().getAccelerationY().update(writeMode ? ultimakerState.getFeedrateY().getTarget() : ultimakerState.getFeedrateY().getCurrent(), writeMode ? ultimakerState.getAccelerationY().getTarget() : ultimakerState.getAccelerationY().getCurrent(), writeMode ? ultimakerState.getJerkXY().getTarget() : ultimakerState.getJerkXY().getCurrent(), ultimakerHardware.getFeedrateY().getMax());
		view.getGraphAcceleration().getAccelerationZ().update(writeMode ? ultimakerState.getFeedrateZ().getTarget() : ultimakerState.getFeedrateZ().getCurrent(), writeMode ? ultimakerState.getAccelerationZ().getTarget() : ultimakerState.getAccelerationZ().getCurrent(), writeMode ? ultimakerState.getJerkZ().getTarget() : ultimakerState.getJerkZ().getCurrent(), ultimakerHardware.getFeedrateZ().getMax());
		view.getGraphAcceleration().getAccelerationE().update(writeMode ? ultimakerState.getFeedrateE().getTarget() : ultimakerState.getFeedrateE().getCurrent(), writeMode ? ultimakerState.getAccelerationE().getTarget() : ultimakerState.getAccelerationE().getCurrent(), writeMode ? ultimakerState.getJerkE().getTarget() : ultimakerState.getJerkE().getCurrent(), ultimakerHardware.getFeedrateE().getMax());
	}


	/**
	 * Update the nozzle preview
	 */
	private void updateNozzleChart()
	{
		view.getGraphNozzle().update(writeMode ? ultimakerState.getFilamentDiameter().getTarget() : ultimakerState.getFilamentDiameter().getCurrent(), writeMode ? ultimakerState.getNozzleDiameter().getTarget() : ultimakerState.getNozzleDiameter().getCurrent(), writeMode ? ultimakerState.getLayerHeight().getTarget() : ultimakerState.getLayerHeight().getCurrent(), writeMode ? ultimakerState.getLayerWidth().getTarget() : ultimakerState.getLayerWidth().getCurrent());
	}


	/**
	 * Update the printer preview
	 */
	private void updatePreviewChart()
	{
		view.getGraphPreview().update(writeMode ? ultimakerState.getX().getTarget() : ultimakerState.getX().getCurrent(), writeMode ? ultimakerState.getY().getTarget() : ultimakerState.getY().getCurrent(), writeMode ? ultimakerState.getZ().getTarget() : ultimakerState.getZ().getCurrent());
	}


	/**
	 * Update the printer history
	 */
	private void updateHistoryChart()
	{
		// Update history data with latest string
		view.getGraphHistory().update(ultimakerConnection.getHistory());
	}


	/**
	 * Update the printer version of the design shape
	 */
	private void updateShape()
	{
		// We calculate the shape outside of the main thread!
		EventQueue.invokeLater(() -> {

			// Calculate the new shape
			shape.generatePrint(ultimakerState.getSpeedTravel().getTarget(), ultimakerState.getSpeedPrint().getTarget(), ultimakerState.getSpeedZ().getTarget(), ultimakerState.getFilamentDiameter().getTarget(), ultimakerState.getNozzleDiameter().getTarget(), ultimakerState.getLayerHeight().getTarget(), ultimakerState.getLayerWidth().getTarget());

			// Invalidate the preview
			view.getGraphPreview().invalidate();
		});
	}


	/**
	 * Update the duration and filament values
	 */
	private void updateInfo()
	{
		// Get hours minutes and seconds from duration
		int duration = Maths.round(shape.getTotalDuration());
		int hours = duration / 3600;
		int minutes = (duration % 3600) / 60;
		int seconds = duration % 60;

		String time = (hours < 10 ? "0" : "") + String.valueOf(hours) + ":" +
			(minutes < 10 ? "0" : "") + String.valueOf(minutes) + ":" +
			(seconds < 10 ? "0" : "") + String.valueOf(seconds);

		// Get filament length in CM
		double filamentSize = shape.getTotalFilament() / 10;
		String filament = String.valueOf(Maths.round(filamentSize)) + " CM";

		// Update fields
		view.getControlConnect().getDurationField().setValue(time);
		view.getControlConnect().getFilamentField().setValue(filament);
	}


	/**
	 * Update loop
	 */
	public void update()
	{
		// Update visuals
		updateValues();
		updatePreviewChart();
		updateInfo();

		// Draw the view
		view.draw();

		// After our drawing action we check if the shape needs to be updated.
		// We do this after the draw, since from this point on the separate thread
		// can mess with the data. If we do this before draw we have a larger change
		// to run into deadlocks.
		if (invalidated) {
			invalidated = false;
			updateShape();
		}
	}
}
