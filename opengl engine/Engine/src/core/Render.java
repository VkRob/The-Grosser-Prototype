package core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;

import java.util.ArrayList;

import org.lwjgl.BufferUtils;

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
	private Vector3f camera_pos = new Vector3f(1.0f, 1.0f, 20.0f);
	private float camera_speed = 0.1f;
	private ArrayList<Tile> tiles = new ArrayList<Tile>();

	private int framebuffer;
	private int textureColorbuffer;

	public Render() {
		projectionMatrix = Matrix4f.perspective(45.0f, Main.WIDTH / Main.HEIGHT, 1.0f, 100.0f);

		int SIZE = 30;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (i % 4 == 0 && j % 4 == 0) {
					tiles.add(new Tile(new Vector2f(i - (SIZE / 2), j - (SIZE / 2)), 2, this));
				} else
					tiles.add(new Tile(new Vector2f(i - (SIZE / 2), j - (SIZE / 2)), 0, this));
			}
		}

		// Create frame buffer
		framebuffer = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);

		// Attach a color attachment to the frame buffer
		textureColorbuffer = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureColorbuffer);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, (int) Main.WIDTH, (int) Main.HEIGHT, 0, GL_RGB, GL_UNSIGNED_BYTE,
				BufferUtils.createByteBuffer((int) (Main.WIDTH * Main.HEIGHT * 3)));
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureColorbuffer, 0);

		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
			System.out.println("ERROR::FRAMEBUFFER:: Framebuffer is not complete!");
		glBindFramebuffer(GL_FRAMEBUFFER, 0);

		for (Tile t : tiles) {
			t.getShader().addFrameBufferTexture("u_Texture", textureColorbuffer);
		}

	}

	public void render() {

		// Sort the tiles by the ones that cast shadows and the ones that do not
		ArrayList<Tile> shadows = new ArrayList<Tile>();

		for (Tile tile : tiles) {
			if (tile.getID() == 2) {
				shadows.add(tile);
			}
		}
		// System.out.println(shadows.size() + "/" + tiles.size());
		// glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
		//
		// glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		// glClear(GL_COLOR_BUFFER_BIT);
		// for (Tile tile : tiles) {
		// // System.out.println(shadows.indexOf(tile));
		// tile.renderNormally(shadows);
		// glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		// }
		//
		// glBindFramebuffer(GL_FRAMEBUFFER, 0);

		glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);
		// System.out.println("#############################");
		for (Tile tile : tiles) {

			tile.renderNormally(shadows);
			glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

		}

		// glBindFramebuffer(GL_FRAMEBUFFER, 0);

		// glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		// glClear(GL_COLOR_BUFFER_BIT);

		// glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		// glClear(GL_COLOR_BUFFER_BIT);

		// Render the quad
		// renderQuad();
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

	public void moveLight(char key) {
		float speed = 0.1f;
		for (Tile t : tiles) {
			if (key == 'u')
				t.getLightPosition().get(0).y += speed;
			if (key == 'j')
				t.getLightPosition().get(0).y -= speed;
			if (key == 'h')
				t.getLightPosition().get(0).x -= speed;
			if (key == 'k')
				t.getLightPosition().get(0).x += speed;
		}
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public Vector3f getCameraPosition() {
		return camera_pos;
	}
}
