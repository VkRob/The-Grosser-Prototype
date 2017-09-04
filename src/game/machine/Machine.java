package game.machine;

import org.joml.Vector2f;

import engine.logic.Registry;

public class Machine {
	public static Registry REGISTRY = new Registry();

	public static void registerMachines() {
		REGISTRY.add(0, "Basic", new BasicMachine());
	}

	private int width, height;
	private Vector2f textureCoords;

	public Machine(int width, int height, Vector2f textureCoords) {
		this.width = width;
		this.height = height;
		this.textureCoords = textureCoords;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Vector2f getTextureCoords() {
		return textureCoords;
	}
}
