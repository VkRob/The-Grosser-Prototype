package game;

import engine.tile.Tile;
import game.machine.Machine;

public class Grosser {
	public static void registerGame() {
		Tile.registerTiles();
		Machine.registerMachines();
	}
}
