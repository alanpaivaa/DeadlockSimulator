package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import interfaces.SimulatorSetupDelegate;

/**
 * This dialog sets up the System parameters
 * @author TARDIS
 */
public class SimulatorSetup extends JDialog {

	private static final long serialVersionUID = -725446525058394433L;
	
	private final JPanel contentPanel = new JPanel();
	private int resourceNumber;
	
	private ArrayList<JTextField> resourcesNames = new ArrayList<JTextField>();
	private ArrayList<JTextField> resourcesQuantity = new ArrayList<JTextField>();
	
	private SimulatorSetupDelegate delegate;

	/**
	 * Create the dialog.
	 */
	public SimulatorSetup() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		{
			
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);

			}
			{
				JButton cancelButton = new JButton("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	/**
	 * Dynamically add the Labels and Fields so the user can fill up the info about the resources 
	 */
	private void redrawResouces()
	{
		
		for(int i = 0; i<resourceNumber; i++)
		{
			JLabel _lbResourceName = new JLabel("Nome do recurso "+(i+1));
			JTextField _tfResourceName = new JTextField();
			_tfResourceName.setColumns(10);
			JLabel _lbResourceInstance = new JLabel("N�mero de instancias do recurso "+(i+1));
			JTextField _tfResourceInstance = new JTextField();
			_tfResourceName.setColumns(10);
			_tfResourceInstance.setColumns(10);
			
			
			
			contentPanel.add(_lbResourceName);
			contentPanel.add(_tfResourceName);
			resourcesNames.add(_tfResourceName);
			contentPanel.add(_lbResourceInstance);
			contentPanel.add(_tfResourceInstance);
			resourcesQuantity.add(_tfResourceInstance);
			
			contentPanel.revalidate();
		}
	}
	
	
	// Getters and setters
	
	public void setResourceNumber(int resourceNumber) {
		
		this.resourceNumber = resourceNumber;
		redrawResouces();
	}

	public SimulatorSetupDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(SimulatorSetupDelegate delegate) {
		this.delegate = delegate;
	}

}
