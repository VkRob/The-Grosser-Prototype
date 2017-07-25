package map;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Map {

	public static final int SIZE = 48;

	private ArrayList<Tile> tiles;

	public Map() {
		tiles = new ArrayList<>();
		this.generateNewWorld();
	}

	private void generateNewWorld() {

		for (int i = -SIZE / 2; i < SIZE / 2; i++) {
			for (int j = -SIZE / 2; j < SIZE / 2; j++) {
				tiles.add(new Tile(i, j, 0));
			}
		}

		Random r = new Random();

		int r1Width = r.nextInt(7) + 10;
		int r1Height = (170 - (r.nextInt(40) + 10)) / r1Width;

		int r2Width = r1Width - r.nextInt(20 - r1Width);
		int r2Height = (170 - (r.nextInt(40) + 10)) / r2Width;

		int index = 0;
		for (int i = -SIZE / 2; i < SIZE / 2; i++) {
			for (int j = -SIZE / 2; j < SIZE / 2; j++) {
				if (i <= r1Width && j <= r1Height && i >= 0 && j >= 0) {
					tiles.get(index).setID(1);
					if ((i == r1Width || j == r1Height && (i >= r2Width || j >= r2Height)) || (i == 0 || j == 0))
						tiles.get(index).setID(2);
				}
				if (i <= r2Width && j <= r2Height && i >= 0 && j >= 0) {
					if (tiles.get(index).getID() == 0) {
						tiles.get(index).setID(1);
					}
					if (((i > r1Width || j > r1Height) && (i == r2Width || j == r2Height)) || (i == 0 || j == 0)) {
						tiles.get(index).setID(2);
					}
				}
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

	public Tile getTile(int x, int y) {
		for (Tile t : tiles) {
			if (t.getX() == x && t.getY() == y) {
				return t;
			}
		}
		throw new NullPointerException("Tile at (" + x + ", " + y + ") missing.");
	}
}
