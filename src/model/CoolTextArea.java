package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Encapsulates a JScrollPane with a JTextArea on it, just for methods abstraction.
 * @author ajeferson
 * */
public class CoolTextArea extends JScrollPane {

	private static final long serialVersionUID = -8589816240079356879L;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	private JTextArea textArea;
	
	/***
	 * Constructor.
	 * */
	public CoolTextArea() {
		this.textArea = new JTextArea();
		this.textArea.setEnabled(true);
		this.textArea.setEditable(false);
		this.getViewport().add(textArea);
	}
	
	/**
	 * Appends a text to the text area and scrolls down.
	 * @param text The text to be appended on the text area.
	 * */
	public void log(String text) {
		this.textArea.append("[" + dateFormat.format(new Date()) + "] " + text + "\n");
		this.textArea.setCaretPosition(this.textArea.getDocument().getLength());
	}
	
	
	// Getters and Setters
	
	public JTextArea getTextArea() {
		return this.textArea;
	}
	
	public void clear()
	{
		this.textArea.setText("");
	}
	
}
