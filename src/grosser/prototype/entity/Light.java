package grosser.prototype.entity;

import grosser.engine.math.Vector3f;

public class Light {
	
	private Vector3f position;
	private Vector3f color;
	
	private boolean ownedByGuard = false;
	
	public Light(Vector3f position, Vector3f color, boolean ownedByGuard) {
		this.setOwnedByGuard(ownedByGuard);
		this.position = position;
		this.color = color;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

	public boolean isOwnedByGuard() {
		return ownedByGuard;
	}

	public void setOwnedByGuard(boolean ownedByGuard) {
		this.ownedByGuard = ownedByGuard;
	}
}
