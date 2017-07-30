package grosser.engine.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageLoader {

	private static ImageLoader imgLoader = new ImageLoader();

	private static ArrayList<String> loadFile(String path) {
		ArrayList<String> out = new ArrayList<String>();
		try {
			InputStream in = imgLoader.getClass().getResourceAsStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String s;
			while ((s = reader.readLine()) != null) {
				out.add(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

	public static String getShader(String path) {
		String shader = "";
		for (String s : loadFile(path)) {
			shader = shader.concat("\n" + s);
		}
		return shader;
	}

	private static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(imgLoader.getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static float[] getGhostedPixels(int id, String path) {
		BufferedImage in = loadImage(path);
		BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = (Graphics2D) img.getGraphics();
		g.drawImage(in, 0, 0, img.getWidth(), img.getHeight(), null);
		g.setColor(Color.RED);
		g.setFont(new Font("serif", Font.PLAIN, 24));
		g.drawString(id + "", 0, 64);

		float[] pixels = new float[img.getWidth() * img.getHeight() * 3];

		int index = 0;
		for (int y = img.getHeight() - 1; y >= 0; y--) {
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

	public static float[] getTexturePixels(String path) {

		BufferedImage img = loadImage(path);
		float[] pixels = new float[img.getWidth() * img.getHeight() * 3];

		int index = 0;
		for (int y = img.getHeight() - 1; y >= 0; y--) {
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

	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img
	 *            The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}
}
