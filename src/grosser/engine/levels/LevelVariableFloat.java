package grosser.engine.levels;

public class LevelVariableFloat extends LevelVariable {

	private float value = 0f;

	public LevelVariableFloat(String name) {
		super(name);
	}

	public void parse(String s) {
		value = Float.parseFloat(s.substring(0, s.indexOf(';')));
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

}
