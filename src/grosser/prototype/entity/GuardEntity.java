package grosser.prototype.entity;

import java.util.ArrayList;

import grosser.engine.math.Vector2f;
import grosser.engine.math.Vector3f;

public class GuardEntity extends Entity {

	private Light light;
	private ArrayList<Vector2f> path;
	private Vector2f currentTarget;
	private int currentNode = 0;
	private float speed = 0.015f;
	private EntityManager mgr;

	public GuardEntity(EntityManager mgr, Light light, Vector2f pos, ArrayList<Vector2f> path) {
		super(pos, Entity.ID_GUARD);
		this.mgr = mgr;
		this.light = light;
		this.path = path;
		if (path.size() == 0) {
			this.path.add(new Vector2f(0, 0));
		}

		currentTarget = pos.add(this.path.get(currentNode));
	}

	public void update() {
		if (currentTarget.subtract(position).length() <= speed) {
			position = new Vector2f(currentTarget);
			currentNode++;
			if (currentNode == this.path.size()) {
				currentNode = 0;
			}
			currentTarget = position.add(this.path.get(currentNode));
		} else {
			position = position.add(this.path.get(currentNode).normalize().scale(speed));
		}
		light.getPosition().x = position.x;
		light.getPosition().y = position.y;

		if (light.getColor().x == 0 && light.getColor().y == 1 && light.getColor().z == 0) {
			mgr.addToGarbage(this); // delete me
			light.setOwnedByGuard(false); // resign ownership of my light
		}
	}

}
