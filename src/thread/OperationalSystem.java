package thread;

import enums.LogType;
import interfaces.SimulatorFacade;
import model.CoolThread;

/**
 * Represents the Operational System.
 * This class is responsible for checking the deadlockson the simulation.
 * @author ajeferson
 * */
public class OperationalSystem extends CoolThread {

	private int interval;
	private SimulatorFacade simulator;
	
	public OperationalSystem(int interval, SimulatorFacade simulator) {
		this.interval = interval;
		this.simulator = simulator;
	}
	
	@Override
	public void run() {
		while(true) {
			if(this.checkForDeadlock()) {
				this.simulator.log(LogType.DEADLOCK, "Deadlock :(");
			}
			sleep(1);
		}
	}
	
	/**
	 * Checks if there's a deadlock happening.
	 * */
	private boolean checkForDeadlock() {
		return true;
	}

	
	// Getters and Setters
	
	public int getInterval() {
		return interval;
	}
	
}
