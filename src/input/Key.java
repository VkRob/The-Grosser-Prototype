package input;

public class Key {

	private int keycode;
	private boolean pressed = false;
	private boolean tapped = false;
	private int time = 0;

	public Key(int kc) {
		this.keycode = kc;
	}

	public void keyPressed(int kc) {
		if (kc == keycode) {
			pressed = true;
		}
	}

	public void keyReleased(int kc) {
		if (kc == keycode) {
			pressed = false;
			time = 0;
		}
	}

	public void update() {
		if (pressed) {
			time++;
		}
		if (time == 1) {
			tapped = true;
		} else {
			tapped = false;
		}
	}

	public int getKeycode() {
		return keycode;
	}

	public void setKeycode(int keycode) {
		this.keycode = keycode;
	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public boolean isTapped() {
		return tapped;
	}

	public void setTapped(boolean tapped) {
		this.tapped = tapped;
	}
}
