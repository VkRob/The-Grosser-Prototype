package scenes;

import java.util.ArrayList;

import render.GamePanel;

/**
 * Shouldn't have to mess around with this class either, it manages which scene
 * is running (menu, game, or whatever we decide to add later)
 * 
 * @author Robert
 *
 */
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
