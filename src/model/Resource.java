package model;

import java.util.concurrent.Semaphore;

/**
 * Represents a resource on the operational system. I.E. an printer, a hard driver, etc.
 * @author ajeferson
 * */
public class Resource {
	
	private static int lastId = 0;
	
	private String name;
	private int id;
	private int amount;
	
	private Semaphore availableResourse;
	
	public Resource(String name, int amount) {
		this.name = name;
		this.amount = amount;
		this.id = ++lastId;
		this.availableResourse = new Semaphore(amount,true);
	}
	
	
	public Semaphore getAvailableResourse() {
		return availableResourse;
	}


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
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "Id: " + this.id + "\nName: " + this.name + "\nAmount: " + this.amount;
	}
	
}
