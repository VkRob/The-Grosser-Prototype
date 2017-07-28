package grosser.engine.shader.uniform;

import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import grosser.engine.math.Matrix4f;

public class ShaderUniformMatrix4f extends ShaderUniform {

	public ShaderUniformMatrix4f(String name, int shaderProgramID) {
		super(name, shaderProgramID, false);
	}

	public void sendValueToShader(Matrix4f mat) {
		glUniformMatrix4fv(getMyID(), false, mat.toBuffer());
	}
}
