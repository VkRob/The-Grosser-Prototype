package engine.tile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;

public abstract class Tile {

    private static final Logger LOG = LogManager.getLogger(Tile.class);

	private static final int numOfTiles = 256;
	private static Tile[] registry;

	public static void registerTiles() {
		registry = new Tile[numOfTiles];
		registerTile(0, "void", new TileVoid());
		registerTile(1, "grass", new TileNormal(new Vector2f(0, 0)));
		registerTile(2, "stone", new TileNormal(new Vector2f(1, 0)));
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
		LOG.error("Error getting tile ID", new RuntimeException("Tile ID not found: " + tile.getClass().getName()));
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
		LOG.error("Error getting tile by name", new RuntimeException("Cannot find tile named " + name));
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
