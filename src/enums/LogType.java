package enums;

import ui.Simulator;

/**
 * Types of log the Simulator can work with.
 * @author ajeferson
 * @see Simulator
 * */
public enum LogType {

	PROCESS_CREATION("Process creation"),
	PROCESS_REQUEST("Process request"),
	PROCESS_RUNNING("Process running"),
	RESOURCE_RELEASE("Resource release"),
	RESOURCE_BLOCK("Resource block"),
	DEADLOCK("Deadlock");
	
	private String description;
	
	LogType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
}
