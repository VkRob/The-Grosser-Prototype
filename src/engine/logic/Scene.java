package engine.logic;

import java.util.ArrayList;

import org.joml.Vector2f;

import engine.entity.Camera;
import engine.entity.Entity;
import engine.window.WindowManager;

public abstract class Scene {

	private ArrayList<Object> scriptVars;

	private ArrayList<Entity> entities;
	private ArrayList<Entity> garbageEntities;
	protected SceneManager mgr;
	private Camera camera;

	public Scene(SceneManager mgr) {
		this.mgr = mgr;
		entities = new ArrayList<Entity>();
		garbageEntities = new ArrayList<Entity>();
		scriptVars = new ArrayList<Object>();

		setCamera(new Camera(new Vector2f(0.0f, 0.0f), WindowManager.getParams().getWidth(),
				WindowManager.getParams().getHeight()));
	}

	public abstract void init();

	public void addEntity(Entity e) {
		e.setParentScene(this);
		entities.add(e);
	}

	public void removeEntity(Entity e) {
		garbageEntities.remove(e);
	}

	public void update() {
		for (Entity e : entities) {
			e.update();
		}
		for (Entity e : garbageEntities) {
			entities.remove(e);
		}
		garbageEntities.clear();

		camera.update();
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public ArrayList<Object> getScriptVars() {
		return scriptVars;
	}

	public void setScriptVars(ArrayList<Object> scriptVars) {
		this.scriptVars = scriptVars;
	}

	public SceneManager getMgr() {
		return mgr;
	}
}
