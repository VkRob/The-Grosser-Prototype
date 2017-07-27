package grosser.prototype.scenes;

import java.awt.Color;
import java.awt.Graphics2D;

import grosser.prototype.entity.EntityMachine;
import grosser.prototype.entity.EntityManager;
import grosser.prototype.entity.EntityType;
import grosser.prototype.entity.EntityWorker;
import grosser.prototype.input.Input;
import grosser.prototype.map.Map;
import grosser.prototype.map.Tile;
import grosser.prototype.render.GamePanel;
import grosser.prototype.render.Render;

public class SceneGame extends Scene {

	private Render render;
	private GamePanel gp;

	private Map map;
	private final EntityManager entityManager;

	SceneGame(SceneManager mgr, GamePanel gamePanel) {
		super(mgr);
		this.gp = gamePanel;
		map = new Map();
		this.entityManager = new EntityManager();
		entityManager.addNewEntity(EntityType.MACHINE_0, 2*Tile.SIZE, 2*Tile.SIZE);
		entityManager.addNewEntity(EntityType.WORKER_0, 4*Tile.SIZE, 4*Tile.SIZE);
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
		
		entityManager.updateWorkers(map);
	}

	@Override
	public void render(Graphics2D g) {
		if (render == null)
			render = new Render(g);
		render.clearScreen(Color.BLACK, GamePanel.WIDTH, GamePanel.HEIGHT);
		render.renderMap(map);
		for (EntityWorker worker : entityManager.getWorkers()) {
			render.renderWorker(worker);
		}
		for (EntityMachine machine : entityManager.getMachines()) {
			render.renderMachine(machine);

		}
	}
}
