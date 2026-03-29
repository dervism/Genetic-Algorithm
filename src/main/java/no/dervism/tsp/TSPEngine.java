package no.dervism.tsp;

import no.dervism.genericalgorithms.BitChromosome;
import no.dervism.genericalgorithms.GeneticAlgorithmEngine;

import java.util.Arrays;

/**
 * Created by dervism on 03/03/14.
 */
public class TSPEngine extends GeneticAlgorithmEngine<Long, BitChromosome> implements Runnable {

    private final TSPPopulation population;
    private final TSPEvolution evolution;
    private final TSPEncoder encoder;
    private final TSPFitnessEvaluator fitnessEvaluator;

    public TSPEngine(TSPPopulation population, TSPEvolution evolution, TSPEncoder encoder, TSPFitnessEvaluator fitnessEvaluator) {
        super(population, evolution, encoder, fitnessEvaluator);
        this.population = population;
        this.evolution = evolution;
        this.encoder = encoder;
        this.fitnessEvaluator = fitnessEvaluator;
    }

    @Override
    public void run() {

        BitChromosome bestRoute = this.execute(1, 0, 0.85, 0.15, 100, 0.6);
        long[] cities = encoder.toArray(bestRoute);
        IO.println(Arrays.toString(cities));

    }
}
