package engine.render.shader;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {

	private static Object classHandle = new Object();

	private static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(classHandle.getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static float[] getTexturePixels(BufferedImage img) {

		float[] pixels = new float[img.getWidth() * img.getHeight() * 3];

		int index = 0;
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {

				int color = img.getRGB(x, y);
				int red = (color & 0x00ff0000) >> 16;
				int green = (color & 0x0000ff00) >> 8;
				int blue = color & 0x000000ff;

				pixels[index] = (float) red / 255f;
				index++;
				pixels[index] = (float) green / 255f;
				index++;
				pixels[index] = (float) blue / 255f;
				index++;

			}
		}
		return pixels;
	}

	public static Texture loadTexture(String path) {
		BufferedImage img = loadImage(path);
		int imgWidth = img.getWidth();
		int imgHeight = img.getHeight();
		return new Texture(imgWidth, imgHeight, getTexturePixels(img));
	}

	private int width;
	private int height;
	private float[] pixels;

	private Texture(int width, int height, float[] pixels) {
		this.setWidth(width);
		this.setHeight(height);
		this.setPixels(pixels);
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

	public float[] getPixels() {
		return pixels;
	}

	public void setPixels(float[] pixels) {
		this.pixels = pixels;
	}
}
