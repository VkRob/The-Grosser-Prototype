package grosser.prototype.entity;

import grosser.engine.core.ImageLoader;
import grosser.engine.math.Vector2f;

public abstract class Entity {

	public static final int NUM_OF_ENTITIES = 9;
	public static float[] textures[] = new float[NUM_OF_ENTITIES][];

	public static final int ID_PLAYER = 0;
	public static final int ID_GUARD = 1;
	public static final int ID_LAMP_UP = 2;
	public static final int ID_LAMP_RIGHT = 3;
	public static final int ID_LAMP_DOWN = 4;
	public static final int ID_LAMP_LEFT = 5;
	public static final int ID_GENERATOR0 = 6;
	public static final int ID_GENERATOR1 = 7;
	public static final int ID_PLAYER_ATTACK = 8;

	static {
		textures[ID_PLAYER] = ImageLoader.getTexturePixels("/Tiles/player.png");
		textures[ID_GUARD] = ImageLoader.getTexturePixels("/Tiles/guard.png");
		textures[ID_LAMP_UP] = ImageLoader.getTexturePixels("/Tiles/lamp/0.png");
		textures[ID_LAMP_RIGHT] = ImageLoader.getTexturePixels("/Tiles/lamp/1.png");
		textures[ID_LAMP_DOWN] = ImageLoader.getTexturePixels("/Tiles/lamp/2.png");
		textures[ID_LAMP_LEFT] = ImageLoader.getTexturePixels("/Tiles/lamp/3.png");
		textures[ID_GENERATOR0] = ImageLoader.getTexturePixels("/Tiles/generator/key.png");
		textures[ID_GENERATOR1] = ImageLoader.getTexturePixels("/Tiles/generator/1.png");
		textures[ID_PLAYER_ATTACK] = ImageLoader.getTexturePixels("/Tiles/playerAttack.png");
	}

	private int typeID;
	protected Vector2f position;

	public Entity(Vector2f pos, int typeID) {
		this.setTypeID(typeID);
		this.setPosition(pos);
	}

	public abstract void update();

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}
}
