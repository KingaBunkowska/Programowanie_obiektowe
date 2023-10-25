package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser{

    public MoveDirection[] convert(String[] args){

        int validArgumentsNo = 0;

        for (String arg : args) {
            if (arg.equals("f") || arg.equals("l") || arg.equals("r") || arg.equals("b")) {
                validArgumentsNo++;
            }
        }

        if (validArgumentsNo==0)
            return null;

        MoveDirection[] moveDirections = new MoveDirection[validArgumentsNo];
        int curr_index = 0;

        for(int i = 0; i<args.length; i++){
            switch(args[i]){
                case "f" -> moveDirections[curr_index] = MoveDirection.FORWARD;
                case "b" -> moveDirections[curr_index] = MoveDirection.BACKWARD;
                case "r" -> moveDirections[curr_index] = MoveDirection.RIGHT;
                case "l" -> moveDirections[curr_index] = MoveDirection.LEFT;
                default -> curr_index = Math.max(curr_index-1, 0);
            }
            curr_index++;
        }

        return moveDirections;
    }

}
