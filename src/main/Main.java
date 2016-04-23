package main;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import ui.Simulator;

public class Main {

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					new Simulator();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
