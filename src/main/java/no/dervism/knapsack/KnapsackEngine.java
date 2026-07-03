package no.dervism.knapsack;

import no.dervism.genericalgorithms.BitChromosome;
import no.dervism.genericalgorithms.GeneticAlgorithmEngine;

/**
 * Created by dervism on 19/02/14.
 */
public class KnapsackEngine extends GeneticAlgorithmEngine<Integer, BitChromosome> implements Runnable {

    private final KnapsackPopulation population;
    private final KnapsackEvolution evolution;
    private final KnapsackEncoder encoder;
    private final KnapsackFitnessEvaluator fitnessEvaluator;

    public KnapsackEngine(KnapsackPopulation population, KnapsackEvolution evolution, KnapsackEncoder encoder, KnapsackFitnessEvaluator fitnessEvaluator) {
        super(population, evolution, encoder, fitnessEvaluator);
        this.population = population;
        this.evolution = evolution;
        this.encoder = encoder;
        this.fitnessEvaluator = fitnessEvaluator;
    }

    @Override
    public void run() {
        BitChromosome best = this.execute(1, 0, 0.85, 0.15, 100, 0.6);

        int value = fitnessEvaluator.calcValue(best);
        int space = fitnessEvaluator.calcSpace(best);
        int items = encoder.itemsInside(best);

        IO.println("Best knapsack value: " + value
                + ", space used: " + space + "/" + fitnessEvaluator.knapsackSpace
                + ", items: " + items);
    }
}
