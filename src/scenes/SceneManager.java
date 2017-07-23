package scenes;

import java.util.ArrayList;

import render.GamePanel;

public class SceneManager {

	private ArrayList<Scene> scenes;
	private Scene currentScene;

	public SceneMainMenu scene_main_menu;
	public SceneGame scene_game;

	public SceneManager(GamePanel gamePanel) {
		scenes = new ArrayList<Scene>();
		scenes.add(scene_main_menu = new SceneMainMenu(this, gamePanel));
		scenes.add(scene_game = new SceneGame(this, gamePanel));

		currentScene = scene_main_menu;
	}

	public void setScene(Scene scene) {
		currentScene = scene;
	}

	public Scene getCurrentScene() {
		return currentScene;
	}
}
