package engine.tile;

import org.joml.Vector2f;

import engine.util.Log;

public abstract class Tile {

	private static final int numOfTiles = 256;
	private static Tile[] registry;

	public static void registerTiles() {
		registry = new Tile[numOfTiles];
		registerTile(0, "sky", new TileSky());
		registerTile(1, "grass", new TileNormal(new Vector2f(0, 0)));
		registerTile(2, "stone", new TileNormal(new Vector2f(1, 0)));
		registerTile(3, "dirt", new TileNormal(new Vector2f(2, 0)));
		registerTile(4, "grass_side", new TileNormal(new Vector2f(3, 0)));
		registerTile(5, "planks", new TileNormal(new Vector2f(4, 0)));
		registerTile(6, "planks", new TileNormal(new Vector2f(5, 0)));
		registerTile(7, "planks", new TileNormal(new Vector2f(6, 0)));
		registerTile(8, "planks", new TileNormal(new Vector2f(7, 0)));
		registerTile(9, "planks", new TileNormal(new Vector2f(8, 0)));
	}

	private static void registerTile(int id, String string, Tile tile) {
		tile.setId(id);
		tile.setName(string);
		registry[id] = tile;
	}

	public static int getTileID(Tile tile) {
		for (int i = 0; i < registry.length; i++) {
			if (tile.getClass().equals(registry[i].getClass())) {
				return i;
			}
		}
		Log.error("Tile ID not found: " + tile.getClass().getName());
		return -1;
	}

	public static Tile getTile(int id) {
		return registry[id];
	}

	public static int getTileID(String name) {
		for (int id = 0; id < registry.length; id++) {
			if (registry[id].name.equals(name)) {
				return id;
			}
		}
		Log.error("Can not find tile named: " + name);
		return -1;
	}

	private int id;
	private Vector2f textureCoords;
	private String name;

	public Tile(Vector2f textureCoords) {
		this.setTextureCoords(textureCoords);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vector2f getTextureCoords() {
		return textureCoords;
	}

	public void setTextureCoords(Vector2f textureCoords) {
		this.textureCoords = textureCoords;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
