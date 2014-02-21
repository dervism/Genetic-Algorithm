package net.dervism.tsp;

import net.dervism.genericalgorithms.Chromosome;
import net.dervism.genericalgorithms.Population;

import java.util.*;

/**
 * Created by dervism on 14/02/14.
 */
public class TSPPopulation implements Population {

    private List<Chromosome> population;

    private TSP tsp;

    public TSPPopulation(TSP tsp) {
        this.tsp = tsp;
    }

    @Override
    public List<Chromosome> createPopulation(int size) {
        population = new ArrayList<Chromosome>(size*4);

        for (int i = 0; i < size; i++) {
            Chromosome chromosome = tsp.tspEncoder.createRandomChromosome(tsp.random);
            population.add(chromosome);
        }

        return population;
    }

    /**
     * Keep the best solutions, and remove the
     * bad ones.
     */
    public void selectBest() {

        // you have to experiment this ratio
        // here, I select only the top 30% of the population
        int reduceSize = (int) (population.size() * 0.3);

        sortByFitness();

        int newSize = population.size() - reduceSize;

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
    public Chromosome[] selectParents() {
        Chromosome[] parents = new Chromosome[2];

        int selection = (int) (population.size() * 0.8);

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

    public void add(Chromosome chromosome) {
        population.add(chromosome);
    }

    public Chromosome getBest() {
        return population.get(0);
    }

    public void sortByFitness() {
        Collections.sort(population, new ChromosomeComparator());
    }

    public class ChromosomeComparator implements Comparator<Chromosome> {
        @Override
        public int compare(Chromosome o1, Chromosome o2) {
            Integer fitness1 = calculateFitness(o1);
            Integer fitness2 = calculateFitness(o2);
            return fitness1.compareTo(fitness2);
        }
    }

    public int calculateFitness(Chromosome chromosome) {
        int fitness = 0;

        long[] sequence = tsp.tspEncoder.toArray(chromosome);

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
     * Method to calculate distance without having a Chromosome.
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
        for (Chromosome chromosome : population) {
            ret += c++ + " " + calculateFitness(chromosome) + "\n";
        }

        return ret;
    }

    public void setTsp(TSP tsp) {
        this.tsp = tsp;
    }

}
