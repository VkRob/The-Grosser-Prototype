package core;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import main.Main;

//import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.opengl.GL12.*;
//import static org.lwjgl.opengl.GL13.*;
//import static org.lwjgl.opengl.GL14.*;
//import static org.lwjgl.opengl.GL15.*;
//import static org.lwjgl.opengl.GL20.*;
//import static org.lwjgl.opengl.GL21.*;
//import static org.lwjgl.opengl.GL30.*;
//import static org.lwjgl.opengl.GL31.*;
//import static org.lwjgl.opengl.GL32.*;
//import static org.lwjgl.opengl.GL33.*;
//import static org.lwjgl.opengl.GL40.*;
//import static org.lwjgl.opengl.GL41.*;
//import static org.lwjgl.opengl.GL42.*;
//import static org.lwjgl.opengl.GL43.*;
//import static org.lwjgl.opengl.GL44.*;
//import static org.lwjgl.opengl.GL45.*;

import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import tile.Tile;

public class Render {

	public static final int sizeOf_GL_FLOAT = 4;

	private Matrix4f projectionMatrix;
	private Vector3f camera_pos = new Vector3f(1.0f, 1.0f, 10.0f);
	private float camera_speed = 0.1f;
	private Tile tile;
	private Tile tile2;

	public Render() {
		projectionMatrix = Matrix4f.perspective(45.0f, Main.WIDTH / Main.HEIGHT, 1.0f, 10.0f);
		tile = new Tile(new Vector2f(0f, 0f), 0, this);
		tile2 = new Tile(new Vector2f(1f, 0f), 0, this);
	}

	public void render() {
		tile.render();
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		tile2.render();
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
	}

	public void moveW() {
		camera_pos.y -= camera_speed;
	}

	public void moveS() {
		camera_pos.y += camera_speed;
	}

	public void moveA() {
		camera_pos.x -= camera_speed;
	}

	public void moveD() {
		camera_pos.x += camera_speed;
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public Vector3f getCameraPosition() {
		return camera_pos;
	}
}
