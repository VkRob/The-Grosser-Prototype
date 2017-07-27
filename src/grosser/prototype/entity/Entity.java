package grosser.prototype.entity;

import java.awt.image.BufferedImage;

/**
 * @author Robert
 * Entity class, mainly a container for important info for each entity, position, size, ID, and also
 * the EntityManager that is managing it.
 */

public abstract class Entity {

	protected int x, y;
	protected int width = 16, height = 16;

    // unique ID provided by the EntityManager
	private final int ID;

	protected final EntityManager entityManager;

    /**
     * This constructor uses the default width and height of 16 by 16
     */

	Entity(int x, int y, int ID, EntityManager entityManager) {
		this.x = x;
		this.y = y;
		this.ID = ID;
		this.entityManager = entityManager;
	}

	Entity(int x, int y, int ID, EntityManager entityManager, int width, int height) {
	    this.x = x;
	    this.y = y;
	    this.ID = ID;
	    this.entityManager = entityManager;
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

	int getID() {
		return ID;
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
