package grosser.engine.core;

import grosser.prototype.scenes.SceneManager;

public class EngineCore {

	private SceneManager mgr;
	private Render render;

	public EngineCore(String name, int width, int height) {
		
	}

	public void init(Render render) {
		mgr = new SceneManager(render);

		this.render = render;
	}

	public void update() {
		mgr.getCurrentScene().update();
	}

	public void render() {
		render.render();
		//mgr.getCurrentScene().render();
	}

}
