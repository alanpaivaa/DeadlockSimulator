package model;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.border.TitledBorder;

/**
 * Just a TitleBorder and custom insets.
 * @author ajeferson
 * */
public class CoolBorder extends TitledBorder {

	private static final long serialVersionUID = -7386187489660479500L;
	
	private Insets customInsets = new Insets(15, 20, 10, 20);
	
	/**
	 * Constructor, builds a border.
	 * */
	public CoolBorder(String title) {
		super(title);
	}
	
	@Override
	public Insets getBorderInsets(Component c) {
		return this.customInsets;
	}

}
