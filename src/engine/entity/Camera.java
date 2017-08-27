package engine.entity;

import org.joml.Vector2f;

public class Camera {

	private Vector2f position;
	// private int width, height;
	private EntitySprite spriteToWatch = null;

	public Camera(Vector2f position, int screenWidth, int screenHeight) {
		this.setPosition(position);
		// this.width = screenWidth;
		// this.height = screenHeight;
	}

	public void update() {
		if (spriteToWatch != null) {
			// position = new
			// Vector2f(spriteToWatch.getPosition()).add(spriteToWatch.getDimensions().mul(0.5f));
		}
		// float speed = 1f;
		// if (Input.getKeyByName("UP").isDown()) {
		// position.y += speed;
		// }
		// if (Input.getKeyByName("DOWN").isDown()) {
		// position.y -= speed;
		// }
		// if (Input.getKeyByName("LEFT").isDown()) {
		// position.x -= speed;
		// }
		// if (Input.getKeyByName("RIGHT").isDown()) {
		// position.x += speed;
		// }
		// // position.x *= 0.9f;
		// // position.y *= 0.9f;
	}

	public void watchSprite(EntitySprite sprite) {
		spriteToWatch = sprite;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}
}
