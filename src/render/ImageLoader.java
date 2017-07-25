package render;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Another one of those classes we shouldn't have to touch, this simply loads
 * images for us so we don;t have to type out the code every time
 */
public class ImageLoader {

	private static ImageLoader imgLoader = new ImageLoader();

	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(imgLoader.getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
}
