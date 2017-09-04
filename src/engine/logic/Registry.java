package engine.logic;

import java.util.ArrayList;

public class Registry {

	private ArrayList<Object> objects;
	private ArrayList<String> objectNames;
	private ArrayList<Integer> objectIds;

	public Registry() {
		objects = new ArrayList<>();
		objectNames = new ArrayList<>();
		objectIds = new ArrayList<>();
	}

	public void add(int id, String name, Object obj) {

		for (String n : objectNames) {
			if (n.equals(name)) {
				System.out.println("WARN: object with name: " + name + " already exists in the registry.");
			}
		}

		for (Integer i : objectIds) {
			if (i == id) {
				System.out.println("WARN: object with ID: " + id + " already exists in the registry.");
			}
		}

		objectIds.add(id);
		objectNames.add(name);
		objects.add(obj);
	}

	public int getIdByName(String name) {
		for (String n : objectNames) {
			if (n.equals(name)) {
				return objectIds.get(objectNames.indexOf(n));
			}
		}
		System.out.println("Object Name not found: " + name);
		return -1;
	}

	public Object getObjByID(int id) {
		for (Integer i : objectIds) {
			if (i.equals(id)) {
				return objects.get(objectIds.indexOf(i));
			}
		}
		System.out.println("Object ID not found: " + id);
		return -1;
	}

	public Object getObjByName(String name) {
		for (String n : objectNames) {
			if (n.equals(name)) {
				return objects.get(objectNames.indexOf(n));
			}
		}
		System.out.println("Object name not found: " + name);
		return -1;
	}

	public int getIdOfObj(Object obj) {
		for (Object o : objects) {
			if (o.equals(obj)) {
				return objectIds.get(objects.indexOf(o));
			}
		}
		System.out.println("Object not found: " + obj);
		return -1;
	}
}
