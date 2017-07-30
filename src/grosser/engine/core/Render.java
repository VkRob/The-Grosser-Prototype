package grosser.engine.core;

import grosser.engine.math.Matrix4f;
import grosser.prototype.entity.Camera;
import grosser.prototype.main.Main;
import grosser.prototype.scenes.SceneManager;

public class Render {

	public static final int sizeOf_GL_FLOAT = 4;

	private Matrix4f projectionMatrix;
	private static float cameraZoom = 20f;

	private SceneManager game;
	private Camera camera;

	// private int framebuffer;
	// private int textureColorbuffer;

	public Render() {
		camera = new Camera();
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

	public void update() {
		game.getCurrentScene().update();
	}

	public void render() {
		game.getCurrentScene().render();
	}

	public void handleInput(char key) {
		game.getCurrentScene().handleInput(key);
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public Camera getCamera() {
		return camera;
	}

	public static float getCameraZoom() {
		return cameraZoom;
	}

	public void setCameraZoom(float cameraZoom) {
		this.cameraZoom = cameraZoom;
	}
}
