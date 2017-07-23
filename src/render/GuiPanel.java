package render;

import java.util.ArrayList;

public class GuiPanel {

	public static final int STATE_DEFAULT = 0;
	public static final int STATE_TOP_DOWN = 0;
	public static final int STATE_LEFT_RIGHT = 1;

	private ArrayList<GuiElement> elements;
	private int state = STATE_DEFAULT;
	private int seperation = 5;
	private int ctr = 0;
	private int width, height;
	private int x = 0;
	private int y = 0;

	public GuiPanel(GamePanel panel) {
		elements = new ArrayList<GuiElement>();
		width = panel.WIDTH;
		height = panel.HEIGHT;
	}

	public void addElement(GuiElement e) {
		elements.add(e);
		if (state == STATE_TOP_DOWN) {
			
			int middleOfScreenX = width / 2;
			int middleOfElementX = e.getWidth() / 2;
			int offsetOfElementX = e.getX();
			e.setX(middleOfScreenX - middleOfElementX + offsetOfElementX + x);
			
			int middleOfScreenY = 0;
			int middleOfElementY = 0 - seperation;
			int offsetOfElementY = e.getY();
			e.setY(middleOfScreenY - middleOfElementY + offsetOfElementY + y + ctr);
			ctr += e.getHeight() + offsetOfElementY + seperation;
		}
	}

	public ArrayList<GuiElement> getElements() {
		return elements;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
