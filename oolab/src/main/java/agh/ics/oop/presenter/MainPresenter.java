package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.GrassField;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.List;

public class MainPresenter{

    @FXML
    private Button start;
    @FXML
    private TextField textField;

    List<SimulationPresenter> simulationPresenters = new LinkedList<>();

    SimulationApp simulationApp;

    @FXML
    private void onSimulationStartClicked() {
        simulationApp.addStage(textField.getText().split(" "));

    }

    public void setSimulationApp(SimulationApp simulationApp){
        this.simulationApp = simulationApp;
    }

}
