package model;

/**
 * Represents a resource on the operational system. I.E. an printer, a hard driver, etc.
 * @author ajeferson
 * */
public class Resource {
	
	private static int lastId = 0;
	
	private String name;
	private int id;
	private final int amount;
	private int resourceType;
	
	private int availableInstances;
	private CoolSemaphore available;

	/**
	 * Constructor, builds this resource.
	 * */
	public Resource(String name, int amount) {
		this.name = name;
		this.amount = amount;
		this.available = new CoolSemaphore(this.amount);
		this.availableInstances = this.amount;
		this.id = ++lastId;
		
	}

	public int getAvailableInstances() {
		return this.availableInstances;
	}
	
	public void decrementInstances() {
		this.availableInstances--;
	}
	
	public void incrementInstances() {
		this.availableInstances++;
	}
	
	/**
	 * Takes an instance of this resource (downs the semaphore).
	 * */
	public void takeInstance() {
		this.available.down();
	}
	
	/**
	 * Returns the number of available permits of this resource.
	 * */
	public int availableInstances() {
		return this.available.availablePermits();
	}
	
	/**
	 * Releases an instance of the resource. (ups the semaphore).
	 * */
	public void releaseInstance() {
		this.available.up();
	}
	
	/**
	 * Releases instances of this resource.
	 * @param amount The number of instances to release.
	 * */
	public void releaseInstances(int amount) {
		this.available.up(amount);
	}
	

	// Getters and Setters
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}
	
	@Override
	public String toString() {
		return "Id: " + this.id + "\nName: " + this.name + "\nAmount: " + this.amount;
	}
	
}
