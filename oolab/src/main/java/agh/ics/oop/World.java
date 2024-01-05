package agh.ics.oop;

import agh.ics.oop.model.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javafx.application.Application;

public class World {

    public static void main(String[] args){
        System.out.println("System wystartował");

        WorldMap worldMap = new GrassField(4);
        worldMap.addObserver(new FileMapDisplay());
        Simulation simulation = new Simulation(
                List.of(new Vector2d(0,0), new Vector2d(1, 1)),
                List.of(
                        MoveDirection.FORWARD,
                        MoveDirection.RIGHT,
                        MoveDirection.FORWARD,
                        MoveDirection.FORWARD,
                        MoveDirection.BACKWARD,
                        MoveDirection.FORWARD
                ),
                worldMap);

        simulation.run();

        System.out.println("I zakończył działanie");
    }






}