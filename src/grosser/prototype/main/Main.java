package grosser.prototype.main;

import java.awt.*;

import javax.swing.*;

import grosser.prototype.render.GamePanel;

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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle r = new Rectangle(screenSize.width, screenSize.height);
		// Rectangle r =
		// GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		window.setBounds(r);
		window.setContentPane(new GamePanel((int) r.getWidth(), (int) r.getHeight()));
		window.setVisible(true);
	}

}