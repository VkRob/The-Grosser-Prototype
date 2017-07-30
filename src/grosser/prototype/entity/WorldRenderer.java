package grosser.prototype.entity;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import org.lwjgl.*;
import static org.lwjgl.stb.STBEasyFont.*;

import java.nio.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
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
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;

import java.util.ArrayList;

import grosser.engine.core.ImageLoader;
import grosser.engine.core.Render;
import grosser.engine.math.Matrix4f;
import grosser.engine.math.Vector2f;
import grosser.engine.math.Vector3f;
import grosser.engine.shader.EntityShader;

public class WorldRenderer {

	private TileManager map;
	private EntityManager entityManager;
	private Camera camera;

	private ArrayList<Light> lights = new ArrayList<Light>();
	private Matrix4f modelMatrix;

	private int vao;
	private int vbo;
	private float[] vertices;
	private int[] elements;
	private int ebo;

	private Render render;
	private ArrayList<EntityShader> tileShaders;
	private ArrayList<EntityShader> entityShaders;

	public static float size = 1f;

	public WorldRenderer(Camera camera, TileManager map, EntityManager entityManager, Render render) {

		this.entityManager = entityManager;
		this.map = map;
		this.camera = camera;
		this.setRender(render);

		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		vbo = glGenBuffers();

		vertices = new float[] {
				// Positions (2f), Texture Coordinates (2f)
				-0.5f * size, 0.5f * size, 0.0f, 0.0f, // Top-left
				0.5f * size, 0.5f * size, 1.0f, 0.0f, // Top-right
				0.5f * size, -0.5f * size, 1.0f, 1.0f, // Bottom-right
				-0.5f * size, -0.5f * size, 0.0f, 1.0f // Bottom-left
		};

		elements = new int[] {
				// Triangles to be rendered
				0, 1, 2, // Triangle 1
				2, 3, 0 // Triangle 2
		};

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);

		ebo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, elements, GL_STATIC_DRAW);

		setModelMatrix(Matrix4f.rotate(180f, new Vector3f(1.0f, 0.0f, 0.0f)));

		// lights.add(new Light(new Vector3f(0.0f, 0.0f, 1.0f *
		// Render.getCameraZoom()), color));
		// lights.add(new Light(new Vector3f(4.0f, 0.0f, 1.0f *
		// Render.getCameraZoom()), color));

		tileShaders = new ArrayList<EntityShader>();
		entityShaders = new ArrayList<EntityShader>();

		// Tiles
		for (int tileID = 0; tileID < Tile.NUM_OF_TILES; tileID++) {
			tileShaders.add(new EntityShader(this, Tile.tileset[tileID]));
		}
		// Entities
		for (int eID = 0; eID < Entity.NUM_OF_ENTITIES; eID++) {
			entityShaders.add(new EntityShader(this, Entity.textures[eID],
					(eID == Entity.ID_PLAYER || eID == Entity.ID_PLAYER_ATTACK)));
		}

	}

	public void render() {
		// Sort the tiles by the ones that cast shadows and the ones that do not
		ArrayList<Tile> shadows = new ArrayList<Tile>();

		for (Tile tile : map.getTiles()) {
			if (Tile.tilesCastShadow[tile.getID()]) {
				shadows.add(tile);
			}
		}

		// glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);

		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);

		for (int i = 0; i < tileShaders.size(); i++) {
			ArrayList<Vector2f> tilePos = new ArrayList<Vector2f>();
			for (Tile t : map.getTiles()) {
				if (t.getID() == i)
					tilePos.add(t.getTilePosition().scale(Tile.SIZE));
			}
			tileShaders.get(i).prepareShader(vao, shadows, tilePos);
			glDrawElementsInstanced(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0, tilePos.size());
		}

		for (int i = 0; i < entityShaders.size(); i++) {
			ArrayList<Vector2f> ePos = new ArrayList<Vector2f>();
			Player player = null;
			for (Entity e : entityManager.getEntities()) {
				if (e.getTypeID() == i)
					ePos.add(e.getPosition().scale(Tile.SIZE));
				if (e.getTypeID() == Entity.ID_PLAYER) {
					player = (Player) e;
				}
				if (e.getTypeID() == Entity.ID_PLAYER_ATTACK) {
					player = (Player) e;
				}
			}
			if (player != null && player.getTypeID() == i) {
				entityShaders.get(i).prepareShader(vao, shadows, ePos, player.getAnimation());
			} else {
				entityShaders.get(i).prepareShader(vao, shadows, ePos);
			}
			glDrawElementsInstanced(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0, ePos.size());
		}

	}

	public ArrayList<Light> getLights() {
		return lights;
	}

	public Matrix4f getModelMatrix() {
		return modelMatrix;
	}

	public void setModelMatrix(Matrix4f modelMatrix) {
		this.modelMatrix = modelMatrix;
	}

	public Render getRender() {
		return render;
	}

	public void setRender(Render render) {
		this.render = render;
	}

	public ArrayList<Vector3f> getLightColors() {
		ArrayList<Vector3f> out = new ArrayList<Vector3f>();
		for (Light l : lights) {
			out.add(l.getColor());
		}
		return out;
	}

	public ArrayList<Vector3f> getLightPositions() {
		ArrayList<Vector3f> out = new ArrayList<Vector3f>();
		for (Light l : lights) {
			out.add(l.getPosition());
		}
		return out;
	}

}
