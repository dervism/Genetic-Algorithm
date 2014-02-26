package net.dervism.tsp;

import net.dervism.genericalgorithms.BitChromosome;
import org.uncommons.maths.combinatorics.PermutationGenerator;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Created by dervism on 14/02/14.
 */
public class TSP {

    public int[][] distanceMatrix;

    private TSPPopulation tspPopulation;

    public TSPEvolution tspEvolution;

    public TSPEncoder tspEncoder;

    public SecureRandom random = new SecureRandom("random".getBytes());

    private int mutationRate;
    private int crossoverRate;

    public TSP() {
        distanceMatrix = new int[16][16];
        createDistanceMatrix();

        // mutation rate must be a number between 0.0 to 1.0
        // the higher the rate is, the more chromosomes will be mutated.
        mutationRate = 100 - (int)(100 * 0.01);
        crossoverRate = 100 - (int)(100 * 0.95);

        tspEncoder = new TSPEncoder();
        tspPopulation = new TSPPopulation(this);
        tspEvolution = new TSPEvolution(this);
    }

    public void createDistanceMatrix() {
        Random randomDistance = new Random(4682);

        // create random distances
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                distanceMatrix[i][j] = 10 + randomDistance.nextInt(100);
            }
        }

        System.out.println("\t ");

        // set distance from a city to itself to 0
        for (int i = 0; i < distanceMatrix.length; i++) {
            System.out.print((i == 0 ? " \t\t" : "") + i + "\t|\t");
            distanceMatrix[i][i] = 0;
        }

        System.out.println();

        for (int i = 0; i < distanceMatrix.length; i++) {
            System.out.print((i < 10 ? " ":"") + i + " | \t");
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                System.out.print(distanceMatrix[i][j] + "\t|\t");
            }
            System.out.println();
        }

    }

    /**
     * The strategy in this algorithm keeps the population size at the same
     * level without adding any random chromosomes in any cycle. Instead, a
     * crossover rate and a mutation rate is used to transform only the given
     * percentage of the population. The selection strategy simply removes as
     * many chromosomes that was added. This way, the population should get
     * fitter with every cycle.
     *
     * This implementation has shown that:
     *
     * - I can use less chromosomes (only 100 are created initially)
     * - I never add additional random chromosomes
     *
     * Best solutions so far:
     * Fitness: 303, route: [0, 12, 10, 8, 7, 6, 9, 14, 2, 13, 11, 3, 5, 4, 15, 1]
     *
     * @param minutes
     * @param crossoverRate
     * @param mutationRate
     */
    public  void evolutionaryGA(final int minutes, final double crossoverRate, final double mutationRate) {

        // create initial population
        final List<BitChromosome> population = tspPopulation.createPopulation(100);
        tspPopulation.sortByFitness();

        System.out.println(tspPopulation.stat());

        new Thread(new Runnable() {
            int best = tspPopulation.calculateFitness(tspPopulation.getBest());
            int generation = 1;

            long start = System.currentTimeMillis();
            long end = start + (minutes * 60000);

            @Override
            public void run() {
                while(System.currentTimeMillis() < end) {

                    // do crossover on the 'crossoverRate' % of the population
                    int crossovers = (int) (population.size() * crossoverRate);
                    for (int i = 0; i < crossovers; i++) {
                        BitChromosome[] parents = tspPopulation.selectParents(0.5);
                        BitChromosome child1 = tspEvolution.crossover(parents[0], parents[1]);
                        BitChromosome child2 = tspEvolution.crossover(parents[1], parents[0]);

                        population.add(child1); population.add(child2);
                    }

                    // do mutations on the 'mutationRate' % of the population
                    int mutations = (int) (population.size() * mutationRate);
                    for (int i = 0; i < mutations; i++) {
                        BitChromosome[] parents = tspPopulation.selectParents(1.0);
                        population.add(tspEvolution.arrayMutate(parents[0]));
                        population.add(tspEvolution.arrayMutate(parents[1]));
                    }

                    // select the best chromosomes from this generation
                    tspPopulation.selectBest( ((crossovers * 2) + (mutations * 2)) );

                    int bestFromGeneration = tspPopulation.calculateFitness(tspPopulation.getBest());
                    if (bestFromGeneration < best) {
                        best = bestFromGeneration;
                        System.out.println("Found better route in generation " + generation + " with score " + best
                                + ", population size " + population.size());
                    }

                    generation++;
                }

                best = tspPopulation.calculateFitness(tspPopulation.getBest());

                BitChromosome bestRoute = tspPopulation.getBest();
                long[] cities = tspEncoder.toArray(bestRoute);

                System.out.println("Best score: " + best);
                System.out.println(Arrays.toString(cities));

                System.out.println(tspPopulation.stat());
            }
        }).run();

    }

    /**
     * NB: Experimental version!
     * In this version of the TSP algorithm, I use the percentage rates to determine
     * if crossovers and mutations should occur or not. The strategy I use in this
     * version will always create a new generation where the children is some sort
     * of random variation of the best chromosomes from previous generation. After
     * selecting the best and reducing the population, random data are injected and
     * the process repeats.
     *
     * @param minutes Number of minutes to run the simulation
     */
    public void runRandomGreedyCrossOverGA(final int minutes) {
        final Random randomRate = new Random();

        // create initial population
        final List<BitChromosome> population = tspPopulation.createPopulation(200);

        tspPopulation.sortByFitness();

        // overriding the default values
        mutationRate = 100 - (int)(100 * 0.05);
        crossoverRate = 100 - (int)(100 * 0.95);

        new Thread(new Runnable() {
            int best = tspPopulation.calculateFitness(tspPopulation.getBest());
            int generation = 1;
            boolean next = true;

            long start = System.currentTimeMillis();
            long end = start + (minutes * 60000);
            double selection = 0.8;

            @Override
            public void run() {
                while(next && (System.currentTimeMillis() < end)) {

                    // generate next generation
                    // select two parents from among the best chromosomes
                    BitChromosome[] parents = tspPopulation.selectParents(selection);

                    // have sex and breed
                    int rate = randomRate.nextInt(100);
                    if (rate >= crossoverRate) {
                        // add random crossover childern from both parents
                        BitChromosome child1 = tspEvolution.crossover(parents[0], parents[1]);
                        BitChromosome child2 = tspEvolution.crossover(parents[1], parents[0]);
                        population.add(child1);
                        population.add(child2);

                        // add the best parent with a mutant
                        population.add(tspEvolution.crossover(tspPopulation.getBest(), tspEvolution.arrayMutate(parents[0])));
                        population.add(tspEvolution.crossover(tspPopulation.getBest(), tspEvolution.arrayMutate(parents[1])));

                        // cross the best parent with a mutated child
                        population.add(tspEvolution.crossover(tspPopulation.getBest(), tspEvolution.arrayMutate(child1)));
                        population.add(tspEvolution.crossover(tspPopulation.getBest(), tspEvolution.arrayMutate(child2)));

                        population.add(tspEvolution.crossover(tspEvolution.arrayMutate(child1), tspPopulation.getBest()));
                        population.add(tspEvolution.crossover(tspEvolution.arrayMutate(child2), tspPopulation.getBest()));
                    }

                    // mutate the parents, however this will not
                    // happend very often as the rate has to be very low.
                    rate = randomRate.nextInt(100);
                    if (rate >= mutationRate) {
                        // add mutaded parents
                        population.add(tspEvolution.arrayMutate(parents[0]));
                        population.add(tspEvolution.arrayMutate(parents[1]));
                    }

                    // select the best chromosomes from this generation
                    // we have to experiment with the selection percentage
                    tspPopulation.selectBest(0.3);

                    int bestFromGeneration = tspPopulation.calculateFitness(tspPopulation.getBest());
                    if (bestFromGeneration < best) {
                        best = bestFromGeneration;
                        System.out.println("Found better route in generation " + generation + " with score " + best
                                + ", population size " + population.size());
                    }
                    generation++;

                    // grow population to avoid convergence to local minima
                    int grow = 150;
                    while (population.size() < grow) population.add(tspEncoder.createRandomChromosome(random));
                    tspPopulation.sortByFitness();
                }

                best = tspPopulation.calculateFitness(tspPopulation.getBest());

                BitChromosome bestRoute = tspPopulation.getBest();
                long[] cities = tspEncoder.toArray(bestRoute);

                System.out.println("Best score: " + best);
                System.out.println(Arrays.toString(cities));

                System.out.println(tspPopulation.stat());
            }
        }).run();
    }

    /**
     * This version does not use the suggested rates to do crossovers or mutations,
     * instead a crossover, a mutant and two randon chromosomes are added until
     * the new generation is created (that is, the population size is at the initial
     * level). When the generation is created, sort and select the best chromosomes
     * and repeat the process.
     *
     * @param minutes
     */
    public void runRandomizedGA(final int minutes) {
        final Random randomRate = new Random();

        final int populationSize = 200;

        // create initial population
        final List<BitChromosome> population = tspPopulation.createPopulation(populationSize);

        tspPopulation.sortByFitness();

        new Thread(new Runnable() {
            int best = tspPopulation.calculateFitness(tspPopulation.getBest());
            int generation = 1;
            boolean next = true;

            long start = System.currentTimeMillis();
            long end = start + (minutes * 60000);

            @Override
            public void run() {
                while(next && (System.currentTimeMillis() < end)) {

                    // create a new generation
                    while (population.size() < populationSize) {
                        // velg to foreldre
                        BitChromosome[] parents = tspPopulation.selectParents();

                        // one crossover child from each parent
                        population.add(tspEvolution.crossover(parents[0], parents[1]));
                        population.add(tspEvolution.crossover(parents[1], parents[0]));

                        // two random childern
                        population.add(tspEncoder.createRandomChromosome(random));
                        population.add(tspEncoder.createRandomChromosome(random));

                        // the best from previous generation, but randomized
                        population.add(tspEvolution.arrayMutate(tspPopulation.getBest()));
                    }

                    // select the best
                    tspPopulation.selectBest(0.3);

                    int bestFromGeneration = tspPopulation.calculateFitness(tspPopulation.getBest());
                    if (bestFromGeneration < best) {
                        best = bestFromGeneration;
                        System.out.println("Found better route in generation " + generation + " with score " + best
                                + ", size " + population.size());
                    }
                    generation++;
                }

                best = tspPopulation.calculateFitness(tspPopulation.getBest());

                BitChromosome bestRoute = tspPopulation.getBest();
                long[] cities = tspEncoder.toArray(bestRoute);

                System.out.println("Best score: " + best);
                System.out.println(Arrays.toString(cities));
            }
        }).run();

    }

    /**
     * Brute-Force algorithm to find the best solution. However,
     * for the problem of TSP with 16 cities and 1,307,674,368,000 possible
     * solutions this process will take nearly two days to complete.
     * This implementation is based on the WatchMaker code
     * (http://watchmaker.uncommons.org/).
     */
    public void runBruteForceTSP() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int startCity = 0;
                Integer[] cities = new Integer[15];
                Integer[] cities2 = {12, 10, 8, 7, 6, 9, 14, 2, 13, 11, 3, 5, 4, 15, 1};
                for (int i = 1; i <= cities.length; i++) {
                    cities[i-1] = new Integer(i);
                }
                System.out.println(Arrays.toString(cities2));

                PermutationGenerator<Integer> pg = new PermutationGenerator<>(cities2);
                long totalPermutations = pg.getTotalPermutations();
                System.out.println("Total permutations: " + totalPermutations);

                int best = Integer.MAX_VALUE;
                int[] bestRoute = null;
                long count = 0;

                while (pg.hasMore()) {
                    List<Integer> route = pg.nextPermutationAsList();
                    route.add(0, startCity);
                    int[] array = new int[16];
                    for (int i = 0; i < array.length; i++) {
                        array[i] = route.get(i);
                    }

                    int fitness = tspPopulation.calculateArrayFitness(array);
                    if (fitness < best) {
                        best = fitness;
                        bestRoute = array;
                        System.out.println("Best: " + best + Arrays.toString(array));
                    }
                    ++count;
                    if ((count % 100000000) == 0) {
                        System.out.printf("%.4f %% done.\n", (((double) count / totalPermutations) * 100.0));
                    }
                }

                System.out.println("Best route: " + best + ", " + Arrays.toString(bestRoute));
            }
        }).run();

    }

}
