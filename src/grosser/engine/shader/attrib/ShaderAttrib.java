package grosser.engine.shader.attrib;

import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;

public abstract class ShaderAttrib {

	private int myID; // Location in shader

	private int shaderProgramID; // Shader's location in memory
	private String name; // Name of the attribute
	private int sizeOfAttribInMemory; // The size of this attribute in memory
	private int sizeOfAttrib; // The number of floats this attrib contains

	public ShaderAttrib(String name, int shaderProgramID, int sizeOfAttribInMemory, int sizeOfAttrib) {

		this.setName(name);
		this.setShaderProgramID(shaderProgramID);
		this.setSizeOfAttribInMemory(sizeOfAttribInMemory);
		this.setSizeOfAttrib(sizeOfAttrib);

		// Find the attribute and enable it
		myID = glGetAttribLocation(shaderProgramID, name);
		glEnableVertexAttribArray(myID);
	}

	public int getShaderProgramID() {
		return shaderProgramID;
	}

	public void setShaderProgramID(int shaderProgramID) {
		this.shaderProgramID = shaderProgramID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSizeOfAttribInMemory() {
		return sizeOfAttribInMemory;
	}

	public void setSizeOfAttribInMemory(int sizeOfAttrib) {
		this.sizeOfAttribInMemory = sizeOfAttrib;
	}

	public int getSizeOfAttrib() {
		return sizeOfAttrib;
	}

	public void setSizeOfAttrib(int sizeOfAttrib) {
		this.sizeOfAttrib = sizeOfAttrib;
	}

	public int getID() {
		return myID;
	}

	public void setMyID(int myID) {
		this.myID = myID;
	}
}
