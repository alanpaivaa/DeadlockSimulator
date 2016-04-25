package interfaces;

import java.util.ArrayList;

import model.Resource;
import ui.SimulatorData;
import ui.SimulatorSetup;

/**
 * Delegate for things the SimulatorSetup events.
 * @see SimulatorSetup
 * @author ajeferson
 * */
public interface SimulatorSetupDelegate {
	
	/**
	 * When the user clicks on the Ok button.
	 * */
	public void simulatorSetupDidSucceedWithResources(ArrayList<Resource> resources);
	
	/**
	 * When the user clicks on the Cancel button.
	 * */
	public void simulatorSetupDidCancel();
	
}
