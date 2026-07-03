package no.dervism.tsp;

import no.dervism.genericalgorithms.BitChromosome;
import no.dervism.genericalgorithms.GeneticAlgorithmEngine;
import org.uncommons.maths.combinatorics.PermutationGenerator;

import java.util.Arrays;
import java.util.List;

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

    /**
     * Brute-Force algorithm to find the best solution. However,
     * for the problem of TSP with 16 cities and 1,307,674,368,000 possible
     * solutions this process will take nearly two days to complete.
     * This implementation is based on the WatchMaker code
     * (http://watchmaker.uncommons.org/).
     *
     * @return the best route found
     */
    public int[] executeBruteForceTSP() {
        int startCity = 0;
        Integer[] cities = new Integer[15];
        Integer[] cities2 = {12, 10, 8, 7, 6, 9, 14, 2, 13, 11, 3, 5, 4, 15, 1};
        for (int i = 1; i <= cities.length; i++) {
            cities[i-1] = Integer.valueOf(i);
        }
        IO.println(Arrays.toString(cities2));

        PermutationGenerator<Integer> pg = new PermutationGenerator<>(cities2);
        long totalPermutations = pg.getTotalPermutations();
        IO.println("Total permutations: " + totalPermutations);

        int best = Integer.MAX_VALUE;
        int[] bestRoute = null;
        long count = 0;

        while (pg.hasMore()) {
            List<Integer> route = pg.nextPermutationAsList();
            route.addFirst(startCity);
            int[] array = new int[16];
            for (int i = 0; i < array.length; i++) {
                array[i] = route.get(i);
            }

            int fitness = fitnessEvaluator.calculateArrayFitness(array);
            if (fitness < best) {
                best = fitness;
                bestRoute = array;
                IO.println("Best: " + best + Arrays.toString(array));
            }
            ++count;
            if ((count % 100000000) == 0) {
                System.out.printf("%.4f %% done.\n", (((double) count / totalPermutations) * 100.0));
            }
        }

        IO.println("Best route: " + best + ", " + Arrays.toString(bestRoute));
        return bestRoute;
    }
}
