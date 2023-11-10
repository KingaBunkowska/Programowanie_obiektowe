package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.List;

public class World {

    public static void main(String[] args){
        System.out.println("System wystartował");
        OptionsParser optionsParser = new OptionsParser();
        List<MoveDirection> moveDirections= optionsParser.convert(args);
        System.out.println("TU");
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
        Simulation simulation = new Simulation(positions, directions);
        simulation.run();

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