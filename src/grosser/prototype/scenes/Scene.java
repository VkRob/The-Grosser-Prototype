package grosser.prototype.scenes;

import java.awt.Graphics2D;

/**
 * Contains basic container methods for grosser.prototype.scenes, also the SceneManager handle for
 * each Scene
 * 
 * @author Robert
 *
 */
public abstract class Scene {

	protected SceneManager mgr;

	public Scene(SceneManager mgr) {
		this.mgr = mgr;
	}

	public abstract void update();

	public abstract void render(Graphics2D g);
}
