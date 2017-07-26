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

    public float jobTime;

    public EntityMachine(int x, int y, SceneGame sceneGame, float jobTime) {
        super(x, y, sceneGame);
        super.setWidth(Tile.SIZE);
        super.setHeight(Tile.SIZE);
        this.jobTime = jobTime;
    }

    /**
     * interact with this grosser.prototype.entity
     * @param entity: the grosser.prototype.entity interacting with this one
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
