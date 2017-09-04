package engine.entity;

import org.joml.Vector2f;

import game.machine.Machine;

public class EntityMachine extends Entity {

	private Vector2f position;
	private Machine type;

	public EntityMachine(Vector2f position, Machine type) {
		super(Entity.TYPE_MACHINE);
		this.position = position;
		this.type = type;
	}

	@Override
	public void update() {

	}

	public Vector2f getDimensions() {
		return new Vector2f(type.getWidth(), type.getHeight());
	}

	public Vector2f getTexCoords() {
		return type.getTextureCoords();
	}

	public Vector2f getPosition() {
		return position;
	}

}
