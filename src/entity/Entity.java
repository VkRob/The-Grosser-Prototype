package entity;

import java.awt.image.BufferedImage;

public abstract class Entity {
	protected int x, y;
	protected int width = 16, height = 16;

	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}

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
