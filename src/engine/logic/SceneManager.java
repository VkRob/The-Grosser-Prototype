package engine.logic;

import engine.input.Input;
import engine.render.RenderCore;
import game.SceneGame;

public class SceneManager {

	private Scene currentScene;
	private RenderCore renderCore;

	public SceneManager() {

	}

	public void init() {
		renderCore = new RenderCore();
		currentScene = new SceneGame(this);
		Input.grabMouse();

		currentScene.init();
	}

	public void update() {
		currentScene.update();
		Input.update(); // Set pressed this frame and released this frame
						// to false
	}

	public void render() {
		renderCore.render(currentScene);
	}

	public void deinit() {
		renderCore.deinit();
	}

	public void handleInput(int key, int action) {
		Input.processKey(key, action);
	}

	public RenderCore getRenderCore() {
		return renderCore;
	}

	public Scene getCurrentScene() {
		return currentScene;
	}

	public void setCurrentScene(Scene currentScene) {
		this.currentScene = currentScene;
	}
}
