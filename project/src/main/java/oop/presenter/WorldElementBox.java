package oop.presenter;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oop.Simulation;
import oop.model.Animal;
import oop.model.Genome;
import oop.model.MapField;
import oop.model.listners.MapFieldChangeListener;

public class WorldElementBox extends VBox implements MapFieldChangeListener {

    private final MapField mapField;
    SimulationPresenter simulationPresenter;

    private Rectangle animalRepresentation;

    private final boolean isPreferableField;

    private final int animalWidth;

    private final int animalHeight;
    private final Simulation simulation;


    public WorldElementBox(MapField mapField, SimulationPresenter simulationPresenter, Simulation simulation, int widthOfBox, int heightOfBox, boolean isPreferableField) {
        this.mapField = mapField;
        mapField.setListener(this);

        this.simulationPresenter = simulationPresenter;
        this.animalRepresentation =new Rectangle();
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(animalRepresentation);
        this.simulation = simulation;
        this.animalWidth=widthOfBox/2;
        this.animalHeight=heightOfBox/2;
        this.isPreferableField = isPreferableField;

//        this.setOnMouseClicked(mouseEvent -> {
//            if(mouseEvent.getButton() == MouseButton.PRIMARY){
//                handleMouseClicked();
//            }
//        });
    }

    public void update() {
        this.getChildren().removeAll(animalRepresentation);
        if (this.mapField.getTopAnimal().isPresent()) {
            Animal topAnimal = mapField.getTopAnimal().get();
            int animalEnergy = topAnimal.getEnergy();
            if(animalEnergy < simulation.getParameters().energyToHealth()){
                animalRepresentation= new Rectangle(animalWidth, animalHeight, Color.rgb(200,50,0));
            }
            else if(animalEnergy < 2*simulation.getParameters().energyToHealth()){
                animalRepresentation=new Rectangle(animalWidth, animalHeight, Color.rgb(150,150,0));
            }
            else{
                animalRepresentation=new Rectangle(animalWidth, animalHeight, Color.rgb(0,50,200));
            }

            this.setAlignment(Pos.CENTER);
            this.getChildren().addAll(animalRepresentation);
        } else {
            this.animalRepresentation =new Rectangle();
            this.getChildren().addAll(animalRepresentation);
            this.setAlignment(Pos.CENTER);
        }
    }

    void setBackground(){
        this.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,255), CornerRadii.EMPTY, Insets.EMPTY)));
        if(mapField.isPresentGrass())
            this.setBackground(new Background(new BackgroundFill(Color.rgb(0,150,0), CornerRadii.EMPTY, Insets.EMPTY)));

        if (isPreferableField && simulation.isPaused()){
            this.setBackground(new Background(new BackgroundFill(Color.rgb(131,255, 100), CornerRadii.EMPTY, Insets.EMPTY)));
        }
//         else if(mapField.hasTrackedAnimal)
    }

    void setPauseBackground(Genome genome){
        if (mapField.hasAnimalWithGenome(genome)){
            this.setBackground(new Background(new BackgroundFill(Color.rgb(150,0, 200), CornerRadii.EMPTY, Insets.EMPTY)));

        }
        else if (isPreferableField){
            this.setBackground(new Background(new BackgroundFill(Color.rgb(131,255, 100), CornerRadii.EMPTY, Insets.EMPTY)));
        }

        else{
            setBackground();
        }
    }

    @Override
    public void mapFieldChanged(String message) {
        Platform.runLater(() -> {
            update();
//            System.out.println(mapField.getPosition() + " " + message);
            setBackground();
        });
    }



//    private void handleMouseClicked(){
//        if (!simulation.isPaused()) return;
//        if(mapField.getTopAnimal().isEmpty()) return;
//
//        simulationPresenter.setTracking(true);
//        simulationPresenter.animalStatsShow.setVisible(true);
//        Collections.sort(mapField.animals);
//        Animal animal = mapField.animals.get(mapField.animals.size()-1);
//        simulationPresenter.setAnimalTracking(animal);
//        simulationPresenter.animalStatistics();
//        simulationPresenter.markTrackedAnimal(this);
//    }
}

