package oop.presenter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import oop.SimulationApp;
import oop.model.GlobeMap;
import oop.model.PortalMap;
import oop.model.SimulationParameters;
import oop.model.WorldMap;
import oop.model.factories.ClassicGenomeFactory;
import oop.model.factories.GenomeFactory;
import oop.model.factories.TwoWaysGenomeFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MainPresenter{

    @FXML
    private Button start;
    @FXML
    private TextField textField;

    @FXML
    private Spinner<Integer> widthSpinner;
    @FXML
    private Spinner<Integer> heightSpinner;
    @FXML
    private Spinner<Integer> startGrassSpinner;
    @FXML
    private Spinner<Integer> grassGrowingSpinner;
    @FXML
    private Spinner<Integer> energyOfGrassSpinner;
    @FXML
    private Spinner<Integer> startAnimalSpinner;
    @FXML
    private Spinner<Integer> startAnimalEnergySpinner;
    @FXML
    private Spinner<Integer> energyToHealthSpinner;
    @FXML
    private Spinner<Integer> energyToBreedSpinner;

    @FXML
    private Spinner<Integer> numberOfGenesSpinner;

    @FXML
    private ChoiceBox<String> genomeChoiceBox;
    @FXML
    private ChoiceBox<String> mapChooseBox;
    @FXML
    private Spinner<Integer> minimalMutationsSpinner;
    @FXML
    private Spinner<Integer> maximalMutationsSpinner;
    List<SimulationPresenter> simulationPresenters = new LinkedList<>();

    SimulationApp simulationApp;

    private class SpinnerChangeListenerSizeLimitation implements ChangeListener<Integer> {
        @Override
        public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
            int width = widthSpinner.getValue();
            int height = heightSpinner.getValue();
            SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryStartGrass = (SpinnerValueFactory.IntegerSpinnerValueFactory) startGrassSpinner.getValueFactory();
            valueFactoryStartGrass.setMax(width*height);

            SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryGrowGrass = (SpinnerValueFactory.IntegerSpinnerValueFactory) grassGrowingSpinner.getValueFactory();
            valueFactoryGrowGrass.setMax(width*height);


        }
    }

    private class SpinnerChangeHigherLimitation implements ChangeListener<Integer>{

        private final Spinner<Integer> higherSpinner;
        private final Spinner<Integer> lowerSpinner;
        private int difference;

        public SpinnerChangeHigherLimitation(Spinner<Integer> higher,
                                             Spinner<Integer> lower,
                                             int difference){
            super();
            this.higherSpinner = higher;
            this.lowerSpinner = lower;
            this.difference = difference;
        }

        @Override
        public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
            int highest = higherSpinner.getValue();
            SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = (SpinnerValueFactory.IntegerSpinnerValueFactory) lowerSpinner.getValueFactory();
            valueFactory.setMax(highest-difference);
        }
    }

    public void initialize() {
        genomeChoiceBox.setValue("Classic Genome");
        mapChooseBox.setValue("Globe Map");

        widthSpinner.valueProperty().addListener(new SpinnerChangeListenerSizeLimitation());
        heightSpinner.valueProperty().addListener(new SpinnerChangeListenerSizeLimitation());

        energyToHealthSpinner.valueProperty().addListener(new SpinnerChangeHigherLimitation(energyToHealthSpinner, energyToBreedSpinner, 1));

        maximalMutationsSpinner.valueProperty().addListener(new SpinnerChangeHigherLimitation(maximalMutationsSpinner, minimalMutationsSpinner, 0));

        numberOfGenesSpinner.valueProperty().addListener(new SpinnerChangeHigherLimitation(numberOfGenesSpinner, maximalMutationsSpinner, 0));
    }

    @FXML
    private void onSimulationStartClicked() {
        int startGrass = startGrassSpinner.getValue();

        int startAnimals = startAnimalSpinner.getValue();


        GenomeFactory genomeFactory = makeGenomeFactory();
        SimulationParameters simulationParameters = makeSimulationParameters(genomeFactory);
        WorldMap map = makeWorldMap(simulationParameters);

        simulationApp.addStage(map, simulationParameters, startAnimals, startGrass);

    }

    private SimulationParameters makeSimulationParameters(GenomeFactory genomeFactory){

        int startAnimalEnergy = startAnimalEnergySpinner.getValue();
        int energyToHealth = energyToHealthSpinner.getValue();
        int energyToBreed = energyToBreedSpinner.getValue();
        int numberOfGenes = numberOfGenesSpinner.getValue();
        int grassGrowing = grassGrowingSpinner.getValue();
        int energyOfGrass = energyOfGrassSpinner.getValue();

        return new SimulationParameters(
                energyOfGrass,
                grassGrowing,
                startAnimalEnergy,
                energyToHealth,
                energyToBreed,
                numberOfGenes,
                genomeFactory
        );
    }
    private GenomeFactory makeGenomeFactory() {
        String genomeName = genomeChoiceBox.getValue();

        int minimalMutations = minimalMutationsSpinner.getValue();
        int maximalMutations = maximalMutationsSpinner.getValue();

        GenomeFactory genomeFactory = null;
        if (Objects.equals(genomeName, "Classic Genome")){
            genomeFactory = new ClassicGenomeFactory(minimalMutations, maximalMutations);
        }
        else{
            genomeFactory = new TwoWaysGenomeFactory(minimalMutations, maximalMutations);
        }
        return genomeFactory;
    }
    private WorldMap makeWorldMap(SimulationParameters simulationParameters){
        String mapName = mapChooseBox.getValue();

        int width = widthSpinner.getValue();
        int height = heightSpinner.getValue();

        WorldMap map = null;
        if (Objects.equals(mapName, "Globe Map")){
            map = new GlobeMap(width, height, simulationParameters);
        }
        else{
            map = new PortalMap(width, height, simulationParameters);
        }
        return map;
    }
    public void setSimulationApp(SimulationApp simulationApp){
        this.simulationApp = simulationApp;
    }

}
