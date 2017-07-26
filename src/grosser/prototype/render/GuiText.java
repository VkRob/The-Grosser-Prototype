package grosser.prototype.render;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class GuiText extends GuiElement {

	private String text;
	private Color color;
	private String font = "DengXian";
	private int fontSize = 36;

	public GuiText(int x, int y, int width, int height, String text, Color c) {
		super(x, y, width, height);
		this.setText(text);
		this.setColor(c);
	}

	@Override
	public void setImage(BufferedImage image) {

	}

	@Override
	public BufferedImage getImage() {
		return ImageLoader.loadImage("/res/HUD/sample.png");
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

}
