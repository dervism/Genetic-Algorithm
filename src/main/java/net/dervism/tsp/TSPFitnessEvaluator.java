package net.dervism.tsp;

import net.dervism.genericalgorithms.BitChromosome;
import net.dervism.genericalgorithms.FitnessEvaluator;
import net.dervism.genericalgorithms.Population;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by dervism on 08/03/14.
 */
public class TSPFitnessEvaluator implements FitnessEvaluator<BitChromosome> {

    public int[][] distanceMatrix;

    private Random random;

    public TSPFitnessEvaluator() {
        this(16);
    }

    public TSPFitnessEvaluator(int cities) {
        distanceMatrix = new int[cities][cities];
        random = new Random(4682);
        createDistanceMatrix();
        hardcodeShortestRoute();
    }

    public void createDistanceMatrix() {

        // create random distances
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                distanceMatrix[i][j] = 10 + random.nextInt(100);
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
     * Use this method to verify the results of the GA algortihms in this class.
     * Hardcoding a shortes route here, among the millions of possible routes, will
     * tell if how good the algorithms preform. The best result here is 136.
     */
    public void hardcodeShortestRoute() {
        distanceMatrix[0][2] = 10;
        distanceMatrix[2][4] = 9;
        distanceMatrix[4][6] = 11;
        distanceMatrix[6][8] = 5;
        distanceMatrix[8][10] = 7;
        distanceMatrix[10][12] = 8;
        distanceMatrix[12][14] = 13;
        distanceMatrix[14][1] = 2;
        distanceMatrix[1][3] = 3;
        distanceMatrix[3][5] = 15;
        distanceMatrix[5][7] = 12;
        distanceMatrix[7][9] = 16;
        distanceMatrix[9][11] = 14;
        distanceMatrix[11][13] = 6;
        distanceMatrix[13][15] = 4;
        distanceMatrix[15][0] = 1;
    }

    @Override
    public int evalute(BitChromosome chromosome) {
        int fitness = 0;

        long[] sequence = chromosome.toArray();

        for (int i = 1; i < sequence.length; i++) {
            int cityFrom = (int)sequence[i-1];
            int cityTo = (int)sequence[i];
            fitness += distanceMatrix[cityFrom][cityTo];
        }

        // add distance back to start city
        int lastCity = (int)sequence[sequence.length-1];
        fitness += distanceMatrix[lastCity][(int)sequence[0]];

        return fitness;
    }

    @Override
    public int evalute(Population<BitChromosome> population) {
        sort(population);
        return evalute(getBest(population));
    }

    /**
     * Method to calculate distance without having a BitChromosome.
     * This can be used to brute force a solution.
     *
     * @param array
     * @return
     */
    public int calculateArrayFitness(int[] array) {
        int fitness = 0;

        int[] sequence = array;

        for (int i = 1; i < sequence.length; i++) {
            int cityFrom = sequence[i-1];
            int cityTo = sequence[i];
            fitness += distanceMatrix[cityFrom][cityTo];
        }

        // add distance back to start city
        int lastCity = sequence[sequence.length-1];
        fitness += distanceMatrix[lastCity][sequence[0]];

        return fitness;
    }

    @Override
    public BitChromosome getBest(Population<BitChromosome> population) {
        return population.get(0);
    }

    @Override
    public void sort(Population<BitChromosome> population) {
        Collections.sort(population.list(), new ChromosomeComparator());
    }

    public class ChromosomeComparator implements Comparator<BitChromosome> {
        @Override
        public int compare(BitChromosome o1, BitChromosome o2) {
            Integer fitness1 = evalute(o1);
            Integer fitness2 = evalute(o2);
            return fitness1.compareTo(fitness2);
        }
    }

    /**
     * Prints out stats about the population.
     *
     * @return
     */
    public String stat(List<BitChromosome> population) {
        String ret = "";
        int c = 1;
        for (BitChromosome chromosome : population) {
            ret += c++ + " " + evalute(chromosome) + ", ";
        }

        return ret;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}
