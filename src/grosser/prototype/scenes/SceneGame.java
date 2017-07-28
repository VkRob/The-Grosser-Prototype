package grosser.prototype.scenes;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;

import java.util.ArrayList;

import grosser.engine.core.Render;
import grosser.prototype.map.Map;
import grosser.prototype.map.Tile;

public class SceneGame extends Scene {

	// private final EntityManager entityManager;
	private Map map;

	public SceneGame(SceneManager mgr, Render render) {
		super(mgr);

		
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

	@Override
	public void render() {
		ArrayList<Tile> shadows = new ArrayList<Tile>();

		for (Tile tile : map.getTiles()) {
			if (tile.getID() == Tile.TILE_WALL) {
				shadows.add(tile);
			}
		}
		
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);

		for (Tile tile : map.getTiles()) {
			tile.renderNormally(shadows);
			glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		}
	}
}
