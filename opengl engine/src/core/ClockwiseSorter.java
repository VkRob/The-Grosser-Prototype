package core;

import java.util.Comparator;

import math.Vector2f;

public class ClockwiseSorter implements Comparator<Vector2f> {

	private Vector2f center;

	public ClockwiseSorter(Vector2f center) {
		this.center = center;
	}

	private boolean less(Vector2f a, Vector2f b) {
		if (a.x - center.x >= 0 && b.x - center.x < 0)
			return true;
		if (a.x - center.x < 0 && b.x - center.x >= 0)
			return false;
		if (a.x - center.x == 0 && b.x - center.x == 0) {
			if (a.y - center.y >= 0 || b.y - center.y >= 0)
				return a.y > b.y;
			return b.y > a.y;
		}

		// compute the cross product of vectors (center -> a) x (center -> b)
		float det = (a.x - center.x) * (b.y - center.y) - (b.x - center.x) * (a.y - center.y);
		if (det < 0f)
			return true;
		if (det > 0f)
			return false;

		// points a and b are on the same line from the center
		// check which point is closer to the center
		float d1 = (a.x - center.x) * (a.x - center.x) + (a.y - center.y) * (a.y - center.y);
		float d2 = (b.x - center.x) * (b.x - center.x) + (b.y - center.y) * (b.y - center.y);
		return d1 > d2;
	}

	@Override
	public int compare(Vector2f a, Vector2f b) {
		if(less(a,b)){
			return -1;
		}else{
			return 1;
		}
	}

}
