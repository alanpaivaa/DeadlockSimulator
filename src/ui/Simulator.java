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
import java.awt.CardLayout;

/** This class is the main UI of the project, that actually renders tue UI elements. */
/**
 * @author TARDIS
 *
 */
public class Simulator extends JFrame {

	private static final long serialVersionUID = -845469012426866915L;
	private JTextField tfTypesResources;
	private JTextField tfRequestTime;
	private JTextField tfUsageTime;
	
	/**
	 *  Class creator
	 */
	public Simulator() {
		
		/*Main window setup*/
		setTitle("Deadlock Simulator");
		setResizable(false);
		this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		/*Start up zone components*/
		JPanel startUpZone = new JPanel();
		startUpZone.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		startUpZone.setBounds(10, 11, 386, 130);
		getContentPane().add(startUpZone);
		startUpZone.setLayout(null);
		
		JButton btnStartSimulation = new JButton("Iniciar Simula\u00E7\u00E3o");
		btnStartSimulation.setBounds(20, 96, 142, 23);
		startUpZone.add(btnStartSimulation);
		
		JButton btnStopSimulation = new JButton("Parar Simula\u00E7\u00E3o");
		btnStopSimulation.setBounds(234, 96, 142, 23);
		btnStopSimulation.setEnabled(false);
		startUpZone.add(btnStopSimulation);
		
		tfTypesResources = new JTextField();
		tfTypesResources.setBounds(160, 65, 86, 20);
		startUpZone.add(tfTypesResources);
		tfTypesResources.setColumns(10);
		
		JLabel lbResourcesTypes = new JLabel("N\u00FAmero de Tipos de Recursos");
		lbResourcesTypes.setBounds(126, 40, 179, 14);
		startUpZone.add(lbResourcesTypes);
		
		JLabel lbSetupSystem = new JLabel("Configurar Sistema");
		lbSetupSystem.setBounds(148, 11, 119, 14);
		startUpZone.add(lbSetupSystem);
		
		/*Status zone components*/
		JPanel statusZone = new JPanel();
		statusZone.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		statusZone.setBounds(10, 152, 787, 262);
		getContentPane().add(statusZone);
		statusZone.setLayout(null);
		
		JTextArea taProcessCreation = new JTextArea();
		taProcessCreation.setEnabled(false);
		taProcessCreation.setEditable(false);
		taProcessCreation.setBounds(475, 36, 145, 215);
		statusZone.add(taProcessCreation);
		
		JTextArea taProcessBlocked = new JTextArea();
		taProcessBlocked.setEnabled(false);
		taProcessBlocked.setEditable(false);
		taProcessBlocked.setBounds(320, 36, 145, 215);
		statusZone.add(taProcessBlocked);
		
		JTextArea taProcessRequest = new JTextArea();
		taProcessRequest.setEnabled(false);
		taProcessRequest.setEditable(false);
		taProcessRequest.setBounds(165, 36, 145, 215);
		statusZone.add(taProcessRequest);
		
		JTextArea taProcessRelease = new JTextArea();
		taProcessRelease.setEnabled(false);
		taProcessRelease.setEditable(false);
		taProcessRelease.setBounds(10, 36, 145, 215);
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
		taProcessExecution.setEnabled(false);
		taProcessExecution.setEditable(false);
		taProcessExecution.setBounds(630, 36, 145, 215);
		statusZone.add(taProcessExecution);
		
		/*Deadlock zone components*/
		JPanel deadlockZone = new JPanel();
		deadlockZone.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		deadlockZone.setBounds(10, 425, 787, 136);
		getContentPane().add(deadlockZone);
		deadlockZone.setLayout(null);
		
		JTextArea taDeadlockProcess = new JTextArea();
		taDeadlockProcess.setEnabled(false);
		taDeadlockProcess.setEditable(false);
		taDeadlockProcess.setBounds(10, 26, 767, 99);
		deadlockZone.add(taDeadlockProcess);
		
		JLabel lblDeadlockProcess = new JLabel("Processos em Deadlock");
		lblDeadlockProcess.setBounds(342, 9, 150, 14);
		deadlockZone.add(lblDeadlockProcess);
		
		/*Process creation zone components*/
		JPanel processCreationZone = new JPanel();
		processCreationZone.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		processCreationZone.setBounds(409, 11, 388, 130);
		getContentPane().add(processCreationZone);
		processCreationZone.setLayout(null);
		
		JLabel lbProcessCreator = new JLabel("Criador de Processos");
		lbProcessCreator.setBounds(143, 5, 127, 14);
		processCreationZone.add(lbProcessCreator);
		
		JButton btnCreateProcess = new JButton("Criar processo");
		btnCreateProcess.setEnabled(false);
		btnCreateProcess.setBounds(10, 96, 121, 23);
		processCreationZone.add(btnCreateProcess);
		
		JButton btnDeleteProcess = new JButton("Excluir Processo...");
		btnDeleteProcess.setEnabled(false);
		btnDeleteProcess.setBounds(237, 96, 141, 23);
		processCreationZone.add(btnDeleteProcess);
		
		JLabel lbRequestTime = new JLabel("Intervalo de solicita\u00E7\u00F5es");
		lbRequestTime.setBounds(10, 30, 151, 14);
		processCreationZone.add(lbRequestTime);
		
		JLabel lbUsageTime = new JLabel("Tempo de Utiliza\u00E7\u00E3o");
		lbUsageTime.setBounds(10, 58, 127, 14);
		processCreationZone.add(lbUsageTime);
		
		tfRequestTime = new JTextField();
		tfRequestTime.setEnabled(false);
		tfRequestTime.setBounds(166, 30, 86, 20);
		processCreationZone.add(tfRequestTime);
		tfRequestTime.setColumns(10);
		
		tfUsageTime = new JTextField();
		tfUsageTime.setEnabled(false);
		tfUsageTime.setBounds(166, 55, 86, 20);
		processCreationZone.add(tfUsageTime);
		tfUsageTime.setColumns(10);
		this.setVisible(true);
	}
}
