package core;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	private static ImageLoader imgLoader = new ImageLoader();

	private static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(imgLoader.getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static float[] getTexturePixels(String path) {

		BufferedImage img = loadImage(path);
		float[] pixels = new float[img.getWidth() * img.getHeight() * 3];

		int index = 0;
		for (int y = img.getHeight()-1; y >= 0; y--) {
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
}
