package grosser.engine.shader.uniform;

import static org.lwjgl.opengl.GL20.glUniform1i;

public class ShaderUniformInt extends ShaderUniform {

	public ShaderUniformInt(String name, int shaderProgramID) {
		super(name, shaderProgramID, false);
	}

	public void sendValueToShader(int i) {
		glUniform1i(getMyID(), i);
	}
}
