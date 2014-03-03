package net.dervism.genericalgorithms;

import java.util.List;

/**
 * Engine that can run a GA on any kind of implementation of the Chromosome class.
 *
 * Created by dervism on 03/03/14.
 *
 * @param <T> The datatype that is encoded.
 * @param <C> The Chromosome implementation to use.
 */
public class GeneticAlgorithmEngine<T, C extends Chromosome> {

    protected Population<C> population;

    protected Evolution<C> evolution;

    protected Encoder<T, C> encoder;

    public GeneticAlgorithmEngine(Population<C> population, Evolution<C> evolution, Encoder<T, C> encoder) {
        this.population = population;
        this.evolution = evolution;
        this.encoder = encoder;
    }

    /**
     * Executes the GA for one minute.
     *
     * @param crossoverRate
     * @param mutationRate
     * @return
     */
    public C execute(double crossoverRate, double mutationRate) {
        return execute(1, 0, crossoverRate, mutationRate);
    }

    /**
     * Executes the GA with a seconds timer.
     *
     * @param seconds
     * @param crossoverRate
     * @param mutationRate
     * @return
     */
    public C execute(int seconds, double crossoverRate, double mutationRate) {
        return execute(0, seconds, crossoverRate, mutationRate);
    }

    /**
     * Standard implementation of a GA algorithm. Recommended rate parameters
     * are 0.85 for the crossover rate and 0.05-0.1 for the mutation rate.
     * You should experiment with the parameters to find the combination that
     * gives the best results.
     *
     * @param minutes
     * @param seconds
     * @param crossoverRate
     * @param mutationRate
     * @return
     */
    public C execute(int minutes, int seconds, double crossoverRate, double mutationRate) {
        // initiate the population
        List<C> list = population.createPopulation(100);
        population.sortByFitness();

        // the initial best chromosome
        int best = population.calculateFitness(population.getBest());
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
                C[] parents = population.selectParents(population.size());
                population.add(evolution.mutate(parents[0]));
                population.add(evolution.mutate(parents[1]));
            }

            // select the best chromosomes from this generation
            population.selectBest( ((crossovers * 2) + (mutations * 2)) );

            int bestFromGeneration = population.calculateFitness(population.getBest());
            if (bestFromGeneration < best) {
                best = bestFromGeneration;
                System.out.println("Found better route in generation " + generation + " with score " + best
                        + ", population size " + population.size());
            }

            generation++;
        }

        return population.getBest();
    }

}
