package model;

import model.factories.GenomeFactory;

public record SimulationParameters
        (int energyOfGrass,
         int numberOfGrassesGrowing,
         int startEnergy,
         int energyToHealth,
         int energyToBreed,
         int numberOfGenes,
         GenomeFactory factory) {}
