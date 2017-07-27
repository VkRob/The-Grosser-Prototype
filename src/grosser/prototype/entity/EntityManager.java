package grosser.prototype.entity;

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

    private WorkerManager workerManager;
    private MachineManager machineManager;

    /*
    count to keep each entities' ID unique.
    each time an entity is added, the count increases
    may be a better way to do this as well
    */
    private int count;

    public EntityManager() {
        workerManager = new WorkerManager(this);
        machineManager = new MachineManager(this);
    }

    // adds a new entity to the respective ArrayList based on the number of the EntityType provided

    public void addNewEntity(EntityType type, int x, int y) {
        switch(type.ordinal()) {
            case 0 :
                workerManager.addNewWorker(x, y, count);
                break;
            case 1 :
                machineManager.addNewMachine(x, y, count);
                break;
        }
        count++;
    }

    public void updateWorkers(Map map) {
        for (EntityWorker worker : workerManager.getWorkers()) {
            worker.update(map);
        }
    }

    EntityWorker getWorkerByID(int ID) {
        for (EntityWorker worker : workerManager.getWorkers()) {
            if (worker.getID() == ID) return worker;
        }
        return null;
    }

    EntityMachine getMachineByID(int ID) {
        for (EntityMachine machine : machineManager.getMachines()) {
            if (machine.getID() == ID) return machine;
        }
        return null;
    }

    Entity getEntityByID(int ID) {
        for (EntityWorker worker : workerManager.getWorkers()) {
            if (worker.getID() == ID) return worker;
        }
        for (EntityMachine machine : machineManager.getMachines()) {
            if (machine.getID() == ID) return machine;
        }
        return null;
    }

    // unmodifiable for hopefully obvious reasons

    public List<EntityWorker> getWorkers() {
        return workerManager.getWorkers();
    }

    public List<EntityMachine> getMachines() {
        return machineManager.getMachines();
    }
}
