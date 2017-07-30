package grosser.prototype.entity;

import grosser.engine.math.Vector2f;
import grosser.engine.math.Vector3f;

public class Player extends Entity {

	private Vector2f velocity;
	private Vector2f inputDeltaV;
	private float playerSpeed = 0.015f;

	private Camera camera;
	private TileManager map;
	private WorldRenderer renderer;

	private Vector2f initPosition;

	private boolean inLight = false;
	private static final Vector2f[] ANIM_RUN_LEFT = { new Vector2f(0, 0), new Vector2f(1, 0) };
	private static final Vector2f[] ANIM_RUN_RIGHT = { new Vector2f(0, 1), new Vector2f(1, 1) };
	private static final Vector2f[] ANIM_RUN_DOWN = { new Vector2f(0, 3), new Vector2f(1, 3), new Vector2f(2, 3),
			new Vector2f(3, 3) };
	private static final Vector2f[] ANIM_RUN_UP = { new Vector2f(0, 2), new Vector2f(1, 2), new Vector2f(2, 2),
			new Vector2f(3, 2) };

	private static final Vector2f[] ANIM_IDLE_DOWN = { new Vector2f(2, 1) };
	private static final Vector2f[] ANIM_IDLE_UP = { new Vector2f(3, 1) };
	private static final Vector2f[] ANIM_IDLE_RIGHT = { new Vector2f(2, 0) };
	private static final Vector2f[] ANIM_IDLE_LEFT = { new Vector2f(3, 0) };

	private static final Vector2f[] ANIM_ATTACK_RIGHT = { new Vector2f(0, 3), new Vector2f(1, 3), new Vector2f(2, 3) };
	private Vector2f[] animation = ANIM_RUN_LEFT;
	private int frame = 0;
	private int frameCtr = 0;

	private int framesInLight = 0;

	public Player(Vector2f position, Camera camera, WorldRenderer renderer) {
		super(position, Entity.ID_PLAYER);
		this.initPosition = position;
		this.renderer = renderer;
		this.camera = camera;
		camera.getPosition().x = initPosition.x;
		camera.getPosition().y = initPosition.y;
		velocity = new Vector2f(0, 0);
		inputDeltaV = new Vector2f(0, 0);
	}

	public void handleInput(char key) {

		if (key == 'w') {
			inputDeltaV.y += 1f;
			animation = ANIM_RUN_UP;
		}
		if (key == 'a') {
			inputDeltaV.x -= 1f;
			animation = ANIM_RUN_LEFT;
		}
		if (key == 's') {
			inputDeltaV.y -= 1f;
			animation = ANIM_RUN_DOWN;
		}
		if (key == 'd') {
			inputDeltaV.x += 1f;
			animation = ANIM_RUN_RIGHT;
		}
		if (key == ' ') {
			this.setTypeID(ID_PLAYER_ATTACK);
			frame = 0;
			animation = ANIM_ATTACK_RIGHT;
		}
		if (this.getTypeID() == Player.ID_PLAYER_ATTACK) {
			animation = ANIM_ATTACK_RIGHT;
		}
	}

	public void update() {
		camera.getPosition().x = position.x;
		camera.getPosition().y = -position.y;
		if (inputDeltaV.x != 0 || inputDeltaV.y != 0) {
			inputDeltaV = inputDeltaV.normalize();
		} else {
			if (animation.equals(ANIM_RUN_UP)) {
				frame = 0;
				animation = ANIM_IDLE_UP;
			}
			if (animation.equals(ANIM_RUN_DOWN)) {
				frame = 0;
				animation = ANIM_IDLE_DOWN;
			}
			if (animation.equals(ANIM_RUN_RIGHT)) {
				frame = 0;
				animation = ANIM_IDLE_RIGHT;
			}
			if (animation.equals(ANIM_RUN_LEFT)) {
				frame = 0;
				animation = ANIM_IDLE_LEFT;
			}
		}
		inputDeltaV = inputDeltaV.scale(playerSpeed);

		velocity = velocity.add(inputDeltaV);

		position.x += velocity.x;
		if (map.anyTilesSolid(map.getTilesTouching(position, new Vector2f(0.9f, 0.9f)))) {
			position.x -= velocity.x;
			velocity.x = 0;
		}

		position.y += velocity.y;
		if (map.anyTilesSolid(map.getTilesTouching(position, new Vector2f(0.9f, 0.9f)))) {
			position.y -= velocity.y;
			velocity.y = 0;
		}

		camera.getPosition().x += velocity.x;
		camera.getPosition().y -= velocity.y;
		velocity = velocity.divide(10f / 8f);

		inputDeltaV = new Vector2f(0, 0);

		boolean oldState = inLight;
		setInLight(updateLightCheck());
		if (inLight) {
			framesInLight++;
		} else {
			framesInLight = 0;
		}
		if (framesInLight > 30) {
			this.position = new Vector2f(initPosition);
		}
		if (oldState != inLight) {
			System.out.println("Player is now visible to guard? (" + inLight + ")");
		}

		frameCtr++;
		if ((frameCtr >= 60 / 4 && animation != ANIM_ATTACK_RIGHT)
				|| (frameCtr >= 60 / 8 && animation == ANIM_ATTACK_RIGHT)) {
			frame++;
			frameCtr = 0;
			if (frame >= animation.length) {
				frame = 0;
				setTypeID(ID_PLAYER);
				animation = ANIM_IDLE_RIGHT;
			}
		}
	}

	float lightFalloff(float d, float radius) {
		float att = clamp(1.0f - d * d / (radius * radius), 0.0f, 1.0f);
		att *= att;
		return att;
	}

	private boolean updateLightCheck() {
		for (Light light : renderer.getLights()) {
			if (light.isOwnedByGuard())
				light.setColor(LampEntity.lightColor);
		}
		Vector2f playerPosition = position.add(new Vector2f(1f, 1f));

		lightLoop: for (Light light : renderer.getLights()) {
			if (!light.isOwnedByGuard()) {
				continue lightLoop;
			}
			light.setColor(LampEntity.lightColor);
			float diff = lightFalloff(light.getPosition().xy().subtract(position).length(), light.getPosition().z);
			if (diff >= 0.005f) {
				// Perform LOS check to see if the light "can see" to the player
				// (and isnt blocked by a shadow casting object)

				float accuracy = 0.2f;// step size
				// int ctr = 0;
				if (light.getPosition().xy().subtract(position).length() <= 0.8f) {
					if (getTypeID() == Player.ID_PLAYER_ATTACK) {
						light.setColor(new Vector3f(0.0f, 1.0f, 0.0f));
					} else {
						light.setColor(new Vector3f(1.0f, 0.0f, 0.0f));
					}
					return true;
				}
				Vector2f dirToPlayer = light.getPosition().xy().subtract(playerPosition).normalize().scale(accuracy);
				Vector2f rayPos = playerPosition;
				// System.out.println((int)
				// (light.getPosition().xy().subtract(rayPos).length() /
				// accuracy));
				for (int ctr = 0; ctr <= (int) (light.getPosition().xy().subtract(rayPos).length() / accuracy); ctr++) {
					// rayPos.subtract(light.getPosition().xy()).length() >
					// accuracy * 7f) {
					// System.out.println(rayPos.subtract(light.getPosition().xy()).length());
					rayPos = rayPos.add(dirToPlayer);

					if (map.anyTilesCastShadow(map.getTilesTouching(rayPos, new Vector2f(0.001f, 0.001f)))) {
						// Light hit a shadow caster, therefore the player is in
						// a shadow
						// light.setColor(new Vector3f(0.0f, 0.0f, 1.0f));
						continue lightLoop;
					}

				}
				if (light.isOwnedByGuard()) {
					light.setColor(new Vector3f(1.0f, 0.0f, 0.0f));
					return true;
				}
			}
		}
		return false;
	}

	public TileManager getMap() {
		return map;
	}

	public void setMap(TileManager map) {
		this.map = map;
	}

	private float clamp(float val, float min, float max) {
		return Math.max(min, Math.min(max, val));
	}

	public boolean isInLight() {
		return inLight;
	}

	public void setInLight(boolean inLight) {
		this.inLight = inLight;
	}

	public Vector2f getAnimation() {
		if (frame >= animation.length) {
			frame = 0;
		}
		return animation[frame];
	}

	public void setInitPosition(Vector2f v) {
		this.initPosition = v;
	}
}
