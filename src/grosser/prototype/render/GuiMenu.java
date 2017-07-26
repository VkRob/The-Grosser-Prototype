package grosser.prototype.render;

import java.awt.Color;

import grosser.prototype.input.Input;

/**
 * A Menu system that allows for easy button and menu creation (very limited
 * atm)
 * 
 * @author Robert
 *
 */

// TODO create a more easy to use menu system, maybe even an external GUI
// editor, this
// game relies on GUI heavily
public class GuiMenu {

	// A GuiPanel holds a set of GUI Elements, the GuiMenu mainly just manages
	// grosser.prototype.input and which option is selected. This allows for menus to be made up
	// of any Gui Element such as textures or text
	private GuiPanel gui;

	// The GuiMenuListener basically has a method in it that gets called when
	// the user picks a button in the menu, and then the listener decides what
	// to do
	private GuiMenuListener listener;

	// Contains the selected button index
	private int selectedButton = 0;

	public GuiMenu(GuiMenuListener listener, GamePanel gp) {
		this.listener = listener;
		gui = new GuiPanel(gp);
	}

	/**
	 * Manage grosser.prototype.input, change which button is selected, if a button is selected,
	 * inform the GuiListener
	 */
	public void update() {

		// Change which option is pressed if the user presses W or S
		if (Input.get.DOWN.isTapped()) {
			selectedButton++;
		}
		if (Input.get.UP.isTapped()) {
			selectedButton--;
		}

		// If the option is negative or too high, fix it
		if (selectedButton < 0) {
			selectedButton = 0;
		}
		if (selectedButton == gui.getElements().size()) {
			selectedButton = gui.getElements().size() - 1;
		}

		// Offset the selected option by 20 so that it is obviously selected
		for (GuiElement e : gui.getElements()) {
			if (gui.getElements().indexOf(e) == selectedButton) {
				e.setX(this.gui.getX() + 20);
			} else {
				e.setX(this.gui.getX());
			}
		}

		// If the user presses space, inform the listener which option was
		// selected
		if (Input.get.SELECT.isTapped()) {
			listener.buttonSelected(((GuiText) gui.getElements().get(selectedButton)).getText());
		}
	}

	/**
	 * Add a text button to the menu
	 * @param text
	 * @param size
	 */
	public void addButton(String text, int size) {
		GuiText t = new GuiText(0, 0, 0, 30, text, Color.WHITE);
		t.setFontSize(size);
		gui.addElement(t);
	}
	
	//TODO add an addButton(Image, size) method so that buttons can be images

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
