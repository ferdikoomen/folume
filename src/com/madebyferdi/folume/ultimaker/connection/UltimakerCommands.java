package com.madebyferdi.folume.ultimaker.connection;


final public class UltimakerCommands
{

	// Codes
	final static public String OK = "ok";
	final static public String PRINT_SETTINGS = "M503";
	final static public String GET_TEMPERATURE = "M105";
	final static public String GET_POSITION = "M114";
	final static public String SET_STEP_PER_UNIT = "M92";
	final static public String SET_MAXIMUM_ACCELERATION = "M201";
	final static public String SET_MAXIMUM_FEEDRATE = "M203";
	final static public String SET_ACCELERATION = "M204";
	final static public String SET_ADVANCED_VARIABLES = "M205";
	final static public String SET_HOME_OFFSET = "M206";
	final static public String SET_POSITION_ABSOLUTE = "G90";
	final static public String SET_EXTURDER_ABSOLUTE = "M82";
	final static public String SET_FAN_SPEED = "M106";
	final static public String SET_LED_BRIGHTNESS = "M42";
	final static public String SET_HEATBED_TEMPERATURE = "M140";
	final static public String SET_HEATBED_TEMPERATURE_WAIT = "M190";
	final static public String SET_EXTRUDER_TEMPERATURE = "M104";
	final static public String SET_EXTRUDER_TEMPERATURE_WAIT = "M109";
	final static public String SET_RETRACT = "M207";
	final static public String SET_POSITION = "G92";
	final static public String HOME_AXIS = "G28";
	final static public String DISABLE_MOTORS = "M84";
	final static public String MOVE = "G0";
	final static public String PRINT = "G1";
	final static public String RETRACT = "G10";
	final static public String RECOVER = "G11";

}
