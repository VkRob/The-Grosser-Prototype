package shader.uniform;

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;

public abstract class ShaderUniform {

	private int myID; // Pointer to location in shader
	private int shaderProgramID; // Pointer to location of shader in memory
	private String name; // Name of this uniform in the shader

	public ShaderUniform(String name, int shaderProgramID, boolean isTexture) {

		this.setName(name);
		this.shaderProgramID = shaderProgramID;

		if (!isTexture)
			// find the uniform's location in the shader
			this.setMyID(glGetUniformLocation(shaderProgramID, name));
		else {
			// find the texture's location in the shader
			this.setMyID(glGenTextures());
		}
	}

	public int getShaderProgramID() {
		return shaderProgramID;
	}

	public void setShaderProgramID(int shaderProgramID) {
		this.shaderProgramID = shaderProgramID;
	}

	public int getMyID() {
		return myID;
	}

	public void setMyID(int myID) {
		this.myID = myID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
