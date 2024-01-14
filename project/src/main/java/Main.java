import model.*;
import model.factories.ClassicGenomeFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SimulationParameters simulationParameters = new SimulationParameters(
                10,
                5,
                5,
                4,
                2,
                3,
                new ClassicGenomeFactory(0,2));

        WorldMap worldMap = new GlobeMap(3, 3, simulationParameters);
        try {
            Simulation simulation = new Simulation(worldMap, 3, simulationParameters);
        }
        catch (OutOfMapException e){
            System.out.println("Exception while creating simulation");
            e.printStackTrace();
        }

    }
}