package engine;

import static engine.util.Log.important;
import static engine.util.Log.setLogMode;
import static engine.util.Log.stringArrayToString;

import engine.logic.SceneManager;
import engine.util.LogMode;
import engine.window.WindowManager;
import engine.window.WindowParams;

public class Engine {

	public static SceneManager sceneManager;

	public static void main(String[] args) {
		// Setup debugging mode
		setLogMode(LogMode.RELEASE);

		// Log that the program has started
		important("EngineTest.main called with arguments: " + stringArrayToString(args, ","));

		// Create SceneManager
		sceneManager = new SceneManager();

		// Create the Window Params
		WindowParams windowParams = new WindowParams("grosser", 800, 600);

		// Create the Window
		WindowManager window = new WindowManager(windowParams, sceneManager);
		window.construct();
		window.loop();
		window.deconstruct();
	}

}
