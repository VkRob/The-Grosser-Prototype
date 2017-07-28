package grosser.prototype.scenes;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;

import java.util.ArrayList;

import grosser.engine.core.Render;
import grosser.engine.math.Vector2f;
import grosser.prototype.map.Tile;

public class SceneGame extends Scene {

	private ArrayList<Tile> tiles = new ArrayList<Tile>();

	public SceneGame(Render render) {
		int SIZE = 30;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (i % 4 == 0 && j % 4 == 0) {
					tiles.add(new Tile(new Vector2f(i - (SIZE / 2), j - (SIZE / 2)), 2, render));
				} else
					tiles.add(new Tile(new Vector2f(i - (SIZE / 2), j - (SIZE / 2)), 0, render));
			}
		}
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
		// Sort the tiles by the ones that cast shadows and the ones that do not
		ArrayList<Tile> shadows = new ArrayList<Tile>();

		for (Tile tile : tiles) {
			if (tile.getID() == 2) {
				shadows.add(tile);
			}
		}

		// glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);

		glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);
		// System.out.println("#############################");
		for (Tile tile : tiles) {

			tile.renderNormally(shadows);
			glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

		}

	}
}
