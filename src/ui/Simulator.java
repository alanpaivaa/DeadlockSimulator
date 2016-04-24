package ui;

import javax.swing.JFrame;

import util.Constants;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import enums.LogType;
import interfaces.SimulatorFacade;
import interfaces.SimulatorSetupDelegate;
import model.CoolSemaphore;
import model.Resource;
import thread.OperationalSystem;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

/** This class is the main UI of the project, that actually renders tue UI elements. */
/**
 * @author TARDIS
 *
 */
public class Simulator extends JFrame implements ActionListener, SimulatorSetupDelegate, SimulatorFacade {

	private static final long serialVersionUID = -845469012426866915L;

	// View Attributes
	private JTextField tfRequestTime;
	private JTextField tfUsageTime;
	private JButton btnStartSimulation;
	private JTextField tfTypesResources;
	private JButton btnStopSimulation;
	
	// Core
	private ArrayList<Resource> resources;
	private ArrayList<Process> processes = new ArrayList<Process>();
	private OperationalSystem operationalSystem = new OperationalSystem(5); // TODO Mocked interval
	private CoolSemaphore mutex = new CoolSemaphore(1);
	

	/**
	 *  Class creator
	 */
	public Simulator() {

		/*Main window UI setup*/
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

		this.btnStartSimulation = new JButton("Iniciar Simulação");
		this.btnStartSimulation.addActionListener(this);
		btnStartSimulation.setBounds(20, 96, 142, 23);
		startUpZone.add(btnStartSimulation);

		btnStopSimulation = new JButton("Parar Simula\u00E7\u00E3o");
		btnStopSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
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


	// ActionListener implementations.

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnStartSimulation) {
			this.didClickOnBtnStartSimulation();
		}
	}


	// Button clicks

	/**
	 * Called whenever the user clicks on the start simulation button.
	 * */
	private void didClickOnBtnStartSimulation() {

		// Validating the input data
		if(tfTypesResources.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, insira o número de tipos de recursos");
			return;
		} else {
			if(Integer.parseInt(tfTypesResources.getText().trim()) > 10) {
				JOptionPane.showMessageDialog(null, "Número máximo de tipos de recursos é 10, insira um valor menor");
				return;
			}
		}

		// Showing the SimulatorSetup
		SimulatorSetup simulatorSetup = new SimulatorSetup();
		simulatorSetup.setDelegate(this);
		simulatorSetup.setResourceNumber(Integer.parseInt(this.tfTypesResources.getText().trim()));
		simulatorSetup.setVisible(true);
	}


	// SimulatorSetupDelegate implementations.

	@Override
	public void simulatorSetupDidSucceedWithResources(ArrayList<Resource> resources) {
		this.resources = resources;
		btnStartSimulation.setEnabled(false);
		btnStopSimulation.setEnabled(true);
		tfTypesResources.setEnabled(false);
		//System.out.println("Resources received");
	}

	@Override
	public void simulatorSetupDidCancel() {
	}


	// SimulatorFacade implementations.
	
	@Override
	public Process processAtIndex(int index) {
		return this.processes.get(index);
	}
	
	public CoolSemaphore getMutex() {
		return this.mutex;
	}

	@Override
	public void log(LogType logType, String text) {
		switch (logType) {
		case PROCESS_CREATION:
			// Do something
			break;
		case PROCESS_REQUEST:
			break;
		case PROCESS_RUNNING:
			break;
		case RESOURCE_RELEASE:
			break;
		case RESOURCE_BLOCK:
			break;
		}
	}

}
