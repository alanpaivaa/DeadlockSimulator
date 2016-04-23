package model;

/**
 * Abstracts methos of the Thread class.
 * @author ajeferson
 * */
public class CoolThread extends Thread {

	/**
	 * Sleeps the thread in which the caller is running.
	 * @param The time in seconds for caller thread to sleep.
	 * */
	public static void sleep(long seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
