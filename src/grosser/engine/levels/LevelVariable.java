package grosser.engine.levels;

public class LevelVariable {

	protected String name;

	public LevelVariable(String name) {
		this.name = name;
	}

	public boolean variableIsSetInLine(String s) {
		if (s.startsWith("#".concat(name))) {
			return true;
		} else {
			return false;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
