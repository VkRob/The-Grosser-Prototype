package grosser.prototype.entity;

import java.util.ArrayList;

public class EntityManager {

	private ArrayList<Entity> entities;
	private ArrayList<Entity> garbage;
	private Player player;

	public EntityManager() {
		entities = new ArrayList<Entity>();
		garbage = new ArrayList<Entity>();
	}

	public void update() {
		for (Entity e : entities) {
			e.update();
		}
		for (Entity e : garbage) {
			entities.remove(e);
		}
		garbage.clear();
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void addEntity(Entity e) {
		entities.add(e);
		if (e instanceof Player) {
			setPlayer((Player) e);
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void addToGarbage(Entity e) {
		garbage.add(e);
	}
}
