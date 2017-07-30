package grosser.prototype.entity;

import grosser.engine.core.ImageLoader;

public class TilePack {

	public static void addNewTilePack(String directory, int firstID, int numOfTiles, boolean solid, boolean castShadow,
			boolean lightSource) {
		for (int i = firstID; i < firstID + numOfTiles; i++) {
			Tile.tilesSolid[i] = solid;
			Tile.tilesCastShadow[i] = castShadow;
			Tile.tilesLightSource[i] = lightSource;
			Tile.tileset[i] = ImageLoader.getTexturePixels("/Tiles/" + directory + "/" + (i - firstID) + ".png");
		}
		Tile.CTR += numOfTiles;
	}
}
