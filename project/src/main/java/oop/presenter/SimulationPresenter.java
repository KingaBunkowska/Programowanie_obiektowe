package oop.presenter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import oop.Simulation;
import oop.SimulationApp;
import oop.SimulationStatistics;
import oop.model.Genome;
import oop.model.Vector2d;
import oop.model.WorldMap;
import oop.model.listners.SimulationStatisticListener;

import java.util.*;

public class SimulationPresenter implements SimulationStatisticListener {

    static final int CELL_WIDTH = 30;
    static final int CELL_HEIGHT = 30;
    private Simulation simulation;
    private WorldMap worldMap;

    private Genome mostPopularGenome;
    private String[] args;
    SimulationApp simulationApp;

    @FXML
    private GridPane gridPane;
    @FXML
    private ListView<String> mostCommonGenotypes;

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
}
