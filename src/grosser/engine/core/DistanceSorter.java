package grosser.engine.core;

import java.util.Comparator;

import grosser.engine.math.Vector2f;

public class DistanceSorter implements Comparator<Vector2f> {

	private Vector2f lightPos;

	public DistanceSorter(Vector2f lightPos) {
		this.lightPos = lightPos;
	}

	@Override
	public int compare(Vector2f o1, Vector2f o2) {
		float myX = lightPos.x;
		float myY = lightPos.y;
		float shaX = o1.x;
		float shaY = -o1.y;

		float o1Try = (float) Math.sqrt(Math.pow(shaX - myX, 2) + Math.pow(shaY - myY, 2));

		shaX = o2.x;
		shaY = -o2.y;

		float o2Try = (float) Math.sqrt(Math.pow(shaX - myX, 2) + Math.pow(shaY - myY, 2));

		return Float.compare(o1Try, o2Try);

	}

}
