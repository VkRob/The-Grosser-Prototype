package engine.entity;

import org.joml.Vector2f;

import engine.tile.Tile;

public class TileEntity {

	private Vector2f position;
	private Tile tileType;

	public TileEntity(Vector2f position, int tileType) {
		this.setPosition(position);
		this.setTileType(Tile.getTile(tileType));
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Tile getTileType() {
		return tileType;
	}
	
	public void setTileType(int id) {
		setTileType(Tile.getTile(id));
	}

	public void setTileType(Tile tileType) {
		this.tileType = tileType;
	}

	public Vector2f getTextureCoords() {
		return tileType.getTextureCoords();
	}
}
