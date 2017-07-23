package render;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {

	private ArrayList<BufferedImage> frames;
	private float currentFrame = 0;
	private float speed = 0.1f;

	public Animation(float speed) {
		frames = new ArrayList<BufferedImage>();
		this.speed = speed;
	}

	public void update() {
		currentFrame += speed;
		currentFrame %= frames.size();
	}

	public BufferedImage getFrame() {
		return frames.get((int) Math.floor(currentFrame));
	}

	public void loadFrames(String path, int num_of_frames) {
		for (int i = 0; i < num_of_frames; i++) {
			frames.add(ImageLoader.loadImage(path + i + ".png"));
		}
	}
}
