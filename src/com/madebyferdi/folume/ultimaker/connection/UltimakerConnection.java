package com.madebyferdi.folume.ultimaker.connection;


import com.madebyferdi.folume.Application;
import com.madebyferdi.folume.ultimaker.state.UltimakerState;
import com.madebyferdi.folume.utils.Listener;
import processing.serial.Serial;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


final public class UltimakerConnection
{

	// Properties
	private Application app;
	private UltimakerState state;
	private UltimakerConnectionQueue queue;
	private Serial port;
	private String[] ports;
	private List<String> list;
	private String newline;
	private StringBuilder builder;
	private List<String> history;
	private Listener listener;
	private String file;
	private PrintWriter output;


	public UltimakerConnection(Application app, UltimakerState state)
	{
		// Store references
		this.app = app;
		this.state = state;

		// Create a new queue
		queue = new UltimakerConnectionQueue(this, state);

		// Needed for socket parsing
		newline = System.lineSeparator();
		builder = new StringBuilder();

		// History data
		history = new ArrayList<>();

		// Connect to serial port
		ports = Serial.list();
		list = new ArrayList<>();
        for (String port : ports) {
            if (port.contains("usbmodem")) {
                list.add(port);
            }
        }
	}


	/**
	 * Start writing to file
	 *
	 * @param file: Absolute file path
	 */
	public void beginWrite(String file)
	{
		this.file = file;
		this.output = app.createWriter(file);
	}


	/**
	 * Stop writing to file
	 */
	public void endWrite()
	{
		this.file = null;
		this.output.flush();
		this.output.close();
		this.output = null;
	}


	/**
	 * Register data listener
	 *
	 * @param listener: Listener method
	 */
	public void onData(Listener listener)
	{
		this.listener = listener;
	}


	/**
	 * Get serial ports
	 */
	public List<String> getList()
	{
		return list;
	}


	/**
	 * Get command history
	 */
	public List<String> getHistory()
	{
		return history;
	}


	/**
	 * Clear the queue
	 */
	public void clear()
	{
		queue.clear();
	}


	/**
	 * Connect serial port
	 */
	public void connect(String name)
	{
		if (port == null) {
			try {
				port = new Serial(app, name, 250000);
			} catch (Exception e) {
				// System.out.print(e.getStackTrace());
			}
		}
	}


	/**
	 * Disconnect serial port
	 */
	public void disconnect()
	{
		if (port != null) {
			port.stop();
			port = null;
		}
	}


	/**
	 * Add command to queue
	 *
	 * @param command: Command to add
	 */
	public void send(UltimakerInstruction command)
	{
		// When we are writing to a file, we just dump the gcode
		// for each command to the output file. This happens
		// before we add the command to queue
		if (file != null) {
			output.println(command.getData());
		}

		// When we have an active connection we add the command
		// to the connection queue. The queue will make sure the
		// commands are executed at the correct speed.
		if (port != null) {
			queue.add(command);
		}
	}


	/**
	 * Write raw model
	 *
	 * @param data: Line to write
	 */
	public void write(String data)
	{
		if (port != null) {

			// Store current command
			history.add("OUT: " + data);

			// Call listener method
			if (listener != null) {
				listener.callback();
			}

			// Write the data
			port.write(data);
			port.write(newline);
		}
	}


	/**
	 * The update loop. Since processing is frame based
	 * we process the serial buffer each frame. This also
	 * means that model is never 100% viz.
	 */
	public void update()
	{
		// Read the lines
		while (port != null && port.available() > 0) {

			// Read single character
			char c = port.readChar();
			String s = String.valueOf(c);

			// If the current character is a line ending then
			// we should read the complete buffer and process the line.
			// Otherwise we can add the character to the buffer and cary on
			if (newline.equals(s)) {

				// Build the sting
				String line = builder.toString();
				builder.setLength(0);

				// Store current command
				history.add("IN: " + line);

				// Call listener method
				if (listener != null) {
					listener.callback();
				}

				// Process current line
				if (this.queue.getCurrent() != null) {
					this.queue.getCurrent().process(line);
				}

			} else {
				builder.append(s);
			}
		}
	}
}