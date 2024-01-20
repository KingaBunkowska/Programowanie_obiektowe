package oop.presenter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import oop.Simulation;
import oop.SimulationApp;
import oop.SimulationStatistics;
import oop.model.Animal;
import oop.model.Genome;
import oop.model.Vector2d;
import oop.model.WorldMap;
import oop.model.listners.AnimalListener;
import oop.model.listners.SimulationStatisticListener;

import java.util.*;

public class SimulationPresenter implements SimulationStatisticListener, AnimalListener {

    static final int CELL_WIDTH = 30;
    static final int CELL_HEIGHT = 30;
    private Simulation simulation;
    private WorldMap worldMap;
    private Genome mostPopularGenome;
    private SimulationApp simulationApp;
    private Optional<Animal> followedAnimal = Optional.empty();

    @FXML
    private GridPane gridPane;
    @FXML
    private ListView<String> mostCommonGenotypes;
    @FXML
    private ListView<String> simulationStatisticsListView;
    @FXML
    private Button unfollowAnimalButton;

    private final List<WorldElementBox> worldElementBoxes = new LinkedList<>();
    public void setMap(WorldMap map) {
        this.worldMap = map;
    }



    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
        simulation.setListenerForStatistic(this);
    }

    public void initializeMap() {
        clearGrid();

        int columns = worldMap.getUpperRight().getX() - worldMap.getLowerLeft().getX() + 1;
        int rows = worldMap.getUpperRight().getY() - worldMap.getLowerLeft().getY() + 1;

        for(int row = 0; row<rows; row++)
            gridPane.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));

        for(int column=0; column<columns; column++)
            gridPane.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));

        for (int row = 0; row < columns; row++){
            for (int column = 0; column < rows; column++){
                Vector2d position = new Vector2d(row, column);
                WorldElementBox worldElementBox = new WorldElementBox(
                        worldMap.getMapField(position),
                        this,
                        simulation,
                        CELL_WIDTH,
                        CELL_HEIGHT,
                        position.precedes(worldMap.getPreferableUpperRight()) && position.follows(worldMap.getPreferableLowerLeft())
                );

                worldElementBoxes.add(worldElementBox);

                gridPane.add(
                    worldElementBox,
                    row,
                    column
                );
            }
        }
    }

    public void forceUpdateWorldElementBoxes(){
        for (WorldElementBox worldElementBox : worldElementBoxes){
            worldElementBox.update();
        }
    }

    @FXML
    private void onSimulationPauseClicked() {
        simulation.changePause();
        forceUpdateWorldElementBoxes();
    }

    @FXML
    private void onUnfollowAnimalClicked(){
        this.followedAnimal.ifPresent(Animal::removeListener);
        this.followedAnimal = Optional.empty();
        this.unfollowAnimalButton.opacityProperty().set(0.3);
        forceUpdateWorldElementBoxes();

    }
    public Genome getMostPopularGenome() {
        return mostPopularGenome;
    }

    private void clearGrid(){
        gridPane.getChildren().retainAll(gridPane.getChildren().get(0));
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
    }

    @Override
    public void simulationStatisticChanged(SimulationStatistics simulationStatistics, String message) {
        Map<Genome, Integer> genomePopularity = simulationStatistics.getGenomePopularity();
        showGenotypePopularity(genomePopularity);

        ObservableList<String> statistics = FXCollections.observableArrayList(
                "Number of animals: \t\t" + simulationStatistics.getNumberOfAnimals(),
                "Number of grasses: \t\t" + simulationStatistics.getNumberOfGrasses(),
                "Number of empty fields: \t\t" + simulationStatistics.getNumberOfEmptySpaces(),
                "Mean energy of animals: \t" + String.format("%.2f", simulationStatistics.getAverageEnergyOfLiving()),
                "Mean number of children: \t" + String.format("%.2f",simulationStatistics.getAverageChildrenOfLiving()),
                "Mean length of life: \t\t\t" + String.format("%.2f",simulationStatistics.getAverageDaysLivedByDead())
        );

        Platform.runLater(() -> {
            simulationStatisticsListView.setItems(statistics);
        });



    }

    private void showGenotypePopularity(Map<Genome, Integer> genomePopularity){

        List<Map.Entry<Genome, Integer>> listOfMostPopularGenomes = genomePopularity.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .toList();

        List<Map.Entry<String, Integer>> listOfMostPopularsGenes = listOfMostPopularGenomes.stream()
                .map(entry -> Map.entry(entry.getKey().toString(), entry.getValue()))
                .toList();

        ObservableList<String> genotypes = FXCollections.observableArrayList();;

        for (Map.Entry<String, Integer> genotypeAndValue : listOfMostPopularsGenes){
            genotypes.add(genotypeAndValue.getKey() + ": " + genotypeAndValue.getValue());
        }

        mostPopularGenome = listOfMostPopularGenomes.get(0).getKey();

        Platform.runLater(() -> {
            mostCommonGenotypes.setItems(genotypes);
        });
    }

    protected void setFollowedAnimal(Animal animal){
        followedAnimal.ifPresent(Animal::removeListener);
        this.followedAnimal = Optional.of(animal);
        followedAnimal.get().addListener(this);
        this.unfollowAnimalButton.opacityProperty().set(1);
    }

    protected Optional<Animal> getFollowedAnimal(){
        return this.followedAnimal;
    }

    @Override
    public void animalStatisticChanged(String message) {

    }
}
