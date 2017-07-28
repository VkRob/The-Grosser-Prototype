package grosser.prototype.main;

import grosser.engine.core.Window;

/**
 * Creates the engine manager (EngineCore)
 * 
 * @author Robert
 *
 */
public class Main {

	public static final float WIDTH = 800.0f;
	public static final float HEIGHT = 600.0f;

	public static void main(String[] args) {
		new Window("Engine Test", (int) WIDTH, (int) HEIGHT);
	}

}