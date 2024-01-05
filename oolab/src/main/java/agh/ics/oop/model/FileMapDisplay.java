package agh.ics.oop.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileMapDisplay implements MapChangeListener {


    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        String logFileName = "out/map_" + worldMap.getId() + ".log";

        try (PrintWriter writer = new PrintWriter(new FileWriter(logFileName, true))) {
            writer.println(message);
            writer.println(worldMap.toString());
            writer.println();
        } catch (IOException e) {
            System.err.println("Error writing to log file");
        }
    }
}
