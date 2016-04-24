package thread;

import enums.LogType;
import interfaces.SimulatorFacade;
import model.Resource;

/** This class is responsible for requesting resources in intervals, simulating a real SO process.  */
public class Process extends Thread {
	
	private static int lastPid = 0;
	
	private SimulatorFacade simulator;
	
	private int[] resourcesInstances;
	private int pid;
	private int currentRequest = -1;
	private int processRequestTime;
	private int processUsageTime;
	
	private Resource requestedResouce;
	private boolean keepAlive = true;
	/**
	 * Constructor, builds this process.
	 * */
	public Process(int numberOfResources,int requestTime, int usageTime, SimulatorFacade simulator) {
		this.resourcesInstances = new int[numberOfResources];
		this.processRequestTime = requestTime;
		this.processUsageTime = usageTime;
		this.simulator = simulator;
		this.pid = ++lastPid;
		this.simulator.log(LogType.PROCESS_CREATION, "Processo"+this.pid+" criado");
		

	}
	
	@Override
	public void run() {
		while(keepAlive)
		{
			try {
				Thread.sleep(processRequestTime*1000);
				
				currentRequest = this.simulator.requestResourcePos();
				
				this.resourcesInstances[currentRequest]++;
				
				requestedResouce = this.simulator.getResourceAt(currentRequest);
				this.simulator.log(LogType.PROCESS_REQUEST, "P"+this.pid+" solicitou Recurso "+requestedResouce.getName());
				
				if(requestedResouce.getAvailable()==0)
				{
					this.simulator.log(LogType.RESOURCE_BLOCK, "P"+this.pid+" esta bloqueado com Recurso "+requestedResouce.getName());	
				}
				requestedResouce.takeInstance();
				this.simulator.log(LogType.PROCESS_RUNNING, "P"+this.pid+" roda com Recurso "+requestedResouce.getName());
				
				
				Thread.sleep(processUsageTime*1000);
				currentRequest = -1;
				requestedResouce.releaseInstance();
				this.simulator.log(LogType.RESOURCE_RELEASE, "P"+this.pid+" liberou o Recurso "+requestedResouce.getName());
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
