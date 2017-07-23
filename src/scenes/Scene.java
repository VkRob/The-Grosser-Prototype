package scenes;

import java.awt.Graphics2D;

public abstract class Scene {
	
	protected SceneManager mgr;
	
	public Scene(SceneManager mgr){
		this.mgr = mgr;
	}
	
	public abstract void update();
	public abstract void render(Graphics2D g);
}
