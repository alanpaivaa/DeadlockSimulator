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
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class SimulatorData extends JFrame {

	private static final long serialVersionUID = -1802463953464142976L;
	private JPanel contentPane;
	private ArrayList<Resource> resources;
	private JPanel existingResources;
	private JPanel availableResources;
	private JPanel requestVector;
	private JPanel currentAlocationVecotr;
	private JLabel lblRecursosExistentes;

	public void redrawStructures()
	{
		String[] columnNames = new String[resources.size()];
		Object[][] data = new Object[1][resources.size()];
		int j = 0;
		for(Iterator<Resource> i = resources.iterator(); i.hasNext(); ) {
			Resource rs = i.next();
			columnNames[j] = rs.getName();
			data[0][j++]= rs.getAmount();
		}

		
		JTable table = new JTable(data,columnNames );
		existingResources.add(table.getTableHeader(), BorderLayout.PAGE_START);
		existingResources.add(table, BorderLayout.CENTER);
		existingResources.revalidate();
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
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		existingResources = new JPanel();
		contentPane.add(existingResources);
		
		lblRecursosExistentes = new JLabel("Recursos Existentes");
		existingResources.add(lblRecursosExistentes);
		
		availableResources = new JPanel();
		contentPane.add(availableResources);
		
		requestVector = new JPanel();
		contentPane.add(requestVector);
		
		currentAlocationVecotr = new JPanel();
		contentPane.add(currentAlocationVecotr);
	}

}
