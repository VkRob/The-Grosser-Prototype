package grosser.prototype.render;

public interface GuiMenuListener {
	/**
	 * Listens for when an option in a menu is selected, key is the text/title
	 * of the button pressed
	 * 
	 * @param key
	 */
	public void buttonSelected(String key);
}
