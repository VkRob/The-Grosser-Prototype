package main;

import java.awt.Rectangle;

import javax.swing.*;

import render.GamePanel;

/**
 * Main class, sets up the JFrame and adds the GamePanel object to it... not
 * much to see here
 * 
 * @author Robert
 *
 */
public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame("Prototype");

		//window.setUndecorated(true);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setResizable(false);
		Rectangle r = new Rectangle(900, 800);
		// Rectangle r =
		// GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		window.setBounds(r);
		window.setContentPane(new GamePanel((int) r.getWidth(), (int) r.getHeight()));
		window.setVisible(true);
	}

}
