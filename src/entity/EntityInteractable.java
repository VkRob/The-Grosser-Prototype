package entity;

/**
 * @author Ben
 * An interface for Entities that are interactable.
 */

public interface EntityInteractable {

    float jobTime = 0;

    void interact(Entity entity);
}
