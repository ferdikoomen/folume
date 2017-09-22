package com.madebyferdi.folume.ultimaker.connection;


import com.madebyferdi.folume.ultimaker.state.UltimakerState;

import java.util.ArrayList;
import java.util.List;


final public class UltimakerConnectionQueue
{

	// Properties
	private UltimakerConnection connection;
	private UltimakerState state;
	private List<UltimakerInstruction> queue;
	private UltimakerInstruction current;


	public UltimakerConnectionQueue(UltimakerConnection connection, UltimakerState state)
	{
		// Store references
		this.connection = connection;
		this.state = state;

		// Queue for commands
		queue = new ArrayList<>();
		current = null;
	}


	/**
	 * Get current instruction
	 */
	public UltimakerInstruction getCurrent()
	{
		return current;
	}


	/**
	 * Add printer instruction
	 *
	 * @param instruction: Instruction to add
	 */
	public void add(UltimakerInstruction instruction)
	{
		// Add command in queue, if this command is the
		// first command then execute it directly
		queue.add(instruction);
		if (current == null) {
			current = instruction;
			current.execute(this, connection, state);
		}
	}


	/**
	 * Continue to next command
	 */
	public void next()
	{
		// Remove the current item
		queue.remove(0);
		current.cleanup();
		current = null;

		// Execute the next one (if possible)
		if (queue.size() > 0) {
			current = queue.get(0);
			current.execute(this, connection, state);
		}
	}


	/**
	 * Clear current command
	 */
	public void clear()
	{
		// Cleanup current command
		if (current != null) {
			current.cleanup();
			current = null;
		}

		// clear queue
		queue.clear();
	}
}