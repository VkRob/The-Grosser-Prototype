package engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationFactory;

import engine.logic.SceneManager;
import engine.util.CustomConfigurationFactory;
import engine.window.WindowManager;
import engine.window.WindowParams;
import game.Grosser;

public class Engine {

	public static SceneManager sceneManager;
	public static WindowParams windowParams = new WindowParams("grosser", 800, 600);

	static {
		ConfigurationFactory.setConfigurationFactory(new CustomConfigurationFactory());
	}

	private static Logger LOG = LogManager.getLogger(Engine.class);

	public static void main(String[] args) {

		// Log that the program has started
		LOG.debug("EngineTest.main called with arguments: " + String.join(",", args));

		// Create SceneManager
		sceneManager = new SceneManager();

		// Register the Game
		Grosser.registerGame();

		// Create the Window
		WindowManager window = new WindowManager(windowParams, sceneManager);
		window.construct();
		window.loop();
		window.deconstruct();
	}

}
