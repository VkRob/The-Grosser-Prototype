package grosser.prototype.entity;

import grosser.prototype.scenes.SceneGame;
import grosser.prototype.map.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ben
 * A manager for all the entities in the game
 */

public class EntityManager {

    /*
    separate ArrayLists for workers and machines, may want to find
    a better way to do this
    I will probably add separate manager classes for each type of Entity
    */

    private ArrayList<EntityWorker> workers;
    private ArrayList<EntityMachine> machines;

    /*
    count to keep each entities' ID unique.
    each time an entity is added, the count increases
    may be a better way to do this as well
    */
    private int count;

    // I don't think I actually need this anymore
    private final SceneGame sceneGame;

    public EntityManager(SceneGame sceneGame) {
        this.sceneGame = sceneGame;
        workers = new ArrayList<>();
        machines = new ArrayList<>();
    }

    // adds a new entity to the respective ArrayList based on the number of the EntityType provided

    public void addNewEntity(EntityType type, int x, int y) {
        switch(type.ordinal()) {
            case 0 :
                workers.add(new EntityWorker(x, y, count, this));
                break;
            case 1 :
                machines.add(new EntityMachine(x, y, count, this, 1.5f));
                break;
        }
        count++;
    }

    public void updateWorkers(Map map) {
        for (EntityWorker worker : workers) {
            worker.update(map);
        }
    }

    EntityWorker getWorkerByID(int ID) {
        for (EntityWorker worker : workers) {
            if (worker.getID() == ID) return worker;
        }
        return null;
    }

    EntityMachine getMachineByID(int ID) {
        for (EntityMachine machine : machines) {
            if (machine.getID() == ID) return machine;
        }
        return null;
    }

    Entity getEntityByID(int ID) {
        for (EntityWorker worker : workers) {
            if (worker.getID() == ID) return worker;
        }
        for (EntityMachine machine : machines) {
            if (machine.getID() == ID) return machine;
        }
        return null;
    }

    SceneGame getSceneGame() {
        return sceneGame;
    }

    // unmodifiable for hopefully obvious reasons

    public List<EntityWorker> getWorkers() {
        return Collections.unmodifiableList(workers);
    }

    public List<EntityMachine> getMachines() {
        return Collections.unmodifiableList(machines);
    }
}
