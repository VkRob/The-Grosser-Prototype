package grosser.engine.tile;

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

	private int vao;
	private int vbo;
	private float[] vertices;
	private int[] elements;
	private float[] pixels;
	private int ebo;
	private int shaderProgramID;
	private ShaderProgram shader;

	public ShaderProgram getShader() {
		return shader;
	}

	private ShaderTexture texture;

	private ArrayList<Vector3f> lightColor = new ArrayList<Vector3f>();
	private ArrayList<Vector3f> lightPosition = new ArrayList<Vector3f>();
	private Matrix4f modelMatrix;

	private ShaderUniformVector2f uniformTilePosition;
	private ShaderUniformVector3f uniformCameraPosition;
	private ShaderUniformMatrix4f uniformModelMatrix;
	private ShaderUniformMatrix4f uniformProjectionMatrix;
	private ShaderUniformVector3fv uniformLightColor;
	private ShaderUniformVector3fv uniformLightPosition;
	private ShaderUniformInt uniformIsSecondRenderPass;
	private ShaderUniformVector2f uniformOcclusion_resolution;
	private ShaderUniformVector2fv uniformShadows;
	private ShaderUniformFloat uniformRecievesShadow;
	private ShaderUniformFloat uniformTime;

	private Render render;
	private Vector2f tilePosition;
	private float time = 0;

	private int ID;

	public Tile(Vector2f tilePosition, int ID, Render render) {
		this.tilePosition = tilePosition;
		this.render = render;
		this.setID(ID);

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

		lightColor.add(new Vector3f(1.0f, 0.0f, 0.0f));
		lightPosition.add(new Vector3f(0.0f, 0.0f, 1.0f));
		//
		lightColor.add(new Vector3f(0f, 1f, 0f));
		lightPosition.add(new Vector3f(0.0f, 0.0f, 1.0f));

		// lightColor.add(new Vector3f(0f, 1f, 0f));
		// lightPosition.add(new Vector3f(0.0f, 1.0f, 2.0f));

		// lightColor.add(new Vector3f(0.0f, 1.0f, 0.0f));
		// lightPosition.add(new Vector3f(-2.0f, -5.0f, 1f));

		// lightColor.add(new Vector3f(1.0f, 1.0f, 1.0f));
		// lightPosition.add(new Vector3f(0.0f, 0.0f, 5f));

		shader = new ShaderProgram("outColor");
		shaderProgramID = shader.getShaderProgramID();

		shader.addShaderAttrib(new ShaderAttribVector2f("position", shaderProgramID));
		shader.addShaderAttrib(new ShaderAttribVector2f("texcoord", shaderProgramID));
		shader.pushAttribPointers();

		uniformCameraPosition = new ShaderUniformVector3f("cameraPosition", shaderProgramID);
		uniformTilePosition = new ShaderUniformVector2f("objectPosition", shaderProgramID);
		uniformModelMatrix = new ShaderUniformMatrix4f("model", shaderProgramID);
		uniformProjectionMatrix = new ShaderUniformMatrix4f("proj", shaderProgramID);
		uniformLightColor = new ShaderUniformVector3fv("lightColor", shaderProgramID);
		uniformLightPosition = new ShaderUniformVector3fv("lightPosition", shaderProgramID);
		uniformIsSecondRenderPass = new ShaderUniformInt("isSecondRenderPass", shaderProgramID);
		uniformOcclusion_resolution = new ShaderUniformVector2f("occlusion_resolution", shaderProgramID);
		uniformShadows = new ShaderUniformVector2fv("shadows", shaderProgramID);
		uniformRecievesShadow = new ShaderUniformFloat("recievesShadow", shaderProgramID);
		uniformTime = new ShaderUniformFloat("time", shaderProgramID);

		shader.addShaderUniform(uniformCameraPosition);
		shader.addShaderUniform(uniformTilePosition);
		shader.addShaderUniform(uniformModelMatrix);
		shader.addShaderUniform(uniformProjectionMatrix);
		shader.addShaderUniform(uniformLightColor);
		shader.addShaderUniform(uniformLightPosition);
		shader.addShaderUniform(uniformIsSecondRenderPass);
		shader.addShaderUniform(uniformOcclusion_resolution);
		shader.addShaderUniform(uniformShadows);
		shader.addShaderUniform(uniformRecievesShadow);
		shader.addShaderUniform(uniformTime);

		/* NEAREST = Pixelated, LINEAR = Blurred */
		texture = new ShaderTexture("texture", pixels, 16, 16, GL_NEAREST);
		shader.addShaderTexture(texture);

	}

	public void renderNormally(ArrayList<Tile> in) {

		time += 1f / 60f;

		shader.use();

		glBindVertexArray(vao);
		glActiveTexture(texture.getTextureBankID());
		glBindTexture(GL_TEXTURE_2D, texture.getMyID());

		uniformModelMatrix.sendValueToShader(modelMatrix);
		uniformProjectionMatrix.sendValueToShader(render.getProjectionMatrix());
		uniformCameraPosition.sendValueToShader(render.getCameraPosition());
		uniformTilePosition.sendValueToShader(tilePosition);

		uniformLightColor.sendValueToShader(lightColor);
		uniformLightPosition.sendValueToShader(lightPosition);

		uniformIsSecondRenderPass.sendValueToShader(0);
		uniformTime.sendValueToShader(time);

		ArrayList<Vector2f> verts = new ArrayList<Vector2f>();
		for (Tile t : in) {
			verts.add(t.getTilePosition());
		}

		Comparator<Vector2f> distanceSorter = new DistanceSorter(
				new Vector2f(render.getCameraPosition().x, -render.getCameraPosition().y));
		Collections.sort(verts, distanceSorter);

		ArrayList<Vector2f> send = new ArrayList<Vector2f>();
		for (int i = 0; i < 32; i++) {
			Vector2f g = verts.get(i);
			send.add(g.add(new Vector2f(-0.5f, 0.5f)));
			send.add(g.add(new Vector2f(0.5f, 0.5f)));
			send.add(g.add(new Vector2f(0.5f, -0.5f)));
			send.add(g.add(new Vector2f(-0.5f, -0.5f)));
		}

		uniformShadows.sendValueToShader(send);

		if (ID == 0)
			uniformRecievesShadow.sendValueToShader(1);
		else
			uniformRecievesShadow.sendValueToShader(0);
	}

	public ArrayList<Vector3f> getLightPosition() {
		return lightPosition;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public ShaderTexture getTexture() {
		return texture;
	}

	public void setTexture(ShaderTexture texture) {
		this.texture = texture;
	}

	public Vector2f getTilePosition() {
		return tilePosition;
	}

	public void setTilePosition(Vector2f tilePosition) {
		this.tilePosition = tilePosition;
	}

}
