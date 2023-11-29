package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.MapVisualizer;

import javax.swing.*;
import java.util.List;

public class World {

    public static void main(String[] args){
        System.out.println("System wystartował");
        OptionsParser optionsParser = new OptionsParser();
        List<MoveDirection> moveDirections= optionsParser.convert(args);
        run(moveDirections);
        System.out.println("System zakończył działanie\nJednak nie");
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
        System.out.println("I teraz zakończył działanie na serio\nZnowu nie");

        System.out.println(MapDirection.NORTH.toUnitVector().toString());

        Animal animal = new Animal();
        Animal animal2 = new Animal(new Vector2d(9,-4));
        System.out.println(animal.toString());
        System.out.println(animal2.toString());

        List<MoveDirection> directions = optionsParser.convert(args);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3, 4));
        AbstractWorldMap map = new RectangularMap(5, 5);
        ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();
        map.addObserver(consoleMapDisplay);

        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();

        GrassField grassField = new GrassField(10);
        grassField.addObserver(consoleMapDisplay);
        grassField.move(animal, MoveDirection.FORWARD);

        try{
            grassField.place(animal);
        }
        catch(PositionAlreadyOccupiedException e){
            System.out.println("Position occupied, so animal was not placed");
        }

        try{
            grassField.place(animal2);
        }
        catch(PositionAlreadyOccupiedException e){
            System.out.println("Position occupied, so animal2 was not placed");
        }

        System.out.println("I teraz tak :)");



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