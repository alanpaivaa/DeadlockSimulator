package thread;

import java.util.ArrayList;
import java.util.Random;

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

	private ArrayList<Resource> resources = new ArrayList<Resource>();
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

			this.simulator.getMutex().down();
			ArrayList<Integer> deadlockedProcesses = this.deadlockedProcesses();
			this.simulator.getMutex().up();

			if(deadlockedProcesses != null) {
				this.simulator.log(LogType.DEADLOCK, this.deadlockString(deadlockedProcesses));
				System.out.println(this.deadlockString(deadlockedProcesses));
			}

			sleep(this.interval);
		}
	}

	/**
	 * Checks if there's a deadlock happening.
	 * @return null If there are no processes in deadlock.
	 * @return ArrayList<Integer> With the pids of the processes in deadlock, if any.
	 * */
	// TODO mutex stuff.
	private ArrayList<Integer> deadlockedProcesses() {

		int n = this.processes.size();
		int m = this.resources.size();

		// Available
		int a[] = new int[m];
		for(int i = 0; i < m; i++) {
			a[i] = this.resources.get(i).availableInstances();
		}

		// Current
		int c[][] = new int[n][m];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				c[i][j] = this.processes.get(i).getResourcesInstances()[j];
			}
		}

		// Requests
		int r[] = new int[n];
		for(int i = 0; i < n; i++) {
			r[i] = this.processes.get(i).getCurrentRequest();
		}

		int runnableProcesses;
		int finishedProcesses = 0;

		// Actually checking for deadlocks
		// -1 Means that the process does not want any resource
		// -2 Means that the process was already simulqted by the algorithm
		do {
			runnableProcesses = 0;
			for(int i = 0; i < n; i++) {
				if(r[i] == -1 || r[i] >= 0 && a[r[i]] > 0) {
					for(int k = 0; k < m; k++) {
						a[k] += c[i][k];
						c[i][k] = 0;
					}
					finishedProcesses++;
					runnableProcesses++;
					r[i] = -2;
				}
			}
		} while(n - finishedProcesses > 1 && runnableProcesses > 0);

		if(n - finishedProcesses <= 1) {
			return null;
		}

		ArrayList<Integer> processesInDeadlock = new ArrayList<Integer>();
		for(int i = 0; i < n; i++) {
			if(r[i] >= 0) {
				processesInDeadlock.add(this.processes.get(i).getPid());
			}
		}

		return processesInDeadlock;

	}

	/**
	 * Builds a string from a list of deadlock processes' pids.
	 * */
	private String deadlockString(ArrayList<Integer> pids) {
		String str = "Deadlock:";
		for(Integer pid : pids) {
			str += (" P" + pid);
		}
		return str;
	}

	/**
	 * Kills the process at the given index.
	 * @return true If all processes where killed.
	 * */
	public void killProcessAtIndex(int index) {

		this.simulator.getMutex().down();

		// Telling the process that it does not need to run anymore

		Process process = this.processes.get(index);

		this.processes.remove(index);

		process.kill();
		
		// If process is blocked
		if(process.getCurrentRequest() >= 0) {
			this.resources.get(process.getCurrentRequest()).deadProcesses++;

		}
		
		this.resources.get(process.getCurrentRequest()).releaseInstance();

		this.simulator.getMutex().up();


	}

	// Getters and Setters

	public int getInterval() {
		return interval;
	}

	public SimulatorFacade getSimulator() {
		return simulator;
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


	
	 /**
	 * Rolls the system back to an initial state
	 */
	public void restartSystem()
	 {
		 for (Process process : processes) {
			 process.setKeepAlice(false);
		 }
		 
		 processes.clear();
		 resources.clear();
		 
	 }


	
	/**Returns a resouce with a given id
	 * @param id The id of the resouce
	 * @return The resouce or null if no resource has that id
	 */
	public Resource getResourceById(int id)
	{
		for (Resource rs : resources) {
			if(rs.getId()==id)
				return rs;
		}
		return null;
	}
	
	/**
	 * Returns the index of a process with the given pid.
	 * */
	public int getIndexOfProcessWihPid(int pid) {
		for(int i = 0; i < this.processes.size(); i++) {
			if(this.processes.get(i).getPid() == pid) {
				return i;
			}
		}
		return -1;
	}

	public int getProccessCount() {
		return this.processes.size();
	}

	/**Gets the Current Alocation data from the processes Array List
	 * @return An bidimensional object  array with the current alocation info
	 */
	public Object[][] retrieveProcessesData() {
		Object[][] data = new Object[processes.size()][resources.size()];
		int i = 0, j;
		for (Process proc : processes) {
			for(j=0; j<resources.size();j++)
			{
				data[i][j] = proc.getResourcesInstances()[j];
			}
			i++;

		}
		return data;
	}

	/**Gets the request data from the processes Array List
	 * @return An bidimensional object  array with the request info 
	 */
	public Object[][] retrieveProcessesRequestData() {
		Object[][] data = new Object[processes.size()][resources.size()];
		int i = 0, j;
		for (Process proc : processes) {
			for(j=0; j<resources.size();j++)
			{
				if(j == (proc.getCurrentRequest()-1))
					data[i][j] = 1;
				else
					data[i][j] = 0;	
			}
			i++;

		}
		return data;
	}

	/**
	 * Returns the index of a random possible resource for the process with the given id.
	 * @param id The id of the process that wants a ramdom resource.
	 * */
	public int randomResourceIndexForProcessWithId(int id) {

		Process process;

		// Retrieving the correct process
		int i = 0;
		while(i < this.processes.size() && this.processes.get(i).getPid() != id) {
			i++;
		}

		if(i >= this.processes.size()) {
			return -1;
		}

		process = this.processes.get(i);

		// Checking for possible resources
		ArrayList<Integer> possible = new ArrayList<Integer>();
		for(int j = 0; j < this.resources.size(); j++) {
			if(process.getResourcesInstances()[j] < this.resources.get(j).getAmount()) {
				possible.add(j);
			}
		}

		if(possible.isEmpty()) {
			return -1;
		}

		Random random = new Random();
		return possible.get(random.nextInt(possible.size()));

	}

}
