package grosser.engine.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;

import java.util.ArrayList;

import grosser.engine.core.ImageLoader;
import grosser.engine.shader.attrib.ShaderAttrib;
import grosser.engine.shader.uniform.ShaderUniform;

public class ShaderProgram {

	private int shaderProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;

	private ArrayList<ShaderAttrib> attribs = new ArrayList<ShaderAttrib>();
	private ArrayList<ShaderUniform> uniforms = new ArrayList<ShaderUniform>();
	private ArrayList<ShaderTexture> textures = new ArrayList<ShaderTexture>();

	private int currentTextureIndex = 0;

	public void use() {
		glUseProgram(shaderProgramID);
	}

	public ShaderProgram(String fragDataLocation) {

		String vertexShaderCode = ImageLoader.getShader("C:/Users/Robert/git/The-Grosser-Prototype/res/glsl_source/vertexShader.glsl");
		String fragmentShaderCode = ImageLoader.getShader("C:/Users/Robert/git/The-Grosser-Prototype/res/glsl_source/fragmentShader.glsl");

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
			System.exit(0);
		}
		status = glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS);
		if (status == GL_FALSE) {
			String error = glGetShaderInfoLog(fragmentShaderID);
			System.out.println("Fragment Shader Failed to compile:\n" + error);
			System.exit(0);
		}

		// Create a shader program containing the vertex and fragment shader
		shaderProgramID = glCreateProgram();
		glAttachShader(shaderProgramID, vertexShaderID);
		glAttachShader(shaderProgramID, fragmentShaderID);

		glBindFragDataLocation(shaderProgramID, 0, "outColor");

		// Link & begin the shader program
		glLinkProgram(shaderProgramID);
		glUseProgram(shaderProgramID);
	}

	public void addShaderTexture(ShaderTexture texture) {
		textures.add(texture);
		texture.setMyID(glGenTextures());

		texture.setTextureBankID(GL_TEXTURE0 + currentTextureIndex);

		glActiveTexture(GL_TEXTURE0 + currentTextureIndex);
		glBindTexture(GL_TEXTURE_2D, texture.getMyID());
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, texture.getWidth(), texture.getHeight(), 0, GL_RGB, GL_FLOAT,
				texture.getPixels());
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, texture.getTextureMode());
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, texture.getTextureMode());
		glUniform1i(glGetUniformLocation(shaderProgramID, texture.getName()), currentTextureIndex);

		currentTextureIndex++;
	}

	public void addFrameBufferTexture(String name, int framebufferColorAttachmentID) {
		glActiveTexture(GL_TEXTURE0 + currentTextureIndex);
		glBindTexture(GL_TEXTURE_2D, framebufferColorAttachmentID);
		glUniform1i(glGetUniformLocation(shaderProgramID, name), currentTextureIndex);
		currentTextureIndex++;
	}

	public void addShaderUniform(ShaderUniform uniform) {
		uniforms.add(uniform);
	}

	public void addShaderAttrib(ShaderAttrib attrib) {
		attribs.add(attrib);
	}

	public void pushAttribPointers() {

		int vertexSize = 0;

		// Find the sum of the sizes on all the attributes
		for (ShaderAttrib attrib : attribs) {
			vertexSize += attrib.getSizeOfAttribInMemory();
		}

		// Set the pointers
		int offset = 0;
		for (ShaderAttrib attrib : attribs) {
			// For debugging
			// System.out.println("glVertexAttribPointer(" + attrib.getID() + ",
			// " + attrib.getSizeOfAttrib()
			// + ", GL_FLOAT, false, " + vertexSize + ", " + offset + ")");
			glVertexAttribPointer(attrib.getID(), attrib.getSizeOfAttrib(), GL_FLOAT, false, vertexSize, offset);
			offset += attrib.getSizeOfAttribInMemory();
		}

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
