package com.madebyferdi.folume.ultimaker.connection;


import com.madebyferdi.folume.ultimaker.state.UltimakerState;
import com.madebyferdi.folume.ultimaker.state.UltimakerStateValue;
import com.madebyferdi.folume.utils.Utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


final public class UltimakerInstruction
{

	// Regexp pattern to parse return data
	final static private Pattern PATTERN_CODE = Pattern.compile("(ok|[MG][\\d]{1,3})");
	final static private Pattern PATTERN_DATA = Pattern.compile("([STBXYZEF]):?([\\d.]+)");


	// Reusable format
	final static private DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
	final static private DecimalFormat decimalFormat = new DecimalFormat();


	// Values
	final static private String S = "S";
	final static private String T = "T";
	final static private String B = "B";
	final static private String X = "X";
	final static private String Y = "Y";
	final static private String Z = "Z";
	final static private String E = "E";
	final static private String F = "F";


	// Properties
	private String code;
	private StringBuilder builder;
	private Map<String, Double> properties;
	private UltimakerConnectionQueue queue;
	private UltimakerConnection connection;
	private UltimakerState state;


	public UltimakerInstruction(String code)
	{
		// Save arguments
		this.code = code;

		// Hashmap with properties
		properties = new HashMap<>();
	}


	/**
	 * Setters
	 */
	public UltimakerInstruction s(double value)
	{
		properties.put(S, value);
		return this;
	}

	public UltimakerInstruction t(double value)
	{
		properties.put(T, value);
		return this;
	}


	public UltimakerInstruction b(double value)
	{
		properties.put(B, value);
		return this;
	}

	public UltimakerInstruction x(double value)
	{
		properties.put(X, value);
		return this;
	}

	public UltimakerInstruction y(double value)
	{
		properties.put(Y, value);
		return this;
	}

	public UltimakerInstruction z(double value)
	{
		properties.put(Z, value);
		return this;
	}

	public UltimakerInstruction e(double value)
	{
		properties.put(E, value);
		return this;
	}

	public UltimakerInstruction f(double value)
	{
		properties.put(F, value);
		return this;
	}


	/**
	 * Setters
	 */
	public UltimakerInstruction s(UltimakerStateValue value)
	{
		properties.put(S, value.getTarget());
		value.sync();
		return this;
	}

	public UltimakerInstruction t(UltimakerStateValue value)
	{
		properties.put(T, value.getTarget());
		value.sync();
		return this;
	}

	public UltimakerInstruction b(UltimakerStateValue value)
	{
		properties.put(B, value.getTarget());
		value.sync();
		return this;
	}

	public UltimakerInstruction x(UltimakerStateValue value)
	{
		properties.put(X, value.getTarget());
		value.sync();
		return this;
	}

	public UltimakerInstruction y(UltimakerStateValue value)
	{
		properties.put(Y, value.getTarget());
		value.sync();
		return this;
	}

	public UltimakerInstruction z(UltimakerStateValue value)
	{
		properties.put(Z, value.getTarget());
		value.sync();
		return this;
	}

	public UltimakerInstruction e(UltimakerStateValue value)
	{
		properties.put(E, value.getTarget());
		value.sync();
		return this;
	}

	public UltimakerInstruction f(UltimakerStateValue value)
	{
		properties.put(F, value.getTarget());
		value.sync();
		return this;
	}


	/**
	 * Execute the command
	 *
	 * @param queue:      Reference to printer queue
	 * @param connection: Reference to printer connection
	 * @param state:      Reference to printer model
	 */
	public void execute(UltimakerConnectionQueue queue, UltimakerConnection connection, UltimakerState state)
	{
		// Save for future reference
		this.queue = queue;
		this.connection = connection;
		this.state = state;

		// When a command is about to execute its a good time to update the current
		// value in the state. Now some values are updated because the printer
		// responds with updated values after the command, but in most cases
		// the printer will just reply back with an OK command. Therefore we need
		// to update the values here.
		switch (code) {

			case UltimakerCommands.SET_FAN_SPEED:
				setValue(state.getFanSpeed(), properties, S);
				break;

			case UltimakerCommands.SET_LED_BRIGHTNESS:
				setValue(state.getLedBrightness(), properties, S);
				break;

			case UltimakerCommands.SET_STEP_PER_UNIT:
				setValue(state.getStepX(), properties, X);
				setValue(state.getStepY(), properties, Y);
				setValue(state.getStepZ(), properties, Z);
				setValue(state.getStepE(), properties, E);
				break;

			case UltimakerCommands.SET_MAXIMUM_FEEDRATE:
				setValue(state.getFeedrateX(), properties, X);
				setValue(state.getFeedrateY(), properties, Y);
				setValue(state.getFeedrateZ(), properties, Z);
				setValue(state.getFeedrateE(), properties, E);
				break;

			case UltimakerCommands.SET_MAXIMUM_ACCELERATION:
				setValue(state.getAccelerationX(), properties, X);
				setValue(state.getAccelerationY(), properties, Y);
				setValue(state.getAccelerationZ(), properties, Z);
				setValue(state.getAccelerationE(), properties, E);
				break;

			case UltimakerCommands.SET_ADVANCED_VARIABLES:
				setValue(state.getJerkXY(), properties, X);
				setValue(state.getJerkZ(), properties, Z);
				setValue(state.getJerkE(), properties, E);
				break;

			case UltimakerCommands.SET_RETRACT:
				setValue(state.getRetractLength(), properties, S);
				setValue(state.getRetractSpeed(), properties, F);
				setValue(state.getRetractZ(), properties, Z);
				break;

			case UltimakerCommands.MOVE:
				setValue(state.getX(), properties, X);
				setValue(state.getY(), properties, Y);
				setValue(state.getZ(), properties, Z);
				setValue(state.getE(), properties, E);
				setValue(state.getF(), properties, F);
				break;
		}

		// Write command string
		connection.write(getData());
	}


	/**
	 * Get command as gcode data
	 *
	 * @return
	 */
	public String getData()
	{
		// Create the command string that will be send to the printer
		builder = new StringBuilder();
		builder.append(code);
		if (properties.containsKey(S)) builder.append(" ").append(S).append(getString(properties.get(S)));
		if (properties.containsKey(T)) builder.append(" ").append(T).append(getString(properties.get(T)));
		if (properties.containsKey(B)) builder.append(" ").append(B).append(getString(properties.get(B)));
		if (properties.containsKey(X)) builder.append(" ").append(X).append(getString(properties.get(X)));
		if (properties.containsKey(Y)) builder.append(" ").append(Y).append(getString(properties.get(Y)));
		if (properties.containsKey(Z)) builder.append(" ").append(Z).append(getString(properties.get(Z)));
		if (properties.containsKey(E)) builder.append(" ").append(E).append(getString(properties.get(E)));
		if (properties.containsKey(F))
			builder.append(" ").append(F).append(properties.get(F) * 60); // F is always in mm/min

		// Write command string
		return builder.toString();
	}


	/**
	 * Process incoming model
	 *
	 * @param line: One complete line from serial port
	 */
	public void process(String line)
	{
		// Create matcher for return code and return data
		Matcher matcherCode = PATTERN_CODE.matcher(line);
		Matcher matcherData = PATTERN_DATA.matcher(line);

		// First find the return code (ok, M100, G10, etc)
		String command = matcherCode.find() ? matcherCode.group(1) : "";

		// Then check if the data contains any values (X100, Y100, F100, etc)
		Map<String, Double> values = new HashMap<>();
		while (matcherData.find()) {
			String key = matcherData.group(1);
			double value = Utils.getDouble(matcherData.group(2));
			if (!values.containsKey(key)) {
				values.put(key, value);
			}
		}

		// Some commands will not return any codes, but instead will
		// return lines of data. Since we know the original code,
		// we can grab the data and update the model values where needed.
		switch (code) {

			case UltimakerCommands.GET_TEMPERATURE:
				setValue(state.getExtruderTemperature(), values, T);
				setValue(state.getHeatbedTemperature(), values, B);
				break;

			case UltimakerCommands.GET_POSITION:
				setValue(state.getX(), values, X);
				setValue(state.getY(), values, Y);
				setValue(state.getZ(), values, Z);
				break;

			case UltimakerCommands.SET_EXTRUDER_TEMPERATURE_WAIT:
				setValue(state.getExtruderTemperature(), values, T);
				break;

			case UltimakerCommands.SET_HEATBED_TEMPERATURE_WAIT:
				setValue(state.getHeatbedTemperature(), values, B);
				break;

		}

		// If we did receive a return code then we need to check
		// if the payload contained any values. If so, then we
		// need to update the models again.
		switch (command) {

			case UltimakerCommands.SET_STEP_PER_UNIT:
				setValue(state.getStepX(), values, X);
				setValue(state.getStepY(), values, Y);
				setValue(state.getStepZ(), values, Z);
				setValue(state.getStepE(), values, E);
				return;

			case UltimakerCommands.SET_MAXIMUM_FEEDRATE:
				setValue(state.getFeedrateX(), values, X);
				setValue(state.getFeedrateY(), values, Y);
				setValue(state.getFeedrateZ(), values, Z);
				setValue(state.getFeedrateE(), values, E);
				return;

			case UltimakerCommands.SET_MAXIMUM_ACCELERATION:
				setValue(state.getAccelerationX(), values, X);
				setValue(state.getAccelerationY(), values, Y);
				setValue(state.getAccelerationZ(), values, Z);
				setValue(state.getAccelerationE(), values, E);
				return;

			case UltimakerCommands.SET_ACCELERATION:
				setValue(state.getDefaultAcceleration(), values, S);
				setValue(state.getDefaultAcceletationRetract(), values, T);
				return;

			case UltimakerCommands.SET_ADVANCED_VARIABLES:
				setValue(state.getJerkXY(), values, X);
				setValue(state.getJerkZ(), values, Z);
				setValue(state.getJerkE(), values, E);
				return;

			case UltimakerCommands.SET_HOME_OFFSET:
				setValue(state.getHomeOffsetX(), values, X);
				setValue(state.getHomeOffsetY(), values, Y);
				setValue(state.getHomeOffsetZ(), values, Z);
				return;

			// All good, continue to next command
			case UltimakerCommands.OK:
				queue.next();
				break;
		}
	}


	/**
	 * Set a value if found
	 *
	 * @param value:  Value to set
	 * @param values: Values (key value pairs)
	 * @param key:    Key to look for
	 */
	private void setValue(UltimakerStateValue value, Map<String, Double> values, String key)
	{
		if (!values.isEmpty() && values.containsKey(key)) {
			value.setCurrent(values.get(key));
		}
	}


	/**
	 * Get double as string with 5 decimal precision
	 *
	 * @param value: Number value
	 */
	private String getString(double value)
	{
		decimalFormatSymbols.setDecimalSeparator('.');
		decimalFormat.setGroupingUsed(false);
		decimalFormat.setMinimumFractionDigits(0);
		decimalFormat.setMaximumFractionDigits(5);
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
		return decimalFormat.format(value);
	}


	/**
	 * Cleanup current command
	 */
	public void cleanup()
	{
		code = null;
		queue = null;
		connection = null;
		state = null;
		builder = null;
	}
}
