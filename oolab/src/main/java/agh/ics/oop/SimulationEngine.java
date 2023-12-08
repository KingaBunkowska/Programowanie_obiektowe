package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<Simulation> simulations;
    List<Thread> threads = new LinkedList<Thread>();
    ExecutorService executorService;

    public SimulationEngine(List<Simulation> simulations){
        this.simulations = simulations;
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public void runSync(){
        for (Simulation simulation : this.simulations){
            simulation.run();
        }
    }

    public void runAsync(){
        for (Simulation simulation : simulations){
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }
    }

    public void awaitSimulationsEnd() throws InterruptedException{
        for(Thread thread : threads){
            thread.join();
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }

    public void runAsyncInThreadPool() throws InterruptedException {
        for (Simulation simulation : simulations){
            executorService.submit(simulation);
        }
    }
}