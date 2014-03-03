package net.dervism.tsp;

import net.dervism.genericalgorithms.BitChromosome;
import net.dervism.genericalgorithms.Population;

import java.util.*;

/**
 * Created by dervism on 14/02/14.
 */
public class TSPPopulation implements Population<BitChromosome> {

    private List<BitChromosome> population;

    private TSP tsp;

    public TSPPopulation(TSP tsp) {
        this.tsp = tsp;
    }

    @Override
    public List<BitChromosome> createPopulation(int size) {
        population = new ArrayList<>(size*4);

        for (int i = 0; i < size; i++) {
            BitChromosome bitChromosome = tsp.tspEncoder.createRandomChromosome(tsp.random);
            population.add(bitChromosome);
        }

        return population;
    }

    /**
     * Keep the best solutions, and remove the
     * bad ones.
     */
    public void selectBest(double selectionRate) {
        selectBest((int) (population.size() * selectionRate));
    }

    public synchronized void selectBest(int reduce) {
        sortByFitness();

        int newSize = population.size() - reduce;

        do {
            population.remove(population.size()-1);
        } while (population.size() > newSize);
    }


    /**
     * Divide population in two and select a parent
     * from the best group of chromosomes.
     *
     * @return
     */
    public synchronized BitChromosome[] selectParents(double selectionRate) {
        BitChromosome[] parents = new BitChromosome[2];

        int selection = (int) (population.size() * selectionRate);

        int mother = tsp.random.nextInt(selection);
        parents[0] = population.get(mother);

        int father = tsp.random.nextInt(selection);

        if (mother == father) {
            do {
                father = tsp.random.nextInt(selection);
            } while (mother == father);
        }
        parents[1] = population.get(father);

        return parents;
    }

    public BitChromosome[] selectParents() {
        return selectParents(0.8);
    }

    public void add(BitChromosome bitChromosome) {
        population.add(bitChromosome);
    }

    public BitChromosome getBest() {
        return population.get(0);
    }

    public void sortByFitness() {
        Collections.sort(population, new ChromosomeComparator());
    }

    public class ChromosomeComparator implements Comparator<BitChromosome> {
        @Override
        public int compare(BitChromosome o1, BitChromosome o2) {
            Integer fitness1 = calculateFitness(o1);
            Integer fitness2 = calculateFitness(o2);
            return fitness1.compareTo(fitness2);
        }
    }

    public int calculateFitness(BitChromosome bitChromosome) {
        int fitness = 0;

        long[] sequence = tsp.tspEncoder.toArray(bitChromosome);

        for (int i = 1; i < sequence.length; i++) {
            int cityFrom = (int)sequence[i-1];
            int cityTo = (int)sequence[i];
            fitness += tsp.distanceMatrix[cityFrom][cityTo];
        }

        // add distance back to start city
        int lastCity = (int)sequence[sequence.length-1];
        fitness += tsp.distanceMatrix[lastCity][(int)sequence[0]];

        return fitness;
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
            fitness += tsp.distanceMatrix[cityFrom][cityTo];
        }

        // add distance back to start city
        int lastCity = sequence[sequence.length-1];
        fitness += tsp.distanceMatrix[lastCity][sequence[0]];

        return fitness;
    }

    /**
     * Prints out stats about the population.
     *
     * @return
     */
    public String stat() {
        String ret = "";
        int c = 1;
        for (BitChromosome bitChromosome : population) {
            ret += c++ + " " + calculateFitness(bitChromosome) + ", ";
        }

        return ret;
    }

    public void setTsp(TSP tsp) {
        this.tsp = tsp;
    }

    @Override
    public int size() {
        return population.size();
    }
}
