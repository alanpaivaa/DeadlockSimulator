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
		int targetResource = 0;
		while(keepAlive)
		{
				//Waits for the next time to get a resource
				sleep(1);
				tic++;
				
				if(tic%processRequestTime==0) //the time to request resources has arrived
				{
					//Selects a resource randomly
					currentRequest = this.simulator.requestResourcePos();

					//get the actual resource from the array list
					requestedResouce = this.simulator.getResourceById(currentRequest);

					
					resourcesHeld.add(requestedResouce);

					this.simulator.log(LogType.PROCESS_REQUEST, "P"+this.pid+" solicitou "+requestedResouce.getName());
					
					//if there are no resources left, the process will be blocked
					if(requestedResouce.getAvailable()==0)
					{
						this.simulator.log(LogType.RESOURCE_BLOCK, "P"+this.pid+" bloqueiou com  "+requestedResouce.getName());	
					}
					
					requestedResouce.takeInstance();
					
					//Increments the resource array
					this.resourcesInstances[currentRequest-1]++;
					
					//process runs for a certain amount of time
					this.simulator.log(LogType.PROCESS_RUNNING, "P"+this.pid+" roda com "+requestedResouce.getName());
					resourcesTimes.add(processUsageTime);
				}

				
				//Decrements all the times from the resources held
				decrementResourcesTimes(resourcesTimes);
				
				//verifies if a resource time has reached zero
				targetResource = resourcesTimesIsZero(resourcesTimes);
				
				if(targetResource!=-1)
				{
					
					resourcesTimes.remove(targetResource);

					
					//free the resource
					this.resourcesInstances[resourcesHeld.get(targetResource).getId()-1]--;
					currentRequest = -1;
					
					resourcesHeld.get(targetResource).releaseInstance();
					this.simulator.log(LogType.RESOURCE_RELEASE, "P"+this.pid+" liberou "+resourcesHeld.get(targetResource).getName());
					resourcesHeld.remove(targetResource);

					
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
	
	/** Checks if a rescorce aged enough. If its time got to zero
	 * @param resourcesTimes The times of the resources held by this process.
	 * @return The position in the array if one of the times is zero. -1 if no time is zero.
	 */
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

	/** Decrements the times saved in the array list. Age the resources.
	 * @param resourcesTimes The times of all resouces held by this process
	 */
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
