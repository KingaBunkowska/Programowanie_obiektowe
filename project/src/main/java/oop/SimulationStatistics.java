package oop;

import oop.model.Genome;
import oop.model.listners.SimulationStatisticListener;
import oop.presenter.SimulationPresenter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SimulationStatistics {

    private final HashMap<Genome, Integer> genomePopularity = new HashMap<>();
    private final List<SimulationStatisticListener> listeners = new LinkedList<>();

    private final Simulation simulation;

    private int numberOfAnimals;
    private int numberOfGrasses;
    private int numberOfEmptySpaces;
    private int sumEnergyOfLiving;
    private int sumDaysLivedByDead;
    private int sumChildrenOfLiving;

    public SimulationStatistics(Simulation simulation) {
        this.simulation = simulation;
    }


    public void addGenome(Genome genome){
        genomePopularity.compute(genome, (key, value) -> (value == null) ? 1 : value + 1);
        for (SimulationStatisticListener listener : listeners){
            listener.simulationStatisticChanged(this, "Added value to genome popularity");
        }
    }

    public void removeGenome(Genome genome){
        genomePopularity.compute(genome, (key, value) -> (value == null || value == 1) ? null : value - 1);
        for (SimulationStatisticListener listener : listeners){
            listener.simulationStatisticChanged(this, "Removed value from genome popularity");
        }
    }

    public void setListener(SimulationPresenter listener) {
        listeners.add(listener);
        listener.simulationStatisticChanged(this, "Initialization");
    }


    public HashMap<Genome, Integer> getGenomePopularity() {
        return genomePopularity;
    }

    void setNumberOfAnimals(int numberOfAnimals) {
        this.numberOfAnimals = numberOfAnimals;
        updateListeners("number of animals changed");
    }

    void setNumberOfGrasses(int numberOfGrasses) {
        this.numberOfGrasses = numberOfGrasses;
        updateListeners("number of grasses changed");
    }

    void setNumberOfEmptyFields(int numberOfEmptySpaces){
        this.numberOfEmptySpaces = numberOfEmptySpaces;
        updateListeners("number of empty fields changed");
    }

    void changeEnergyOfLiving(int valueOfChange){
        this.sumEnergyOfLiving += valueOfChange;
        updateListeners("mean energy changed");
    }

    void changeSumDaysLivedByDead(int valueOfChange){
        this.sumDaysLivedByDead += valueOfChange;
        updateListeners("mean length of life changed");
    }

    void changeSumChildrenOfLiving(int valueOfChange){
        this.sumChildrenOfLiving += valueOfChange;
        updateListeners("mean number of children changed");
    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public int getNumberOfGrasses(){
        return numberOfGrasses;
    }

    public int getNumberOfEmptySpaces() {
        return numberOfEmptySpaces;
    }

    public float getAverageChildrenOfLiving() {
        return (float) sumChildrenOfLiving/numberOfAnimals;
    }

    public float getAverageEnergyOfLiving() {
        return (float) sumEnergyOfLiving/numberOfAnimals;
    }

    public float getAverageDaysLivedByDead() {
        return (float) sumDaysLivedByDead/simulation.getDeadAnimals().size();
    }

    private void updateListeners(String message){
        for (SimulationStatisticListener listener : listeners){
            listener.simulationStatisticChanged(this, message);
        }
    }
}
