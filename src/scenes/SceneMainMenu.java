package scenes;

import java.awt.Color;
import java.awt.Graphics2D;

import render.GamePanel;
import render.GuiElement;
import render.GuiMenu;
import render.GuiMenuListener;
import render.ImageLoader;
import render.Render;

public class SceneMainMenu extends Scene implements GuiMenuListener {

	private Render render;
	private GamePanel gp;
	private GuiMenu gui;
	private GuiElement logo;
	private int width, height;

	public SceneMainMenu(SceneManager mgr, GamePanel gamePanel) {
		super(mgr);
		gui = new GuiMenu(this, gamePanel);
		width = GamePanel.WIDTH;
		height = GamePanel.HEIGHT;
		this.gp = gamePanel;

		logo = new GuiElement(GamePanel.WIDTH/2 - 128, 0, 256, 256);
		logo.setImage(ImageLoader.loadImage("res/HUD/Logo.png"));

		gui.getGui().setX(GamePanel.WIDTH / 2 - 50);
		gui.getGui().setY(300);

		gui.addButton("Play", 36);
		gui.addButton("Options", 36);
		gui.addButton("Exit", 36);
	}

	public void update() {
		gui.update();
	}

	public void render(Graphics2D g) {
		if (render == null)
			render = new Render(g, gp.getImage());
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
