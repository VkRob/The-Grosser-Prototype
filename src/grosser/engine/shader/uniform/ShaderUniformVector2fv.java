package grosser.engine.shader.uniform;

import static org.lwjgl.opengl.GL20.glUniform2fv;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import grosser.engine.math.Vector2f;

public class ShaderUniformVector2fv extends ShaderUniform {

	public ShaderUniformVector2fv(String name, int shaderProgramID) {
		super(name, shaderProgramID, false);
	}

	public void sendValueToShader(ArrayList<Vector2f> list) {
		// System.out.println(list.size());
		FloatBuffer buffer = BufferUtils.createFloatBuffer(2 * list.size());

		int ctr = 0;
		for (Vector2f v : list) {
			buffer.put(v.x).put(v.y);
			ctr += 2;
		}

		buffer.flip();
		glUniform2fv(getMyID(), buffer);

	}
}
