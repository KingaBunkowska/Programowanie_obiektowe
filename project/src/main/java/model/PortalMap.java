package model;

import java.util.Random;

public class PortalMap extends AbstractWorldMap{
    Random random;

    public PortalMap(int width, int height, SimulationParameters simulationParameters){
        super(width, height, simulationParameters);
    }

    @Override
    public MoveGuidelines findPosition(Animal animal, Vector2d desiredPosition) {
        if (isOnMap(desiredPosition)){
            return new MoveGuidelines(desiredPosition, 1, animal.getOrientation());
        }

        return new MoveGuidelines(
                getRandomPosition(),
                simulationParameters.energyToBreed(),
                animal.getOrientation()
                );
    }
}
