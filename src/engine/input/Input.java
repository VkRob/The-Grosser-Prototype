package engine.input;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import engine.entity.Camera;
import engine.window.WindowManager;

public class Input {
	private static Key getKeyByID(int id) {
		for (Key k : keys) {
			if (k.getId() == id)
				return k;
		}
		return null;
	}

	public static Key getKeyByName(String name) {
		for (Key k : keys) {
			if (k.getName().equals(name))
				return k;
		}
		return null;
	}

	private static ArrayList<Key> keys = new ArrayList<Key>();
	static {
		keys.add(new Key("W", GLFW_KEY_W));
		keys.add(new Key("A", GLFW_KEY_A));
		keys.add(new Key("S", GLFW_KEY_S));
		keys.add(new Key("D", GLFW_KEY_D));

		keys.add(new Key("UP", GLFW_KEY_UP));
		keys.add(new Key("DOWN", GLFW_KEY_DOWN));
		keys.add(new Key("LEFT", GLFW_KEY_LEFT));
		keys.add(new Key("RIGHT", GLFW_KEY_RIGHT));

		keys.add(new Key("SHIFT", GLFW_KEY_LEFT_SHIFT));
	}

	public static void processKey(int keyID, int state) {
		Key key = getKeyByID(keyID);
		if (key != null) {
			if (state == GLFW_PRESS) {
				key.setDown(true);
				key.setPressedThisFrame(true);
				key.setReleasedThisFrame(false);
			}
			if (state == GLFW_RELEASE) {
				key.setDown(false);
				key.setPressedThisFrame(false);
				key.setReleasedThisFrame(true);
			}
			if (state == GLFW_REPEAT) {
				key.setDown(true);
				key.setPressedThisFrame(false);
				key.setReleasedThisFrame(false);
			}
		}
	}

	public static void grabMouse() {
		// glfwSetInputMode(WindowManager.windowID, GLFW_CURSOR,
		// GLFW_CURSOR_HIDDEN);
	}

	public static Vector2f getMouseScreenPosition() {
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(WindowManager.windowID, xBuffer, yBuffer);
		return new Vector2f((float) xBuffer.get(0), (float) (-yBuffer.get(0)) + (WindowManager.params.getHeight()));
	}

	private static boolean disableClickUntilRelease = false;
	private static boolean disableHoldUntilRelease = false;

	public static void disableHoldUntilRelease() {
		disableHoldUntilRelease = true;
	}

	public static boolean isLeftClickDown() {
		if (disableHoldUntilRelease) {
			return false;
		} else {

			return 1 == glfwGetMouseButton(WindowManager.windowID, GLFW_MOUSE_BUTTON_1);
		}
	}

	public static boolean didLeftClick() {
		if (disableClickUntilRelease) {
			return false;
		} else {
			boolean val = 1 == glfwGetMouseButton(WindowManager.windowID, GLFW_MOUSE_BUTTON_1);
			if (val) {
				disableClickUntilRelease = true;
			}
			return val;
		}
	}

	public static Vector2f getMouseWorldPosition(Camera camera) {
		return getMouseScreenPosition().add(new Vector2f(camera.getPosition()));
	}

	public static void updateLate() {
		for (Key k : keys) {
			k.setPressedThisFrame(false);
			k.setReleasedThisFrame(false);
		}
	}

	public static void updateEarly() {
		if (disableHoldUntilRelease && !(1 == glfwGetMouseButton(WindowManager.windowID, GLFW_MOUSE_BUTTON_1))) {
			disableHoldUntilRelease = false;
		}
		if (disableClickUntilRelease && !(1 == glfwGetMouseButton(WindowManager.windowID, GLFW_MOUSE_BUTTON_1))) {
			disableClickUntilRelease = false;
		}
	}

}
