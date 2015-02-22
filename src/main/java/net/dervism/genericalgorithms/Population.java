package net.dervism.genericalgorithms;

import java.util.Comparator;
import java.util.List;

/**
 * Interface defining the methods needed to create a population of Chromosomes.
 *
 * Created by dervism on 14/02/14.
 */
public interface Population<T extends Chromosome> {

    public List<T> createPopulation(int size);

    public T[] selectParents(double selectionRate);

    public void selectBest(double reduce);

    public List<T> list();

    public int size();

    public void add(T chromosome);

    public T get(int index);

}
