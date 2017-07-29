package grosser.prototype.map;

import java.util.ArrayList;
import java.util.Random;

import grosser.engine.core.Render;
import grosser.engine.math.Vector2f;
import grosser.engine.shader.ShaderProgram;

/**
 * Map class manages the tiles on the grosser.prototype.map
 * 
 * @author Robert
 *
 */

// TODO remove SIZE variable and make the grosser.prototype.map dynamic and
// infinite

public class Map {

	private int SIZE = 20;

	private Render render;
	private TileRenderer tileRenderer;

	private ArrayList<Tile> tiles = new ArrayList<Tile>();

	public Map(Render render) {
		this.render = render;
		tileRenderer = new TileRenderer(this, render);
		generateLevel();
	}

	public void render() {
		tileRenderer.render();
	}

	private void generateLevel() {
		// w1=a h1=b w2=c h2=d
		// w1*h2-[(w1-w2)(h2-h1)]=A
		// a*d-[(a-c)(d-b)]=A
		Random rand = new Random();

		int targetArea = 64;

		int w1 = rand.nextInt(16) + 2;
		int h1 = rand.nextInt(16) + 2;
		int h2 = h1 + rand.nextInt(1) + 1;

		int w2 = (w1 * h1 - targetArea) / (h1 - h2);

		System.out.println("w: " + w1 + ", " + w2);
		System.out.println("h: " + h1 + ", " + h2);
		System.out.println("area: " + (w1 * h2 - ((w1 - w2) * (h2 - h1))));

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				float x = i - (SIZE / 2);
				float y = j - (SIZE / 2);
				int id = 0;
				/*
				 * if (x == 0 || y == 0 || x == w1 || y == h1 || x == w2 || y ==
				 * h2) { id = Tile.TILE_WALL; }
				 */
				if (x % 2 == 0) {
					id = Tile.TILE_WALL;
				}

				tiles.add(new Tile(new Vector2f(x, y), id));
			}
		}
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}
}
