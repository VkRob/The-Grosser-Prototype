package grosser.prototype.map;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
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
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import grosser.engine.core.DistanceSorter;
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

public class TileRenderer {

	private Map map;

	private ShaderProgram tileShader;
	private ShaderTexture texture;

	private ArrayList<Vector3f> lightColor = new ArrayList<Vector3f>();
	private ArrayList<Vector3f> lightPosition = new ArrayList<Vector3f>();
	private Matrix4f modelMatrix;

	private ShaderUniformVector2fv uniformTilePositions;
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

	private int vao;
	private int vbo;
	private float[] vertices;
	private int[] elements;
	private float[] pixels;
	private int ebo;
	private int shaderProgramID;

	private Render render;
	private float time = 0;

	public TileRenderer(Map map, Render render) {
		this.map = map;
		this.render = render;
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

		pixels = Tile.tileset[Tile.TILE_WALL];

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
		lightPosition.add(new Vector3f(2.0f, 0.0f, 1.0f));

		// lightColor.add(new Vector3f(0f, 1f, 0f));
		// lightPosition.add(new Vector3f(0.0f, 1.0f, 2.0f));

		// lightColor.add(new Vector3f(0.0f, 1.0f, 0.0f));
		// lightPosition.add(new Vector3f(-2.0f, -5.0f, 1f));

		// lightColor.add(new Vector3f(1.0f, 1.0f, 1.0f));
		// lightPosition.add(new Vector3f(0.0f, 0.0f, 5f));

		tileShader = new ShaderProgram("outColor");
		shaderProgramID = tileShader.getShaderProgramID();

		tileShader.addShaderAttrib(new ShaderAttribVector2f("position", shaderProgramID));
		tileShader.addShaderAttrib(new ShaderAttribVector2f("texcoord", shaderProgramID));
		// shader.addShaderAttrib(new ShaderAttribVector2f("objectPosition",
		// shaderProgramID));
		tileShader.pushAttribPointers();

		uniformCameraPosition = new ShaderUniformVector3f("cameraPosition", shaderProgramID);
		// uniformTilePosition = new ShaderUniformVector2f("objectPosition",
		// shaderProgramID);
		uniformModelMatrix = new ShaderUniformMatrix4f("model", shaderProgramID);
		uniformProjectionMatrix = new ShaderUniformMatrix4f("proj", shaderProgramID);
		uniformLightColor = new ShaderUniformVector3fv("lightColor", shaderProgramID);
		uniformLightPosition = new ShaderUniformVector3fv("lightPosition", shaderProgramID);
		uniformIsSecondRenderPass = new ShaderUniformInt("isSecondRenderPass", shaderProgramID);
		uniformOcclusion_resolution = new ShaderUniformVector2f("occlusion_resolution", shaderProgramID);
		uniformShadows = new ShaderUniformVector2fv("shadows", shaderProgramID);
		uniformTilePositions = new ShaderUniformVector2fv("tilePositions", shaderProgramID);
		uniformRecievesShadow = new ShaderUniformFloat("recievesShadow", shaderProgramID);
		uniformTime = new ShaderUniformFloat("time", shaderProgramID);

		tileShader.addShaderUniform(uniformCameraPosition);
		// shader.addShaderUniform(uniformTilePosition);
		tileShader.addShaderUniform(uniformModelMatrix);
		tileShader.addShaderUniform(uniformProjectionMatrix);
		tileShader.addShaderUniform(uniformLightColor);
		tileShader.addShaderUniform(uniformLightPosition);
		tileShader.addShaderUniform(uniformIsSecondRenderPass);
		tileShader.addShaderUniform(uniformOcclusion_resolution);
		tileShader.addShaderUniform(uniformShadows);
		tileShader.addShaderUniform(uniformRecievesShadow);
		tileShader.addShaderUniform(uniformTime);
		tileShader.addShaderUniform(uniformTilePositions);

		/* NEAREST = Pixelated, LINEAR = Blurred */
		texture = new ShaderTexture("texture", pixels, 16, 16, GL_NEAREST);
		tileShader.addShaderTexture(texture);
	}

	public void render() {
		// Sort the tiles by the ones that cast shadows and the ones that do not
		ArrayList<Tile> shadows = new ArrayList<Tile>();

		for (Tile tile : map.getTiles()) {
			if (tile.getID() == 2) {
				shadows.add(tile);
			}
		}

		// glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);

		glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);

//		ArrayList<Tile> visibleTiles = new ArrayList<Tile>();
//		for (Tile tile : map.getTiles()) {
//			if (this.prepareTile(tile))
//				visibleTiles.add(tile);
//		}
		prepareShader(map.getTiles(), shadows);

		glDrawElementsInstanced(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0, 400);

	}

	private void prepareShader(ArrayList<Tile> tiles, ArrayList<Tile> shadows) {
		time += 1f / 60f;
		tileShader.use();

		glBindVertexArray(vao);
		glActiveTexture(texture.getTextureBankID());
		glBindTexture(GL_TEXTURE_2D, texture.getMyID());

		uniformModelMatrix.sendValueToShader(modelMatrix);
		uniformProjectionMatrix.sendValueToShader(render.getProjectionMatrix());
		uniformCameraPosition.sendValueToShader(render.getCameraPosition());

		uniformLightColor.sendValueToShader(lightColor);
		uniformLightPosition.sendValueToShader(lightPosition);

		uniformIsSecondRenderPass.sendValueToShader(0);
		uniformRecievesShadow.sendValueToShader(1);
		uniformTime.sendValueToShader(time);

		ArrayList<Vector2f> verts = new ArrayList<Vector2f>();
		for (Tile t : shadows) {
			verts.add(t.getTilePosition());
		}

		Comparator<Vector2f> distanceSorter = new DistanceSorter(
				new Vector2f(render.getCameraPosition().x, -render.getCameraPosition().y));
		Collections.sort(verts, distanceSorter);

		ArrayList<Vector2f> send = new ArrayList<Vector2f>();
		for (int i = 0; i < 16; i++) {
			Vector2f g = verts.get(i);
			send.add(g.add(new Vector2f(-0.5f, 0.5f)));
			send.add(g.add(new Vector2f(0.5f, 0.5f)));
			send.add(g.add(new Vector2f(0.5f, -0.5f)));
			send.add(g.add(new Vector2f(-0.5f, -0.5f)));
		}

		uniformShadows.sendValueToShader(send);
		ArrayList<Vector2f> tilePos = new ArrayList<Vector2f>();
		for (Tile t : tiles) {
			tilePos.add(t.getTilePosition());
		}
		
		uniformTilePositions.sendValueToShader(tilePos);
	}

	private boolean prepareTile(Tile tile) {

		if (tile.getTilePosition().subtract(render.getCameraPosition().xy()).length() > 14.5f) {
			// return false;
		}

		// if (tile.getID() == 0)
		
		// else
		// uniformRecievesShadow.sendValueToShader(0);

		return true;
	}
}
