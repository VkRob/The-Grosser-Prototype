package engine.entity;

import org.joml.Vector3f;

public class EntityBackground extends Entity {

	private Vector3f color;

	public EntityBackground(Vector3f color) {
		super(Entity.TYPE_BACKGROUND);
		this.setColor(color);
	}

	@Override
	public void update() {

	}

	public float getColorRed() {
		return color.x;
	}

	public float getColorGreen() {
		return color.y;
	}

	public float getColorBlue() {
		return color.z;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

}
