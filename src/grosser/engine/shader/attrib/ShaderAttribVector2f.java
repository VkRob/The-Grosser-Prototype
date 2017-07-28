package grosser.engine.shader.attrib;

import grosser.engine.core.Render;

public class ShaderAttribVector2f extends ShaderAttrib {

	public ShaderAttribVector2f(String name, int shaderProgramID) {
		super(name, shaderProgramID, 2 * Render.sizeOf_GL_FLOAT, 2); // 2 Floats in
																	// a
																	// Vector2f
	}

}
