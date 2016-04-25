package ui;

import javax.swing.JFrame;

import util.Constants;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
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
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

	private SimulatorData simulatorDataWindow = new SimulatorData(this);

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
		setTitle("Simulador de Deadlocks");
		setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel root = new JPanel();
		root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
		getContentPane().add(root, BorderLayout.CENTER);
		root.setBorder(new EmptyBorder(Constants.WINDOW_GAPS, Constants.WINDOW_GAPS, Constants.WINDOW_GAPS, Constants.WINDOW_GAPS));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 2, Constants.WINDOW_GAPS, 0));
		root.add(topPanel);
		topPanel.setPreferredSize(new Dimension(Constants.WINDOW_WIDTH, 130));

		/*Start up zone components*/
		JPanel startUpZone = new JPanel();
		startUpZone.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		startUpZone.setLayout(new BorderLayout());
		topPanel.add(startUpZone);

		JLabel lbSetupSystem = this.horizontallyCenteredJLabel("Configurar Sistema");
		startUpZone.add(lbSetupSystem, BorderLayout.NORTH);
		
		JPanel m = new JPanel();
		m.setLayout(new GridBagLayout());
		startUpZone.add(m, BorderLayout.CENTER);
		
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(1, 2));
		center.setPreferredSize(new Dimension(300, 35)); // TODO Magic Numbers
		GridBagConstraints c = new GridBagConstraints();
		m.add(center, c);


		JLabel lbResourcesTypes = new JLabel("Número de Recursos");
		lbResourcesTypes.setHorizontalAlignment(SwingConstants.RIGHT);
		center.add(lbResourcesTypes);
		
		tfTypesResources = new JTextField();
		center.add(tfTypesResources);
		
		JPanel south = new JPanel();
		south.setLayout(new FlowLayout(FlowLayout.CENTER));
		startUpZone.add(south, BorderLayout.SOUTH);
		
		this.btnStartSimulation = new JButton("Iniciar Simulação");
		this.btnStartSimulation.addActionListener(this);
		south.add(btnStartSimulation);

		btnStopSimulation = new JButton("Parar Simulação");
		btnStopSimulation.setEnabled(false);
		south.add(btnStopSimulation);
		
		/*Process creation zone components*/
		JPanel processCreationZone = new JPanel();
		processCreationZone.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		processCreationZone.setLayout(new BorderLayout());
		topPanel.add(processCreationZone);

		JLabel lbProcessCreator = this.horizontallyCenteredJLabel("Criador de Processos");
		processCreationZone.add(lbProcessCreator, BorderLayout.NORTH);

		JPanel middle = new JPanel();
		middle.setLayout(new GridLayout(2, 2));
		middle.setBorder(new EmptyBorder(0, 40, 0, 40));
		processCreationZone.add(middle, BorderLayout.CENTER);
		
		JLabel lbRequestTime = new JLabel("Intervalo de solicitações");
		lbRequestTime.setHorizontalAlignment(SwingConstants.RIGHT);
		middle.add(lbRequestTime);
		
		tfRequestTime = new JTextField();
		tfRequestTime.setEnabled(false);
		middle.add(tfRequestTime);
		
		JLabel lbUsageTime = new JLabel("Tempo de Utilização");
		lbUsageTime.setHorizontalAlignment(SwingConstants.RIGHT);
		middle.add(lbUsageTime);

		tfUsageTime = new JTextField();
		tfUsageTime.setEnabled(false);
		middle.add(tfUsageTime);
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new FlowLayout(FlowLayout.CENTER));
		processCreationZone.add(bottom, BorderLayout.SOUTH);
		
		btnCreateProcess = new JButton("Criar processo");
		btnCreateProcess.addActionListener(this);
		btnCreateProcess.setEnabled(false);
		bottom.add(btnCreateProcess);

		btnDeleteProcess = new JButton("Excluir Processo");
		btnDeleteProcess.setEnabled(false);
		btnDeleteProcess.addActionListener(this);
		bottom.add(btnDeleteProcess);
		
		root.add(Box.createRigidArea(new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_GAPS)));

		/*Status zone components*/
		JPanel statusZone = new JPanel();
		statusZone.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		statusZone.setPreferredSize(new Dimension(Constants.WINDOW_WIDTH, 0 /*270*/));
		root.add(statusZone);
		statusZone.setLayout(new BorderLayout());

		JPanel topStatusZone = new JPanel();
		topStatusZone.setLayout(new GridLayout(1, 5));
		statusZone.add(topStatusZone, BorderLayout.NORTH);
		
		JLabel lbBlocked = this.horizontallyCenteredJLabel("Bloqueados");
		JLabel lbRequest = this.horizontallyCenteredJLabel("Solicita��o");
		JLabel lbExecution = this.horizontallyCenteredJLabel("Executando");
		JLabel lbRelease = this.horizontallyCenteredJLabel("Libera��o");
		JLabel lbCreation = this.horizontallyCenteredJLabel("Cria��o");
		
		topStatusZone.add(lbCreation);
		topStatusZone.add(lbBlocked);
		topStatusZone.add(lbRequest);
		topStatusZone.add(lbExecution);
		topStatusZone.add(lbRelease);
		
		JPanel bottomStatusZone = new JPanel();
		bottomStatusZone.setLayout(new GridLayout(1, 5));
		statusZone.add(bottomStatusZone, BorderLayout.CENTER);
		
		this.taProcessCreation = new CoolTextArea();
		this.taProcessExecution = new CoolTextArea();
		this.taProcessRelease = new CoolTextArea();
		this.taProcessRequest = new CoolTextArea();
		this.taProcessBlocked = new CoolTextArea();
		
		bottomStatusZone.add(this.taProcessCreation);
		bottomStatusZone.add(this.taProcessBlocked);
		bottomStatusZone.add(this.taProcessRequest);
		bottomStatusZone.add(this.taProcessExecution);
		bottomStatusZone.add(this.taProcessRelease);
		//bottomStatusZone.add(this.taProcessExecution);
		
		
		
		
		root.add(Box.createRigidArea(new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_GAPS)));
		
		/*Deadlock zone components*/
		JPanel deadlockZone = new JPanel();
		deadlockZone.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		deadlockZone.setPreferredSize(new Dimension(Constants.WINDOW_WIDTH, /*140*/400));
		root.add(deadlockZone);
		deadlockZone.setLayout(new BorderLayout());

		JLabel lblDeadlockProcess = this.horizontallyCenteredJLabel("Processos em Deadlock");
		deadlockZone.add(lblDeadlockProcess, BorderLayout.NORTH);
		
		this.taDeadlockProcess = new CoolTextArea();
		deadlockZone.add(this.taDeadlockProcess,  BorderLayout.CENTER);

		this.pack();
		this.setVisible(true);
		
		// Starting the SO
		this.setupOpeationalSystem();
	}
	
	/**
	 * Returns a label that is horizontally centered.
	 * */
	private JLabel horizontallyCenteredJLabel(String text) {
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}

	/**
	 * Creates and starts an OperationalSystem.
	 * */
	private void setupOpeationalSystem() {
		this.operationalSystem = new OperationalSystem(2, this); // TODO Mocked interval
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
			final int index = this.operationalSystem.getIndexOfProcessWihPid(pid);
			
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
	public void simulatorSetupDidSucceedWithResources(ArrayList<Resource> resources) {

		btnStartSimulation.setEnabled(false);
		btnStopSimulation.setEnabled(true);
		tfTypesResources.setEnabled(false);

		btnCreateProcess.setEnabled(true);
		tfRequestTime.setEnabled(true);
		tfUsageTime.setEnabled(true);
		
		this.simulatorDataWindow.setVisible(true);
		this.simulatorDataWindow.setResources(resources);
		
		this.operationalSystem.addResources(resources);


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
	
	@Override
	public int randomResourceIndexForProcessWithId(int id) {
		return this.operationalSystem.randomResourceIndexForProcessWithId(id);
	}
	
	
	// Other methods
	
	/**
	 * Displays a message of invalid pid.
	 * */
	private void showInvalidPidMessage() {
		JOptionPane.showMessageDialog(this, "Pid inválido!");
	}

	@Override
	public Resource getResourceById(int id) {
		
		return this.operationalSystem.getResourceById(id);
	}

	@Override
	public Object[][] getProcessesData() {
		
		return this.operationalSystem.retrieveProcessesData();
	}

}
