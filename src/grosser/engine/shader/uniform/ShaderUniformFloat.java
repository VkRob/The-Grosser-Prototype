package grosser.engine.shader.uniform;

import static org.lwjgl.opengl.GL20.glUniform1f;

public class ShaderUniformFloat extends ShaderUniform {

	public ShaderUniformFloat(String name, int shaderProgramID) {
		super(name, shaderProgramID, false);
	}

	public void sendValueToShader(float f) {
		glUniform1f(getMyID(), f);
	}
}
