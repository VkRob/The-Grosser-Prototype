package entity;
/*
 * Created by Ben on 7/25/2017.
 * Base machine to implement EntityInteractable
 */

import map.Tile;
import render.ImageLoader;
import scenes.SceneGame;

import java.awt.image.BufferedImage;

public class EntityMachine extends Entity implements EntityInteractable {

    private BufferedImage img = ImageLoader.loadImage("/Sprites/Bot/0.png");

    public float jobTime;

    public EntityMachine(int x, int y, SceneGame sceneGame, float jobTime) {
        super(x, y, sceneGame);
        super.setWidth(Tile.SIZE);
        super.setHeight(Tile.SIZE);
        this.jobTime = jobTime;
    }

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
