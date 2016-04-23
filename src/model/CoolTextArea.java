package model;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Encapsulates a JScrollPane with a JTextArea on it, just for methods abstraction.
 * @author ajeferson
 * */
public class CoolTextArea extends JScrollPane {

	private static final long serialVersionUID = -8589816240079356879L;
	
	private JTextArea textArea;
	
	/***
	 * Constructor.
	 * */
	public CoolTextArea(int x, int y, int width, int heigh) {
		this.textArea = new JTextArea();
		this.textArea.setEnabled(false);
		this.textArea.setEditable(false);
		this.setBounds(10, 26, 767, 99);
		this.getViewport().add(textArea);
	}
	
	/**
	 * Appends a text to the text area and scrolls down.
	 * @param text The text to be appended on the text area.
	 * */
	public void log(String text) {
		this.textArea.append(text + "\n");
		this.textArea.setCaretPosition(this.textArea.getDocument().getLength());
	}
	
}
