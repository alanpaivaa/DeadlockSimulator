package ui;

import javax.swing.JFrame;

import util.Constants;

/** This class is the main UI of the project, that actually renders tue UI elements. */
public class Simulator extends JFrame {

	private static final long serialVersionUID = -845469012426866915L;
	
	public Simulator() {
		this.setSize(Constants.WINDOW_SIZE, Constants.WINDOW_SIZE);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
