package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import map.Map;
import map.Tile;
import render.ImageLoader;

public class EntityWorker extends Entity {

	/**
	 * @author Robert
	 * Most of this code is just placeholder code... thrown together to get the
	 * entity to work
	 */

	private BufferedImage img = ImageLoader.loadImage("/Sprites/McCree/0.png");

	private int targetX, targetY;

	public EntityWorker(int x, int y) {
		super(x, y);
		super.setWidth(Tile.SIZE);
		super.setHeight(Tile.SIZE);

		plotNewPosition();
	}

	/**
	 * Picks a random location for the Worker to "wander" to next, sets the
	 * targetX and targetY variables to the position
	 * 
	 * Range of (1,1) to (16,16)
	 */
	private void plotNewPosition() {
		Random r = new Random();
		targetX = (r.nextInt(15) + 1) * Tile.SIZE;
		targetY = (r.nextInt(15) + 1) * Tile.SIZE;
	}

	/**
	 * Update and perform Worker actions (mainly just wandering around)
	 */
	public void update(Map map) {

		// Worker calculates the change in X and Y required to get from its
		// current position to its target location
		float changeX = (float) (targetX - getX());
		float changeY = (float) (targetY - getY());

		// Worker normalizes this to a unit vector (magnitude of a unit vector =
		// 1)
		float magnitude = (float) Math.sqrt((changeX * changeX) + (changeY * changeY));
		float unitX = changeX / magnitude;
		float unitY = changeY / magnitude;

		// Speed of 5f
		int moveX = (int) (unitX * 5f);
		int moveY = (int) (unitY * 5f);

		// If the distance to the target is less than 5, find a new location
		if (magnitude <= 5f) {
			plotNewPosition();
		}

		// Move on X axis, check collision. If collided, move back.
		setX(getX() + moveX);
		for (Tile t : map.getTilesTouching(new Rectangle(getX(), getY(), getWidth(), getHeight()))) {
			if (Tile.isTileSolid(t)) {
				// Find new location because clearly this location is outside
				// the factory (and thus impossible to reach)
				plotNewPosition();
				setX(getX() - moveX);
			}
		}

		// Move on Y axis, check collision. If collided, move back.
		setY(getY() + moveY);
		for (Tile t : map.getTilesTouching(new Rectangle(getX(), getY(), getWidth(), getHeight()))) {
			if (Tile.isTileSolid(t)) {
				// Find new location because clearly this location is outside
				// the factory (and thus impossible to reach)
				plotNewPosition();
				setY(getY() - moveY);
			}
		}

	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

}
