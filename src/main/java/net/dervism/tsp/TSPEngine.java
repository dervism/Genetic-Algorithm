package net.dervism.tsp;

import net.dervism.genericalgorithms.*;

/**
 * Created by dervism on 03/03/14.
 */
public class TSPEngine extends GeneticAlgorithmEngine<Long, BitChromosome> implements Runnable {

    private final Population<BitChromosome> population;
    private final Evolution<BitChromosome> evolution;
    private final Encoder<Long, BitChromosome> encoder;
    private final FitnessEvaluator<BitChromosome> fitnessEvaluator;

    public TSPEngine(
            Population<BitChromosome> population,
            Evolution<BitChromosome> evolution,
            Encoder<Long, BitChromosome> encoder,
            FitnessEvaluator<BitChromosome> fitnessEvaluator) {
        super(population, evolution, encoder, fitnessEvaluator);

        this.population = population;
        this.evolution = evolution;
        this.encoder = encoder;
        this.fitnessEvaluator = fitnessEvaluator;
    }

    @Override
    public void run() {

        this.execute(1, 0, 0.85, 0.15, 100, 0.6);

    }
}
