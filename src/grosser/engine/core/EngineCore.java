package grosser.engine.core;

import grosser.prototype.input.Input;
import grosser.prototype.scenes.SceneManager;

public class EngineCore {

	private SceneManager mgr;
	private Window window;

	public EngineCore(String name, int width, int height) {
		window = new Window(this, name, width, height);
	}

	public void init(Render render) {
		mgr = new SceneManager(render);
		System.out.println("engine core created");
		Input.init();
	}

	public void update() {
		mgr.getCurrentScene().update();
	}

	public void render() {
		mgr.getCurrentScene().render();
	}

}
