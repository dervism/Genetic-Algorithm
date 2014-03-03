package net.dervism.genericalgorithms;

import java.util.List;

/**
 * Interface defining the methods needed to create a population of Chromosomes.
 *
 * Created by dervism on 14/02/14.
 */
public interface Population<T extends Chromosome> {

    public List<T> createPopulation(int size);

    public T[] selectParents(double selectionRate);

    public void sortByFitness();

    public int calculateFitness(T chromosome);

    public void selectBest(int reduce);

    public T getBest();

    public int size();

    public void add(T chromosome);

}
