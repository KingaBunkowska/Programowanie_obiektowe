package model;

import javax.management.InvalidAttributeValueException;
import java.util.Random;

public class Couple {

    private final Animal parent1;
    private final Animal parent2;
    private final Random random = new Random();

    public Couple(Animal animal1, Animal animal2){
        this.parent1 = animal1;
        this.parent2 = animal2;
    }

    public Animal getParent1() {
        return parent1;
    }

    public Animal getParent2(){
        return parent2;
    }

    public Animal makeChild(SimulationParameters simulationParameters){

        parent1.breed();
        parent2.breed();

        if (random.nextBoolean()){
            int parentRightGenes = (int) parent1.getEnergy() / (parent1.getEnergy() + parent2.getEnergy());

            return new Animal(parent1.getPosition(), parent1.getGenome().getLeftPart(parentRightGenes), parent2.getGenome().getRightPart(parentRightGenes), 2*simulationParameters.energyToBreed(), simulationParameters);
        }
        else{
            int parentRightGenes = (int) parent2.getEnergy() / (parent1.getEnergy() + parent2.getEnergy());

            return new Animal(parent1.getPosition(), parent2.getGenome().getLeftPart(parentRightGenes), parent1.getGenome().getRightPart(parentRightGenes), 2*simulationParameters.energyToBreed(), simulationParameters);
        }
    }
}
