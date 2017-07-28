package grosser.prototype.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import grosser.prototype.map.Map;
import grosser.prototype.map.Tile;
import grosser.prototype.render.ImageLoader;

	/**
	 * @author Robert
	 * Most of this code is just placeholder code... thrown together to get the
	 * entity to work
	 */

public class EntityWorker extends Entity {

	private BufferedImage img = ImageLoader.loadImage("/Sprites/McCree/0.png");

	private int targetX, targetY;

	private EntityInteractable interactTarget;
	private boolean interacting;

	private final WorkerManager workerManager;

	EntityWorker(int x, int y, int ID, WorkerManager workerManager) {
		super(x, y, ID);
		super.setWidth(Tile.SIZE);
		super.setHeight(Tile.SIZE);
		this.workerManager = workerManager;

		// assumes there is a first machine to interact with
        interactTarget = this.workerManager.getEntityManager().getMachines().get(0);
        goInteractWith(interactTarget);
	}

	/**
	 * Picks a random location for the Worker to "wander" to next, sets the
	 * targetX and targetY variables to the position
	 * 
	 * Range of (1,1) to (16,16)
	 */
	private void plotNewRandomPosition() {
		Random r = new Random();
		targetX = (r.nextInt(15) + 1) * Tile.SIZE;
		targetY = (r.nextInt(15) + 1) * Tile.SIZE;
	}

    /**
     * Sets the target position to specified coordinates
     * May or may not keep this, instead use goInteractWith
     */

	private void plotNewPosition(int x, int y) {
	    targetX = x;
	    targetY = y;
    }

    /**
     * Sets the target position to the position of an Interactable Entity
     */

    private void goInteractWith(EntityInteractable entity) {
	    targetX = entity.getX();
	    targetY = entity.getY();
	    interactTarget = entity;
    }

	/**
	 * Update and perform Worker actions (wandering around, or moving to and interacting with machines)
	 */
	public void update(Map map) {

	    if (interacting) return;

		// Worker calculates the change in X and Y required to get from its
		// current position to its target location
		float changeX = (float) (targetX - getX());
		float changeY = (float) (targetY - getY());

		// Worker normalizes this to a unit vector (magnitude of a unit vector =
		// 1)
		float magnitude = (float) Math.sqrt((changeX * changeX) + (changeY * changeY));
		float unitX = changeX / magnitude;
		float unitY = changeY / magnitude;

		// Speed of 5f
		int moveX = (int) (unitX * 5f);
		int moveY = (int) (unitY * 5f);

		// If the distance to the target is less than 5, find a new location

        if (magnitude <= 5f) {

            // if the worker is moving towards something it wants to interact with...
            if (interactTarget != null) {

                interacting = true;
                interactTarget.isBusy = true;
                ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
                executorService.schedule(() -> {
                    this.interacting = false;
                    this.interactTarget.interact(this);
                }, interactTarget.getJobTime(), TimeUnit.MILLISECONDS);
//                synchronized (this) {
//                    // interact with it! synchronized so that the worker thread will wake up
//                    // when the machine thread is done
//                    workerManager.getEntityManager().getMachines().get(0).interact(this);
//                    waiting = true;
//                    try {
//                        while (waiting) {
//                            this.wait();
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                List<EntityMachine> notBusyMachines = workerManager.getEntityManager().getNotBusyMachines();
//                if (notBusyMachines.size() == 1) {
//                    interactTarget = notBusyMachines.get(0);
//                    goInteractWith(interactTarget);
//                } else if (notBusyMachines.size() == 0) plotNewRandomPosition();
//                else {
//                    interactTarget = notBusyMachines.get(new Random().nextInt(notBusyMachines.size()));
//                    goInteractWith(interactTarget);
//                }
            }
            List<EntityMachine> notBusyMachines = workerManager.getEntityManager().getNotBusyMachines();
            if (notBusyMachines.size() == 1) {
                interactTarget = notBusyMachines.get(0);
                goInteractWith(interactTarget);
            }
            else if (notBusyMachines.size() == 0) plotNewRandomPosition();
            else {
                interactTarget = notBusyMachines.get(new Random().nextInt(notBusyMachines.size()));
                goInteractWith(interactTarget);
            }
        }

		// Move on X axis, check collision. If collided, move back.
		setX(getX() + moveX);
		for (Tile t : map.getTilesTouching(new Rectangle(getX(), getY(), getWidth(), getHeight()))) {
			if (Tile.isTileSolid(t)) {
				// Find new location because clearly this location is outside
				// the factory (and thus impossible to reach)
				plotNewRandomPosition();
				setX(getX() - moveX);
			}
		}

		// Move on Y axis, check collision. If collided, move back.
		setY(getY() + moveY);
		for (Tile t : map.getTilesTouching(new Rectangle(getX(), getY(), getWidth(), getHeight()))) {
			if (Tile.isTileSolid(t)) {
				// Find new location because clearly this location is outside
				// the factory (and thus impossible to reach)
				plotNewRandomPosition();
				setY(getY() - moveY);
			}
		}

	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

}