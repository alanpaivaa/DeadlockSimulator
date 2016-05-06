package ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import model.Resource;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import interfaces.SimulatorFacade;

public class SimulatorData extends JFrame {

	private static final long serialVersionUID = -1802463953464142976L;
	
	//View
	private JPanel contentPane;
	private JPanel existingResources;
	private JPanel availableResources;
	private JPanel requestVector;
	private JPanel currentAlocation;
	private JLabel lblRecursosExistentes;
	private JLabel lblRecursosDisponveis;
	private JLabel lblVetorDeSolicitaes;
	private JLabel lblVetorDeAlocao;

	
	//Core
	private ArrayList<Resource> resources;
	private SimulatorFacade simulator;
	
	/**
	 * Redraws the data structures in the data panel
	 */
	public void redrawExistingStructure()
	{
			
		String[] columnNames = new String[resources.size()];
		Object[][] dataExistingResouces = new Object[1][resources.size()];
		int j = 0;
		for(Iterator<Resource> i = resources.iterator(); i.hasNext(); ) {
			Resource rs = i.next();
			columnNames[j] = rs.getName();
			dataExistingResouces[0][j++]= rs.getAmount();
		}
		
		existingResources.removeAll();
		/*Existing Resources Table*/
		existingResources.add(lblRecursosExistentes);
		JTable tableExistingResources = new JTable(dataExistingResouces,columnNames );
		existingResources.add(tableExistingResources.getTableHeader(), BorderLayout.PAGE_START);
		existingResources.add(tableExistingResources, BorderLayout.CENTER);
		existingResources.revalidate();
		
	}
	
	public void redrawAvailableStructure()
	{
			
		String[] columnNames = new String[resources.size()];
		Object[][] dataAvilableResouces = new Object[1][resources.size()];
		int j = 0;
		for(Iterator<Resource> i = resources.iterator(); i.hasNext(); ) {
			Resource rs = i.next();
			columnNames[j] = rs.getName();
			dataAvilableResouces[0][j++] = rs.getAvailableInstances();
		}
		
		availableResources.removeAll();
		/*Avialable Resources Table*/
		availableResources.add(lblRecursosDisponveis);
		JTable tableAvailableResources = new JTable(dataAvilableResouces,columnNames );
		availableResources.add(tableAvailableResources.getTableHeader(), BorderLayout.PAGE_START);
		availableResources.add(tableAvailableResources, BorderLayout.CENTER);
		availableResources.revalidate();
		
		
	}
	
	public void redrawRequestStructure()
	{
			
		String[] columnNames = new String[resources.size()+1];

		Object[][] dataRequests= simulator.getProcessesRequest();
		
		columnNames[0] = "Processos";
		
		int j = 1;
		
		for(Iterator<Resource> i = resources.iterator(); i.hasNext(); ) {
			Resource rs = i.next();
			columnNames[j++] = rs.getName();
		}
		
		requestVector.removeAll();
		/*Avialable Resources Table*/
		requestVector.add(lblVetorDeSolicitaes);
		JTable tableRequests = new JTable(dataRequests,columnNames );
		requestVector.add(tableRequests.getTableHeader(), BorderLayout.PAGE_START);
		requestVector.add(tableRequests, BorderLayout.CENTER);
		requestVector.revalidate();
		
		
	}
	
	public void redrawCurrentStructure()
	{
			
		String[] columnNames = new String[resources.size()+1];

		Object[][] dataCurrent= simulator.getProcessesData();
		
		columnNames[0] = "Processos";
		
		int j = 1;
		for(Iterator<Resource> i = resources.iterator(); i.hasNext(); ) {
			Resource rs = i.next();
			columnNames[j++] = rs.getName();

		}
		
		currentAlocation.removeAll();
		
		/*Available Resources Table*/
		currentAlocation.add(lblVetorDeAlocao);
		JTable tableCurrentAlocation = new JTable(dataCurrent,columnNames );
		currentAlocation.add(tableCurrentAlocation.getTableHeader(), BorderLayout.PAGE_START);
		currentAlocation.add(tableCurrentAlocation, BorderLayout.CENTER);
		currentAlocation.revalidate();

		
	}
	
	public void setResources(ArrayList<Resource> resources) {
		this.resources = resources;
		redrawExistingStructure();
		redrawAvailableStructure();
	}


	/**
	 * Create the frame.
	 */
	public SimulatorData(Simulator simulator) {
		
		this.simulator = simulator;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, util.Constants.DATA_WINDOW_WIDTH, util.Constants.DATA_WINDOW_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS ));
		
		existingResources = new JPanel();
		existingResources.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(existingResources);
		existingResources.setLayout(new BoxLayout(existingResources, BoxLayout.Y_AXIS));
		
		lblRecursosExistentes = new JLabel("Recursos Existentes");
		lblRecursosExistentes.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		availableResources = new JPanel();
		availableResources.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		Component verticalStrut = Box.createVerticalStrut(40);
		contentPane.add(verticalStrut);
		contentPane.add(availableResources);
		availableResources.setLayout(new BoxLayout(availableResources, BoxLayout.Y_AXIS));
		
		lblRecursosDisponveis = new JLabel("Recursos Disponíveis");
		
		
		requestVector = new JPanel();
		requestVector.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(Box.createVerticalStrut(40));
		contentPane.add(requestVector);
		requestVector.setLayout(new BoxLayout(requestVector, BoxLayout.Y_AXIS));
		
		lblVetorDeSolicitaes = new JLabel("Requisições");

		
		currentAlocation = new JPanel();
		currentAlocation.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(Box.createVerticalStrut(40));
		contentPane.add(currentAlocation);
		currentAlocation.setLayout(new BoxLayout(currentAlocation, BoxLayout.Y_AXIS));
		
		lblVetorDeAlocao = new JLabel("Alocação Corrente");
		
	}

	/** Checks if this window has been filled in
	 * @return True if so, false if not
	 */
	public boolean isOn() {
		
		if(resources==null)
			return false;
		return true;
	}



}
