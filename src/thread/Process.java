package thread;

/** This class is responsible for requesting resources in intervals, simulating a real SO process.  */
public class Process extends Thread {
	
	private static int lastPid = 0;
	
	private int[] resourcesInstances;
	private int pid;
	private int currentRequest = -1;
	
	/**
	 * Constructor, builds this process.
	 * */
	public Process(int numberOfResources) {
		this.resourcesInstances = new int[numberOfResources];
		this.pid = ++lastPid;
	}
	
	@Override
	public void run() {
		System.out.println("This is a cool process requesting for random resources...");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	// Getters and Setters

	public int[] getResourcesInstances() {
		return resourcesInstances;
	}

	public void setResourcesInstances(int[] resourcesInstances) {
		this.resourcesInstances = resourcesInstances;
	}
	
	@Override
	public String toString() {
		return "Processo: " + this.pid;
	}

	public int getCurrentRequest() {
		return currentRequest;
	}
	
	public int getPid() {
		return this.pid;
	}
	
}
