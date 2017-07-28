package grosser.engine.shader.uniform;

import static org.lwjgl.opengl.GL20.glUniform3f;

import grosser.engine.math.Vector3f;

public class ShaderUniformVector3f extends ShaderUniform {

	public ShaderUniformVector3f(String name, int shaderProgramID) {
		super(name, shaderProgramID, false);
	}

	public void sendValueToShader(Vector3f vec) {
		glUniform3f(getMyID(), vec.x, vec.y, vec.z);
	}
}
