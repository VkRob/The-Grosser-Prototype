package grosser.prototype.entity;

/**
 * @author Ben
 * An interface for Entities that are interactable.
 */

abstract class EntityInteractable extends Entity{

    int jobTime = 1500;
    boolean isBusy;

    EntityInteractable(int x, int y, int ID) {
        super(x, y, ID);
        this.isBusy = false;
    }

    EntityInteractable(int x, int y, int ID, int width, int height) {
        super(x, y, ID, width, height);
        this.isBusy = false;
    }

    EntityInteractable(int x, int y, int ID, int jobTime) {
        super(x, y, ID);
        this.jobTime = jobTime;
        this.isBusy = false;
    }

    EntityInteractable(int x, int y, int ID, int width, int height, int jobTime) {
        super(x, y, ID, width, height);
        this.jobTime = jobTime;
        this.isBusy = false;
    }

    abstract void interact(Entity entity);

    void setJobTime(int jobTime) {
        this.jobTime = jobTime;
    }

    public int getJobTime() {
        return jobTime;
    }
}
