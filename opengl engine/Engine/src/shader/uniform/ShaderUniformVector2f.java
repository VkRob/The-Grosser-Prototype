package shader.uniform;

import static org.lwjgl.opengl.GL20.glUniform2f;

import math.Vector2f;

public class ShaderUniformVector2f extends ShaderUniform {

	public ShaderUniformVector2f(String name, int shaderProgramID) {
		super(name, shaderProgramID);
	}

	public void sendValueToShader(Vector2f vec) {
		glUniform2f(getMyID(), vec.x, vec.y);
	}
}
