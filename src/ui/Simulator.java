package ui;

import javax.swing.JFrame;

import util.Constants;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;

import enums.LogType;
import interfaces.SimulatorFacade;
import interfaces.SimulatorSetupDelegate;
import model.CoolSemaphore;
import model.CoolTextArea;
import model.Resource;
import thread.OperationalSystem;
import thread.Process;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.Random;

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

	private SimulatorData simulatorDataWindow;

	private JButton btnStopSimulation;
	private JButton btnCreateProcess;
	private JButton btnDeleteProcess;

	private CoolTextArea taDeadlockProcess, taProcessRelease, taProcessExecution;
	private CoolTextArea taProcessRequest, taProcessCreation, taProcessBlocked;

	// Core
	private OperationalSystem operationalSystem; 
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

		btnStopSimulation = new JButton("Parar Simulação");

		btnStopSimulation.setBounds(234, 96, 142, 23);
		btnStopSimulation.setEnabled(false);
		startUpZone.add(btnStopSimulation);

		tfTypesResources = new JTextField();
		tfTypesResources.setBounds(160, 65, 86, 20);
		startUpZone.add(tfTypesResources);
		tfTypesResources.setColumns(10);

		JLabel lbResourcesTypes = new JLabel("Número de Recursos");
		lbResourcesTypes.setBounds(0, 40, 386, 14);
		lbResourcesTypes.setHorizontalAlignment(SwingConstants.CENTER);
		startUpZone.add(lbResourcesTypes);

		JLabel lbSetupSystem = new JLabel("Configurar Sistema");
		lbSetupSystem.setBounds(0, 11, 386, 14);
		lbSetupSystem.setHorizontalAlignment(SwingConstants.CENTER);
		startUpZone.add(lbSetupSystem);

		/*Status zone components*/
		JPanel statusZone = new JPanel();
		statusZone.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		statusZone.setBounds(10, 152, 787, 262);
		getContentPane().add(statusZone);
		statusZone.setLayout(null);

		this.taProcessExecution = new CoolTextArea(630, 36, 145, 215);
		statusZone.add(this.taProcessExecution);

		this.taProcessRelease = new CoolTextArea(475, 36, 145, 215);
		statusZone.add(this.taProcessRelease);

		this.taProcessExecution = new CoolTextArea(320, 36, 145, 215);
		statusZone.add(this.taProcessExecution);

		this.taProcessRequest = new CoolTextArea(165, 36, 145, 215);
		statusZone.add(this.taProcessRequest);

		this.taProcessCreation = new CoolTextArea(10, 36, 145, 215);
		statusZone.add(this.taProcessCreation);

		this.taProcessBlocked = new CoolTextArea(630, 36, 145, 215);
		statusZone.add(this.taProcessBlocked);

		JLabel lbBlocked = new JLabel("Bloqueados");
		lbBlocked.setBounds(630, 11, 145, 14);
		lbBlocked.setHorizontalAlignment(SwingConstants.CENTER);
		statusZone.add(lbBlocked);

		JLabel lbRequest = new JLabel("Solicitação");
		lbRequest.setBounds(165, 11, 145, 14);
		lbRequest.setHorizontalAlignment(SwingConstants.CENTER);
		statusZone.add(lbRequest);

		JLabel lbExecution = new JLabel("Executando");
		lbExecution.setBounds(320, 11, 145, 14);
		lbExecution.setHorizontalAlignment(SwingConstants.CENTER);
		statusZone.add(lbExecution);

		JLabel lbRelease = new JLabel("Liberação");
		lbRelease.setBounds(475, 11, 145, 14);
		lbRelease.setHorizontalAlignment(SwingConstants.CENTER);
		statusZone.add(lbRelease);

		JLabel lbCreation = new JLabel("Criação");
		lbCreation.setBounds(10, 11, 145, 14);
		lbCreation.setHorizontalAlignment(SwingConstants.CENTER);
		statusZone.add(lbCreation);


		/*Deadlock zone components*/
		JPanel deadlockZone = new JPanel();
		deadlockZone.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		deadlockZone.setBounds(10, 425, 787, 136);
		getContentPane().add(deadlockZone);
		deadlockZone.setLayout(null);

		this.taDeadlockProcess = new CoolTextArea(10, 26, 767, 99);
		deadlockZone.add(this.taDeadlockProcess);

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
		lbProcessCreator.setBounds(0, 5, 388, 14);
		lbProcessCreator.setHorizontalAlignment(SwingConstants.CENTER);
		processCreationZone.add(lbProcessCreator);

		btnCreateProcess = new JButton("Criar processo");
		btnCreateProcess.addActionListener(this);
		btnCreateProcess.setEnabled(false);
		btnCreateProcess.setBounds(10, 96, 121, 23);
		processCreationZone.add(btnCreateProcess);

		btnDeleteProcess = new JButton("Excluir Processo");
		btnDeleteProcess.setEnabled(false);
		btnDeleteProcess.setBounds(227, 96, 151, 23);
		btnDeleteProcess.addActionListener(this);
		processCreationZone.add(btnDeleteProcess);

		JLabel lbRequestTime = new JLabel("Intervalo de solicitações");
		lbRequestTime.setBounds(55, 30, 160, 14);
		lbRequestTime.setHorizontalAlignment(SwingConstants.RIGHT);
		processCreationZone.add(lbRequestTime);

		JLabel lbUsageTime = new JLabel("Tempo de Utilização");
		lbUsageTime.setBounds(55, 58, 160, 14);
		lbUsageTime.setHorizontalAlignment(SwingConstants.RIGHT);
		processCreationZone.add(lbUsageTime);

		tfRequestTime = new JTextField();
		tfRequestTime.setEnabled(false);
		tfRequestTime.setBounds(220, 30, 86, 20);
		processCreationZone.add(tfRequestTime);
		tfRequestTime.setColumns(10);

		tfUsageTime = new JTextField();
		tfUsageTime.setEnabled(false);
		tfUsageTime.setBounds(220, 55, 86, 20);
		processCreationZone.add(tfUsageTime);
		tfUsageTime.setColumns(10);
		this.setVisible(true);

		// Starting the SO
		this.setupOpeationalSystem();
	}

	/**
	 * Creates and starts an OperationalSystem.
	 * */
	private void setupOpeationalSystem() {
		this.operationalSystem = new OperationalSystem(1, this); // TODO Mocked interval
		this.operationalSystem.start();
	}


	// ActionListener implementations.

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnStartSimulation) {
			this.didClickOnBtnStartSimulation();
		} else if(e.getSource() == this.btnCreateProcess) {
			this.didClickOnBtnCreateProcess();
		} else if(e.getSource() == this.btnDeleteProcess) {
			this.didClickOnBtnDeleteProcess();
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
		new SimulatorSetup(Integer.parseInt(this.tfTypesResources.getText().trim()), this);
	}

	/**
	 * Called whenever the user clicks on the create process button.
	 * */
	private void didClickOnBtnCreateProcess() {
		if(tfRequestTime.getText().isEmpty() || tfUsageTime.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Por favor, insira todas as informações sobre o Processo");
			return;
		}
		Process process = new Process(Integer.parseInt(tfTypesResources.getText().trim()), Integer.parseInt(tfRequestTime.getText().trim()), Integer.parseInt(tfUsageTime.getText().trim()),operationalSystem.getSimulator());

		operationalSystem.addProcess(process);
		process.start();

		this.btnDeleteProcess.setEnabled(true);
	}

	/**
	 * Called whenever the user clicks on the delete process button.
	 * */
	private void didClickOnBtnDeleteProcess() {
		
		try {
			
			Integer pid = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite o pid do processo:", "Excluir Processo", JOptionPane.QUESTION_MESSAGE));
			int index = this.operationalSystem.getIndexOfProcessWihPid(pid);
			
			if(index < 0) {
				this.showInvalidPidMessage();
			}
			
			// New Thread, so the mutex stuff will never block the UI.
			new Thread(new Runnable() {
				@Override
				public void run() {
					if(operationalSystem.killProcessAtIndex(index)) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								btnDeleteProcess.setEnabled(false);
							}
						});
					}
				}
			}).start();
			
		} catch(Exception e) {
			this.showInvalidPidMessage();
		}		
		
	}
	
	


	// SimulatorSetupDelegate implementations.

	@Override
	public void simulatorSetupDidSucceedWithResources(ArrayList<Resource> resources,  SimulatorData dataWindow) {

		btnStartSimulation.setEnabled(false);
		btnStopSimulation.setEnabled(true);
		tfTypesResources.setEnabled(false);

		btnCreateProcess.setEnabled(true);
		tfRequestTime.setEnabled(true);
		tfUsageTime.setEnabled(true);

		this.operationalSystem.addResources(resources);
		this.simulatorDataWindow = dataWindow;

	}

	@Override
	public void simulatorSetupDidCancel() {
	}


	// SimulatorFacade implementations.

	public CoolSemaphore getMutex() {
		return this.mutex;
	}

	@Override
	public void log(LogType logType, String text) {

		switch (logType) {
		case PROCESS_CREATION:
			this.taProcessCreation.log(text);
			break;
		case PROCESS_REQUEST:
			this.taProcessRequest.log(text);
			simulatorDataWindow.redrawStructures(); //data structures changed, redraw panels
			break;
		case PROCESS_RUNNING:
			this.taProcessExecution.log(text);
			break;
		case RESOURCE_RELEASE:
			this.taProcessRelease.log(text);
			simulatorDataWindow.redrawStructures(); //data structures changed, redraw panels
			break;
		case RESOURCE_BLOCK:
			this.taProcessBlocked.log(text);
			break;
		case DEADLOCK:
			this.taDeadlockProcess.log(text);
			break;
		}

	}

	@Override
	public int requestResourcePos() {
		Random rand = new Random();
		return rand.nextInt(Integer.parseInt(tfTypesResources.getText().trim()));
	}

	@Override
	public Resource getResourceAt(int index) {
		return operationalSystem.getResourceAt(index);
	}
	
	
	// Other methods
	
	/**
	 * Displays a message of invalid pid.
	 * */
	private void showInvalidPidMessage() {
		JOptionPane.showMessageDialog(this, "Pid inválido!");
	}

}
