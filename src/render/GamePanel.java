package render;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import input.Input;
import scenes.SceneManager;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	// Dimensions
	public static int WIDTH = 900;
	public static int HEIGHT = 500;
	public static final int SCALE = 1;

	// Game Thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;

	// Image
	private BufferedImage image;

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	private Graphics2D g;

	// Scene Manager
	private SceneManager sceneManager;

	// Constructor
	public GamePanel(int w, int h) {
		super();
		WIDTH = w;
		HEIGHT = h;
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);

			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0),
					"blank cursor");

			this.setCursor(blankCursor);

			thread.start();
		}
	}

	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		g = (Graphics2D) g;

		sceneManager = new SceneManager(this);
		Input.init();
		running = true;
	}

	public void run() {
		init();

		long start;
		long elapsed;
		long wait;

		// Game Loop
		while (running) {

			start = System.nanoTime();

			update();
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;

			try {
				if (wait > 0)
					Thread.sleep(wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g2.dispose();
	}

	private void draw() {
		sceneManager.getCurrentScene().render((Graphics2D) image.getGraphics());
	}

	private void update() {
		Input.updateInput();
		sceneManager.getCurrentScene().update();
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		Input.keyPressed(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		Input.keyReleased(e.getKeyCode());
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			Input.keyPressed(990990990);
		else if (e.getButton() == MouseEvent.BUTTON3)
			Input.keyPressed(880880880);
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			Input.keyReleased(990990990);
		else if (e.getButton() == MouseEvent.BUTTON3)
			Input.keyReleased(880880880);
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Input.mouseMoved(e.getX() - (WIDTH / 2), e.getY() - (HEIGHT / 2));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Input.mouseMoved(e.getX() - (WIDTH / 2), e.getY() - (HEIGHT / 2));
	}

}
