package grosser.prototype.entity;

import grosser.engine.math.Vector2f;
import grosser.engine.math.Vector3f;

public class LampEntity extends Entity {

	public static final Vector3f lightColor = new Vector3f(0.855f, 0.647f, 0.125f);

	public LampEntity(Vector2f pos, int eID) {
		super(pos, eID);
	}

	@Override
	public void update() {
	}

}
