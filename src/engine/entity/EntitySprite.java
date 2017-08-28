package engine.entity;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class EntitySprite extends Entity {

	private Vector2f position;
	private Vector2f dimensions = new Vector2f(0.5f, 0.5f);
	private Vector2f texCoords = new Vector2f(0, 0);
	private Vector3f tint = new Vector3f(1, 1, 1);

	private boolean usesTexture = true;

	public EntitySprite() {
		super(Entity.TYPE_SPRITE);
		position = new Vector2f(0, 0);
		dimensions = new Vector2f(0.5f, 0.5f);
	}

	@Override
	public void update() {

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

	public void setDimensions(Vector2f dimensions) {
		this.dimensions = dimensions;
	}

	public Vector2f getTexCoords() {
		return texCoords;
	}

	public void setTexCoords(Vector2f texCoords) {
		this.texCoords = texCoords;
	}

	public boolean isUsesTexture() {
		return usesTexture;
	}

	public void setUsesTexture(boolean usesTexture) {
		this.usesTexture = usesTexture;
	}

	public Vector3f getTint() {
		return tint;
	}

	public void setTint(Vector3f tint) {
		this.tint = tint;
	}

}
