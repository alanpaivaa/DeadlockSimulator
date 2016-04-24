package thread;

import enums.LogType;
import interfaces.SimulatorFacade;

/** This class is responsible for requesting resources in intervals, simulating a real SO process.  */
public class Process extends Thread {
	
	private static int lastId = 0;
	
	private SimulatorFacade simulator;
	
	private int[] resourcesInstances;
	private int id;
	private int currentRequest = -1;
	private int processRequestTime;
	private int processUsageTime;
	/**
	 * Constructor, builds this process.
	 * */
	public Process(int numberOfResources,int requestTime, int usageTime, SimulatorFacade simulator) {
		this.resourcesInstances = new int[numberOfResources];
		this.id = ++lastId;
		this.processRequestTime = requestTime;
		this.processUsageTime = usageTime;
		
		this.simulator = simulator;
		this.simulator.log(LogType.PROCESS_CREATION, "Processo"+this.id+" criado");
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
		return "Processo: " + this.id;
	}

	public int getCurrentRequest() {
		return currentRequest;
	}
	
}
