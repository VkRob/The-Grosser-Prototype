package game;

import org.joml.Vector3f;

import engine.entity.EntityBackground;
import engine.entity.EntityTilemap;
import engine.logic.Scene;
import engine.logic.SceneManager;
import engine.script.Script;

public class EditorScene extends Scene {

	private Script editorScript;

	private EntityTilemap world;

	private int cursorTileID = 0;

	public EditorScene(SceneManager mgr) {
		super(mgr);
	}

	@Override
	public void init() {
		this.addEntity(new EntityBackground(new Vector3f(0, 0, 0)));
		this.addEntity(world); // world is added from the SceneGame

		editorScript = new Script("Editor.lua");
		editorScript.execute("Init", this);
	}

	@Override
	public void update() {
		super.update();
		editorScript.refresh();
		editorScript.execute("Run", this);
	}

	public EntityTilemap getWorld() {
		return world;
	}

	public void setWorld(EntityTilemap world) {
		this.world = world;
	}

	public int getCursorTileID() {
		return cursorTileID;
	}

	public void setCursorTileID(int cursorTileID) {
		this.cursorTileID = cursorTileID;
	}
}
