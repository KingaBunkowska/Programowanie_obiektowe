package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.MapVisualizer;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class World {

    public static void main(String[] args){
        System.out.println("System wystartował");

        OptionsParser optionsParser = new OptionsParser();
        List<MoveDirection> moveDirections = optionsParser.convert(args);

        RectangularMap rectangularMap = new RectangularMap(10,10);
        GrassField grassField = new GrassField(10);

        ConsoleMapDisplay observer = new ConsoleMapDisplay();
        rectangularMap.addObserver(observer);
        grassField.addObserver(observer);

        List<Vector2d> animalPositions = List.of(
                new Vector2d(1, 2),
                new Vector2d(4, 5),
                new Vector2d(2, 6),
                new Vector2d(1, 1)
        );

        List<Simulation> simulations= List.of(
                new Simulation(animalPositions, moveDirections, rectangularMap),
                new Simulation(animalPositions, moveDirections, grassField)
        );


        SimulationEngine simulationEngine = new SimulationEngine(simulations);

        simulationEngine.runAsync();

        System.out.println("Those were two simulations ^^^\n-----------------------------------\n\n");

        int n = 1000;

        List<Simulation> resultList = new LinkedList<Simulation>();

        for (int i = 0; i < n; i++) {
            GrassField grassFieldTmp = new GrassField(10);
            grassFieldTmp.addObserver(observer);
            Simulation simulation = new Simulation(animalPositions, moveDirections, grassFieldTmp);

            resultList.add(simulation);
        }

        SimulationEngine simulationEngine2 = new SimulationEngine(resultList);
        simulationEngine2.runAsync();

        System.out.println("Those were n simulations ^^^\n-----------------------------------\n\n");

        try {
            simulationEngine2.runAsyncInThreadPool();
        }
        catch (InterruptedException e){
            System.out.println("Something was interrupted, why it was computing more than 10s?");
            e.printStackTrace();
        }

        System.out.println("Those were n simulations but with executor ^^^\n-----------------------------------\n\n");

        System.out.println("I zakończył działanie");



    }


    public static void run(List<MoveDirection> move_directions){
        System.out.println(move_directions);
        for (MoveDirection move_direction : move_directions){
            switch(move_direction){
                case MoveDirection.FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case MoveDirection.BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case MoveDirection.RIGHT -> System.out.println("Zwierzak skręca w prawo");
                case MoveDirection.LEFT -> System.out.println("Zwierzak skręca w lewo");
            }
        }
    }
}