package render;

import java.awt.image.BufferedImage;

/**
 * Basic GUI Texture, literally just a container for position, scaling, and
 * image data
 * 
 * @author Robert
 *
 */
public class GuiElement {
	private int x;
	private int y;
	private int width;
	private int height;
	private BufferedImage image;

	public GuiElement(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Sets the GuiElement's width and height to its image's width and height
	 */
	public void pack() {
		this.width = image.getWidth();
		this.height = image.getHeight();
	}

	public void setImage(BufferedImage img) {
		this.image = img;
	}

	public BufferedImage getImage() {
		return image;
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
