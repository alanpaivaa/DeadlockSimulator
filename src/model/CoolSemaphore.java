package model;

import java.util.concurrent.Semaphore;

/**
 * Encapsulates the usage of semaphores, just for not to handle exceptions all the time.
 * @author ajeferson
 * */
public class CoolSemaphore extends Semaphore {

	private static final long serialVersionUID = -4447916638090472724L;
	
	public CoolSemaphore(int permits) {
		super(permits, true);
	}
	
	/**
	 * Downs the semaphore.
	 * */
	public void down() {
		try {
			this.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ups the semaphore.
	 * */
	public void up() {
		this.release();
	}
	
	/**
	 * Ups the semaphore.
	 * @param permits The number of times to up this semaphore.
	 * */
	public void up(int permits) {
		this.release(permits);
	}

}
