package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser{

    public MoveDirection[] convert(String[] args){
        MoveDirection[] moveDirections = new MoveDirection[args.length];
        for(int i = 0; i< args.length; i++){

            switch(args[i]){
                case "f" -> moveDirections[i] = MoveDirection.FORWARD;
                case "b" -> moveDirections[i] = MoveDirection.BACKWARD;
                case "r" -> moveDirections[i] = MoveDirection.RIGHT;
                case "l" -> moveDirections[i] = MoveDirection.LEFT;
            }
        }

        return moveDirections;
    }

}
