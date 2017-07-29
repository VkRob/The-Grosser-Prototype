package grosser.prototype.scenes;

import grosser.engine.core.Render;
import grosser.prototype.map.Map;

public class SceneGame extends Scene {

	// private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private Map map;

	public SceneGame(Render render) {

		map = new Map(render);
		/*
		 * this.entityManager = new EntityManager();
		 * entityManager.addNewEntity(EntityType.MACHINE_0, 2 * Tile.SIZE, 2 *
		 * Tile.SIZE); entityManager.addNewEntity(EntityType.MACHINE_0, 4 *
		 * Tile.SIZE, 2 * Tile.SIZE);
		 * entityManager.addNewEntity(EntityType.WORKER_0, 4 * Tile.SIZE, 4 *
		 * Tile.SIZE);
		 */
	}

	@Override
	public void update() {
		// entityManager.updateWorkers(map);
	}

	public void moveLight(char key) {
		float speed = 0.1f;
//		for (Tile t : map.getTiles()) {
//			if (key == 'u')
//				t.getLightPosition().get(0).y += speed;
//			if (key == 'j')
//				t.getLightPosition().get(0).y -= speed;
//			if (key == 'h')
//				t.getLightPosition().get(0).x -= speed;
//			if (key == 'k')
//				t.getLightPosition().get(0).x += speed;
//		}
	}

	@Override
	public void render() {
		map.render();
	}
}
