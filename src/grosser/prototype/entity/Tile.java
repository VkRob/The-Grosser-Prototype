package grosser.prototype.entity;

import grosser.engine.core.ImageLoader;
import grosser.engine.math.Vector2f;
import grosser.engine.shader.ShaderProgram;

public class Tile {

	public static int NUM_OF_TILES = 100;
	public static float[] tileset[] = new float[NUM_OF_TILES][];
	public static boolean[] tilesSolid = new boolean[NUM_OF_TILES];
	public static boolean[] tilesCastShadow = new boolean[NUM_OF_TILES];
	public static boolean[] tilesLightSource = new boolean[NUM_OF_TILES];

	/* /res/Tiles/street/*.png */
	public static final int TILE_STREET_CURB_EDGE = 0;

	public static final int TILE_SHADOWS = 98;
	public static final int TILE_GUARD_SPAWN = 99;
	public static final int TILE_BLANK = 65;
	public static int CTR = 0;

	static {
		TilePack.addNewTilePack("street/road", CTR, 3, false, false, false);
		TilePack.addNewTilePack("street/curb_edge", CTR, 8, false, false, false);
		TilePack.addNewTilePack("street/crosswalk_end", CTR, 8, false, false, false);
		TilePack.addNewTilePack("street/crosswalk_mid", CTR, 8, false, false, false);
		TilePack.addNewTilePack("street/curb_inside", CTR, 8, false, false, false);
		TilePack.addNewTilePack("street/curb_outside", CTR, 8, false, false, false);
		TilePack.addNewTilePack("street/manhole", CTR, 1, false, false, false);
		TilePack.addNewTilePack("sidewalk", CTR, 18, false, false, false);
		TilePack.addNewTilePack("generator", 67, 10, true, false, false);
		//TilePack.addNewTilePack("lamp", CTR, 4, false, false, true);

		tilesSolid[TILE_SHADOWS] = true;
		tilesCastShadow[TILE_SHADOWS] = true;
		tilesLightSource[TILE_SHADOWS] = false;
		tileset[TILE_SHADOWS] = ImageLoader.getTexturePixels("/Tiles/blank.png");

		tilesSolid[TILE_GUARD_SPAWN] = false;
		tilesCastShadow[TILE_GUARD_SPAWN] = false;
		tilesLightSource[TILE_GUARD_SPAWN] = false;
		tileset[TILE_GUARD_SPAWN] = ImageLoader.getTexturePixels("/Tiles/sidewalk/0.png");
		
		tilesSolid[TILE_GUARD_SPAWN] = false;
		tilesCastShadow[TILE_GUARD_SPAWN] = false;
		tilesLightSource[TILE_GUARD_SPAWN] = false;
		tileset[TILE_GUARD_SPAWN] = ImageLoader.getTexturePixels("/Tiles/sidewalk/0.png");
		
		tilesSolid[TILE_BLANK] = true;
		tilesCastShadow[TILE_BLANK] = false;
		tilesLightSource[TILE_BLANK] = false;
		tileset[TILE_BLANK] = ImageLoader.getTexturePixels("/Tiles/blank.png");
	}

	public static final float SIZE = 1f; // Coordinate
											// system is
											// no longer
											// in
	// integers but openGL units!!!

	private ShaderProgram shader;

	private Vector2f tilePosition;
	private int ID;
	private Object metaData;

	public Tile(Vector2f tilePosition, int ID) {
		this.tilePosition = tilePosition;
		this.setID(ID);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Vector2f getTilePosition() {
		return tilePosition;
	}

	public void setTilePosition(Vector2f tilePosition) {
		this.tilePosition = tilePosition;
	}

	public boolean isSolid() {
		return tilesSolid[ID];
	}

	public Object getMetaData() {
		return metaData;
	}

	public void setMetaData(Object metaData) {
		this.metaData = metaData;
	}

}
