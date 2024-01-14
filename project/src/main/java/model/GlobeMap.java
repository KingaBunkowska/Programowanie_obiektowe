package model;

public class GlobeMap extends AbstractWorldMap{

    public GlobeMap(int width, int height, SimulationParameters simulationParameters) {
        super(width, height, simulationParameters);
    }

    @Override
    public MoveGuidelines findPosition(Animal animal, Vector2d desiredPosition) {

        int goodX = desiredPosition.getX();
        int goodY = desiredPosition.getY();
        MapDirection goodOrientation = animal.getOrientation();

        // North and south border
        if (desiredPosition.getY()> getUpperRight().getY() || desiredPosition.getY() < getLowerLeft().getY()){
            goodOrientation = animal.getOrientation().opposite();
            goodY = animal.getPosition().getY();
        }
        // east border
        if (desiredPosition.getX() > getUpperRight().getX()){
            goodX = getLowerLeft().getX();
        }
        // west border
        if (desiredPosition.getX() < getLowerLeft().getX()){
            goodX = getUpperRight().getX();
        }

        return new MoveGuidelines(new Vector2d(goodX, goodY), 1, goodOrientation);
    }

}
