package grosser.prototype.scenes;

/**
 * Contains basic container methods for grosser.prototype.scenes, also the SceneManager handle for
 * each Scene
 * 
 * @author Robert
 *
 */
public abstract class Scene {

	public abstract void update();

	public abstract void render();

	public abstract void handleInput(char key);

}
