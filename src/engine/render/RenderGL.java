package engine.render;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.render.shader.Shader;
import engine.render.shader.SpriteShader;
import engine.render.shader.TilemapShader;

public class RenderGL {

	private float vertices[] = {
			// Position Texcoords
			-0.5f, 0.5f, 0.0f, 0.0f, // Top-left
			0.5f, 0.5f, 1.0f, 0.0f, // Top-right
			0.5f, -0.5f, 1.0f, 1.0f, // Bottom-right
			-0.5f, -0.5f, 0.0f, 1.0f, // Bottom-left
	};
	private int elements[] = { 0, 1, 2, 2, 3, 0 };

	private int tileMapVao;
	private int tileMapVbo;
	private int tileMapEbo;

	private int quadVao;
	private int quadVbo;
	private int quadEbo;

	private TilemapShader tileMapShader;
	private SpriteShader spriteShader;

	public RenderGL() {

		spriteShader = new SpriteShader();
		tileMapShader = new TilemapShader();

		initTileVAO();// Model Container
		initTileVBO();// Model Vertices
		initTileEBO();// Model Elements
		initTileShader();

		initQuadVAO();// Model Container
		initQuadVBO();// Model Vertices
		initQuadEBO();// Model Elements
		initQuadShader();

	}

	private void initQuadShader() {
		spriteShader.init();
	}

	private void initQuadEBO() {
		quadEbo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, quadEbo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, elements, GL_STATIC_DRAW);
	}

	private void initQuadVBO() {
		quadVbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, quadVbo);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
	}

	private void initQuadVAO() {
		quadVao = glGenVertexArrays();
		glBindVertexArray(quadVao);
	}

	public void bindQuad() {
		spriteShader.use();
		glBindVertexArray(quadVao);
	}

	public void unbind() {
		glBindVertexArray(0);
	}

	public void bindTiles() {
		glBindVertexArray(tileMapVao);
		tileMapShader.use();
	}

	private void initTileEBO() {
		tileMapEbo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, tileMapEbo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, elements, GL_DYNAMIC_DRAW);
	}

	public void deinit() {
		tileMapShader.deinit();

		glDeleteBuffers(tileMapEbo);
		glDeleteBuffers(tileMapVbo);
		glDeleteVertexArrays(tileMapVao);

		glDeleteBuffers(quadEbo);
		glDeleteBuffers(quadVbo);
		glDeleteVertexArrays(quadVao);
	}

	private void initTileVAO() {
		tileMapVao = glGenVertexArrays();
		glBindVertexArray(tileMapVao);
	}

	private void initTileVBO() {
		tileMapVbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, tileMapVbo);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW);
	}

	public void updateTileVBO(float[] data, int[] elements) {
		glBindBuffer(GL_ARRAY_BUFFER, tileMapVbo);
		glBufferData(GL_ARRAY_BUFFER, data, GL_DYNAMIC_DRAW);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, tileMapEbo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, elements, GL_DYNAMIC_DRAW);
	}

	public void loadUniformsToTileShader(Vector2f entityPosition, Vector2f cameraPosition) {
		tileMapShader.loadUniformsToShader(entityPosition, cameraPosition);
	}

	private void initTileShader() {
		tileMapShader.init();
	}

	public void clearScreen(Vector3f clearColor) {
		glClearColor(clearColor.x, clearColor.y, clearColor.z, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void loadUniformsToSpriteShader(Vector2f texCoords, Vector2f entityPosition, Vector2f cameraPosition) {
		spriteShader.loadUniformsToShader(texCoords, entityPosition, cameraPosition);
	}

	public void loadTintToShaders(Vector3f tint) {
		spriteShader.loadUniformTint(tint);
		tileMapShader.loadUniformsTint(tint);
	}
}
