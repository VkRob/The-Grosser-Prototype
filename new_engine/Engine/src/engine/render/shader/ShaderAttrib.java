package engine.render.shader;

public class ShaderAttrib {

	private int numOfFloats;
	private String name;
	private int id;

	public ShaderAttrib(String name, int numOfFloats) {
		this.numOfFloats = numOfFloats;
		this.setName(name);
	}

	public int getNumOfFloats() {
		return numOfFloats;
	}

	public void setNumOfFloats(int numOfFloats) {
		this.numOfFloats = numOfFloats;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
