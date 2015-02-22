package net.dervism.genericalgorithms;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Genetic algorithm engine that can run on any kind of implementation of the Chromosome class.
 *
 * Created by dervism on 02/21/15.
 *
 * @param <T> The datatype that is encoded.
 * @param <C> The Chromosome implementation to use.
 */
public class GeneticAlgorithmEngine<T, C extends Chromosome> {

    protected Population<C> population;

    protected Evolution<C> evolution;

    protected Encoder<T, C> encoder;

    protected FitnessEvaluator<C> fitnessEvaluator;

    public GeneticAlgorithmEngine(
            Population<C> population, Evolution<C> evolution, Encoder<T, C> encoder, FitnessEvaluator<C> fitnessEvaluator) {
        this.population = population;
        this.evolution = evolution;
        this.encoder = encoder;
        this.fitnessEvaluator = fitnessEvaluator;
    }

    /**
     * Executes the GA for one minute.
     *
     * @param crossoverRate
     * @param mutationRate
     * @return
     */
    public C execute(double crossoverRate, double mutationRate) {
        return execute(1, 0, crossoverRate, mutationRate, 100, 0.6);
    }

    /**
     * Executes the GA with a seconds timer.
     *
     * @param seconds
     * @param crossoverRate
     * @param mutationRate
     * @return
     */
    public C execute(int seconds, double crossoverRate, double mutationRate, int populationSize, double reductionPercent) {
        return execute(0, seconds, crossoverRate, mutationRate, populationSize, reductionPercent);
    }

    /**
     * Standard implementation of a genetic algorithm that countinuesly increases
     * population size to avoid convergence to local minima. Recommended rate
     * parameters are 0.85 for the crossover rate and 0.05-0.1 for the mutation
     * rate. You should experiment with the parameters to find the combination
     * that gives the best results.
     *
     * @param minutes
     * @param seconds
     * @param crossoverRate
     * @param mutationRate
     * @return
     */
    public C execute(int minutes, int seconds, double crossoverRate, double mutationRate,
                     int populationSize, double reductionPercent) {
        // initiate the population
        population.createPopulation(populationSize);
        fitnessEvaluator.sort(population);

        // the initial best chromosome
        int best = fitnessEvaluator.evalute(population);
        int generation = 1;

        // timer
        long start = System.currentTimeMillis();
        long end = start + (minutes * 60000) + (seconds * 1000);

        while(System.currentTimeMillis() < end) {

            // do crossover on the 'crossoverRate' % of the population
            int crossovers = (int) (population.size() * crossoverRate);
            for (int i = 0; i < crossovers; i++) {
                C[] parents = population.selectParents(0.5);
                C child1 = evolution.crossover(parents[0], parents[1]);
                C child2 = evolution.crossover(parents[1], parents[0]);
                population.add(child1); population.add(child2);
            }

            // do mutations on the 'mutationRate' % of the population
            int mutations = (int) (population.size() * mutationRate);
            for (int i = 0; i < mutations; i++) {
                C[] parents = population.selectParents(0.3);
                population.add(evolution.mutate(parents[0]));
                population.add(evolution.mutate(parents[1]));
            }

            // select the best chromosomes from this generation
            fitnessEvaluator.sort(population);
            population.selectBest(reductionPercent);

            int bestFromGeneration = fitnessEvaluator.evalute(population);
            if (bestFromGeneration < best) {
                best = bestFromGeneration;
                System.out.println("Found better route in generation " + generation + " with score " + best
                        + ", population size " + population.size());
            }

            generation++;
        }

        return fitnessEvaluator.getBest(population);
    }

}
