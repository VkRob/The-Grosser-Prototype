package engine.render.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import engine.util.Log;

public class Shader {

	private static Object shader = new Object();

	/**
	 * Loads a .glsl file into a String for easy passing into OpenGL bindings
	 * 
	 * @param path
	 *            to the file
	 * @return The file as a String, lines seperated by "\n"
	 */
	public static String loadFile(String path) {
		String out = "";
		try {
			InputStream in = shader.getClass().getResourceAsStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String s;
			while ((s = reader.readLine()) != null) {
				out = out + s + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.substring(0, out.length() - 2);
		return out;
	}

	private int vertexShader;
	private int fragmentShader;
	private int shaderProgram;
	private int texture;

	private ArrayList<ShaderAttrib> attribs;

	public Shader(String vertexSource, String fragmentSource) {

		attribs = new ArrayList<ShaderAttrib>();

		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, vertexSource);
		glCompileShader(vertexShader);
		int status = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
		if (status == GL_FALSE) {
			String err = glGetShaderInfoLog(vertexShader);
			Log.error("Vertex Shader failed: " + err);
		}

		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, fragmentSource);
		glCompileShader(fragmentShader);
		status = glGetShaderi(fragmentShader, GL_COMPILE_STATUS);
		if (status == GL_FALSE) {
			String err = glGetShaderInfoLog(fragmentShader);
			Log.error("Fragment Shader failed: " + err);
		}

		shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glBindFragDataLocation(shaderProgram, 0, "outColor");
		glLinkProgram(shaderProgram);

		// Now that these are linked in a program, it is safe to delete them
		glDeleteShader(fragmentShader);
		glDeleteShader(vertexShader);
	}

	public void deinit() {
		glDeleteTextures(texture);
		glDeleteProgram(shaderProgram);
	}

	public void loadTexture(Texture textureObject) {
		texture = glGenTextures();
		// Only needs to be binded when we apply an operation to this texture
		glBindTexture(GL_TEXTURE_2D, texture);
		// glGenerateMipmap(GL_TEXTURE_2D);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, textureObject.getWidth(), textureObject.getHeight(), 0, GL_RGB, GL_FLOAT,
				textureObject.getPixels());
	}

	public void createUniform(ShaderUniform uniform) {
		uniform.setId(glGetUniformLocation(shaderProgram, uniform.getName()));
	}

	public void loadUniformVector3f(ShaderUniform uniform, Vector3f vec) {
		glUniform3f(uniform.getId(), vec.x, vec.y, vec.z);
	}

	public void loadUniformVector2f(ShaderUniform uniform, Vector2f vec) {
		glUniform2f(uniform.getId(), vec.x, vec.y);
	}

	public void loadUniformFloat(ShaderUniform uniform, float f) {
		glUniform1f(uniform.getId(), f);
	}

	public void loadUniformMatrix4f(ShaderUniform uniform, Matrix4f mat) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		glUniformMatrix4fv(uniform.getId(), false, mat.get(buffer));
	}

	public void loadUniformVector4f(ShaderUniform uniform, Vector4f vec) {
		glUniform4f(uniform.getId(), vec.x, vec.y, vec.z, vec.w);
	}

	public void loadUniformBool(ShaderUniform uniform, boolean b) {
		if (b)
			glUniform1i(uniform.getId(), 1);
		else
			glUniform1i(uniform.getId(), 0);
	}

	public void addAttrib(ShaderAttrib shaderAttrib) {
		shaderAttrib.setId(glGetAttribLocation(shaderProgram, shaderAttrib.getName()));
		attribs.add(shaderAttrib);
	}

	public void pushAttribs() {
		int stride = 0;
		for (ShaderAttrib shaderAttrib : attribs) {
			stride += shaderAttrib.getNumOfFloats() * sizeof(float.class);
		}

		this.use();
		int offset = 0;
		for (ShaderAttrib shaderAttrib : attribs) {
			glEnableVertexAttribArray(shaderAttrib.getId());
			glVertexAttribPointer(shaderAttrib.getId(), shaderAttrib.getNumOfFloats(), GL_FLOAT, false, stride, offset);

			offset += shaderAttrib.getNumOfFloats() * sizeof(float.class);
		}
	}

	public void use() {
		glUseProgram(shaderProgram);
		glBindTexture(GL_TEXTURE_2D, texture);
	}

	public void stop() {
		glUseProgram(0);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	private int sizeof(Class<Float> class1) {
		return 4;
	}

}
