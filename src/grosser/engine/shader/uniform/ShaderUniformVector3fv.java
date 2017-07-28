package grosser.engine.shader.uniform;

import static org.lwjgl.opengl.GL20.glUniform3fv;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import grosser.engine.math.Vector3f;

public class ShaderUniformVector3fv extends ShaderUniform {

	public ShaderUniformVector3fv(String name, int shaderProgramID) {
		super(name, shaderProgramID, false);
	}

	public void sendValueToShader(ArrayList<Vector3f> vecs) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3 * vecs.size());
		for(Vector3f v : vecs){
			buffer.put(v.x).put(v.y).put(v.z);
		}
		buffer.flip();
		glUniform3fv(getMyID(), buffer);
		//System.out.println(buffer.get(0));
	}
}
