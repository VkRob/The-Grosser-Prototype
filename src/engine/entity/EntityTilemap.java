package engine.entity;

import java.util.ArrayList;

import org.joml.Vector2f;

import engine.render.TextureAtlas;
import engine.script.Script;
import engine.tile.Tile;
import engine.util.Log;

public class EntityTilemap extends Entity {

	private Script script;

	private Vector2f position;
	private ArrayList<TileEntity> tiles;

	private TextureAtlas atlas;

	private boolean needsUpdating = false;

	// Mesh Data
	private float[] vertices;
	private int[] elements;

	public EntityTilemap() {
		super(Entity.TYPE_TILEMAP);

		/* TODO: MOVE REGISTER METHOD TO MAIN */
		Tile.registerTiles();

		position = new Vector2f(0, 0);

		atlas = new TextureAtlas(new Vector2f(16, 16));
		tiles = new ArrayList<TileEntity>();

		// for (int x = 0; x < 10; x++) {
		// for (int y = 0; y < 10; y++) {
		// tiles.add(new TileEntity(new Vector2f(x, y), 0));
		// }
		// }

		script = new Script("GenerateWorld.lua");
		script.execute("GenerateWorld", this);

		generateNewMesh(new Vector2f(0, 0), new Vector2f(100, 100));
	}

	public TileEntity getTileAtPos(Vector2f position) {
		for (TileEntity e : tiles) {
			if (e.getPosition().equals(position)) {
				return e;
			}
		}
		Log.log("Failed to find TileEntity at position: " + position);
		return null;
	}

	public void generateNewMesh(Vector2f cullingPosition, Vector2f cullingRect) {

		needsUpdating = true;

		ArrayList<Float> verticesL = new ArrayList<>();
		ArrayList<Integer> elementsL = new ArrayList<>();

		int ctr = 0;
		final float size = 1f;
		final float halfSize = size / 2;
		for (TileEntity tile : tiles) {

			Vector2f t = tile.getPosition();

			// if (intersects(cullingPosition, cullingRect, t, new Vector2f(1,
			// 1))) {
			if (true) {
				/* Top Left */

				// Position
				verticesL.add(t.x - halfSize);
				verticesL.add(t.y + halfSize);

				// TextureCoord
				verticesL.add(tile.getTextureCoords().x * atlas.getXSize());
				verticesL.add(tile.getTextureCoords().y * atlas.getYSize());

				/* Top Right */

				// Position
				verticesL.add(t.x + halfSize);
				verticesL.add(t.y + halfSize);

				// TextureCoord
				verticesL.add(tile.getTextureCoords().x * atlas.getXSize() + atlas.getXSize());
				verticesL.add(tile.getTextureCoords().y * atlas.getYSize());

				/* Bottom Right */

				// Position
				verticesL.add(t.x + halfSize);
				verticesL.add(t.y - halfSize);

				// TextureCoord
				verticesL.add(tile.getTextureCoords().x * atlas.getXSize() + atlas.getXSize());
				verticesL.add(tile.getTextureCoords().y * atlas.getYSize() + atlas.getYSize());

				/* Bottom Left */

				// Position
				verticesL.add(t.x - halfSize);
				verticesL.add(t.y - halfSize);

				// TextureCoord
				verticesL.add(tile.getTextureCoords().x * atlas.getXSize());
				verticesL.add(tile.getTextureCoords().y * atlas.getYSize() + atlas.getYSize());

				/* Elements */

				elementsL.add(ctr + 0);
				elementsL.add(ctr + 1);
				elementsL.add(ctr + 2);

				elementsL.add(ctr + 2);
				elementsL.add(ctr + 3);
				elementsL.add(ctr + 0);

				ctr += 4;
			}
		}

		vertices = toPrimitive(verticesL.toArray(new Float[verticesL.size()]));
		elements = toPrimitive(elementsL.toArray(new Integer[elementsL.size()]));
	}

	private float[] toPrimitive(Float[] array) {
		float[] r = new float[array.length];
		for (int i = 0; i < array.length; i++) {
			r[i] = array[i];
		}
		return r;
	}

	private int[] toPrimitive(Integer[] array) {
		int[] r = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			r[i] = array[i];
		}
		return r;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public float[] getVertices() {
		return vertices;
	}

	public int[] getElements() {
		return elements;
	}

	public boolean isNeedsUpdating() {
		return needsUpdating;
	}

	public void setNeedsUpdating(boolean needsUpdating) {
		this.needsUpdating = needsUpdating;
	}

	public boolean intersects(Vector2f pos, Vector2f dimensions, Vector2f pos2, Vector2f dimensions2) {
		float tw = dimensions.x;
		float th = dimensions.y;
		float rw = dimensions2.x;
		float rh = dimensions2.y;
		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		float tx = pos.x;
		float ty = pos.y;
		float rx = pos2.x;
		float ry = pos2.y;
		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;
		// overflow || intersect
		return ((rw < rx || rw > tx) && (rh < ry || rh > ty) && (tw < tx || tw > rx) && (th < ty || th > ry));
	}

	@Override
	public void update() {

	}

	public ArrayList<TileEntity> getTiles() {
		return tiles;
	}

	public void setTiles(ArrayList<TileEntity> tiles) {
		this.tiles = tiles;
	}

}
