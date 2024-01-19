package oop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oop.model.Genome;
import oop.model.listners.SimulationStatisticListener;
import oop.presenter.SimulationPresenter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimulationStatistics {

    private final HashMap<Genome, Integer> genomePopularity = new HashMap<>();
    private final List<SimulationStatisticListener> listeners = new LinkedList<>();

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
}
