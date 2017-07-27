package grosser.prototype.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ben
 * Manager for EntityMachines
 */

public class MachineManager {

    private ArrayList<EntityMachine> machines;

    private final EntityManager entityManager;

    MachineManager(EntityManager entityManager) {
        this.machines = new ArrayList<>();
        this.entityManager = entityManager;
    }

    void addNewMachine(int x, int y, int ID) {
        machines.add(new EntityMachine(x, y, ID, this, 1500));
    }

    EntityMachine getMachineByID(int ID) {
        for (EntityMachine machine : machines) {
            if (machine.getID() == ID) return machine;
        }
        return null;
    }

    EntityManager getEntityManager() {
        return this.entityManager;
    }

    List<EntityMachine> getMachines() {
        return Collections.unmodifiableList(machines);
    }
}
