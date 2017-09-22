package com.madebyferdi.folume.ultimaker;

import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.ultimaker.connection.UltimakerCommands;
import com.madebyferdi.folume.ultimaker.connection.UltimakerConnection;
import com.madebyferdi.folume.ultimaker.connection.UltimakerInstruction;
import com.madebyferdi.folume.ultimaker.hardware.UltimakerHardware;
import com.madebyferdi.folume.ultimaker.state.UltimakerState;

final public class Ultimaker
{


	// Properties
	private Application app;
	private UltimakerHardware hardware;
	private UltimakerState state;
	private UltimakerConnection connection;


	public Ultimaker(Application app)
	{
		this.app = app;

		hardware = new UltimakerHardware();
		state = new UltimakerState(hardware);
		connection = new UltimakerConnection(app, state);
	}


	/**
	 * Getters
	 */
	public UltimakerHardware getHardware()
	{
		return hardware;
	}

	public UltimakerState getState()
	{
		return state;
	}

	public UltimakerConnection getConnection()
	{
		return connection;
	}


	/**
	 * Startup commands
	 */
	public void startup()
	{
		// 1. Get settings
		// 2. Set absolute positions
		// 3. Home axis
		// 4. Get temperature
		// 5. Get position
		// 6. Set config options
		// 7. Set retract options
		// 8. Set fan speed
		// 9. Set led brightness
		// 10. Move to start position
		connection.send(new UltimakerInstruction(UltimakerCommands.PRINT_SETTINGS));
		connection.send(new UltimakerInstruction(UltimakerCommands.SET_POSITION_ABSOLUTE));
		connection.send(new UltimakerInstruction(UltimakerCommands.SET_EXTURDER_ABSOLUTE));
		connection.send(new UltimakerInstruction(UltimakerCommands.HOME_AXIS));
		connection.send(new UltimakerInstruction(UltimakerCommands.GET_TEMPERATURE));
		connection.send(new UltimakerInstruction(UltimakerCommands.GET_POSITION));
		connection.send(new UltimakerInstruction(UltimakerCommands.SET_STEP_PER_UNIT).x(state.getStepX()).y(state.getStepY()).z(state.getStepZ()).e(state.getStepE()));
		connection.send(new UltimakerInstruction(UltimakerCommands.SET_MAXIMUM_FEEDRATE).x(state.getFeedrateX()).y(state.getFeedrateY()).z(state.getFeedrateZ()).e(state.getFeedrateE()));
		connection.send(new UltimakerInstruction(UltimakerCommands.SET_MAXIMUM_ACCELERATION).x(state.getAccelerationX()).y(state.getAccelerationY()).z(state.getAccelerationZ()).e(state.getAccelerationE()));
		connection.send(new UltimakerInstruction(UltimakerCommands.SET_ADVANCED_VARIABLES).x(state.getJerkXY()).z(state.getJerkZ()).e(state.getJerkE()));
		connection.send(new UltimakerInstruction(UltimakerCommands.SET_RETRACT).s(state.getRetractLength()).f(state.getRetractSpeed()).z(state.getRetractZ()));
		connection.send(new UltimakerInstruction(UltimakerCommands.SET_FAN_SPEED).s(state.getFanSpeed()));
		connection.send(new UltimakerInstruction(UltimakerCommands.SET_LED_BRIGHTNESS).s(state.getLedBrightness()));
	}


	/**
	 * Shutdown commands
	 */
	public void shutdown()
	{
		// 1. Set temperature to 0
		// 2. Turn off fans
		// 3. Turn on led
		// 4. Home axes
		// 5. Disable all motors
		connection.send(new UltimakerInstruction(UltimakerCommands.HOME_AXIS).x(0));
		connection.send(new UltimakerInstruction(UltimakerCommands.SET_HEATBED_TEMPERATURE).s(0));
		connection.send(new UltimakerInstruction(UltimakerCommands.SET_EXTRUDER_TEMPERATURE).s(0));
		connection.send(new UltimakerInstruction(UltimakerCommands.SET_FAN_SPEED).s(0));
		connection.send(new UltimakerInstruction(UltimakerCommands.SET_LED_BRIGHTNESS).s(255));
		connection.send(new UltimakerInstruction(UltimakerCommands.DISABLE_MOTORS));
	}


	/**
	 * Reset commands
	 */
	public void reset()
	{
		state.reset();
		connection.clear();
		connection.disconnect();
	}


	/**
	 * When the printer is in direct drive mode the view will
	 * update the target values in the model, we do this so we won't
	 * flood the printer with commands. Instead we check each loop
	 * what values have changed and send those to the printer.
	 * Note that is NOT the same as sending print codes to the printer
	 * Since some values might be combined in one instruction, for instance
	 * When XYZ values have changed, they will be send as one instruction.
	 */
	private void sendCommands()
	{
		if (state.getStepX().changed() || state.getStepY().changed() || state.getStepZ().changed() || state.getStepE().changed()) {
			connection.send(
				new UltimakerInstruction(UltimakerCommands.SET_STEP_PER_UNIT)
					.x(state.getStepX())
					.y(state.getStepY())
					.z(state.getStepZ())
					.e(state.getStepE())
			);
		}
		if (state.getFeedrateX().changed() || state.getFeedrateY().changed() || state.getFeedrateZ().changed() || state.getFeedrateE().changed()) {
			connection.send(
				new UltimakerInstruction(UltimakerCommands.SET_MAXIMUM_FEEDRATE)
					.x(state.getFeedrateX())
					.y(state.getFeedrateY())
					.z(state.getFeedrateZ())
					.e(state.getFeedrateE())
			);
		}
		if (state.getAccelerationX().changed() || state.getAccelerationY().changed() || state.getAccelerationZ().changed() || state.getAccelerationE().changed()) {
			connection.send(
				new UltimakerInstruction(UltimakerCommands.SET_MAXIMUM_ACCELERATION)
					.x(state.getAccelerationX())
					.y(state.getAccelerationY())
					.z(state.getAccelerationZ())
					.e(state.getAccelerationE())
			);
		}
		if (state.getJerkXY().changed() || state.getJerkZ().changed() || state.getJerkE().changed()) {
			connection.send(
				new UltimakerInstruction(UltimakerCommands.SET_ADVANCED_VARIABLES)
					.x(state.getJerkXY())
					.z(state.getJerkZ())
					.e(state.getJerkE())
			);
		}
		if (state.getRetractLength().changed() || state.getRetractSpeed().changed() || state.getRetractZ().changed()) {
			connection.send(
				new UltimakerInstruction(UltimakerCommands.SET_ADVANCED_VARIABLES)
					.s(state.getRetractLength())
					.f(state.getRetractSpeed())
					.z(state.getRetractZ())
			);
		}
		if (state.getExtruderTemperature().changed()) {
			connection.send(
				new UltimakerInstruction(UltimakerCommands.SET_EXTRUDER_TEMPERATURE)
					.s(state.getExtruderTemperature())
			);
		}
		if (state.getHeatbedTemperature().changed()) {
			connection.send(
				new UltimakerInstruction(UltimakerCommands.SET_HEATBED_TEMPERATURE)
					.s(state.getHeatbedTemperature())
			);
		}
		if (state.getLedBrightness().changed()) {
			connection.send(
				new UltimakerInstruction(UltimakerCommands.SET_LED_BRIGHTNESS)
					.s(state.getLedBrightness())
			);
		}
		if (state.getFanSpeed().changed()) {
			connection.send(
				new UltimakerInstruction(UltimakerCommands.SET_FAN_SPEED)
					.s(state.getFanSpeed())
			);
		}
		if (state.getX().changed() || state.getY().changed()) {
			connection.send(
				new UltimakerInstruction(UltimakerCommands.MOVE)
					.x(state.getX())
					.y(state.getY())
					.f(state.getSpeedTravel())
			);
		}
		if (state.getZ().changed()) {
			connection.send(
				new UltimakerInstruction(UltimakerCommands.MOVE)
					.z(state.getZ())
					.f(state.getSpeedZ())
			);
		}
	}


	public void update()
	{
		// Check if we need to send model changes
		sendCommands();

		// Update connection
		connection.update();
	}

}
