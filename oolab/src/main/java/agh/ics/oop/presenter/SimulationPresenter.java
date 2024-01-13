package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.SimpleTimeZone;

public class SimulationPresenter implements MapChangeListener {

    static final int CELL_WIDTH = 30;
    static final int CELL_HEIGHT = 30;
    private WorldMap worldMap;
    private SimulationEngine simulationEngine;

    @FXML
    private Button start;
    @FXML
    private Label moveLabel;
    @FXML
    private TextField textField;
    @FXML
    private GridPane gridPane;

    public void drawMap() {
        clearGrid();

        int width = worldMap.getCurrentBounds().upperRight().getX() - worldMap.getCurrentBounds().lowerLeft().getX();
        int height = worldMap.getCurrentBounds().upperRight().getY() - worldMap.getCurrentBounds().lowerLeft().getY();
        int curr_row = worldMap.getCurrentBounds().upperRight().getY();
        int curr_col = worldMap.getCurrentBounds().lowerLeft().getX();

        // init of grid

        Label label = new Label("y\\x");
        GridPane.setHalignment(label, HPos.CENTER);
        gridPane.add(label, 0, 0);

        for (int i=0; i< height + 2; i++){
            gridPane.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }
        for (int i=0; i<width+2; i++){
            gridPane.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }

        for (int row = 1; row < height + 2; row++){

            label = new Label(curr_row+"");
            curr_row -= 1;
            gridPane.add(label, 0, row);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int col = 1; col < width + 2; col++){
            label = new Label(curr_col+"");
            curr_col += 1;
            gridPane.add(label, col, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (WorldElement worldElement : worldMap.getElements()){
            label = new Label(worldElement.toString());
            gridPane.add(label, 1 + worldElement.getPosition().getX() - worldMap.getCurrentBounds().lowerLeft().getX(), worldMap.getCurrentBounds().upperRight().getY() + 1 - worldElement.getPosition().getY());
            GridPane.setHalignment(label, HPos.CENTER);
        }

    }

    private void clearGrid(){
        gridPane.getChildren().retainAll(gridPane.getChildren().get(0));
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
    }
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap();
            moveLabel.setText(message);
        });
    }

    @FXML
    private void onSimulationStartClicked() {

        List<MoveDirection> moves = OptionsParser.convert(textField.getText().split(" "));
        List<Vector2d> positions = List.of(new Vector2d(0,0), new Vector2d(2,2));

        WorldMap map = new GrassField(10);
        map.addObserver(this);
        this.worldMap = map;

        List<Simulation> simulations = List.of(new Simulation(positions, moves, map));
        simulationEngine =new SimulationEngine(simulations);
        simulationEngine.runAsyncInThreadPool();
    }

    public void shutdown() throws InterruptedException {
            simulationEngine.awaitSimulationsEnd();
    }
}
