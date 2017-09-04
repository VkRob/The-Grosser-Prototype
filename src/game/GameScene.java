package game;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.entity.EntityBackground;
import engine.entity.EntityMachine;
import engine.entity.EntitySprite;
import engine.entity.EntityTilemap;
import engine.logic.Scene;
import engine.logic.SceneManager;
import engine.script.Script;
import game.machine.Machine;

public class GameScene extends Scene {

	EditorScene editor;
	Script cameraScript;
	Script gameScript;

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
		this.addEntity(new EntityMachine(new Vector2f(0, 0), (Machine) Machine.REGISTRY.getObjByName("Basic")));

		cameraScript = new Script("Camera.lua");
		gameScript = new Script("Game.lua");

		editor.setWorld(tilemap);
		editor.init();

		gameScript.execute("Init", this);
	}

	@Override
	public void update() {
		cameraScript.refresh();
		cameraScript.execute("MoveCamera");

		// gameScript.refresh();
		gameScript.execute("Loop", this);

		super.update();

		// go to the editor scene
		// editor.setCamera(this.getCamera());
		// super.mgr.setCurrentScene(editor);
	}

	public EditorScene getEditor() {
		return editor;
	}

}
