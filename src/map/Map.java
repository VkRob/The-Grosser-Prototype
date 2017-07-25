package map;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 * Map class manages the tiles on the map
 * 
 * @author Robert
 *
 */

// TODO remove SIZE variable and make the map dynamic and infinite

public class Map {

	// currently, SIZE determines the size of the map, but eventually it should
	// become infinite and this variable will be removed
	public static final int SIZE = 48;

	// Instead of a normal Tile[][] array, the ArrayList allows for an infinite
	// map later
	private ArrayList<Tile> tiles;

	/**
	 * Creates the map object and automatically generates a new factory
	 * surrounded by grass
	 */
	public Map() {
		tiles = new ArrayList<Tile>();
		this.generateNewWorld();
	}

	/**
	 * Kind of hodgepodge way of generating the world
	 */
	// TODO fix generateNewWorld to
	// -allow infinite map size
	// -allow rotating the factory
	// -more variety to factory sizes?

	private void generateNewWorld() {

		// Create an empty SIZE by SIZE square of grass tiles (empty world)
		for (int i = -SIZE / 2; i < SIZE / 2; i++) {
			for (int j = -SIZE / 2; j < SIZE / 2; j++) {
				tiles.add(new Tile(i, j, Tile.TILE_GRASS));
			}
		}

		Random r = new Random();

		// Generate a random rectangle with area around 170
		int r1Width = r.nextInt(7) + 10;
		int r1Height = (170 - (r.nextInt(40) + 10)) / r1Width;

		// Create another random rectangle with area around 170, but
		// proportioned to the size of the first rectangle
		int r2Width = r1Width - r.nextInt(20 - r1Width);
		int r2Height = (170 - (r.nextInt(40) + 10)) / r2Width;

		// Index keeps track of which tile is at (i, j) on the map...
		// (Kind of a messy way of doing it)
		int index = 0;

		// Loop through the map, i = x, j = y, tile[i][j] == tiles.get(index)
		for (int i = -SIZE / 2; i < SIZE / 2; i++) {
			for (int j = -SIZE / 2; j < SIZE / 2; j++) {

				// If the current tile is inside the first rectangle, set the
				// tile to stone (floor)
				if (i <= r1Width && j <= r1Height && i >= 0 && j >= 0) {
					tiles.get(index).setID(Tile.TILE_STONE);
					// If the current tile is on the border of the first
					// rectangle and is not overlapped by the second rectangle,
					// set the tile to wall
					if ((i == r1Width || j == r1Height && (i >= r2Width || j >= r2Height)) || (i == 0 || j == 0))
						tiles.get(index).setID(Tile.TILE_WALL);
				}
				// Repeat similarly for rectangle2
				if (i <= r2Width && j <= r2Height && i >= 0 && j >= 0) {
					if (tiles.get(index).getID() == 0) {
						tiles.get(index).setID(Tile.TILE_STONE);
					}
					if (((i > r1Width || j > r1Height) && (i == r2Width || j == r2Height)) || (i == 0 || j == 0)) {
						tiles.get(index).setID(Tile.TILE_WALL);
					}
				}
				// Keeps tile[i][j] == tiles.get(index), necessary if we use
				// ArrayLists instead of normal 2d arrays
				index++;
			}
		}

	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}

	/**
	 * @param box
	 *            Rectangle that functions as a hitbox.
	 * @return Which tiles collide with the hitbox, regardless if they are solid
	 *         or not
	 */
	public ArrayList<Tile> getTilesTouching(Rectangle box) {
		ArrayList<Tile> touching = new ArrayList<Tile>();
		for (Tile t : tiles) {
			Rectangle tBox = new Rectangle(t.getX() * Tile.SIZE, t.getY() * Tile.SIZE, Tile.SIZE, Tile.SIZE);
			if (tBox.intersects(box)) {
				touching.add(t);
			}
		}
		return touching;
	}

	/**
	 * @param x
	 *            X position of desired tile
	 * @param y
	 *            Y position of desired tile
	 * @return Tile at tiles[x][y] from the ArrayList
	 */
	public Tile getTile(int x, int y) {
		for (Tile t : tiles) {
			if (t.getX() == x && t.getY() == y) {
				return t;
			}
		}
		// If the tile isn't found throw an exception (better to do it here than
		// somewhere else for debugging reasons
		throw new NullPointerException("Tile at (" + x + ", " + y + ") missing.");
	}
}
