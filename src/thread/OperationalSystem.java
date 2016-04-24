package thread;

import java.util.ArrayList;

import enums.LogType;
import interfaces.SimulatorFacade;
import model.CoolThread;
import model.Resource;

/**
 * Represents the Operational System.
 * This class is responsible for checking the deadlocks on the simulation.
 * @author ajeferson
 * */
public class OperationalSystem extends CoolThread {

	private ArrayList<Resource> resources;
	private ArrayList<Process> processes = new ArrayList<Process>();
	
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
	// TODO mutex stuff.
	private boolean checkForDeadlock() {
		
		int n = this.processes.size();
		int m = this.resources.size();
		
		// Available
		int a[] = new int[m];
		for(int i=0; i<m; i++) {
			a[i] = this.resources.get(i).availableInstances();
		}
		
		// Current requests
		int c[] = new int[n];
		for(int i=0; i<n; i++) {
			c[i] = this.processes.get(i).getCurrentRequest();
		}
		
//		boolean checking;
//		do {
//			checking = false;
//			int i = 0;
//			while(i<n) {
//				if(!checking && c[i] <)
//			}
//			
//		} while(checking);
		
		return true;
	}

	
	// Getters and Setters
	
	public int getInterval() {
		return interval;
	}
	
	public void addResource(Resource resource) {
		this.resources.add(resource);
	}
	
	public void addResources(ArrayList<Resource> resources) {
		this.resources.addAll(resources);
	}
	
	public void addProcess(Process process) {
		this.processes.add(process);
	}
	
	public void addProcesses(ArrayList<Process> processes) {
		this.processes.addAll(processes);
	}
}
