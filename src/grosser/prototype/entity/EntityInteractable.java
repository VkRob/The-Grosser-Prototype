package grosser.prototype.entity;

/**
 * @author Ben
 * An interface for Entities that are interactable.
 */

abstract class EntityInteractable extends Entity{

    int jobTime = 1500;

    EntityInteractable(int x, int y, int ID) {
        super(x, y, ID);
    }

    EntityInteractable(int x, int y, int ID, int width, int height) {
        super(x, y, ID, width, height);
    }

    EntityInteractable(int x, int y, int ID, int jobTime) {
        super(x, y, ID);
        this.jobTime = jobTime;
    }

    EntityInteractable(int x, int y, int ID, int width, int height, int jobTime) {
        super(x, y, ID, width, height);
        this.jobTime = jobTime;
    }

    abstract void interact(Entity entity);

    void setJobTime(int jobTime) {
        this.jobTime = jobTime;
    }

    public int getJobTime() {
        return jobTime;
    }
}
