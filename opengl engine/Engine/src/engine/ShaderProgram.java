package engine;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;

public class ShaderProgram {
	private int shaderProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;

	public ShaderProgram() {
		// Setup source for the vertex and fragment shaders
		String vertexShaderSource[] = {

				"#version 150 core",

				// Inputs

				"in vec2 position;",

				"in vec2 texcoord;",

				// Outputs

				"out vec2 Texcoord;",

				// Uniforms

				"uniform mat4 model;",

				"uniform mat4 proj;",

				"uniform vec2 cameraPosition;",

				// Program

				"void main(){",

				"	Texcoord = texcoord;",

				"	gl_Position = proj * model * vec4(position-cameraPosition, 3.0, 1.0);",

				"}",

		};

		String fragmentShaderSource[] = {

				"#version 150 core",

				// Inputs

				"in vec2 Texcoord;",

				// Uniforms

				"uniform sampler2D tex1;",

				"uniform sampler2D tex2;",

				// Outputs

				"out vec4 outColor;",

				// Program

				"void main(){",

				"	vec4 col1 = texture(tex1, Texcoord);",

				"	vec4 col2 = texture(tex2, Texcoord);",

				"	outColor = mix(col1, col2, 0.5);",

				"}",

		};

		String vertexShaderCode = vertexShaderSource[0];
		for (int i = 1; i < vertexShaderSource.length; ++i) {
			vertexShaderCode = vertexShaderCode + "\n" + vertexShaderSource[i];
		}
		String fragmentShaderCode = fragmentShaderSource[0];
		for (int i = 1; i < fragmentShaderSource.length; ++i) {
			fragmentShaderCode = fragmentShaderCode + "\n" + fragmentShaderSource[i];
		}

		// Create shader objects and fill them with the source
		vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShaderID, vertexShaderCode);
		glCompileShader(vertexShaderID);

		fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShaderID, fragmentShaderCode);
		glCompileShader(fragmentShaderID);

		// Verify the shaders compiled correctly
		int status = glGetShaderi(vertexShaderID, GL_COMPILE_STATUS);
		if (status == GL_FALSE) {
			String error = glGetShaderInfoLog(vertexShaderID);
			System.out.println("Vertex Shader Failed to compile:\n" + error);
		}
		status = glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS);
		if (status == GL_FALSE) {
			String error = glGetShaderInfoLog(fragmentShaderID);
			System.out.println("Fragment Shader Failed to compile:\n" + error);
		}

		// Create a shader program containing the vertex and fragment shader
		shaderProgramID = glCreateProgram();
		glAttachShader(shaderProgramID, vertexShaderID);
		glAttachShader(shaderProgramID, fragmentShaderID);
	}

	public int getShaderProgramID() {
		return shaderProgramID;
	}

	public void setShaderProgramID(int shaderProgramID) {
		this.shaderProgramID = shaderProgramID;
	}

	public int getVertexShaderID() {
		return vertexShaderID;
	}

	public void setVertexShaderID(int vertexShaderID) {
		this.vertexShaderID = vertexShaderID;
	}

	public int getFragmentShaderID() {
		return fragmentShaderID;
	}

	public void setFragmentShaderID(int fragmentShaderID) {
		this.fragmentShaderID = fragmentShaderID;
	}
}
