package grosser.prototype.entity;

import java.util.ArrayList;

import grosser.engine.levels.LevelLoader;
import grosser.engine.math.Vector2f;
import grosser.engine.math.Vector3f;

/**
 * Map class manages the tiles on the grosser.prototype.map
 * 
 * @author Robert
 *
 */

// TODO remove SIZE variable and make the grosser.prototype.map dynamic and
// infinite

public class TileManager {

	private ArrayList<Tile> tiles = new ArrayList<Tile>();

	public TileManager() {

	}

	public void generateLevel(WorldRenderer renderer, EntityManager emgr) {

		ArrayList<Tile> level = LevelLoader.loadLevel("C:/LD39/level0.lvl");

		// Add Light really far away from level so that the shader works ecks
		// dee
		renderer.getLights().add(
				new Light(new Vector3f(-10, -10, LevelLoader.lightRadius.getValue()), new Vector3f(1, 1, 1), false));

		int index = 0;
		for (int i = 0; i < LevelLoader.dimensions.getY(); i++) {
			for (int j = 0; j < LevelLoader.dimensions.getX(); j++) {
				float y = i - (LevelLoader.dimensions.getY() / 2);
				float x = j - (LevelLoader.dimensions.getX() / 2);
				int id = level.get(index).getID();
				Object metaData = level.get(index).getMetaData();
				if (metaData == null) {
					metaData = 0x00;
				}

				if (Tile.tilesLightSource[id]) {

				}
				if ((Integer) metaData == 67 || (Integer) metaData == 68) {

					int eID = Entity.ID_PLAYER;
					if ((Integer) metaData == 67) {
						eID = Entity.ID_GENERATOR0;
						renderer.getLights().add(new Light(new Vector3f(x + 2.5f, -y - 1.5f, 5f),
								new Vector3f(1.0f, 1.0f, 1.0f), false));
					}
					if ((Integer) metaData == 68) {
						eID = Entity.ID_GENERATOR1;
					}

					emgr.addEntity(new LampEntity(new Vector2f(x, -y), eID));
				}
				if ((Integer) metaData == 62 || (Integer) metaData == 63 || (Integer) metaData == 64
						|| (Integer) metaData == 65) {
					renderer.getLights().add(new Light(new Vector3f(x, -y, LevelLoader.lightRadius.getValue()),
							LampEntity.lightColor, false));
					int eID = Entity.ID_PLAYER;
					if ((Integer) metaData == 62) {
						eID = Entity.ID_LAMP_UP;
					}
					if ((Integer) metaData == 63) {
						eID = Entity.ID_LAMP_RIGHT;
					}
					if ((Integer) metaData == 64) {
						eID = Entity.ID_LAMP_DOWN;
					}
					if ((Integer) metaData == 65) {
						eID = Entity.ID_LAMP_LEFT;
					}
					emgr.addEntity(new LampEntity(new Vector2f(x, -y), eID));
				}
				if ((Integer) metaData == 33) {
					emgr.getPlayer().setPosition(new Vector2f(x, -y));
					emgr.getPlayer().setInitPosition(new Vector2f(x, -y));
				}
				if (id == Tile.TILE_GUARD_SPAWN) {
					Light guardLight = new Light(new Vector3f(x, -y, LevelLoader.lightRadius.getValue()),
							LampEntity.lightColor, true);
					renderer.getLights().add(guardLight);
					emgr.addEntity(new GuardEntity(emgr, guardLight, new Vector2f(x, -y),
							LevelLoader.guardPaths.get((Integer) metaData)));
				}

				tiles.add(new Tile(new Vector2f(x, -y), id));
				index++;
			}
		}
	}

	public boolean anyTilesSolid(ArrayList<Tile> t) {
		for (Tile tile : t) {
			if (tile.isSolid()) {
				return true;
			}
		}
		return false;
	}

	private boolean tilesIntersect(Vector2f pPos, Vector2f pDim, Vector2f tPos, Vector2f tDim) {
		float tw = pDim.x;
		float th = pDim.y;
		float rw = tDim.x;
		float rh = tDim.y;
		if (rw <= 0f || rh <= 0f || tw <= 0f || th <= 0f) {
			return false;
		}
		float tx = pPos.x;
		float ty = pPos.y;
		float rx = tPos.x;
		float ry = tPos.y;
		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;
		// overflow || intersect
		return ((rw < rx || rw > tx) && (rh < ry || rh > ty) && (tw < tx || tw > rx) && (th < ty || th > ry));

	}

	public ArrayList<Tile> getTilesTouching(Vector2f position, Vector2f dimensions) {
		ArrayList<Tile> out = new ArrayList<Tile>();
		for (Tile t : tiles) {
			if (tilesIntersect(position, dimensions, t.getTilePosition(), new Vector2f(Tile.SIZE, Tile.SIZE))) {
				out.add(t);
			}
		}
		return out;
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public boolean anyTilesCastShadow(ArrayList<Tile> t) {
		for (Tile tile : t) {
			if (Tile.tilesCastShadow[tile.getID()]) {
				return true;
			}
		}
		return false;
	}
}
