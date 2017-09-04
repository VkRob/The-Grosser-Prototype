package engine.render.shader.modules;

import engine.render.shader.ShaderModule;

public class MachineShader extends ShaderModule {

	private static final String VERTEX = "/shader/sprite/vertex.glsl";
	private static final String FRAGMENT = "/shader/sprite/fragment.glsl";
	private static final String TEXTURE = "/texture/machine.png";

	public MachineShader() {
		super(VERTEX, FRAGMENT, TEXTURE);
	}

}
