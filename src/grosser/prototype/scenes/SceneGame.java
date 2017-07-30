package grosser.prototype.scenes;

import grosser.engine.core.Render;
import grosser.engine.math.Vector2f;
import grosser.prototype.entity.EntityManager;
import grosser.prototype.entity.Player;
import grosser.prototype.entity.TileManager;
import grosser.prototype.entity.WorldRenderer;

public class SceneGame extends Scene {

	private WorldRenderer worldRenderer;

	private TileManager map;
	private EntityManager entityManager;

	private Player player;

	public SceneGame(Render render) {

		map = new TileManager();
		entityManager = new EntityManager();
		worldRenderer = new WorldRenderer(render.getCamera(), map, entityManager, render);
		player = new Player(new Vector2f(2, 0), render.getCamera(), worldRenderer);
		entityManager.addEntity(player);

		map.generateLevel(worldRenderer, entityManager);
		player.setMap(map);
	}

	public void handleInput(char key) {
		player.handleInput(key);
	}

	@Override
	public void update() {
		entityManager.update();
	}

	@Override
	public void render() {
		worldRenderer.render();
	}
}
