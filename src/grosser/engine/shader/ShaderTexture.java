package grosser.engine.shader;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

public class ShaderTexture {
	
	private int textureMode = GL_NEAREST;
	private int width, height;
	private float[] pixels;
	private int myID;
	private int textureBankID;
	private String name;

	public ShaderTexture(String name, float[] pixels, int width, int height, int textureMode) {
		super();
		this.textureMode = textureMode;
		this.pixels = pixels;
		this.name = name;
		this.setWidth(width);
		this.setHeight(height);
	}

	public int getMyID() {
		return myID;
	}

	public void setMyID(int myID) {
		this.myID = myID;
	}

	public float[] getPixels() {
		return pixels;
	}

	public void setPixels(float[] pixels) {
		this.pixels = pixels;
	}

	public int getTextureMode() {
		return textureMode;
	}

	public void setTextureMode(int textureMode) {
		this.textureMode = textureMode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getTextureBankID() {
		return textureBankID;
	}

	public void setTextureBankID(int textureBankID) {
		this.textureBankID = textureBankID;
	}

}
