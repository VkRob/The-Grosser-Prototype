package engine.render;

import org.joml.Vector2f;

public class TextureAtlas {

	private Vector2f dimensions;

	public TextureAtlas(Vector2f dimensions) {
		this.setDimensions(dimensions);
	}

	public float getXSize() {
		return 1f / dimensions.x;
	}

	public float getYSize() {
		return 1f / dimensions.y;
	}

	public Vector2f getTextureCoordFor(Vector2f spriteCoord) {
		Vector2f r = new Vector2f();
		float xSize = 1f / dimensions.x;
		float ySize = 1f / dimensions.y;
		r.x = spriteCoord.x * xSize;
		r.y = spriteCoord.y * ySize;
		return r;
	}

	public Vector2f getDimensions() {
		return dimensions;
	}

	public void setDimensions(Vector2f dimensions) {
		this.dimensions = dimensions;
	}
}
