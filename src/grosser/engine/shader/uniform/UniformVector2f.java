package grosser.engine.shader.uniform;

import static org.lwjgl.opengl.GL20.glUniform2f;

import grosser.engine.math.Vector2f;

public class UniformVector2f extends Uniform {

	public UniformVector2f(String name, int shaderProgramID) {
		super(name, shaderProgramID);
	}

	public void sendValueToShader(Vector2f vec) {
		glUniform2f(getMyID(), vec.x, vec.y);
	}
}
