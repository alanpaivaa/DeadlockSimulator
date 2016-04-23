package thread;

/** Represents the Operational System. This class is responsible for checking the deadlocks on the simulation. */
public class OperationalSystem extends Thread {

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
	
}
