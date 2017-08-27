package game;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.editor.Editor;
import engine.entity.Camera;
import engine.entity.EntityBackground;
import engine.entity.EntitySprite;
import engine.entity.EntityTilemap;
import engine.logic.Scene;
import engine.logic.SceneManager;
import engine.script.Script;

public class SceneGame extends Scene {

	Editor editor;
	Script cameraScript;

	Camera camera;
	EntityBackground background;
	EntityTilemap tilemap;
	EntitySprite sprite;

	public SceneGame(SceneManager mgr) {
		super(mgr);
		editor = new Editor(mgr);
	}

	public void init() {
		background = new EntityBackground(new Vector3f(0.2f, 0.2f, 0.2f));
		tilemap = new EntityTilemap();
		sprite = new EntitySprite();
		sprite.setTexCoords(new Vector2f(0, 0));

		this.addEntity(background);
		this.addEntity(tilemap);
		this.addEntity(sprite);

		cameraScript = new Script("Camera.lua");
		
		editor.setWorld(tilemap);
		editor.init();
	}

	@Override
	public void update() {
		cameraScript.refresh();
		cameraScript.execute("MoveCamera");

		super.update();
		
		//go to the editor scene
		editor.setCamera(this.getCamera());
		super.mgr.setCurrentScene(editor);
	}

}
