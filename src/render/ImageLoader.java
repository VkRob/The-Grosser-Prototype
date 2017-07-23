package render;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	private static ImageLoader imgLoader = new ImageLoader();

	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(imgLoader.getClass().getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
