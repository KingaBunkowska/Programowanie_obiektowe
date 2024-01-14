package model;

import java.util.Random;

public class PortalMap extends AbstractWorldMap{

    int energyToBreed = 5;
    Random random;
    public PortalMap(int width, int height, int seed){
        super(width, height);
        this.random = new Random(seed);
    }

    public PortalMap(int width, int height){
        super(width, height);
        this.random = new Random();
    }

    @Override
    public MoveGuidelines findPosition(Animal animal, Vector2d desiredPosition) {
        if (isOnMap(desiredPosition)){
            return new MoveGuidelines(desiredPosition, 1, animal.getOrientation());
        }

        return new MoveGuidelines(
                new Vector2d(
                        random.nextInt(getLowerLeft().getX(), getUpperRight().getX()),
                        random.nextInt(getLowerLeft().getY(), getUpperRight().getY())),
                energyToBreed,
                animal.getOrientation()
                );
    }
}
