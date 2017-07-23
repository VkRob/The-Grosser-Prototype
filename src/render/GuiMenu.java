package render;

import java.awt.Color;

import input.Input;

public class GuiMenu {

	private GuiPanel gui;

	private GuiMenuListener listener;
	private int selectedButton = 0;

	public GuiMenu(GuiMenuListener listener, GamePanel gp) {
		this.listener = listener;
		gui = new GuiPanel(gp);
	}

	public void update() {
		if (Input.get.DOWN.isTapped()) {
			selectedButton++;
		}
		if (Input.get.UP.isTapped()) {
			selectedButton--;
		}
		if (selectedButton < 0) {
			selectedButton = 0;
		}
		if (selectedButton == gui.getElements().size()) {
			selectedButton = gui.getElements().size() - 1;
		}

		for (GuiElement e : gui.getElements()) {
			if (gui.getElements().indexOf(e) == selectedButton) {
				e.setX(this.gui.getX()+20);
			} else {
				e.setX(this.gui.getX());
			}
		}

		if (Input.get.SELECT.isTapped()) {
			listener.buttonSelected(((GuiText) gui.getElements().get(selectedButton)).getText());
		}
	}

	public void addButton(String text, int size) {
		GuiText t = new GuiText(0, 0, 0, 30, text, Color.WHITE);
		t.setFontSize(size);
		gui.addElement(t);
	}

	public void render(Render r) {
		r.renderGui(gui);
	}

	public GuiPanel getGui() {
		return gui;
	}

	public void setGui(GuiPanel gui) {
		this.gui = gui;
	}
}
