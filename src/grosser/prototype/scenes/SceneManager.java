
package grosser.prototype.scenes;

import java.util.ArrayList;

import grosser.engine.core.Render;


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
	
	SceneGame scene_game;

	public SceneManager(Render render) {
		scenes = new ArrayList<>();
		scene_game = new SceneGame(this, render);
		scenes.add(scene_game);

		currentScene = scene_game;
	}

	void setScene(Scene scene) {
		currentScene = scene;
	}

	public Scene getCurrentScene() {
		return currentScene;
	}
}