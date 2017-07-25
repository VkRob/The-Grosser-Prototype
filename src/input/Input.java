package input;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * 
 * 
 * If you want to access input anywhere in the code, use
 * <code>Input.get.(Key-name).isPressed()</code>
 * 
 * Don't worry about the code in this class, its not important to change.
 * 
 * Key-names: <code> UP (w), DOWN (s), LEFT (a), RIGHT (d), SELECT (space), M1,
 * M2.
 * 
 * @author Robert
 * 
 */

public class Input {

	/**
	 * 
	 */
	public static Input get;

	public static void init() {
		get = new Input();
	}

	public static void keyPressed(int i) {
		get.press(i);
	}

	public static void keyReleased(int e) {
		get.release(e);
	}

	public static void updateInput() {
		get.update();
	}

	public static void mouseMoved(int x, int y) {
		// get.mouseMove(x, y);
	}

	private ArrayList<Key> keyboard;
	public Key UP = new Key(KeyEvent.VK_W);
	public Key DOWN = new Key(KeyEvent.VK_S);
	public Key RIGHT = new Key(KeyEvent.VK_D);
	public Key LEFT = new Key(KeyEvent.VK_A);
	public Key SELECT = new Key(KeyEvent.VK_SPACE);

	public Key M1 = new Key(990990990);
	public Key M2 = new Key(880880880);

	public int MOUSE_X = 0;
	public int MOUSE_Y = 0;

	private Input() {
		keyboard = new ArrayList<Key>();
		keyboard.add(UP);
		keyboard.add(DOWN);
		keyboard.add(RIGHT);
		keyboard.add(LEFT);
		keyboard.add(SELECT);
		keyboard.add(M1);
		keyboard.add(M2);
	}

	private void mouseMove(int x, int y) {
		MOUSE_X = x;
		MOUSE_Y = y;
	}

	private void update() {
		for (Key k : keyboard) {
			k.update();
		}
	}

	private void press(int e) {
		for (Key k : keyboard) {
			k.keyPressed(e);
		}
	}

	private void release(int e) {
		for (Key k : keyboard) {
			k.keyReleased(e);
		}
	}
}
