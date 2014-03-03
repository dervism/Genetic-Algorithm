package net.dervism.genericalgorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class defining a population.
 *
 * Created by dervism on 14/02/14.
 */
public abstract class ChromosomePopulation<T extends Chromosome> implements Population<T> {

    protected List<T> population;

    public ChromosomePopulation() {
        this.population = new ArrayList<>();
    }

    public abstract List<T> createPopulation(int size);

    @Override
    public synchronized void selectBest(int reduce) {
        sortByFitness();

        int newSize = population.size() - reduce;

        do {
            population.remove(population.size()-1);
        } while (population.size() > newSize);
    }

    @Override
    public T getBest() {
        return population.get(0);
    }

}
