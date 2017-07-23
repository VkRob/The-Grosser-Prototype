package main;

import java.awt.Rectangle;

import javax.swing.JFrame;

import render.GamePanel;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame("Prototype");

		//window.setUndecorated(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		Rectangle r = new Rectangle(900, 800);
		// Rectangle r =
		// GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		window.setBounds(r);
		window.setContentPane(new GamePanel((int) r.getWidth(), (int) r.getHeight()));
		window.setVisible(true);
	}

}
