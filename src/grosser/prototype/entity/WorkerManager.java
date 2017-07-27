package grosser.prototype.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ben
 * Manager for all the workers in the game
 */

class WorkerManager {

    private ArrayList<EntityWorker> workers;

    private final EntityManager entityManager;

    WorkerManager(EntityManager entityManager) {
        this.workers = new ArrayList<>();
        this.entityManager = entityManager;
    }

    void addNewWorker(int x, int y, int ID) {
        workers.add(new EntityWorker(x, y, ID, this));
    }

    EntityWorker getWorkerByID(int ID) {
        for (EntityWorker worker : workers) {
            if (worker.getID() == ID) return worker;
        }
        return null;
    }

    EntityManager getEntityManager() {
        return this.entityManager;
    }

    List<EntityWorker> getWorkers() {
        return Collections.unmodifiableList(workers);
    }
}
