package oop.model.listners;

import oop.SimulationStatistics;

public interface SimulationStatisticListener{

    void simulationStatisticChanged(SimulationStatistics simulationStatistics, String message);
}
