package agh.ics.oop;

import agh.ics.oop.model.*;
import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;

public class World {

    public static void main(String[] args){
        System.out.println("System wystartował");
//
//        OptionsParser optionsParser = new OptionsParser();
//        List<MoveDirection> moveDirections = optionsParser.convert(args);
//
//        ConsoleMapDisplay observer = new ConsoleMapDisplay();
////
//        List<Vector2d> animalPositions = List.of(
//                new Vector2d(1, 2),
//                new Vector2d(4, 5),
//                new Vector2d(2, 6),
//                new Vector2d(1, 1)
//        );
//        int n = 100;
//
//        List<Simulation> resultList = new LinkedList<Simulation>();
//
//        for (int i = 0; i < n; i++) {
//            GrassField grassFieldTmp = new GrassField(10);
//            grassFieldTmp.addObserver(observer);
//            Simulation simulation = new Simulation(animalPositions, moveDirections, grassFieldTmp);
//
//            resultList.add(simulation);
//        }
////
//        SimulationEngine simulationEngine = new SimulationEngine(resultList);
//        simulationEngine.runAsync();
//        try{
//            simulationEngine.awaitSimulationsEnd();
//            System.out.println("Tu koniec runAsync");
//        }
//        catch (InterruptedException e){
//            System.out.println("Wątek przerwany");
//            e.printStackTrace();
//        }
//
//
//        SimulationEngine simulationEngine2 = new SimulationEngine(resultList);
//        try {
//            simulationEngine2.runAsyncInThreadPool();
//            simulationEngine2.awaitSimulationsEnd();
//            System.out.println("Tu koniec runAsyncThreadPool");
//        }
//        catch (InterruptedException e){
//            System.out.println("Wątek przerwany");
//            e.printStackTrace();
//        }

        WorldGUI.main(args);

        System.out.println("I zakończył działanie");
    }






}