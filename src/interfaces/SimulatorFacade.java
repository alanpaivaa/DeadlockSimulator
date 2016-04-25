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
	 * Randomly selects a resource
	 * @return Returns the position of the selected resource in the OS arrayLIST
	 * 
	 * */
	public int requestResourcePos();
	
	/**
	 * Gets a resource from the resources Array List at a certain index
	 * @param index The index where to get the Resource
	 * @return Returns the Resource at index
	 * 
	 * */
	public Resource getResourceAt(int index);
	
	public Resource getResourceById(int id);
	
	public Object[][] getProcessesData();
	
}
