package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private List<Simulation> simulations;

    public SimulationEngine(List<Simulation> simulations){
        this.simulations = simulations;
    }

    public void runSync(){
        for (Simulation simulation : this.simulations){
            simulation.run();
        }
    }

    public void runAsync(){
        List<Thread> threads = new LinkedList<Thread>();
        for (Simulation simulation : simulations){
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }

        awaitSimulationsEnd(threads);
    }

    public void awaitSimulationsEnd(List<Thread> threads){
        for(Thread thread : threads){
            try {
                thread.join();
            }
            catch (InterruptedException e){
                System.out.println("Thread was interrupted!");
            }
        }
    }

    public void runAsyncInThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (Simulation simulation : simulations){
            executorService.submit(simulation);
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
}
