package net.dervism.tsp;

import net.dervism.genericalgorithms.Chromosome;
import org.uncommons.maths.combinatorics.PermutationGenerator;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
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

    public void runRandomGreedyCrossOverGA(final int minutes) {
        final Random randomRate = new Random();

        // create initial population
        final List<Chromosome> population = tspPopulation.createPopulation(200);

        tspPopulation.sortByFitness();

        mutationRate = 100 - (int)(100 * 0.05);
        crossoverRate = 100 - (int)(100 * 0.95);

        new Thread(new Runnable() {
            int best = tspPopulation.calculateFitness(tspPopulation.getBest());
            int generation = 1;
            boolean next = true;

            long start = System.currentTimeMillis();
            long end = start + (minutes * 60000); // 4 minutes

            @Override
            public void run() {
                while(next && (System.currentTimeMillis() < end)) {

                    // generate next generation
                    // select two parents from among the best chromosomes
                    Chromosome[] parents = tspPopulation.selectParents();

                    // have sex and breed
                    int rate = randomRate.nextInt(100);
                    if (rate >= crossoverRate) {
                        // add random crossover childern from both parents
                        Chromosome child1 = tspEvolution.crossover(parents[0], parents[1]);
                        Chromosome child2 = tspEvolution.crossover(parents[1], parents[0]);
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
                    tspPopulation.selectBest();

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
                }

                best = tspPopulation.calculateFitness(tspPopulation.getBest());

                Chromosome bestRoute = tspPopulation.getBest();
                long[] cities = tspEncoder.toArray(bestRoute);

                System.out.println("Best score: " + best);
                System.out.println(Arrays.toString(cities));
            }
        }).run();

    }

    public void runRandomizedGA(final int minutes) {
        final Random randomRate = new Random();

        final int populationSize = 200;

        // create initial population
        final List<Chromosome> population = tspPopulation.createPopulation(populationSize);

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
                        Chromosome[] parents = tspPopulation.selectParents();

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
                    tspPopulation.selectBest();

                    int bestFromGeneration = tspPopulation.calculateFitness(tspPopulation.getBest());
                    if (bestFromGeneration < best) {
                        best = bestFromGeneration;
                        System.out.println("Found better route in generation " + generation + " with score " + best
                                + ", size " + population.size());
                    }
                    generation++;
                }

                best = tspPopulation.calculateFitness(tspPopulation.getBest());

                Chromosome bestRoute = tspPopulation.getBest();
                long[] cities = tspEncoder.toArray(bestRoute);

                System.out.println("Best score: " + best);
                System.out.println(Arrays.toString(cities));
            }
        }).run();

    }

    public void runBruteForceTSP() {
        int startCity = 0;
        Integer[] cities = new Integer[15];
        for (int i = 1; i <= cities.length; i++) {
            cities[i-1] = new Integer(i);
        }
        System.out.println(Arrays.toString(cities));

        PermutationGenerator<Integer> pg = new PermutationGenerator<>(cities);
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
                array[i] = route.get(0);
            }

            int fitness = tspPopulation.calculateArrayFitness(array);
            if (fitness < best) {
                best = fitness;
                bestRoute = array;
                System.out.println("Best: " + best + Arrays.toString(array));
            }
            ++count;
            if (count % 100000000 == 0) {
                System.out.println((((double)count / totalPermutations) * 100.0) + "% done.");
            }
        }

        System.out.println("Best route: " + best + ", " + Arrays.toString(bestRoute));

    }

}











