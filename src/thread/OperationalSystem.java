package thread;

import model.CoolThread;

/**
 * Represents the Operational System.
 * This class is responsible for checking the deadlockson the simulation.
 * @author ajeferson
 * */
public class OperationalSystem extends CoolThread {

	private int interval;
	
	public OperationalSystem(int interval) {
		this.interval = interval;
	}
	
	@Override
	public void run() {
		while(true) {
			sleep(1);
		}
	}

	
	// Getters and Setters
	
	public int getInterval() {
		return interval;
	}
	
}
