package agh.ics.oop;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

public class World {

    public static void main(String[] args){
        System.out.println("System wystartował");
        OptionsParser optionsParser = new OptionsParser();
        MoveDirection[] moveDirections = optionsParser.convert(args);
        run(moveDirections);
        System.out.println("System zakończył działanie\nJednak nie");
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
        System.out.println("I teraz zakończył działanie na serio\nZnowu nie");

        System.out.println(MapDirection.NORTH.toUnitVector().toString());
        System.out.println("I teraz tak :)");



    }


    public static void run(MoveDirection[] move_directions){
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