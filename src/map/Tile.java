package map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import render.ImageLoader;

public class Tile {

	public static int TILE_GRASS = 0;
	public static int TILE_STONE = 1;
	public static int TILE_WALL = 2;

	private static ArrayList<Integer> SOLIDS = new ArrayList<Integer>();
	static {
		SOLIDS.add(TILE_WALL);
	}

	public static boolean isTileSolid(Tile t) {
		for (Integer i : SOLIDS) {
			if (t.getID() == i) {
				return true;
			}
		}
		return false;
	}

	public static BufferedImage tileset[] = { 
			ImageLoader.loadImage("/Tiles/grass.png"),
			ImageLoader.loadImage("/Tiles/stone.png"), 
			ImageLoader.loadImage("/Tiles/wall.png"), 
			};

	public static final int SIZE = 32;

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
