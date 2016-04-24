package interfaces;

import enums.LogType;
import model.CoolSemaphore;
import model.Resource;

/**
 * Contains all the methods that must be visible by the classes that uses the simulator.
 * @author ajeferson
 * */
public interface SimulatorFacade {
	
	/**
	 * Give access to the mutex semaphore.
	 * */
	public CoolSemaphore getMutex();
	
	/**
	 * Writes on a log.
	 * @param logType Specifies in which log to write.
	 * @param text The text to append on the log.
	 * */
	public void log(LogType logType, String text);
	
	/**
	 * Randmonly selects a resource
	 * @return Returns the position of the selected resouce in the OS arrayLIST
	 * 
	 * */
	public int requestResourcePos();
	
	public Resource getResourceAt(int index);
	
}
