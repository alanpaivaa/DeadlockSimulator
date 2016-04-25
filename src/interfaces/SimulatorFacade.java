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
	 * Gets a resource from the resources Array List at a certain id
	 * @param id The id of the Resource
	 * @return Returns the Resource or null
	 * 
	 * */
	
	public Resource getResourceById(int id);
	
	/**Gets the Current Alocation data from the processes Array List
	 * @return An bidimensional object  array with the current alocation info
	 */
	public Object[][] getProcessesData();

	/**Gets the request data from the processes Array List
	 * @return An bidimensional object  array with the request info 
	 */
	public Object[][] getProcessesRequest();
	
}
