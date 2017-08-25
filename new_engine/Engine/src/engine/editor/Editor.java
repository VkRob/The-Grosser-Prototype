package engine.editor;

import org.joml.Vector3f;

import engine.entity.EntityBackground;
import engine.entity.EntitySprite;
import engine.entity.EntityTilemap;
import engine.logic.Scene;
import engine.logic.SceneManager;
import engine.script.Script;

public class Editor extends Scene {

	private Script editorScript;

	private EntityTilemap world;
	private EntitySprite tileToPlace;

	private int cursorTileID = 0;

	public Editor(SceneManager mgr) {
		super(mgr);
	}

	private boolean b = false;

	@Override
	public void init() {
		tileToPlace = new EntitySprite();

		this.addEntity(new EntityBackground(new Vector3f(0, 0, 0)));
		this.addEntity(world); // world is added from the SceneGame

		this.addEntity(tileToPlace);

		editorScript = new Script("Editor.lua");
		editorScript.execute("Init", this);
		b = true;
		//System.out.println("dbg");
	}

	@Override
	public void update() {
		super.update();
		if (b) {
			editorScript.refresh();
			editorScript.execute("Run", this);
		}
	}

	public EntityTilemap getWorld() {
		return world;
	}

	public void setWorld(EntityTilemap world) {
		this.world = world;
	}

	public EntitySprite getTileToPlace() {
		return tileToPlace;
	}

	public void setTileToPlace(EntitySprite tileToPlace) {
		this.tileToPlace = tileToPlace;
	}

	public int getCursorTileID() {
		return cursorTileID;
	}

	public void setCursorTileID(int cursorTileID) {
		this.cursorTileID = cursorTileID;
	}
}
