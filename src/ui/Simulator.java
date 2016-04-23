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
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

/** This class is the main UI of the project, that actually renders tue UI elements. */
public class Simulator extends JFrame {

	private static final long serialVersionUID = -845469012426866915L;
	private JTextField tfTypesResources;
	
	public Simulator() {
		setResizable(false);
		this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel startUpZone = new JPanel();
		startUpZone.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		startUpZone.setBounds(10, 11, 787, 86);
		getContentPane().add(startUpZone);
		startUpZone.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lbResourcesTypes = new JLabel("N\u00FAmero de Tipos de Recursos");
		startUpZone.add(lbResourcesTypes);
		
		tfTypesResources = new JTextField();
		startUpZone.add(tfTypesResources);
		tfTypesResources.setColumns(10);
		
		JButton btnStartSimulation = new JButton("Iniciar Simula\u00E7\u00E3o");
		startUpZone.add(btnStartSimulation);
		
		JButton btnStopSimulation = new JButton("Parar Simula\u00E7\u00E3o");
		btnStopSimulation.setEnabled(false);
		startUpZone.add(btnStopSimulation);
		
		JPanel statusZone = new JPanel();
		statusZone.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		statusZone.setBounds(10, 108, 787, 306);
		getContentPane().add(statusZone);
		statusZone.setLayout(null);
		
		JTextArea taProcessCreation = new JTextArea();
		taProcessCreation.setBounds(475, 36, 145, 259);
		statusZone.add(taProcessCreation);
		
		JTextArea taProcessBlocked = new JTextArea();
		taProcessBlocked.setBounds(320, 36, 145, 259);
		statusZone.add(taProcessBlocked);
		
		JTextArea taProcessRequest = new JTextArea();
		taProcessRequest.setBounds(165, 36, 145, 259);
		statusZone.add(taProcessRequest);
		
		JTextArea taProcessRelease = new JTextArea();
		taProcessRelease.setBounds(10, 36, 145, 259);
		statusZone.add(taProcessRelease);
		
		JLabel lbBlocked = new JLabel("Bloqueados");
		lbBlocked.setBounds(663, 11, 81, 14);
		statusZone.add(lbBlocked);
		
		JLabel lbRequest = new JLabel("Solicita\u00E7\u00E3o");
		lbRequest.setBounds(203, 11, 63, 14);
		statusZone.add(lbRequest);
		
		JLabel lbExecution = new JLabel("Executando");
		lbExecution.setBounds(357, 11, 81, 14);
		statusZone.add(lbExecution);
		
		JLabel lbRelease = new JLabel("Libera\u00E7\u00E3o");
		lbRelease.setBounds(525, 11, 63, 14);
		statusZone.add(lbRelease);
		
		JLabel lbCreation = new JLabel("Cria\u00E7\u00E3o");
		lbCreation.setBounds(57, 11, 46, 14);
		statusZone.add(lbCreation);
		
		JTextArea taProcessExecution = new JTextArea();
		taProcessExecution.setBounds(630, 36, 145, 259);
		statusZone.add(taProcessExecution);
		
		JPanel deadlockZone = new JPanel();
		deadlockZone.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		deadlockZone.setBounds(10, 425, 787, 136);
		getContentPane().add(deadlockZone);
		deadlockZone.setLayout(null);
		
		JTextArea taDeadlockProcess = new JTextArea();
		taDeadlockProcess.setBounds(10, 26, 767, 99);
		deadlockZone.add(taDeadlockProcess);
		
		JLabel lblProcessosEmDeadlock = new JLabel("Processos em Deadlock");
		lblProcessosEmDeadlock.setBounds(342, 9, 150, 14);
		deadlockZone.add(lblProcessosEmDeadlock);
		this.setVisible(true);
	}
}
