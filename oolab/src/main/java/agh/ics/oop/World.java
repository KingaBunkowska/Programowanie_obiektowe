package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class World {

    public static void main(String[] args){
        System.out.println("System wystartował");
        OptionsParser optionsParser = new OptionsParser();
        MoveDirection[] moveDirections = optionsParser.convert(args);
        run(moveDirections);
        System.out.println("System zakończył działanie");
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