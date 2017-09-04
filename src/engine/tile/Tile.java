package engine.tile;

import org.joml.Vector2f;

import engine.logic.Registry;

public abstract class Tile {

	private static Registry registry = new Registry();

	public static void registerTiles() {
		registerTile(0, "void", new TileVoid());
		registerTile(1, "grass", new TileNormal(new Vector2f(0, 0)));
		registerTile(2, "stone", new TileNormal(new Vector2f(1, 0)));
	}

	private static void registerTile(int id, String string, Tile tile) {
		tile.setId(id);
		tile.setName(string);
		registry.add(id, string, tile);
	}

	public static int getTileID(Tile tile) {
		return registry.getIdOfObj(tile);
	}

	public static Tile getTile(int id) {
		return (Tile) registry.getObjByID(id);
	}

	public static int getTileID(String name) {
		return registry.getIdByName(name);
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
