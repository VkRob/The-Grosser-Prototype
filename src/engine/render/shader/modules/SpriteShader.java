package engine.render.shader.modules;

import engine.render.shader.ShaderModule;

public class SpriteShader extends ShaderModule {

	private static final String VERTEX = "/shader/sprite/vertex.glsl";
	private static final String FRAGMENT = "/shader/sprite/fragment.glsl";
	private static final String TEXTURE = "/texture/test.png";

	public SpriteShader() {
		super(VERTEX, FRAGMENT, TEXTURE);
	}

}
