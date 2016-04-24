package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import model.Resource;

import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

public class SimulatorData extends JFrame {

	private static final long serialVersionUID = -1802463953464142976L;
	private JPanel contentPane;
	private ArrayList<Resource> resources;
	private JPanel existingResources;
	private JPanel availableResources;
	private JPanel requestVector;
	private JPanel currentAlocationVecotr;
	private JLabel lblRecursosExistentes;
	private JLabel lblRecursosDisponveis;
	private JLabel lblVetorDeSolicitaes;
	private JLabel lblVetorDeAlocao;

	public void redrawStructures()
	{
		
		existingResources.removeAll();
		availableResources.removeAll();
		String[] columnNames = new String[resources.size()];
		Object[][] dataExistingResouces = new Object[1][resources.size()];
		Object[][] dataAvilableResouces = new Object[1][resources.size()];
		int j = 0;
		for(Iterator<Resource> i = resources.iterator(); i.hasNext(); ) {
			Resource rs = i.next();
			columnNames[j] = rs.getName();
			dataExistingResouces[0][j]= rs.getAmount();
			dataAvilableResouces[0][j++] = rs.getAvailable();
		}

		/*Existing Resources Table*/
		existingResources.add(lblRecursosExistentes);
		JTable tableExistingResources = new JTable(dataExistingResouces,columnNames );
		existingResources.add(tableExistingResources.getTableHeader(), BorderLayout.PAGE_START);
		existingResources.add(tableExistingResources, BorderLayout.CENTER);
		existingResources.revalidate();
		
		
		/*Avialable Resources Table*/
		availableResources.add(lblRecursosDisponveis);
		JTable tableAvailableResources = new JTable(dataAvilableResouces,columnNames );
		availableResources.add(tableAvailableResources.getTableHeader(), BorderLayout.PAGE_START);
		availableResources.add(tableAvailableResources, BorderLayout.CENTER);
		availableResources.revalidate();
		
		
	}
	
	public void setResources(ArrayList<Resource> resources) {
		this.resources = resources;
		redrawStructures();
	}


	/**
	 * Create the frame.
	 */
	public SimulatorData() {
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
		
		lblRecursosDisponveis = new JLabel("Recursos Dispon\u00EDveis");
		
		
		requestVector = new JPanel();
		contentPane.add(Box.createVerticalStrut(40));
		contentPane.add(requestVector);
		requestVector.setLayout(new BoxLayout(requestVector, BoxLayout.Y_AXIS));
		
		lblVetorDeSolicitaes = new JLabel("Vetor de Solicita\u00E7\u00F5es");
		requestVector.add(lblVetorDeSolicitaes);
		
		currentAlocationVecotr = new JPanel();
		contentPane.add(Box.createVerticalStrut(40));
		contentPane.add(currentAlocationVecotr);
		currentAlocationVecotr.setLayout(new BoxLayout(currentAlocationVecotr, BoxLayout.Y_AXIS));
		
		lblVetorDeAlocao = new JLabel("Vetor de Aloca\u00E7\u00E3o Corrente");
		currentAlocationVecotr.add(lblVetorDeAlocao);
	}

}
