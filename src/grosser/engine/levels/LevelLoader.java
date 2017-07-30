package grosser.engine.levels;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import grosser.engine.math.Vector2f;
import grosser.prototype.entity.Tile;

public class LevelLoader {

	private static ArrayList<LevelVariable> levelVars = new ArrayList<LevelVariable>();
	private static LevelLoader get = new LevelLoader();
	public static LevelVariableVector2i dimensions = new LevelVariableVector2i("DIMENSIONS");
	public static LevelVariableFloat lightRadius = new LevelVariableFloat("LIGHT_RADIUS");
	public static ArrayList<ArrayList<Vector2f>> guardPaths = new ArrayList<ArrayList<Vector2f>>();
	static {
		levelVars.add(dimensions);
		levelVars.add(lightRadius);
	}

	private static ArrayList<String> loadFile(String path) {
		ArrayList<String> out = new ArrayList<String>();
		File file = new File(path);
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);

			System.out.println("Total file size to read (in bytes) : " + fis.available());

			int content;
			String line = "";
			while ((content = fis.read()) != -1) {
				// convert to char and display it
				// System.out.print((char) content);
				if (content == '\n') {
					out.add(new String(line.substring(0, line.length() - 1)));
					System.out.print(line);
					line = "";

				} else {
					line = line.concat((char) content + "");
				}
			}
			out.add(new String(line.substring(0, line.length() - 1)));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return out;
	}

	private static boolean checkForVariables(String s) {
		if (s.startsWith("//") || s.startsWith("/")) {
			return true;
		}
		if (s.startsWith("#NUM_OF_GUARDS")) {
			String[] data = s.substring(14).split(" ");
			int numOfGuards = Integer.parseInt(data[1]);
			System.out.println("Level contains " + numOfGuards + " guards.");
			for (int i = 0; i < numOfGuards; i++) {
				guardPaths.add(new ArrayList<Vector2f>());
			}
			return true;
		}
		if (s.startsWith("#GUARD")) {
			String[] data = s.substring(6).split(" ");
			for (int i = 3; i < data.length; i++) {
				LevelVariableVector2i vec = new LevelVariableVector2i("Guard Data[" + i + "]");
				vec.parse(data[i]);
				guardPaths.get(Integer.parseInt(data[2])).add(new Vector2f(vec.getX(), vec.getY()));
			}
			return true;
		}

		for (LevelVariable var : levelVars) {
			if (var.variableIsSetInLine(s)) {
				if (var instanceof LevelVariableVector2i) {
					LevelVariableVector2i vec = (LevelVariableVector2i) var;
					int indexOfEquals = s.indexOf('=');
					String remainder = s.substring(indexOfEquals);
					for (int i = 0; i < remainder.length(); i++) {
						if (remainder.charAt(i) != ' ' && remainder.charAt(i) != '=') {
							vec.parse(remainder.substring(i));
							break;
						}
					}

				}
				if (var instanceof LevelVariableFloat) {
					LevelVariableFloat value = (LevelVariableFloat) var;
					int indexOfEquals = s.indexOf('=');
					String remainder = s.substring(indexOfEquals);
					for (int i = 0; i < remainder.length(); i++) {
						if (remainder.charAt(i) != ' ' && remainder.charAt(i) != '=') {
							value.parse(remainder.substring(i));
							break;
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	public static ArrayList<Tile> loadLevel(String path) {
		ArrayList<String> file = loadFile(path);

		int levelStartLineIndex = 0;
		for (int i = 0; i < file.size(); i++) {
			String s = file.get(i);
			if (!checkForVariables(s)) {
				levelStartLineIndex = i;
				break;
			}
		}

		if (dimensions.getX() == 0 || dimensions.getY() == 0) {
			System.err.println("LEVEL LOADER ERROR: " + path
					+ "	Make sure you have defined a \"#DIMENSIONS\" variable in the level file! If you have, remember dimensions must be non zero, non negative numbers!");
			System.exit(1);
		}

		if (file.size() - levelStartLineIndex < dimensions.getY()) {
			System.err.println("LEVEL LOADER ERROR: " + path + " has invalid number of lines ("
					+ (file.size() - levelStartLineIndex) + " instead of expected " + dimensions.getY() + ")!");
		}

		ArrayList<Tile> tiles = new ArrayList<Tile>();
		for (int y = levelStartLineIndex; y < file.size(); y++) {
			String s = file.get(y);
			String[] ids = s.split(",");
			if (ids.length < dimensions.getX()) {
				System.err.println("LEVEL LOADER ERROR: " + path + "Line " + y + " has invalid number of tiles!");
			}
			for (int x = 0; x < ids.length; x++) {

				String tileID = ids[x];
				String metaData = "";
				if (ids[x].contains(":")) {
					tileID = ids[x].split(":")[0];
					metaData = ids[x].split(":")[1];
				}
				Tile t = new Tile(new Vector2f(x, y), Integer.parseInt(tileID));
				if (metaData != "")
					t.setMetaData(Integer.parseInt(metaData));
				tiles.add(t);
			}
		}
		return tiles;
	}
}
