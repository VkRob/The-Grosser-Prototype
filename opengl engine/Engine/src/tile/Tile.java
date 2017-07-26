package tile;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.awt.image.BufferedImage;

import core.ImageLoader;
import core.Render;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import shader.ShaderProgram;
import shader.ShaderTexture;
import shader.attrib.ShaderAttribVector2f;
import shader.uniform.ShaderUniformMatrix4f;
import shader.uniform.ShaderUniformVector2f;
import shader.uniform.ShaderUniformVector3f;

public class Tile {

	public static float[] tileset[] = {

			ImageLoader.getTexturePixels("/Tiles/grass.png"),

			ImageLoader.getTexturePixels("/Tiles/stone.png"),

			ImageLoader.getTexturePixels("/Tiles/wall.png"),

	};

	private int vao;
	private int vbo;
	private float[] vertices;
	private int[] elements;
	private float[] pixels;
	private int ebo;
	private int shaderProgramID;
	private ShaderProgram shader;

	private Matrix4f modelMatrix;

	private ShaderUniformVector2f uniformTilePosition;
	private ShaderUniformVector3f uniformCameraPosition;
	private ShaderUniformMatrix4f uniformModelMatrix;
	private ShaderUniformMatrix4f uniformProjectionMatrix;

	private Render render;
	private Vector2f tilePosition;
	private int ID;

	public Tile(Vector2f tilePosition, int ID, Render render) {
		this.tilePosition = tilePosition;
		this.render = render;
		this.ID = ID;

		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		vbo = glGenBuffers();

		vertices = new float[] {
				// Positions (2f), Texture Coordinates (2f)
				-0.5f, 0.5f, 0.0f, 0.0f, // Top-left
				0.5f, 0.5f, 1.0f, 0.0f, // Top-right
				0.5f, -0.5f, 1.0f, 1.0f, // Bottom-right
				-0.5f, -0.5f, 0.0f, 1.0f // Bottom-left
		};

		elements = new int[] {
				// Triangles to be rendered
				0, 1, 2, // Triangle 1
				2, 3, 0 // Triangle 2
		};

		pixels = Tile.tileset[ID];

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);

		ebo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, elements, GL_STATIC_DRAW);

		modelMatrix = Matrix4f.rotate(180f, new Vector3f(1.0f, 0.0f, 0.0f));

		shader = new ShaderProgram("outColor");
		shaderProgramID = shader.getShaderProgramID();

		shader.addShaderAttrib(new ShaderAttribVector2f("position", shaderProgramID));
		shader.addShaderAttrib(new ShaderAttribVector2f("texcoord", shaderProgramID));
		shader.pushAttribPointers();

		uniformCameraPosition = new ShaderUniformVector3f("cameraPosition", shaderProgramID);
		uniformTilePosition = new ShaderUniformVector2f("objectPosition", shaderProgramID);
		uniformModelMatrix = new ShaderUniformMatrix4f("model", shaderProgramID);
		uniformProjectionMatrix = new ShaderUniformMatrix4f("proj", shaderProgramID);

		shader.addShaderUniform(uniformCameraPosition);
		shader.addShaderUniform(uniformTilePosition);
		shader.addShaderUniform(uniformModelMatrix);
		shader.addShaderUniform(uniformProjectionMatrix);

		uniformModelMatrix.sendValueToShader(modelMatrix);
		uniformProjectionMatrix.sendValueToShader(render.getProjectionMatrix());
		uniformCameraPosition.sendValueToShader(render.getCameraPosition());
		uniformTilePosition.sendValueToShader(tilePosition);

		/* NEAREST = Pixelated, LINEAR = Blurred */
		shader.addShaderTexture(new ShaderTexture("texture", pixels, 16, 16, GL_NEAREST));

	}

	public void render() {
		glBindVertexArray(vao);

		uniformCameraPosition.sendValueToShader(render.getCameraPosition());
		uniformTilePosition.sendValueToShader(tilePosition);
	}
}
