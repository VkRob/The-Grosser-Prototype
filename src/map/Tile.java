package map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import render.ImageLoader;

/**
 * Tile Object
 * 
 * @author Robert
 *
 */
public class Tile {

	// Tile ID constants, allows us to store the tile type as an integer and
	// then figure out what type it is with one of these constants
	public static final int TILE_GRASS = 0;
	public static final int TILE_STONE = 1;
	public static final int TILE_WALL = 2;

	// This keeps track of all the solid tiles. Currently, that is just the
	// factory wall tile.
	private static ArrayList<Integer> SOLIDS = new ArrayList<Integer>();
	static {
		SOLIDS.add(TILE_WALL);
	}

	/**
	 * 
	 * @param tile
	 * @return whether or not tile's ID type is solid (so a grass tile would
	 *         return false, but a wall tile would return true)
	 */
	public static boolean isTileSolid(Tile tile) {
		for (Integer i : SOLIDS) {
			if (tile.getID() == i) {
				return true;
			}
		}
		return false;
	}

	// Array of images, tileset[0] is the image for tile ID 0 (grass),
	// tileset[1] is the image for tile ID 1 (stone) etc etc
	public static BufferedImage tileset[] = {

			ImageLoader.loadImage("/Tiles/grass.png"),

			ImageLoader.loadImage("/Tiles/stone.png"),

			ImageLoader.loadImage("/Tiles/wall.png"),

	};

	// Constant for the size of a Tile in pixels
	public static final int SIZE = 32;

	// Basic Tile info, position and type of tile (ID)
	private int x, y;
	private int ID;

	public Tile(int x, int y, int ID) {
		this.setX(x);
		this.setY(y);
		this.setID(ID);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
}
