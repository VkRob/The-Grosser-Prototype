package input;

public class Key {

	private String name;
	private int id;
	private boolean isDown = false;
	private boolean isPressedThisFrame = false;
	private boolean isReleasedThisFrame = false;

	public Key(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isDown() {
		return isDown;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}

	public boolean isPressedThisFrame() {
		return isPressedThisFrame;
	}

	public void setPressedThisFrame(boolean isPressedThisFrame) {
		this.isPressedThisFrame = isPressedThisFrame;
	}

	public boolean isReleasedThisFrame() {
		return isReleasedThisFrame;
	}

	public void setReleasedThisFrame(boolean isReleasedThisFrame) {
		this.isReleasedThisFrame = isReleasedThisFrame;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
