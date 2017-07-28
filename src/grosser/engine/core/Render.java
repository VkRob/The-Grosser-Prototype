package grosser.engine.core;

import grosser.engine.math.Matrix4f;
import grosser.engine.math.Vector3f;
import grosser.prototype.main.Main;
import grosser.prototype.scenes.SceneManager;

public class Render {

	public static final int sizeOf_GL_FLOAT = 4;

	private Matrix4f projectionMatrix;
	private Vector3f camera_pos = new Vector3f(1.0f, 1.0f, 20.0f);
	private float camera_speed = 0.1f;

	
	private SceneManager game;

	private int framebuffer;
	private int textureColorbuffer;

	public Render() {
		projectionMatrix = Matrix4f.perspective(45.0f, Main.WIDTH / Main.HEIGHT, 1.0f, 100.0f);
		game = new SceneManager(this);
		
		
		
		/*
		 * This bit of commented out code is for framebuffers, which will allow
		 * us to add post processing effects
		 */
		// // Create frame buffer
		// framebuffer = glGenFramebuffers();
		// glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
		//
		// // Attach a color attachment to the frame buffer
		// textureColorbuffer = glGenTextures();
		// glBindTexture(GL_TEXTURE_2D, textureColorbuffer);
		// glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, (int) Main.WIDTH, (int)
		// Main.HEIGHT, 0, GL_RGB, GL_UNSIGNED_BYTE,
		// BufferUtils.createByteBuffer((int) (Main.WIDTH * Main.HEIGHT * 3)));
		// glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		// glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		// glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0,
		// GL_TEXTURE_2D, textureColorbuffer, 0);
		//
		// if (glCheckFramebufferStatus(GL_FRAMEBUFFER) !=
		// GL_FRAMEBUFFER_COMPLETE)
		// System.out.println("ERROR::FRAMEBUFFER:: Framebuffer is not
		// complete!");
		// glBindFramebuffer(GL_FRAMEBUFFER, 0);

	}

	public void render() {
		game.getCurrentScene().render();
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
		// for (Tile t : tiles) {
		// if (key == 'u')
		// t.getLightPosition().get(0).y += speed;
		// if (key == 'j')
		// t.getLightPosition().get(0).y -= speed;
		// if (key == 'h')
		// t.getLightPosition().get(0).x -= speed;
		// if (key == 'k')
		// t.getLightPosition().get(0).x += speed;
		// }
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public Vector3f getCameraPosition() {
		return camera_pos;
	}
}
