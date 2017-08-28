package engine.entity;

import org.joml.Vector3f;

import engine.logic.Scene;

public abstract class Entity {

	public static final int TYPE_BACKGROUND = 0;
	public static final int TYPE_TILEMAP = 1;
	public static final int TYPE_SPRITE = 2;
	public static final int TYPE_GUI = 3;

	private int type;

	protected Scene parentScene;

	private Vector3f tint = new Vector3f(1, 1, 1);

	public Entity(int type) {
		this.setType(type);
	}

	public abstract void update();

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Scene getParentScene() {
		return parentScene;
	}

	public void setParentScene(Scene parentScene) {
		this.parentScene = parentScene;
	}

	public Vector3f getTint() {
		return tint;
	}

	public void setTint(Vector3f tint) {
		this.tint = tint;
	}
}
