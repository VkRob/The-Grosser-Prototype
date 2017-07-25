package entity;

import scenes.SceneGame;

import java.awt.image.BufferedImage;

/**
 * Entity class, mainly a container for important info for each entity like
 * position and sizing
 */

// TODO Reformat this class, the constructor is exclusive for x and y (etc), its
// messy assigning data for an Entity

public abstract class Entity {

	protected int x, y;
	protected int width = 16, height = 16;


	protected final SceneGame sceneGame;

	public Entity(int x, int y, SceneGame sceneGame) {
		this.x = x;
		this.y = y;
		this.sceneGame = sceneGame;
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
