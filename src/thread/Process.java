package thread;

import java.util.ArrayList;
import java.util.LinkedList;

import enums.LogType;
import interfaces.SimulatorFacade;
import model.CoolThread;
import model.Resource;

/** This class is responsible for requesting resources in intervals, simulating a real SO process.  */
public class Process extends CoolThread {
	
	private static int lastPid = 0;
	
	private SimulatorFacade simulator;
	
	private int[] resourcesInstances;
	private LinkedList<Resource> resourcesHeld = new LinkedList<>();
	private ArrayList<Integer> resourcesTimes = new ArrayList<Integer>(); 
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
		int tic = 0;
		int toc = 0;
		while(keepAlive)
		{
				//Waits for the next time to get a resource
				sleep(1);
				tic++;
				
				if(tic%processRequestTime==0) //the time to request resources arrived
				{
					//Selects a resource randomly
					currentRequest = this.simulator.requestResourcePos();
					
					//Increments the resource array
					
					
					//get the actual resource from the array list
					requestedResouce = this.simulator.getResourceAt(currentRequest);
					
					this.resourcesInstances[requestedResouce.getId()-1]++;
					
					resourcesHeld.add(requestedResouce);
					
					this.simulator.log(LogType.PROCESS_REQUEST, "P"+this.pid+" solicitou "+requestedResouce.getName());
					
					//if there are no resources left, the process will be blocked
					if(requestedResouce.getAvailable()==0)
					{
						this.simulator.log(LogType.RESOURCE_BLOCK, "P"+this.pid+" bloqueiou com  "+requestedResouce.getName());	
					}
					
					requestedResouce.takeInstance();
					//process runs for a certain amount of time
					this.simulator.log(LogType.PROCESS_RUNNING, "P"+this.pid+" roda com "+requestedResouce.getName());
					resourcesTimes.add(processUsageTime);
				}
				
				
				decrementResourcesTimes(resourcesTimes);
				
				toc = resourcesTimesIsZero(resourcesTimes);
				
				if(toc!=-1)
				{
					
					resourcesTimes.remove(toc);
					
					//free the resource
					this.resourcesInstances[resourcesHeld.get(toc).getId()-1]--;
					currentRequest = -1;
					
					resourcesHeld.get(toc).releaseInstance();
					resourcesHeld.remove(toc);
					
					this.simulator.log(LogType.RESOURCE_RELEASE, "P"+this.pid+" liberou "+requestedResouce.getName());
					
				}
				
			
		}
		
		this.simulator.log(LogType.PROCESS_CREATION, "P" + this.pid + " finalizou");
		
	}
	
	/**
	 * Acts like a setKeepAlive(false).
	 * */
	public void kill() {
		this.keepAlive = false;
	}
	
	private int resourcesTimesIsZero(ArrayList<Integer> resourcesTimes) {
		int i = 0;
		for (Integer time : resourcesTimes) {
			if(time==0)
			{
				return i;
			}
			i++;
		}
		return -1;
	}

	private void decrementResourcesTimes(ArrayList<Integer> resourcesTimes) {
		int i = 0;
		for (Integer time : resourcesTimes) {
			
			resourcesTimes.set(i, --time);
			i++;
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

	public void setKeepAlice(boolean b) {
		keepAlive = false;
		
	}
	
}
