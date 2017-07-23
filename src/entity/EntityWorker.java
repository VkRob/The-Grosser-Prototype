package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import map.Map;
import map.Tile;
import render.ImageLoader;

public class EntityWorker extends Entity {

	private BufferedImage img = ImageLoader.loadImage("/Sprites/McCree/0.png");

	private int targetX, targetY;

	public EntityWorker(int x, int y) {
		super(x, y);
		super.setWidth(Tile.SIZE);
		super.setHeight(Tile.SIZE);

		plotNewPosition();
	}

	private void plotNewPosition() {
		Random r = new Random();
		targetX = (r.nextInt(15) + 1) * Tile.SIZE;
		targetY = (r.nextInt(15) + 1) * Tile.SIZE;
	}

	public void update(Map map) {
		float changeX = (float) (targetX - getX());
		float changeY = (float) (targetY - getY());

		float magnitude = (float) Math.sqrt((changeX * changeX) + (changeY * changeY));
		float unitX = changeX / magnitude;
		float unitY = changeY / magnitude;

		int moveX = (int) (unitX * 5f);
		int moveY = (int) (unitY * 5f);

		if (magnitude <= 5f) {
			plotNewPosition();
		}

		setX(getX() + moveX);
		for (Tile t : map.getTilesTouching(new Rectangle(getX(), getY(), getWidth(), getHeight()))) {
			if (Tile.isTileSolid(t)) {
				plotNewPosition();
				setX(getX() - moveX);
			}
		}

		setY(getY() + moveY);
		for (Tile t : map.getTilesTouching(new Rectangle(getX(), getY(), getWidth(), getHeight()))) {
			if (Tile.isTileSolid(t)) {
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
