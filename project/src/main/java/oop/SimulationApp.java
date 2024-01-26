package oop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oop.model.OutOfMapException;
import oop.model.SimulationParameters;
import oop.model.WorldMap;
import oop.presenter.MainPresenter;
import oop.presenter.SimulationPresenter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class SimulationApp extends Application{
    private List<Thread> threads = new LinkedList<>();
    @Override
    public void start(Stage primaryStage) throws Exception { // ?

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("main.fxml"));
        BorderPane viewRoot = loader.load();
        MainPresenter presenter = loader.getController();
        presenter.setSimulationApp(this);

//         handler of closing window
        primaryStage.setOnCloseRequest(event -> {
            for (Thread thread : threads){
                thread.interrupt();
            }
            Platform.exit();
        });

        configureStage(primaryStage, viewRoot, "oop.Main Window");
        primaryStage.show();

    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot, String title) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    public void addStage(WorldMap map, SimulationParameters simulationParameters, int startAnimals, int startGrasses) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
            BorderPane newWindowRoot = loader.load();
            SimulationPresenter presenter = loader.getController();

            Simulation simulation = new Simulation(map, startAnimals, startGrasses, simulationParameters);
            presenter.setMap(map);
            presenter.setSimulation(simulation);

            presenter.initializeMap();

            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();



            Stage newWindowStage = new Stage();
            configureStage(newWindowStage, newWindowRoot, "oop.Simulation Window");

            // handler closing window
            newWindowStage.setOnCloseRequest(event -> {
                thread.interrupt();
                newWindowStage.close();
            });

            newWindowStage.show();
        } catch (IOException e) {
            System.out.println("Error with loading");
            e.printStackTrace();
        } catch (OutOfMapException e) {
            throw new RuntimeException(e); // skoro Pani opakowuje ten wyjątek, to może OutOfMap powinien dziedziczyć z Runtime?
        }
    }
}
