package agh.ics.oop;

import agh.ics.oop.model.GrassField;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.presenter.MainPresenter;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SimulationApp extends Application{


    private final SimulationEngine simulationEngine = new SimulationEngine(new LinkedList<>());
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("main.fxml"));
        BorderPane viewRoot = loader.load();
        MainPresenter presenter = loader.getController();
        presenter.setSimulationApp(this);

//         handler of closing window
        primaryStage.setOnCloseRequest(event -> {
            try{
                simulationEngine.awaitSimulationsEnd();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Unexpected interruption");
            }
            Platform.exit();
        });

        configureStage(primaryStage, viewRoot, "Main Window");
        primaryStage.show();

    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot, String title) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    public void addStage(String[] args){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
            BorderPane newWindowRoot = loader.load();
            SimulationPresenter presenter = loader.getController();

            //preparing simulation
            List<MoveDirection> moves = OptionsParser.convert(args);
            List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,2));

            WorldMap map = new GrassField(10);
            map.addObserver(presenter);
            presenter.setWorldMap(map);

            Simulation simulation = new Simulation(positions, moves, map);
            simulationEngine.addRunAsync(simulation);

            Stage newWindowStage = new Stage();
            configureStage(newWindowStage, newWindowRoot, "Simulation Window");

            // handler closing window
            newWindowStage.setOnCloseRequest(event -> {
                newWindowStage.close();
            });

            newWindowStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error with loading");
        }
    }
}
