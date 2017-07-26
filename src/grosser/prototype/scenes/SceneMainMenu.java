package grosser.prototype.scenes;

import java.awt.Color;
import java.awt.Graphics2D;

import grosser.prototype.render.GamePanel;
import grosser.prototype.render.GuiElement;
import grosser.prototype.render.GuiMenu;
import grosser.prototype.render.GuiMenuListener;
import grosser.prototype.render.ImageLoader;
import grosser.prototype.render.Render;

public class SceneMainMenu extends Scene implements GuiMenuListener {

	// Needed objects
	private Render render;
	private GuiMenu gui;
	private GuiElement logo;

	// Kinda unneeded, but this contains the width and height of the game
	private int width, height;

	public SceneMainMenu(SceneManager mgr, GamePanel gamePanel) {
		super(mgr);

		// setup the menu object
		gui = new GuiMenu(this, gamePanel);

		// set the width and height variables to the dimensions of the game
		width = GamePanel.WIDTH;
		height = GamePanel.HEIGHT;

		// Create the logo at the top center of the screen with a size of
		// 256x256, and load its image from the HUD folder in res
		logo = new GuiElement(GamePanel.WIDTH / 2 - 128, 0, 256, 256);
		logo.setImage(ImageLoader.loadImage("/HUD/Logo.png"));

		// Set the location of the gui menu to be in the middle of the screen,
		// just below the logo
		gui.getGui().setX(GamePanel.WIDTH / 2 - 50);
		gui.getGui().setY(300);

		// Add play, options, and exit buttons, each size 36 font
		gui.addButton("Play", 36);
		gui.addButton("Options", 36);
		gui.addButton("Exit", 36);
	}

	public void update() {
		gui.update();
	}

	public void render(Graphics2D g) {
		if (render == null)
			render = new Render(g);
		renderBg();
		renderGui();
	}

	private void renderBg() {
		render.clearScreen(Color.BLACK, width, height);
	}

	private void renderGui() {
		render.renderGui(logo);
		gui.render(render);
	}

	/**
	 * If the user selects play, go to the game scene, if they select exit, quit
	 * the game
	 */
	@Override
	public void buttonSelected(String key) {
		System.out.println(key);
		if (key == "Play") {
			mgr.setScene(mgr.scene_game);
		}
		if (key == "Exit") {
			System.exit(0);
		}
	}

}
