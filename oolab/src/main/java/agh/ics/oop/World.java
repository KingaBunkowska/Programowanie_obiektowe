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

        ConsoleMapDisplay observer = new ConsoleMapDisplay();

        List<Vector2d> animalPositions = List.of(
                new Vector2d(1, 2),
                new Vector2d(4, 5),
                new Vector2d(2, 6),
                new Vector2d(1, 1)
        );
        int n = 100;

        List<Simulation> resultList = new LinkedList<Simulation>();

        for (int i = 0; i < n; i++) {
            GrassField grassFieldTmp = new GrassField(10);
            grassFieldTmp.addObserver(observer);
            Simulation simulation = new Simulation(animalPositions, moveDirections, grassFieldTmp);

            resultList.add(simulation);
        }

        SimulationEngine simulationEngine = new SimulationEngine(resultList);
        simulationEngine.runAsync();
        try{
            simulationEngine.awaitSimulationsEnd();
            System.out.println("Tu koniec runAsync");
        }
        catch (InterruptedException e){
            System.out.println("Wątek przerwany");
            e.printStackTrace();
        }


        SimulationEngine simulationEngine2 = new SimulationEngine(resultList);
        try {
            simulationEngine2.runAsyncInThreadPool();
            simulationEngine2.awaitSimulationsEnd();
            System.out.println("Tu koniec runAsyncThreadPool");
        }
        catch (InterruptedException e){
            System.out.println("Wątek przerwany");
            e.printStackTrace();
        }

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