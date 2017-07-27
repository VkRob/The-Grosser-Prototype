package main;

import core.EngineCore;

public class Main {

	public static final float WIDTH = 800.0f;
	public static final float HEIGHT = 600.0f;

	public static void main(String[] args) {
		new EngineCore("Engine Test", (int) WIDTH, (int) HEIGHT);
	}

}
