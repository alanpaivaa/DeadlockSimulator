package thread;

/**
 * Represents the Operational System.
 * This class is responsible for checking the deadlockson the simulation.
 * @author ajeferson
 * */
public class OperationalSystem extends Thread {

	private int interval;
	
	public OperationalSystem(int interval) {
		this.interval = interval;
	}
	
	@Override
	public void run() {
		while(true) {
			System.out.println("This is the SO checking for Deadlocks!");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
	// Getters and Setters
	
	public int getInterval() {
		return interval;
	}
	
}
