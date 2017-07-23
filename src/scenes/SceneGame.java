package scenes;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.EntityWorker;
import input.Input;
import map.Map;
import map.Tile;
import render.GamePanel;
import render.Render;

public class SceneGame extends Scene {

	private Render render;
	private GamePanel gp;

	private Map map;
	private EntityWorker worker;

	public SceneGame(SceneManager mgr, GamePanel gamePanel) {
		super(mgr);
		this.gp = gamePanel;
		map = new Map();
		worker = new EntityWorker(4*Tile.SIZE, 4*Tile.SIZE);
	}

	@Override
	public void update() {
		int speed = 6;
		if (Input.get.UP.isPressed()) {
			render.setCameraY(render.getCameraY() + speed);
		}
		if (Input.get.DOWN.isPressed()) {
			render.setCameraY(render.getCameraY() - speed);
		}
		if (Input.get.LEFT.isPressed()) {
			render.setCameraX(render.getCameraX() + speed);
		}
		if (Input.get.RIGHT.isPressed()) {
			render.setCameraX(render.getCameraX() - speed);
		}
		
		worker.update(map);
	}

	@Override
	public void render(Graphics2D g) {
		if (render == null)
			render = new Render(g, gp.getImage());
		render.clearScreen(Color.BLACK, GamePanel.WIDTH, GamePanel.HEIGHT);
		render.renderMap(map);
		render.renderWorker(worker);
	}

}