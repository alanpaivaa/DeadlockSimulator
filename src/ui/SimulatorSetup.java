package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import interfaces.SimulatorSetupDelegate;
import model.CoolBorder;
import model.Resource;

/**
 * This dialog sets up the System parameters
 * @author TARDIS
 */
public class SimulatorSetup extends JDialog implements ActionListener {

	private static final long serialVersionUID = -725446525058394433L;

	// View Attributes
	private final JPanel contentPanel = new JPanel();
	private JButton okButton, cancelButton;

	private int resourceNumber;

	private ArrayList<JTextField> resourcesNames = new ArrayList<JTextField>();
	private ArrayList<JTextField> resourcesQuantity = new ArrayList<JTextField>();

	private SimulatorSetupDelegate delegate;
	

	/**
	 * Create the dialog.
	 * @param resourceNumber The number of resources that this dialog must ask for.
	 * @param delegate The delegate attached to this dialog used to track events.
	 */
	public SimulatorSetup(int resourceNumber, SimulatorSetupDelegate delegate) {
		this.resourceNumber = resourceNumber;
		this.delegate = delegate;
		this.drawStaticComponents();
		this.drawDynamicComponents();
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Draws the area to draw the dynamic stuff and the buttons.
	 * */
	private void drawStaticComponents() {

		this.setTitle("Configuração de recursos");
		setModalityType(ModalityType.APPLICATION_MODAL);

		// Scroll
		JScrollPane scroll = new JScrollPane(contentPanel);
		scroll.setPreferredSize(new Dimension(450, 95 * (this.resourceNumber <= 3 ? this.resourceNumber : 3)));
		getContentPane().add(scroll, BorderLayout.CENTER);

		// ContentPanel Setup
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		// Bottom Buttons Panel
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		this.okButton = new JButton("OK");
		this.okButton.addActionListener(this);
		buttonPane.add(this.okButton);
		getRootPane().setDefaultButton(okButton);

		this.cancelButton = new JButton("Cancel");
		this.cancelButton.addActionListener(this);
		buttonPane.add(this.cancelButton);

	}

	/**
	 * Dynamically add the Labels and Fields so the user can fill up the info about the resources 
	 */
	private void drawDynamicComponents() {
		for(int i = 0; i<resourceNumber; i++) {

			JPanel resourceHolder = new JPanel();
			resourceHolder.setLayout(new GridLayout(2, 2));
			resourceHolder.setBorder(new CoolBorder("Recurso " + (i + 1)));
			contentPanel.add(resourceHolder);

			JLabel _lbResourceName = new JLabel("Nome:");
			JTextField _tfResourceName = new JTextField();
			_tfResourceName.setColumns(10);
			JLabel _lbResourceInstance = new JLabel("Inst�ncias:");
			JTextField _tfResourceInstance = new JTextField("1");
			_tfResourceName.setColumns(10);
			_tfResourceInstance.setColumns(10);

			resourceHolder.add(_lbResourceName);
			resourceHolder.add(_tfResourceName);
			resourcesNames.add(_tfResourceName);
			resourceHolder.add(_lbResourceInstance);
			resourceHolder.add(_tfResourceInstance);
			resourcesQuantity.add(_tfResourceInstance);

		}
	}


	// ActionListener implement

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.okButton) {
			this.didClickOnOkButton();
		} else {
			this.didClickOnCancelButton();
		}
	}

	/**
	 * Called whenever the user clicks on the Ok button.
	 * */
	private void didClickOnOkButton() {
		ArrayList<Resource> resources = this.buildResources();
		if(resources != null) {
			this.delegate.simulatorSetupDidSucceedWithResources(resources);
			this.dispose();

		} else {
			this.displayErrorMessage();
		}



	}

	/**
	 * Called whenever the user clicks on the cancel button.
	 * */
	private void didClickOnCancelButton() {
		this.dispose();
	}

	/**
	 * Validates the inputs and builds and ArrayList of resources from the input values.
	 * */
	private ArrayList<Resource> buildResources() {

		ArrayList<Resource> resources = new ArrayList<Resource>();
		int amount;

		for(int i=0; i<this.resourceNumber; i++) {

			if(this.resourcesNames.get(i).getText().trim().isEmpty() || this.resourcesQuantity.get(i).getText().trim().isEmpty()) {
				return null;
			}

			try {
				amount = Integer.parseInt(this.resourcesQuantity.get(i).getText().trim());
			} catch(Exception e) {
				return null;
			}

			resources.add(new Resource(this.resourcesNames.get(i).getText().trim(), amount));
		}

		return resources;
	}

	/**
	 * Displays an error message telling that inputs has wrong values.
	 * */
	private void displayErrorMessage() {
		JOptionPane.showMessageDialog(this, "Valores inv�lidos!");
	}


	// Getters and setters

	public void setResourceNumber(int resourceNumber) {
		this.resourceNumber = resourceNumber;
		drawDynamicComponents();
	}

	public SimulatorSetupDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(SimulatorSetupDelegate delegate) {
		this.delegate = delegate;
	}

}
