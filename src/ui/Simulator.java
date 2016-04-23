package ui;

import javax.swing.JFrame;

import util.Constants;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Color;

/** This class is the main UI of the project, that actually renders tue UI elements. */
public class Simulator extends JFrame {

	private static final long serialVersionUID = -845469012426866915L;
	
	public Simulator() {
		setResizable(false);
		this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel startUpZone = new JPanel();
		startUpZone.setBounds(10, 11, 674, 86);
		getContentPane().add(startUpZone);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 108, 674, 344);
		getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 463, 674, 98);
		getContentPane().add(panel_1);
		this.setVisible(true);
	}
}
