package grosser.prototype.map;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import grosser.engine.core.Render;
import grosser.engine.math.Vector2f;

/**
 * Map class manages the tiles on the grosser.prototype.map
 * 
 * @author Robert
 *
 */

// TODO remove SIZE variable and make the grosser.prototype.map dynamic and
// infinite

public class Map {

	// currently, SIZE determines the size of the grosser.prototype.map, but
	// eventually it should
	// become infinite and this variable will be removed
	public static final int SIZE = 3;

	// Instead of a normal Tile[][] array, the ArrayList allows for an infinite
	// grosser.prototype.map later
	// private ArrayList<Tile> tiles;
	// private HashMap<TilePosition, Tile> tiles;
	private ArrayList<Tile> tiles;

	private Render render;

	/**
	 * Creates the grosser.prototype.map object and automatically generates a
	 * new factory surrounded by grass
	 */
	public Map(Render render) {
		this.render = render;
		tiles = new ArrayList<Tile>();
		this.generateNewWorld();
	}

	/**
	 * Kind of hodgepodge way of generating the world
	 */
	// TODO fix generateNewWorld to
	// -allow infinite grosser.prototype.map size
	// -allow rotating the factory
	// -more variety to factory sizes?

	private void generateNewWorld() {

		// Create an empty SIZE by SIZE square of grass tiles (empty world)
		for (int x = -SIZE / 2; x < SIZE / 2; x++) {
			for (int y = -SIZE / 2; y < SIZE / 2; y++) {
				Tile t = new Tile(new Vector2f((float) x, (float) y), Tile.TILE_GRASS, render);
				tiles.add(t);
				if (x % 2 == 0) {
					t.setID(Tile.TILE_WALL);
				}
			}
		}

		// Random r = new Random();
		//
		// // Generate a random rectangle with area around 170
		// int r1Width = r.nextInt(7) + 10;
		// int r1Height = (170 - (r.nextInt(40) + 10)) / r1Width;
		//
		// // Create another random rectangle with area around 170, but
		// // proportioned to the size of the first rectangle
		// int r2Width = r1Width - r.nextInt(20 - r1Width);
		// int r2Height = (170 - (r.nextInt(40) + 10)) / r2Width;
		//
		// // Index keeps track of which tile is at (i, j) on the
		// grosser.prototype.map...
		// // (Kind of a messy way of doing it)
		//
		// // Loop through the grosser.prototype.map, i = x, j = y, tile[i][j]
		// == tiles.get(index)
		// for (int x = -SIZE / 2; x < SIZE / 2; x++) {
		// for (int y = -SIZE / 2; y < SIZE / 2; y++) {
		//
		// TilePosition currentPos = new TilePosition(x, y);
		//
		// // If the current tile is inside the first rectangle, set the
		// // tile to stone (floor)
		// if (x <= r1Width && y <= r1Height && x >= 0 && y >= 0) {
		// tiles.get(currentPos).setID(Tile.TILE_STONE);
		// // If the current tile is on the border of the first
		// // rectangle and is not overlapped by the second rectangle,
		// // set the tile to wall
		// if ((x == r1Width || y == r1Height && (x >= r2Width || y >=
		// r2Height)) || (x == 0 || y == 0))
		// tiles.get(currentPos).setID(Tile.TILE_WALL);
		// }
		// // Repeat similarly for rectangle2
		// if (x <= r2Width && y <= r2Height && x >= 0 && y >= 0) {
		// if (tiles.get(currentPos).getID() == 0) {
		// tiles.get(currentPos).setID(Tile.TILE_STONE);
		// }
		// if (((x > r1Width || y > r1Height) && (x == r2Width || y ==
		// r2Height)) || (x == 0 || y == 0)) {
		// tiles.get(currentPos).setID(Tile.TILE_WALL);
		// }
		// }
		// }
		// }
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	// public void setTiles(HashMap<TilePosition, Tile> tiles) {
	// this.tiles = tiles;
	// }

	/**
	 * @param box
	 *            Rectangle that functions as a hitbox.
	 * @return Which tiles collide with the hitbox, regardless if they are solid
	 *         or not
	 */
	// public ArrayList<Tile> getTilesTouching(Rectangle box) {
	// ArrayList<Tile> touching = new ArrayList<>();
	// Rectangle tBox = new Rectangle(tPos.getX() * Tile.SIZE, tPos.getY() *
	// Tile.SIZE, Tile.SIZE, Tile.SIZE);
	// if (tBox.intersects(box)) {
	// touching.add(tiles.get(tPos));
	// }
	//
	// return touching;
	// }

	/**
	 * @param x
	 *            X position of desired tile
	 * @param y
	 *            Y position of desired tile
	 * @return Tile at tiles[x][y] from the tiles HashMap
	 */

	public Tile getTile(int x, int y) {
		for (Tile t : tiles) {
			if (t.getTilePosition().x == (float) x) {
				if (t.getTilePosition().y == (float) y) {
					return t;
				}
			}
			
		}
		return null;
	}
	// public Tile getTile(int x, int y) {
	// Tile returnVal = tiles.get(new TilePosition(x, y));
	// if (returnVal != null) return returnVal;
	// // If the tile isn't found throw an exception (better to do it here than
	// // somewhere else for debugging reasons
	// throw new NullPointerException("Tile at (" + x + ", " + y + ")
	// missing.");
	// }
}
