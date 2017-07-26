package grosser.prototype.entity;

import grosser.prototype.scenes.SceneGame;

import java.awt.image.BufferedImage;

/**
 * @author Robert
 * Entity class, mainly a container for important info for each grosser.prototype.entity like
 * position and sizing
 */

public abstract class Entity {

	protected int x, y;
	protected int width = 16, height = 16;

	final SceneGame sceneGame;

	Entity(int x, int y, SceneGame sceneGame) {
		this.x = x;
		this.y = y;
		this.sceneGame = sceneGame;
	}

	Entity(int x, int y, SceneGame sceneGame, int width, int height) {
	    this.x = x;
	    this.y = y;
	    this.sceneGame = sceneGame;
	    this.height = height;
	    this.width = width;
    }

	/**
	 * Sets the Entity's dimensions to inputted image's dimensions
	 */
	public void pack(BufferedImage img) {
		this.width = img.getWidth();
		this.height = img.getHeight();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
