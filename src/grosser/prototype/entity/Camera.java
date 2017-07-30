package grosser.prototype.entity;

import grosser.engine.core.Render;
import grosser.engine.math.Vector2f;

public class Camera {

	private Vector2f position;
	private Vector2f velocity;

	public Camera() {
		position = new Vector2f(0, 0);
		velocity = new Vector2f(0, 0);
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getEndPosition(Render render) {
		//return position.scale(1f + (1f / render.getCameraZoom()));
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}
}
