package agh.ics.oop.model;


public class ConsoleMapDisplay implements MapChangeListener {
    private int updates = 0;
    @Override
    public synchronized void mapChanged(AbstractWorldMap worldMap, String message) {
        System.out.println("ID: " + worldMap.getId());
        System.out.println(message);
        System.out.println(worldMap);
        updates += 1;
    }
}
