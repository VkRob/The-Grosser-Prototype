package grosser.prototype.map;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import grosser.engine.core.DistanceSorter;
import grosser.engine.core.ImageLoader;
import grosser.engine.core.Render;
import grosser.engine.math.Matrix4f;
import grosser.engine.math.Vector2f;
import grosser.engine.math.Vector3f;
import grosser.engine.shader.ShaderProgram;
import grosser.engine.shader.ShaderTexture;
import grosser.engine.shader.attrib.ShaderAttribVector2f;
import grosser.engine.shader.uniform.ShaderUniformFloat;
import grosser.engine.shader.uniform.ShaderUniformInt;
import grosser.engine.shader.uniform.ShaderUniformMatrix4f;
import grosser.engine.shader.uniform.ShaderUniformVector2f;
import grosser.engine.shader.uniform.ShaderUniformVector2fv;
import grosser.engine.shader.uniform.ShaderUniformVector3f;
import grosser.engine.shader.uniform.ShaderUniformVector3fv;

public class Tile {

	public static final int TILE_GRASS = 0;
	public static final int TILE_STONE = 1;
	public static final int TILE_WALL = 2;

	public static final int SIZE = 1; // Coordinate system is no longer in
										// integers but openGL units!!!

	public static float[] tileset[] = {

			ImageLoader.getTexturePixels("/test.png"),

			ImageLoader.getTexturePixels("/Tiles/stone.png"),

			ImageLoader.getTexturePixels("/Tiles/wall.png"),

	};

	
	private ShaderProgram shader;

	public ShaderProgram getShader() {
		return shader;
	}

	private Vector2f tilePosition;
	

	private int ID;

	public Tile(Vector2f tilePosition, int ID) {
		this.tilePosition = tilePosition;
		this.setID(ID);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Vector2f getTilePosition() {
		return tilePosition;
	}

	public void setTilePosition(Vector2f tilePosition) {
		this.tilePosition = tilePosition;
	}

}
