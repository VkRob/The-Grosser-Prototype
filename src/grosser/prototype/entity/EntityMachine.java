package grosser.prototype.entity;

import grosser.prototype.map.Tile;
import grosser.prototype.render.ImageLoader;
import grosser.prototype.scenes.SceneGame;

import java.awt.image.BufferedImage;

/**
 * @author Ben
 * Base machine to implement EntityInteractable
 */

public class EntityMachine extends Entity implements EntityInteractable {

    private BufferedImage img = ImageLoader.loadImage("/Sprites/Bot/0.png");

    /**
     * time for interaction with this Entity to take, in seconds
     */

    private final float jobTime;

    EntityMachine(int x, int y, int ID, EntityManager entityManager, float jobTime) {
        super(x, y, ID, entityManager);
        super.setWidth(Tile.SIZE);
        super.setHeight(Tile.SIZE);
        this.jobTime = jobTime;
    }

    /**
     * interact with this entity
     * @param entity: the entity interacting with this one
     */

    public void interact(Entity entity) {
        synchronized (entity) {
            try {
                Thread.sleep((long)this.jobTime * 1000);
            } catch (InterruptedException e) {
                // serious issues if this happens
                e.printStackTrace();
            }
        // do something here like make new items
            entity.notify();
        }
    }

    public BufferedImage getImg() {
        return img;
    }
}
