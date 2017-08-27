package engine.entity;

import org.joml.Vector2f;

public class EntitySprite extends Entity {

	private Vector2f position;
	private Vector2f dimensions;
	private Vector2f texCoords = new Vector2f(0, 0);

	public EntitySprite() {
		super(Entity.TYPE_SPRITE);
		position = new Vector2f(0, 0);
		dimensions = new Vector2f(1, 1);
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getDimensions() {
		return dimensions;
	}

	public Vector2f getTextureCoords() {
		return texCoords;
	}

	public void setTexCoords(Vector2f texCoords) {
		this.texCoords = texCoords;
	}

	@Override
	public void update() {
		
	}

}
