package grosser.prototype.map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import grosser.prototype.render.ImageLoader;

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
	private static ArrayList<Integer> SOLIDS = new ArrayList<>();
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
		return SOLIDS.contains(tile.getID());
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
//	private TilePosition pos;
	private int ID;

//	public Tile(int x, int y, int ID) {
//		this.pos = new TilePosition(x, y);
//		this.setID(ID);
//	}

    public Tile(int ID) {
        this.setID(ID);
    }

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

//	public int getX() {
//		return pos.getX();
//	}

//	public void setX(int x) {
//		this.x = x;
//	}

//	public int getY() {
//		return pos.getY();
//	}

//	public void setY(int y) {
//		this.y = y;
//	}
}
