package grosser.prototype.entity;

import grosser.prototype.map.Tile;
import grosser.prototype.render.ImageLoader;
import grosser.prototype.scenes.SceneGame;

import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Ben
 * Base machine to implement EntityInteractable
 */

public class EntityMachine extends EntityInteractable {

    private BufferedImage img = ImageLoader.loadImage("/Sprites/Bot/0.png");

    /**
     * time for interaction with this Entity to take, in milliseconds
     */
    private int jobTime;

    private final MachineManager machineManager;

    EntityMachine(int x, int y, int ID, MachineManager machineManager, int jobTime) {
        super(x, y, ID);
        super.setWidth(Tile.SIZE);
        super.setHeight(Tile.SIZE);
        this.machineManager = machineManager;
        this.jobTime = jobTime;
    }

    /**
     * interact with this entity
     * @param entity: the entity interacting with this one
     */

    public void interact(Entity entity) {
        isBusy = false;
        // do something here like make new items
    }

    public BufferedImage getImg() {
        return img;
    }


}
