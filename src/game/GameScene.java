package game;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.entity.Camera;
import engine.entity.EntityBackground;
import engine.entity.EntitySprite;
import engine.entity.EntityTilemap;
import engine.logic.Scene;
import engine.logic.SceneManager;
import engine.script.Script;

public class GameScene extends Scene {

	EditorScene editor;
	Script cameraScript;

	EntityBackground background;
	EntityTilemap tilemap;
	EntitySprite sprite;

	public GameScene(SceneManager mgr) {
		super(mgr);
		editor = new EditorScene(mgr);
	}

	public void init() {
		background = new EntityBackground(new Vector3f(0.2f, 0.2f, 0.2f));
		tilemap = new EntityTilemap();

		this.addEntity(background);
		this.addEntity(tilemap);

		cameraScript = new Script("Camera.lua");

		editor.setWorld(tilemap);
		editor.init();
	}

	@Override
	public void update() {
		cameraScript.refresh();
		cameraScript.execute("MoveCamera");

		super.update();

		// go to the editor scene
		editor.setCamera(this.getCamera());
		super.mgr.setCurrentScene(editor);
	}

}
